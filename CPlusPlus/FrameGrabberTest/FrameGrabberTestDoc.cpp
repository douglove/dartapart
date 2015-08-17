// FrameGrabberTestDoc.cpp : implementation of the CFrameGrabberTestDoc class
//
// (c) Vadim Gorbatenko, 1999 
// gvv@mail.tomsknet.ru
// All rights reserved
//

#include "stdafx.h"
#include "math.h"
#include "FrameGrabberTest.h"
#include "ImageUtil.h"
#include "DartDataServer.h"
#include <iostream>

#include "FrameGrabberTestDoc.h"

#ifdef _DEBUG
#define new DEBUG_NEW
#undef THIS_FILE
static char THIS_FILE[] = __FILE__;
#endif

#define	PROCESSOR_PAUSED			0
#define	PROCESSOR_SIMPLE_VIEWER		1
#define	PROCESSOR_IMAGE_FILTER		2
#define	PROCESSOR_MOTION_DETECTOR	3

//forward definition for motion detector
LONG	summ_rect_arena32(LPBYTE data, int dx, int dy, int ptX, int ptY, int rx, int ry);
LONG	summ_rect_arena24(LPBYTE data, int dx, int dy, int ptX, int ptY, int rx, int ry);
/////////////////////////////////////////////////////////////////////////////
// CFrameGrabberTestDoc

IMPLEMENT_DYNCREATE(CFrameGrabberTestDoc, CDocument)

BEGIN_MESSAGE_MAP(CFrameGrabberTestDoc, CDocument)
	//{{AFX_MSG_MAP(CFrameGrabberTestDoc)
	ON_COMMAND(ID_EDIT_COPY, OnEditCopy)
	ON_UPDATE_COMMAND_UI(ID_EDIT_COPY, OnUpdateEditCopy)
	ON_UPDATE_COMMAND_UI(ID_SIMPLE_FILTER, OnUpdateSimpleFilter)
	ON_UPDATE_COMMAND_UI(ID_SIMPLE_VIEWER, OnUpdateSimpleViewer)
	ON_UPDATE_COMMAND_UI(ID_DETECTOR, OnUpdateDetector)
	ON_COMMAND(ID_DETECTOR, OnDetector)
	ON_COMMAND(ID_SIMPLE_FILTER, OnSimpleFilter)
	ON_COMMAND(ID_SIMPLE_VIEWER, OnSimpleViewer)
	ON_COMMAND(ID_FILE_SAVE_AS, OnFileSaveAs)
	ON_UPDATE_COMMAND_UI(ID_FILE_SAVE_AS, OnUpdateFileSaveAs)
	ON_COMMAND(ID_PAUSE, OnPause)
	ON_UPDATE_COMMAND_UI(ID_PAUSE, OnUpdatePause)
	//}}AFX_MSG_MAP
END_MESSAGE_MAP()

/////////////////////////////////////////////////////////////////////////////
// CFrameGrabberTestDoc construction/destruction

CFrameGrabberTestDoc::CFrameGrabberTestDoc()
{
	m_pDataServer = NULL;
	m_CalibrateScanLine = false;
	m_CalibrateBackground = false;
	m_iPrevDartsFound = 0;
	m_iBackgroundCount = 0;
	m_dartLocations[0] = 0;
	m_dartLocations[1] = 0;
	m_dartLocations[2] = 0;

	m_iDefaultScanLine = 120;
	m_iThreshold = 50;
	m_iThresholdBG = 150;

	m_bClearWatch = false;

	for (int x = 0; x < 640; x++) {
		m_backgroundLine[x] = 0;
	}
}

CFrameGrabberTestDoc::~CFrameGrabberTestDoc()
{
}


UINT startSocket( LPVOID pParam )
{
    DartDataServer * pDataServer = (DartDataServer *) pParam;
	pDataServer->runSocket();
	return 0;   // thread completed successfully
}


BOOL CFrameGrabberTestDoc::OnNewDocument()
{
	if (!CDocument::OnNewDocument())
		return FALSE;

	m_ProcessorMode = PROCESSOR_SIMPLE_VIEWER;

	return TRUE;
}

/////////////////////////////////////////////////////////////////////////////
// CFrameGrabberTestDoc diagnostics

#ifdef _DEBUG
void CFrameGrabberTestDoc::AssertValid() const
{
	CDocument::AssertValid();
}

void CFrameGrabberTestDoc::Dump(CDumpContext& dc) const
{
	CDocument::Dump(dc);
}
#endif //_DEBUG

/////////////////////////////////////////////////////////////////////////////
// CFrameGrabberTestDoc commands

void CFrameGrabberTestDoc::OnEditCopy() 
{
	if(m_ImageBitmap.GetSafeHandle() && 
		AfxGetMainWnd()->OpenClipboard())
	{
		EmptyClipboard();
		SetClipboardData(CF_DIB, m_ImageBitmap.DibFromBitmap());
		CloseClipboard();
	}
	
}

void CFrameGrabberTestDoc::OnUpdateEditCopy(CCmdUI* pCmdUI) 
{
		pCmdUI->Enable((BOOL)m_ImageBitmap.GetSafeHandle());
}

#define	IMAGEWIDTH(lpd) ((LPBITMAPINFOHEADER)lpd)->biWidth
#define	IMAGEHEIGHT(lpd) ((LPBITMAPINFOHEADER)lpd)->biHeight
#define	IMAGEBITS(lpd) ((LPBITMAPINFOHEADER)lpd)->biBitCount
#define	IMAGEDATA(lpd) (((LPBYTE)lpd) + (((LPBITMAPINFOHEADER)lpd)->biSize))

// This method called by CFrameGrabberTestView
// 
void	CFrameGrabberTestDoc::ProcessImage(LPBITMAPINFO lpBi)
{
	static BOOL bRunNow = FALSE;
	if(!lpBi || bRunNow)	return;
	bRunNow = TRUE;
	switch(m_ProcessorMode)
	{
	case PROCESSOR_SIMPLE_VIEWER:
				m_ImageBitmap.CreateFromDib(lpBi);
				UpdateAllViews(NULL);	break;
	case PROCESSOR_IMAGE_FILTER:
				if(IMAGEBITS(lpBi)!=32&&
					IMAGEBITS(lpBi)!=24)
				{
					AfxMessageBox("Can't run filter: unsupported color resolution!",MB_ICONWARNING);
					m_ProcessorMode =PROCESSOR_SIMPLE_VIEWER;
					break;
				}

				if(ApplyFilter(lpBi))
				{	m_ImageBitmap.CreateFromDib(lpBi);
					UpdateAllViews(NULL);}	break;
	case PROCESSOR_MOTION_DETECTOR:
				if(IMAGEBITS(lpBi)!=32&&
					IMAGEBITS(lpBi)!=24)
				{
					AfxMessageBox("Can't run motion detector: unsupported color resolution!",MB_ICONWARNING);
					m_ProcessorMode =PROCESSOR_SIMPLE_VIEWER;
					break;
				}
				if(RunDetector(lpBi))
				{	m_ImageBitmap.CreateFromDib(lpBi);
					UpdateAllViews(NULL);}	break;
	default:	
					break;
	}
	bRunNow = FALSE;
}

void CFrameGrabberTestDoc::OnUpdateSimpleFilter(CCmdUI* pCmdUI) 
{
	pCmdUI->SetCheck(m_ProcessorMode==PROCESSOR_IMAGE_FILTER);
	
}

void CFrameGrabberTestDoc::OnUpdateSimpleViewer(CCmdUI* pCmdUI) 
{
	pCmdUI->SetCheck(m_ProcessorMode==PROCESSOR_SIMPLE_VIEWER);
	
}

void CFrameGrabberTestDoc::OnUpdateDetector(CCmdUI* pCmdUI) 
{
	pCmdUI->SetCheck(m_ProcessorMode==PROCESSOR_MOTION_DETECTOR);
	
}

void CFrameGrabberTestDoc::OnDetector() 
{
	m_ProcessorMode=PROCESSOR_MOTION_DETECTOR;
}

void CFrameGrabberTestDoc::OnSimpleFilter() 
{
	m_ProcessorMode=PROCESSOR_IMAGE_FILTER;
	
}

void CFrameGrabberTestDoc::OnSimpleViewer() 
{
	m_ProcessorMode=PROCESSOR_SIMPLE_VIEWER;
}

void CFrameGrabberTestDoc::OnFileSaveAs() 
{
	UINT oldp=m_ProcessorMode;

	m_ProcessorMode=PROCESSOR_PAUSED;

	CDC *pDC=m_ImageBitmap.BegingModify();	
		pDC->SetTextColor(196);
		pDC->SetBkMode(TRANSPARENT);
		CSize sz=m_ImageBitmap.GetSize();
		char str[32];
		char str2[32];
		CString title;
	
		title.Format("REC %s %s", _strtime(str),_strdate(str2));;
		pDC->TextOut(16,sz.cy-16, title);
	m_ImageBitmap.EndModify();

	m_ImageBitmap.Save(NULL);
	m_ProcessorMode=oldp;
}

void CFrameGrabberTestDoc::OnUpdateFileSaveAs(CCmdUI* pCmdUI) 
{
	pCmdUI->Enable((BOOL)m_ImageBitmap.GetSafeHandle());
	
}

void CFrameGrabberTestDoc::OnPause() 
{
	m_ProcessorMode=PROCESSOR_PAUSED;
	
}

void CFrameGrabberTestDoc::OnUpdatePause(CCmdUI* pCmdUI) 
{
	pCmdUI->SetCheck(m_ProcessorMode==PROCESSOR_PAUSED);
}

//////////////////////////////////////////////////////////
// Processor stuff
//
//
BOOL CFrameGrabberTestDoc::ApplyFilter(LPBITMAPINFO lpBi)
{

		ASSERT(lpBi);

		int arena_dx = IMAGEWIDTH(lpBi);
		int arena_dy = IMAGEHEIGHT(lpBi);

		//
		// This is the simplest Edge detector
		// Pixel(x0,y0)	= (abs(Pixel(x0,y0)-Pixel(x+1,y+1)) + abs(Pixel(x+1,y0)-Pixel(x0,y+1)))/2
		//

		switch(IMAGEBITS(lpBi))
		{
		case 32:
			{
				LONG * ptr =(LONG *)IMAGEDATA(lpBi);
				LONG * tmpLine = new LONG[arena_dx];

				for(int y=0; y<arena_dy-1;y++)
				{

					for(int x=0; x<arena_dx-1;x++)
					{
						int x1y1=x+arena_dx+1;
						int x1y0 =x+1;
						int x0y1=x+arena_dx;
						LONG r_diag1 = 	abs(ptr[x]&0xff -ptr[x1y1]&0xff);
						LONG g_diag1 = 	abs((ptr[x]>>8)&0xff - (ptr[x1y1]>>8)&0xff);
						LONG b_diag1 = 	abs((ptr[x]>>16)&0xff -(ptr[x1y1]>>16)&0xff);

						LONG r_diag2 = 	abs(ptr[x1y0]&0xff -ptr[x0y1]&0xff);
						LONG g_diag2 = 	abs((ptr[x1y0]>>8)&0xff -(ptr[x0y1]>>8)&0xff);
						LONG b_diag2 = 	abs((ptr[x1y0]>>16)&0xff -(ptr[x0y1]>>16)&0xff);
						
						tmpLine[x] = ((r_diag1+r_diag2)>>1) |
									 (((g_diag1+g_diag2)>>1)<<8) |
									 (((b_diag1+b_diag2)>>1)<<16);


					}
					memcpy(ptr,tmpLine, (arena_dx-1)*sizeof(DWORD));
					ptr+=arena_dx;
				}
				
				delete tmpLine;
			}
			break;
		case 24:
			{
				ImageUtil util;
				BYTE * ptr =(BYTE *)IMAGEDATA(lpBi);
				BYTE ptrResult[640];



                /** 
				 * Calibrate the scan line
				 **/
				if (! m_CalibrateScanLine) 
				{
					bool bFound = false;
					for (int i = m_iDefaultScanLine; i < 480; i++) {
						util.convertBlackWhite(ptr, i, m_iThreshold, ptrResult);
						if (util.isAll(ptrResult, 1)) {
							m_iScanLine = i + 1;
							bFound = true;
							break;
						}
					}
					if (! bFound) {
						m_iScanLine = m_iDefaultScanLine;
					}

					int yMax = 0;
					int iNoiseSpacer = 6;
					for (int x = 0; x < 640; x++) {
						for (int y = m_iScanLine; y > 0; y--) {
							int color = util.convertBlackWhite(ptr, x, y, m_iThreshold);
							if (color == 0) { // black pixel
								m_scanLineY[x] = y + iNoiseSpacer;
								if (m_scanLineY[x] > yMax) {
									yMax = m_scanLineY[x];
								}
								break;
							}
						}
					}

					for (int x = 0; x < 640; x++) {
						//m_scanLineY[x] = yMax;
						if (yMax - m_scanLineY[x] > 3) {
							m_scanLineY[x] = yMax - 3;
						}
					}
					m_CalibrateScanLine = true;

				} else if (! m_CalibrateBackground) {

					//if (m_iBackgroundCount == 0) {
					//	//AfxMessageBox("Starting background calibration");
					//}

					//int iBackgroundFrames = 25;
					//m_iBackgroundCount++;

					//for (int x = 0; x < 640; x++) {
					//	int avgColor = util.getAvgColor(ptr, x, m_scanLineY[x]);
					//	m_backgroundLine[x] += avgColor;
					//}

					//if (m_iBackgroundCount == iBackgroundFrames) 
					//{
					//	CString msg;
					//	for (int x = 0; x < 640; x++) {
					//		m_backgroundLine[x] /= iBackgroundFrames;	
					//	}

					//	m_CalibrateBackground = true;
					//	//AfxMessageBox("Ending background calibration");

					//	m_pDataServer = new DartDataServer();
					//	AfxBeginThread(startSocket, m_pDataServer);
					//}


					int iBackgroundFrames = 25;
					m_iBackgroundCount++;

					if (m_iBackgroundCount < iBackgroundFrames) {
						if (m_iBackgroundCount == 1) {
							m_iThreshold = 255;
						}


						for (int x = 0; x < 640; x++) {
							int avgColor = util.getAvgColor(ptr, x, m_scanLineY[x]);
							if (avgColor < m_iThreshold) {
								m_iThreshold = avgColor;
							}
						}

					} else {
						m_iThreshold -= 25 ;
						CString s;
						s.Format("Threshold: %d", m_iThreshold);
						AfxMessageBox(s);
						m_CalibrateBackground = true;
						m_pDataServer = new DartDataServer();
						AfxBeginThread(startSocket, m_pDataServer);
					}

				} else {

					/** 
					* Analyze the image
					**/
					if (false) {
						util.convertBlackWhite(ptr, m_scanLineY, m_iThresholdBG, m_backgroundLine, ptrResult);
					} else {
						//for (int i = 0; i < 480; i++) {
						//	util.convertBlackWhite(ptr, i, iThreshold, ptrResult);
						//}
						util.convertBlackWhite(ptr, m_scanLineY, m_iThreshold, ptrResult);					
					}

					bool bNewFound = false;
					int dartsFound = 0;
					int blackRun = 0;

					for (int i = 0; i < 640; i++) 
					{
						if (ptrResult[i] == 0) {
							blackRun++;
						} else if (blackRun > 0) {
							
							bool skip = false;
							if (i+1 < 640) {
								if (ptrResult[i+1] == 0) {
									blackRun++;
									skip = true;
								}

								if (!skip && i+2 < 640) {
									if (ptrResult[i+2] == 0) {
										blackRun++;
										skip = true;
									}
								}
							}
							
							if (skip) 
								continue;

							dartsFound++;

							if (m_iPrevDartsFound < 3) {
								
								int dartPixel = (i - (blackRun / 2));
								float dartPercent = dartPixel / 640.0f;

								bool bAlreadyTracked = false;
								for (int j = 0; j < m_iPrevDartsFound; j++) {
									double diff = fabs(dartPercent - m_dartLocations[j]);
									if (diff < .003) {
										bAlreadyTracked = true;
										break;
									}
								}

								if (! bAlreadyTracked) {
									m_dartLocations[m_iPrevDartsFound] = dartPercent;
									m_iPrevDartsFound++;
									bNewFound = true;
								}
							}	

							blackRun = 0;
						}
					}
		
					for (int j = 0; j < m_iPrevDartsFound; j++) {
						util.drawLine(ptr, m_dartLocations[j] * 640, 0, m_dartLocations[j] * 640, m_scanLineY[(int) (m_dartLocations[j] * 640)] - 3, 255, 0, 0);
					}

					if (dartsFound == 0 && m_iPrevDartsFound > 0) {
						m_dartLocations[0] = 0;
						m_dartLocations[1] = 0;
						m_dartLocations[2] = 0;
						//bNewFound = true;
						m_iPrevDartsFound = 0;
						m_bClearWatch = true;
					}

					if (m_bClearWatch && dartsFound == 0) {
						bNewFound = true;
						m_bClearWatch = false;
					}

					if (bNewFound)
					{
						if (m_pDataServer != NULL) {
							m_pDataServer->sendDarts(m_dartLocations);
						}
					}



					// Draw center line
					//util.drawVertLine(ptr, 320, 0, 255, 0);

					// Draw scan line
					//for (int x = 0; x < 640; x++) {
					//	util.setPixel(ptr, x, m_scanLineY[x] + 1, 0, 150, 0);
					//	util.setPixel(ptr, x, m_scanLineY[x] - 1, 0, 150, 0);
					//}		
					//util.drawHorzLine(ptr, m_iScanLine, 0, 150, 0);
				}
			}
		default: break;
		}
	return TRUE;
}

//Simplest Motion detector
BOOL CFrameGrabberTestDoc::RunDetector(LPBITMAPINFO lpBi)
{

	//constants definitions for motion detector
#define	ZONESX	4
#define	ZONESY	4
#define	ZONES	ZONESX*ZONESY
#define	DETECTION_LEVEL 0.05f

	static LONG lastIntensity[ZONES];
	static BOOL init = TRUE;

	LONG newIntensity[ZONES];

	int rx = IMAGEWIDTH(lpBi)/ZONESX;
	int ry = IMAGEHEIGHT(lpBi)/ZONESY;

	for(int y = 0; y<ZONESY; y++)
		for(int x = 0; x<ZONESX; x++)
			if(IMAGEBITS(lpBi)==32)
			 newIntensity[y*ZONESX+x] = summ_rect_arena32(  IMAGEDATA(lpBi),
															IMAGEWIDTH(lpBi),
															IMAGEHEIGHT(lpBi),
															x*rx, y*ry, rx, ry);
			else
			if(IMAGEBITS(lpBi)==24)
			 newIntensity[y*ZONESX+x] = summ_rect_arena24(IMAGEDATA(lpBi),
															IMAGEWIDTH(lpBi),
															IMAGEHEIGHT(lpBi),
															x*rx, y*ry, rx, ry);
			else return FALSE;

	BOOL ret=FALSE;
	if(!init)
	{
		FLOAT lastRel[ZONES-1];
		FLOAT newRel[ZONES-1];
		
		for(int i=0; i<ZONES-1; i++)
			lastRel[i] = (float)lastIntensity[i]/(float)(lastIntensity[i+1]+1);

		for(i=0; i< ZONES-1; i++)
		{
			newRel[i]= (float)newIntensity[i]/(float)(newIntensity[i+1]+1);
			
			float alarm = (float)fabs(lastRel[i]-newRel[i])/newRel[i];

			if(alarm >DETECTION_LEVEL)
				{ret=TRUE; break;}
		}

	
	}
	memcpy(lastIntensity,newIntensity, ZONES*sizeof(LONG));
	init = FALSE;
	return ret;
}

//some detectors stuff
LONG	summ_rect_arena32(LPBYTE data, int dx, int dy, int ptX, int ptY, int rx, int ry)
{
	LONG summ = 0;
	int lineBytes = dx*4;

	data+= (lineBytes*ptY + ptX*4);//offset

	for(int y = 0; y<ry; y++, data+=lineBytes)
		for(int x = 0; x< rx*4; x+=4)
		{
			summ+= data[x+1];
			summ+= data[x+2];
			summ+= data[x+3];
		}
	summ/=3;
	return summ;
}

LONG	summ_rect_arena24(LPBYTE data, int dx, int dy, int ptX, int ptY, int rx, int ry)
{
	LONG summ = 0;
	int lineBytes = dx*3;

	data+= (lineBytes*ptY + ptX*3);//offset

	for(int y = 0; y<ry; y++, data+=lineBytes)
		for(int x = 0; x< rx*3; x+=3)
		{
			summ+= data[x+0];
			summ+= data[x+1];
			summ+= data[x+2];
		}
	summ/=3;
	return summ;
}

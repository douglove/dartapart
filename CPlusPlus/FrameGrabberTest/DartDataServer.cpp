#include "stdafx.h"
#include ".\dartdataserver.h"

DartDataServer::DartDataServer(void)
{
	AfxSocketInit();
	m_bRunning = true;
	m_bClientConnected = false;
	m_bAnalyzerConnected = false;
}

DartDataServer::~DartDataServer(void)
{
}

void DartDataServer::runSocket()
{
	m_serverSocket = new CSocket();
	if (! m_serverSocket->Create(12000)) {
		if (! m_serverSocket->Create(12001)) {
			AfxMessageBox("Unable to create server socket!");
		}
	}

	if (m_serverSocket->Listen()) 
	{
		while (m_bRunning) 
		{
			m_clientSocket = new CSocket();
			if (m_serverSocket->Accept(*m_clientSocket)) 
			{
				//AfxMessageBox("server accepted connection");

				m_hClientSocket = m_clientSocket->Detach();
                m_bClientConnected = true;
				m_bAnalyzerConnected = false;
			}
		}
	}
}

void DartDataServer::sendDarts(float * darts)
{
	
	if (m_bClientConnected) 
	{
		if (! m_bAnalyzerConnected)
		{
			m_clientSocketAnalyzer = new CSocket();
			m_clientSocketAnalyzer->Attach(m_hClientSocket);
			m_bAnalyzerConnected = true;
		}

		CString sData;
		sData.Format("%f\r\n%f\r\n%f\r\n\r\n", darts[0], darts[1], darts[2]);
		int rc = m_clientSocketAnalyzer->Send(sData.GetBuffer(), sData.GetLength());

		if (rc == SOCKET_ERROR )
        {
            int iErr = ::GetLastError();
			CString err;
			err.Format("rc = %d, err = %d", rc, iErr);
			//AfxMessageBox(err);
            TRACE("rc = %d, err = %d", rc, iErr);
			m_bClientConnected = false;
			m_bAnalyzerConnected = false;

		}
	}
}
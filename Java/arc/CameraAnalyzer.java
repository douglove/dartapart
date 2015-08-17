package net.magicstudios.jdart;

import java.awt.*;
import java.awt.image.*;

import javax.swing.*;

import net.magicstudios.jdart.util.*;
import java.util.*;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author Nick Cramer (d3k199)
 * @version 1.0
 */
public class CameraAnalyzer extends JPanel implements Runnable {

    public int [] PIXEL_WHITE = new int[] {255, 255, 255, 255};
    public int [] PIXEL_BLACK = new int[] {0, 0, 0, 255};

    private volatile int m_iScanLineTop = 240;
    private volatile int m_iScanLineBottom = 240;
    private volatile int m_iTolerance = 75;
    private volatile boolean m_bRunning = true;
    private volatile boolean m_bProcess = false;
    private volatile boolean m_bCalibrate = false;
    private volatile boolean m_bCenterLine = false;
    private int [] m_iDartTop = new int[3];
    private int [] m_iDartBot = new int[3];
    private int m_iDartNumber = 0;
    private FrameGrabber mGrabber = null;
    private Image mFrame = null;
    private Boolean mLock = new Boolean(true);
    private int iPrevDartCount = 0;

    public CameraAnalyzer() {
    }

    public void setTolerance(int t) {
        m_iTolerance = t;
    }

    public void setScanLineTop(int x) {
        m_iScanLineTop = x;
    }

    public void setScanLineBottom(int x) {
        m_iScanLineBottom = x;
    }


    public int getTolerance() {
        return m_iTolerance;
    }

    public int getScanLineTop() {
        return m_iScanLineTop;
    }

    public int getScanLineBottom() {
        return m_iScanLineBottom;
    }

    public void setProcessImage(boolean b) {
        m_bProcess = b;
    }

    public double [] getDarts() {
        double [] percents = new double[3];
        for (int i = 0; i < m_iDartBot.length; i++) {
            percents[i] = m_iDartBot[i] / 640.0;
        }
        return percents;
    }


    public void processFrame() {

        if (mFrame != null) {
//            synchronized (mLock) {

                BufferedImage image = new BufferedImage(640, 480, BufferedImage.TYPE_INT_ARGB_PRE);
                Graphics2D gProc = (Graphics2D) image.getGraphics();
                gProc.drawImage(mFrame, 0, 0, null);

                int iDartsFound = 0;

                if (m_bProcess || m_bCalibrate) {

                  // ********** calibrate **********************
                    if (m_bCalibrate) {

                        WritableRaster raster = image.getRaster();
                        int width = raster.getWidth();
                        int height = raster.getHeight();

                        if (true) {
                            int[] pixel = new int[4];
                            for (int i = 0; i < width; i++) {
                                for (int j = 0; j < height; j++) {
                                    raster.getPixel(i, j, pixel);
                                    if ((pixel[0] + pixel[1] + pixel[2]) / 3 > m_iTolerance) {
                                        raster.setPixel(i, j, PIXEL_WHITE);
                                    } else {
                                        raster.setPixel(i, j, PIXEL_BLACK);
                                    }
                                }
                            }
                        }

                        System.out.println("calibrating");
                        int[] pixel = new int[4];
                        for (int y = (int) (height * .5) - 1; y >= 0; y--) {
                            boolean bLineWhite = true;
                            for (int x = 0; x < width; x++) {
                                raster.getPixel(x, y, pixel);
                                if (pixel[0] == 0) {
                                    bLineWhite = false;
                                    break;
                                }
                            }

                            if (bLineWhite) {
                                System.out.println("white found");
                                m_iScanLineBottom = y - 3;
                                break;
                            }
                        }

                        for (int y = m_iScanLineBottom - 1; y >= 0; y--) {
                            boolean bLineWhite = true;
                            for (int x = 0; x < width; x++) {
                                raster.getPixel(x, y, pixel);
                                if (pixel[0] == 0) {
                                    bLineWhite = false;
                                    break;
                                }
                            }

                            if (!bLineWhite) {
                                m_iScanLineTop = y + 2;
                                break;
                            }
                        }
                        m_bCalibrate = false;
                    }
                    // ********** end calibrate **********************

                    // ********** find darts **********************


                    WritableRaster raster = image.getRaster();
                    int width = raster.getWidth();
                    int height = raster.getHeight();

                    int[] pixel = new int[4];
                    for (int i = 0; i < width; i++) {
                        raster.getPixel(i, m_iScanLineBottom, pixel);
                        if ((pixel[0] + pixel[1] + pixel[2]) / 3 > m_iTolerance) {
                            raster.setPixel(i, m_iScanLineBottom, PIXEL_WHITE);
                        } else {
                            raster.setPixel(i, m_iScanLineBottom, PIXEL_BLACK);
                        }
                    }


                    int blackRun = 0;

                    //                    for (int i = 0; i < width; i++) {
                    //                        raster.getPixel(i, m_iScanLineTop, pixel);
                    //                        System.out.print((pixel[0] == 0) ? 0 : 1);
                    //                    }
                    //                    System.out.println();

                    for (int i = 0; i < width; i++) {
                        raster.getPixel(i, m_iScanLineBottom, pixel);
                        if (pixel[0] == 0) {
                            blackRun++;
                        } else if (blackRun > 0) {

                            raster.getPixel(i + 1, m_iScanLineBottom, pixel);
                            if (pixel[0] == 0) {
                                //System.out.println("look ahead 1 pixel");
                                blackRun++;
                                continue;
                            }

                            raster.getPixel(i + 2, m_iScanLineBottom, pixel);
                            if (pixel[0] == 0) {
                                //System.out.println("look ahead 2 pixel");
                                blackRun++;
                                continue;
                            }

                            /** @todo Add tolerance for a single white pixel found in what should be a run of black **/
                            iDartsFound++;

                            if (m_iDartNumber < m_iDartBot.length) {
                                int dartPos = i - (blackRun / 2);
                                if (!contains(m_iDartBot, dartPos)) {
                                    m_iDartBot[m_iDartNumber] = dartPos;
                                    m_iDartNumber++;
                                    //System.out.println("Dart " + m_iDartNumber + " found at " + dartPos + "!");
                                }
                            }

                            blackRun = 0;
                            if (m_iDartNumber == m_iDartBot.length) {
                                break;
                            }
                        }
                    }

                    if (iDartsFound == 0) {
                        m_iDartNumber = 0;
                        Arrays.fill(m_iDartBot, 0);
                        //System.out.println("Reset dart count!");
                    }
                }

                if (iPrevDartCount != iDartsFound) {
                    System.out.println("darts found: " + iDartsFound);
                    //this.repaint();
                }

                iPrevDartCount = iDartsFound;
            //  }
        }
    }


//                    blackRun = 0;

//                    for (int i = 0; i < width; i++) {
//                        raster.getPixel(i, m_iScanLineTop, pixel);
//                        if (pixel[0] == 0) {
//                            blackRun++;
//                        } else if (blackRun > 0) {
//
//                            m_iDartTop[iDart] = i - (blackRun / 2);
//
//                            iDart++;
//                            blackRun = 0;
//                            if (iDart == m_iDartTop.length) {
//                                break;
//                            }
//                        }
//                    }

//                }

    public void paint(Graphics g) {

        System.out.println("painting");
        /** @todo Use the top and bottom scan lines to project the base of the dart to the surface of the board.
         *  Calculate y position of surface of board using percent of white pixels vs black pixels.
         **/
        //if (iDartsFound > 0) {
        g.setColor(Color.black);
        g.fillRect(0, 0, 640, 480);

        if (mFrame != null) {
            synchronized (mLock) {
                g.drawImage(mFrame, 0, 0, null);
            }
        }
        for (int i = 0; i < m_iDartBot.length; i++) {
            g.setColor(Color.yellow);
            g.drawLine(m_iDartBot[i], m_iScanLineBottom + 10, m_iDartBot[i], m_iScanLineBottom + 30);

            g.setColor(Color.cyan);
            g.drawLine(m_iDartTop[i], m_iScanLineBottom, m_iDartTop[i], m_iScanLineTop - 10);
        }

        g.setColor(Color.red);
        g.drawLine(0, m_iScanLineTop, 640, m_iScanLineTop);
        g.setColor(Color.green);
        g.drawLine(0, m_iScanLineBottom, 640, m_iScanLineBottom);

        if (m_bCenterLine) {
            g.setColor(Color.green);
            g.drawLine(320, 0, 320, 480);
        }

        //}
    //            }
    //        } else {
    //            g.setColor(Color.red);
//            g.drawLine(0, 0, getWidth(), getHeight());
//            g.drawLine(0, getHeight(), getWidth(), 0);
//        }
    }

    public void run() {
        while (m_bRunning) {
            if (mGrabber == null) {
                try {
                    mGrabber = new FrameGrabber("vfw:Microsoft WDM Image Capture (Win32):0", new Dimension(640, 480), 24);
                    mGrabber.setSkipTime(500);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    m_bRunning = false;
                    break;
                }
            }

            //System.out.println(System.currentTimeMillis());

            Image frame = mGrabber.getImage();
            if (mFrame != null) {
                //synchronized (mLock) {
                    if (!m_bRunning) {
                        break;
                    }
                    mFrame = frame;
                //}
            } else {
                mFrame = frame;
            }
//            long start = System.currentTimeMillis();
            processFrame();
//            System.out.println("Time: " + (System.currentTimeMillis() - start));
            //repaint();

//            try {
//                Thread.sleep(5000);
//            } catch (InterruptedException ex) {
//                ex.printStackTrace();
//            }
        }

       //mGrabber.shutdown();
    }

    public void start(int port) {
        Thread t = new Thread(this);
        t.start();

        int rc = JOptionPane.showConfirmDialog(this, "Swap for port " + port + "?", "Swap", JOptionPane.YES_NO_OPTION);

        CameraTransmitter transmit = new CameraTransmitter(this, port, rc == JOptionPane.YES_OPTION);
        transmit.start();
    }

    public void stop() {
        if (mGrabber != null) {
            mGrabber.shutdown();
            m_bRunning = false;
        }
    }

    public void calibrate() {
        m_bCalibrate = true;
    }

    public void setCenterLine(boolean b) {
        m_bCenterLine = b;
    }


    public boolean contains(int [] arr, int value) {
        for (int i = 0; i < arr.length; i++) {
            if (value >= arr[i] - 1 && value <= arr[i] + 1) {
                return true;
            }
        }
        return false;
    }
}


package net.magicstudios.jdart.comm;

import java.util.*;
import java.net.*;
import java.io.*;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class ServerCommModel extends Thread implements OcclusionListener {

    private String m_sHost = "localhost";
    private int m_iPort = 12000;

    private CameraReader m_camLeftReader = null;
    private CameraReader m_camRightReader = null;

    private double[] m_prevLeftDarts = new double[] {0, 0, 0};
    private double[] m_prevRightDarts = new double[] {0, 0, 0};

    private double[] m_leftDarts = new double[] {0, 0, 0};
    private double[] m_rightDarts = new double[] {0, 0, 0};

    private Vector m_vecListeners = new Vector();

    private OcclusionWatcher m_occlusionWatcher = null;

    public ServerCommModel() {
    }

    public ServerCommModel(String host, int port) {
        this();
        setHost(host);
        setPort(port);
    }

    /**
     *
     * @param host String
     */
    public void setHost(String host) {
        m_sHost = host;
    }

    public void setPort(int port) {
        m_iPort = port;
    }

    /**
     *
     * @return String
     */
    public String getHost() {
        return m_sHost;
    }

    public int getPort() {
        return m_iPort;
    }

    /**
     *
     */
    public void connect() {
        m_camLeftReader = new CameraReader(getHost(), getPort());
        m_camLeftReader.addCameraListener(new CameraListener() {
            public void cameraDataRecieved(double dart1, double dart2, double dart3) {
                leftCameraDataRecieved(dart1, dart2, dart3);
            }
        });

        m_camRightReader = new CameraReader(getHost(), getPort() + 1);
        m_camRightReader.addCameraListener(new CameraListener() {
            public void cameraDataRecieved(double dart1, double dart2, double dart3) {
                rightCameraDataRecieved(dart1, dart2, dart3);
            }
        });

        m_camLeftReader.start();
        m_camRightReader.start();
    }

    public void connect(String host, int port) {
        setHost(host);
        setPort(port);
        connect();
    }

    public void disconnect() {
        m_camLeftReader.stopReader();
        m_camRightReader.stopReader();
    }

    /**
     *
     * @param dart1 double
     * @param dart2 double
     * @param dart3 double
     */
    public void leftCameraDataRecieved(double dart1, double dart2, double dart3) {
        m_prevLeftDarts[0] = m_leftDarts[0];
        m_prevLeftDarts[1] = m_leftDarts[1];
        m_prevLeftDarts[2] = m_leftDarts[2];

        m_leftDarts[0] = dart1;
        m_leftDarts[1] = dart2;
        m_leftDarts[2] = dart3;

//        System.out.println(System.currentTimeMillis() + " Left  Camera Prev: " + m_prevLeftDarts[0] + ", " + m_prevLeftDarts[1] + ", " + m_prevLeftDarts[2] +
//                           "  Left  Camera Now : " + m_leftDarts[0] + ", " + m_leftDarts[1] + ", " + m_leftDarts[2]);

        calculateBoardState();
    }

    public void rightCameraDataRecieved(double dart1, double dart2, double dart3) {
        m_prevRightDarts[0] = m_rightDarts[0];
        m_prevRightDarts[1] = m_rightDarts[1];
        m_prevRightDarts[2] = m_rightDarts[2];

        m_rightDarts[0] = dart1;
        m_rightDarts[1] = dart2;
        m_rightDarts[2] = dart3;

//        System.out.println(System.currentTimeMillis() + " Right Camera Prev: " + m_prevRightDarts[0] + ", " + m_prevRightDarts[1] + ", " +
//                           m_prevRightDarts[2] + "  Right Camera Now : " + m_rightDarts[0] + ", " + m_rightDarts[1] + ", " + m_rightDarts[2]);

        calculateBoardState();
    }

    private void calculateBoardState() {

        int iLeftCount = getCount(m_leftDarts);
        int iRightCount = getCount(m_rightDarts);

        if (iLeftCount == 0 && iRightCount == 0) {
            // board was cleared of darts
            occlusionResolved();
            fireBoardCleared();
        } else if (iLeftCount != iRightCount) {
            // start occlusion watch
            if (m_occlusionWatcher == null) {
                /** @todo set this in a config file */
                m_occlusionWatcher = new OcclusionWatcher(500);
                m_occlusionWatcher.addOcclusionListener(this);
                m_occlusionWatcher.startOcclusionWatch();
            }
        } else {
            // a new dart was found
            occlusionResolved();

            int iLeftPrevCount = getCount(m_prevLeftDarts);
            int iRightPrevCount = getCount(m_prevRightDarts);

            if (iLeftPrevCount < iLeftCount &&
                iRightPrevCount < iRightCount) {
                fireDartDetected(m_leftDarts[iLeftCount - 1],
                                 m_rightDarts[iRightCount - 1]);
            }
        }
    }

    private void occlusionResolved() {
        if (m_occlusionWatcher != null) {
            m_occlusionWatcher.occlusionResolved();
            m_occlusionWatcher.removeOcclusionListener(this);
            m_occlusionWatcher = null;
        }
    }

    public int getCount(double[] arr) {
        int count = 0;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] != 0) {
                count++;
            }
        }
        return count;
    }

    /**
     *
     */
    public void occlusionTimerExpired() {
        fireOcclusionDetected();
    }

    /**
     *
     * @param listener ServerCommListener
     */
    public void addBoardListener(ServerCommListener listener) {
        m_vecListeners.add(listener);
    }


    /**
     *
     */
    private void fireDartDetected(double left, double right) {
        Object[] listeners = m_vecListeners.toArray();
        for (int i = 0; i < listeners.length; i++) {
            ((ServerCommListener) listeners[i]).dartDetected(left, right);
        }
    }

    private void fireOcclusionDetected() {
        Object[] listeners = m_vecListeners.toArray();
        for (int i = 0; i < listeners.length; i++) {
            ((ServerCommListener) listeners[i]).occlusionDetected();
        }
    }

    private void fireBoardCleared() {
        Object[] listeners = m_vecListeners.toArray();
        for (int i = 0; i < listeners.length; i++) {
            ((ServerCommListener) listeners[i]).boardCleared();
        }
    }
}

package net.magicstudios.jdart.comm;

import java.io.*;
import java.net.*;
import java.util.*;
import javax.swing.*;

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
public class CameraReader extends Thread {

    private String m_sHost = "localhost";
    private int m_iPort = 12000;
    private boolean m_bRunning = true;
    private Vector m_vecListeners = new Vector();

    public CameraReader() {
    }

    public CameraReader(String host, int port) {
        setHost(host);
        setPort(port);
    }

    public void stopReader() {
        m_bRunning = false;
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
    public void run() {

        try {

            Socket socket = new Socket(getHost(), getPort());

            BufferedReader reader  = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            while (m_bRunning) {

                String d1 = reader.readLine();
                String d2 = reader.readLine();
                String d3 = reader.readLine();
                String blank = reader.readLine();

                final double dart1 = Double.parseDouble(d1);
                final double dart2 = Double.parseDouble(d2);
                final double dart3 = Double.parseDouble(d3);

                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        fireCameraDataRecieved(dart1, dart2, dart3);
                    }
                });
            }
            reader.close();



//        final double dart1 = 0.0;
//        final double dart2 = 0.0;
//        final double dart3 = 0.0;
//        SwingUtilities.invokeLater(new Runnable() {
//            public void run() {
//                fireCameraDataRecieved(dart1, dart2, dart3);
//            }
//        });



        } catch (Exception ex) {
//            ex.printStackTrace();
        }
    }


    /**
     *
     * @param listener ServerCommListener
     */
    public void addCameraListener(CameraListener listener) {
        m_vecListeners.add(listener);
    }

    /**
     *
     */
    private void fireCameraDataRecieved(double dart1, double dart2, double dart3) {
        Object [] listeners = m_vecListeners.toArray();
        for (int i = 0; i < listeners.length; i++) {
            ((CameraListener) listeners[i]).cameraDataRecieved(dart1, dart2, dart3);
        }
    }

}


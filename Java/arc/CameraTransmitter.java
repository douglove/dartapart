package net.magicstudios.jdart;

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
 * @author Nick Cramer (d3k199)
 * @version 1.0
 */
public class CameraTransmitter extends Thread {

    private CameraAnalyzer m_camera = null;
    private int m_port = 1000;
    private boolean m_swap = false;

    public CameraTransmitter(CameraAnalyzer camera, int port, boolean swap) {
        m_camera = camera;
        m_port = port;
        m_swap = swap;
    }

    public void run() {

        ServerSocket listenSocket = null;
        try {
            listenSocket = new ServerSocket(m_port);

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        while (true) {

            try {
                System.out.println("Waiting for connection");
                Socket masterSocket = listenSocket.accept();
                System.out.println("New connection accepted: " + masterSocket.getRemoteSocketAddress().toString());

                BufferedReader reader = new BufferedReader(new InputStreamReader(masterSocket.getInputStream()));
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(masterSocket.getOutputStream()));

                writer.write(m_swap + "\r\n");
                writer.flush();

                String line = "";
                while ((line = reader.readLine()) != null) {
                    double[] percents = m_camera.getDarts();
                    writer.write(percents.length + "\r\n");
                    for (int i = 0; i < percents.length; i++) {
                        writer.write(percents[i] + "\r\n");
                    }
                    writer.flush();
                }

                reader.close();
                writer.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }
    }
}

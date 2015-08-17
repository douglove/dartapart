package net.magicstudios.jdart.data;

import java.io.*;
import java.util.*;

import javax.swing.*;

import net.magicstudios.jdart.comm.*;
import net.magicstudios.jdart.event.*;

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
public class Triangulator extends Thread implements ServerCommListener {

    public Triangulator() {
    }

    private boolean m_bRunning = true;
    private Vector m_vecListeners = new Vector();

    private double c1_x = -352;
    private double c1_y = -610;

    private double c2_x = 352;
    private double c2_y = -610;

    private double c1_camViewDeg = Math.toRadians(34.5);
    private double c1_centerDeg = Math.toRadians(60);

    private double c2_camViewDeg = Math.toRadians(34.5);
    private double c2_centerDeg = Math.toRadians(60);


    private ZoneConverter zc = new ZoneConverter();
    private String m_sHost = "localhost";

    private boolean m_bPortSwap = true;

//    private double [] mPreviousPercentLeft  = new double[] {0.0, 0.0, 0.0};
//    private double [] mPreviousPercentRight = new double[] {0.0, 0.0, 0.0};

    private boolean [] mCorrectionLeft  = new boolean[] {false, false, false};
    private boolean [] mCorrectionRight = new boolean[] {false, false, false};

    private Properties m_props = null;

    private ServerCommModel mCommModel = null;

    public Triangulator(String host, Properties props) {
      m_sHost = host;
      m_props = props;
    }

    public boolean connectCameras(){
        try {
                c1_camViewDeg = Math.toRadians(Double.parseDouble(m_props.getProperty("camera.left.viewangle")));
                c2_camViewDeg = Math.toRadians(Double.parseDouble(m_props.getProperty("camera.right.viewangle")));

                double bullseye = 25.4 * Double.parseDouble(m_props.getProperty("camera.bullseye"));
                double baseline = 25.4 * Double.parseDouble(m_props.getProperty("camera.baseline"));

                m_bPortSwap = m_props.getProperty("camera.swap").equals("true");

                c1_x = - (baseline / 2.0);
                c2_x = (baseline / 2.0);

                c1_y = - Math.sqrt((bullseye * bullseye) - (c1_x * c1_x));
                c2_y = c1_y;

                // using c2_x because it is always positive
                c1_centerDeg = Math.acos(c2_x / bullseye);
                c2_centerDeg = c1_centerDeg;

//                System.out.println("c1_x = " + c1_x);
//                System.out.println("c1_y = " + c1_y);
//                System.out.println("c1_camViewDeg = " + c1_camViewDeg);
//                System.out.println("c1_centerDeg = " + c1_centerDeg);
//                System.out.println("");
//                System.out.println("c2_x = " + c2_x);
//                System.out.println("c2_y = " + c2_y);
//                System.out.println("c2_camViewDeg = " + c2_camViewDeg);
//                System.out.println("c2_centerDeg = " + c2_centerDeg);

            int iPortLeft  = 12000;

            mCommModel = new ServerCommModel(m_sHost, iPortLeft);
            mCommModel.addBoardListener(this);
            mCommModel.connect();

            return true;
        }
        catch(Exception e){
            e.printStackTrace();
            fireCameraException(e);
            return false;
        }
    }



    public void dartDetected(double left, double right) {
        if (m_bPortSwap) {
            double tempLeft = left;
            left = right;
            right = tempLeft;
        }
        Zone zone = triangulate(left, right);
        fireDartsAdded(zone);
    }

    public void occlusionDetected() {
        fireOcclusionDetected();
    }

    public void boardCleared() {
        fireDartsCleared();
    }

    public void signalProblem() {
    }

    public void resetProblems() {
        for (int i = 0; i < 3; i++) {
            mCorrectionLeft[i] = false;
            mCorrectionRight[i] = false;
        }
    }

    public Zone triangulate(double percentOne, double percentTwo) {

        double camOneAngle = c1_centerDeg + (c1_camViewDeg / 2.0) - (percentOne * c1_camViewDeg);
        double camTwoAngle = c2_centerDeg - (c2_camViewDeg / 2.0) + (percentTwo * c2_camViewDeg);

        double radians90 = Math.toRadians(90);
        double sineOf90 = Math.sin(radians90);

        double dartAngle = Math.toRadians(180) - camOneAngle - camTwoAngle;
        double c = Math.sqrt(Math.pow(c2_x - c1_x, 2) + Math.pow(c2_y - c1_y, 2));
//        double a = Math.sin(camOneAngle) * ((c2_x - c1_x)) / Math.sin(dartAngle);
        double a = Math.sin(camOneAngle) * c / Math.sin(dartAngle);
        double D = radians90 - camTwoAngle;
        double d = Math.sin(D) * a / sineOf90;
        double e = Math.sin(camTwoAngle) * a / sineOf90;
        double x = c2_x - d;
        double y = c2_y + e;

//        System.out.println("(" + x + ", " + y + ")");
        return  zc.zone(x, y);
    }

    public double[] readCamera(BufferedReader reader, BufferedWriter writer) throws Exception {
        double[] percent = new double[3];

        for (int i = 0; i < 3; i++) {
            percent[i] = Double.parseDouble(reader.readLine());
        }
        reader.readLine();
        return percent;
    }

    public void addDartListener(DartListener listener) {
        m_vecListeners.add(listener);
    }

    public Object [] getDartListeners() {
        return m_vecListeners.toArray();
    }

    public void fireDartsAdded(Zone z) {
        DartEvent evt = new DartEvent(z);
        Object[] listeners = getDartListeners();
        for (int i = 0; i < listeners.length; i++) {
            ((DartListener) listeners[i]).dartsAdded(evt);
        }
    }

    public void fireDartsCleared() {
        Object[] listeners = getDartListeners();
        for (int i = 0; i < listeners.length; i++) {
            ((DartListener) listeners[i]).dartsCleared();
        }
    }

    public void fireCameraException(Exception e){
        CameraExceptionEvent evt = new CameraExceptionEvent(e);
        Object[] listeners = getDartListeners();
        for (int i = 0; i < listeners.length; i++) {
            ((DartListener) listeners[i]).cameraException(evt);
        }
    }

    public void fireOcclusionDetected(){
        Object[] listeners = getDartListeners();
        for (int i = 0; i < listeners.length; i++) {
            ((DartListener) listeners[i]).occlusionDetected();
        }
    }

    public void print(double [] values) {
        for (int i = 0; i < values.length; i++) {
            System.out.print(values[i]);
            System.out.print(' ');
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Triangulator triangulator = new Triangulator("localhost", null);
        triangulator.run();
    }

    public void swapPorts() {
        m_bPortSwap = ! m_bPortSwap;
        m_props.setProperty("camera.swap", Boolean.toString(m_bPortSwap));
    }

    public boolean isSwapped() {
      return m_bPortSwap;
    }
}

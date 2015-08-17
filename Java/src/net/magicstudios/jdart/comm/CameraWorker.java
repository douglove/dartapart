package net.magicstudios.jdart.comm;

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
public class CameraWorker {

    private double m_dart1 = 0;
    private double m_dart2 = 0;
    private double m_dart3 = 0;

    public CameraWorker(double dart1, double dart2, double dart3) {
        m_dart1 = dart1;
        m_dart2 = dart2;
        m_dart3 = dart3;
    }

    public double getDart1() {
        return m_dart1;
    }

    public double getDart2() {
        return m_dart2;
    }

    public double getDart3() {
        return m_dart3;
    }
}

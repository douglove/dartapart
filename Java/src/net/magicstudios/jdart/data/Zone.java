package net.magicstudios.jdart.data;

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
public class Zone {

    public static final int RING_DOUBLE_BULLSEYE = 0;
    public static final int RING_SINGLE_BULLSEYE = 1;
    public static final int RING_SINGLE  = 2;
    public static final int RING_DOUBLE  = 3;
    public static final int RING_TRIPLE  = 4;
    public static final int RING_OUTSIDE = 5;

    private int m_slice = 0;
    private int m_ring = 0;
    private double m_x = 0.0;
    private double m_y = 0.0;

    public Zone() {

    }

    public Zone(int slice, int ring) {
        m_slice = slice;
        m_ring = ring;
    }

    public Zone(int slice, int ring, double x, double y) {
        m_slice = slice;
        m_ring = ring;
        m_x = x;
        m_y = y;
    }

    /**
     *
     * @param zone int
     */
    public void setSlice(int slice) {
        m_slice = slice;
    }

    public void setRing(int ring) {
        m_ring = ring;
    }

    /**
     *
     * @return double
     */
    public void setX(double x) {
        m_x = x;
    }

    /**
     *
     * @return double
     */
    public void setY(double y) {
        m_y = y;
    }


    /**
     *
     * @return double
     */
    public double getX() {
        return m_x;
    }

    /**
     *
     * @return double
     */
    public double getY() {
        return m_y;
    }

    /**
     *
     * @return double
     */
    public double getXPercent() {
        return m_x / DartBoardDimensions.MM_BOARD;
    }

    /**
     *
     * @return double
     */
    public double getYPercent() {
        return - m_y / DartBoardDimensions.MM_BOARD;
    }

    /**
     *
     * @return int
     */
    public int getSlice() {
        return m_slice;
    }


    /**
     *
     * @return boolean
     */
    public boolean isSingle() {
      return getRing() == RING_SINGLE;
    }

    public boolean isDouble() {
      return getRing() == RING_DOUBLE;
    }

    public boolean isTriple() {
      return getRing() == RING_TRIPLE;
    }

    public boolean isSingleBullseye() {
      return getRing() == RING_SINGLE_BULLSEYE;
    }

    public boolean isDoubleBullseye() {
      return getRing() == RING_DOUBLE_BULLSEYE;
    }

    public boolean isMiss() {
      return getRing() == RING_OUTSIDE;
    }


    /**
     *
     * @return int
     */
    public int getRing() {
      return m_ring;
    }

    public int getRingMultiplier() {
        int ring = 1;

        switch (m_ring) {
        case RING_DOUBLE_BULLSEYE:
            ring = 2;
            break;
        case RING_SINGLE_BULLSEYE:
            ring = 1;
            break;
        case RING_SINGLE:
            ring = 1;
            break;
        case RING_DOUBLE:
            ring = 2;
            break;
        case RING_TRIPLE:
            ring = 3;
            break;
        case RING_OUTSIDE:
            ring = 0;
            break;

        }
        return ring;
    }
    /**
     *
     * @return int
     */
    public int getPoints() {
        int points = 0;

        switch (m_ring) {
        case RING_DOUBLE_BULLSEYE:
            points = 50;
            break;
        case RING_SINGLE_BULLSEYE:
            points = 25;
            break;
        case RING_SINGLE:
            points = m_slice;
            break;
        case RING_DOUBLE:
            points = 2 * m_slice;
            break;
        case RING_TRIPLE:
            points = 3 * m_slice;
            break;
        case RING_OUTSIDE:
            points = 0;
            break;

        }

        return points;
    }

    /**
     * Calculates distance between two darts
     * @param zone Zone
     * @return double
     */
    public double getDistanceTo(Zone zone) {
      double a = this.getX() - zone.getX();
      double b = this.getY() - zone.getY();
      return Math.sqrt(a*a + b*b);
    }

    public String toString() {

      String sDisplay = "";
      switch (getRing()) {
        case RING_DOUBLE_BULLSEYE:
            sDisplay = "DBE";
            break;
        case RING_SINGLE_BULLSEYE:
            sDisplay = "BE";
            break;
        case RING_SINGLE:
            sDisplay = " " + getSlice();
            break;
        case RING_DOUBLE:
            sDisplay = "D" + getSlice();
            break;
        case RING_TRIPLE:
            sDisplay = "T" + getSlice();
            break;
        case RING_OUTSIDE:
            sDisplay = "MISS";
            break;
        }

        return sDisplay;
    }
}

package net.magicstudios.jdart.game;

import java.text.*;

import java.awt.*;
import java.awt.event.*;
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
 * @author Nick Cramer (d3k199)
 * @version 1.0
 */
public class DartBoard extends JPanel {



    public static final double PERCENT_DOUBLE_OUTSIDE   = 0.739352;
    public static final double PERCENT_DOUBLE_INSIDE    = 0.697204;

    public static final double PERCENT_TRIPLE_OUTSIDE   = 0.464729;
    public static final double PERCENT_TRIPLE_INSIDE    = 0.422582;

    public static final double PERCENT_BULLSEYE_OUTSIDE = 0.070319;
    public static final double PERCENT_BULLSEYE_INSIDE  = 0.028172;

    // Size in 1/10'ths of a millimeter
    public static final int SIZE_CAM_DEGREES = 30;
    public static final int SIZE_CAM_VIEW = 4508;
    public static final int SIZE_DART  = 64;

    public static final int SIZE_CAMERA = 128;
    public static final int SIZE_POINT  = 32;
    public static final int SIZE_BOARD  = 4508;
//
//    public static final int SIZE_DOUBLE_OUTSIDE = 3365;
//    public static final int SIZE_DOUBLE_INSIDE  = 3175;
//
//    public static final int SIZE_TRIPLE_OUTSIDE = 2095;
//    public static final int SIZE_TRIPLE_INSIDE  = 1905;
//
//    public static final int SIZE_BULLSEYE_OUTSIDE = 317;
//    public static final int SIZE_BULLSEYE_INSIDE  = 190;

    public static final int SIZE_DOUBLE_OUTSIDE = 3333;
    public static final int SIZE_DOUBLE_INSIDE  = 3143;

    public static final int SIZE_TRIPLE_OUTSIDE = 2095;
    public static final int SIZE_TRIPLE_INSIDE  = 1905;

    public static final int SIZE_BULLSEYE_OUTSIDE = 317;
    public static final int SIZE_BULLSEYE_INSIDE  = 127;

    private double mScale = 0.15;
    private int mWidth  = 0;
    private int mHeight = 0;
    private int mCenterX = 0;
    private int mCenterY = 0;

    private Point mCamAlpha = new Point(0, 0);
    private Point mCamBeta  = new Point(0, 0);
    private Point mMousePoint = new Point(0, 0);
    private NumberFormat mFormat = NumberFormat.getInstance();
    private ImageIcon mImageBoard = null;
    private boolean mDrawBoardImage = false;
    private boolean mDrawTransparent = false;

    private Point [] darts = new Point[] {
                             new Point(0, 0),
                             new Point(200, 200),
                             new Point(-2200, -200)
    };

    private Camera camLeft = new Camera(-3620, -6223, 40); // 14.25, 24.5
    private Camera camRight = new Camera(3620, -6223, 40);

    private BorderLayout borderLayout1 = new BorderLayout();

    public DartBoard() {
        try {
            jbInit();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        this.setLayout(borderLayout1);
        this.addMouseWheelListener(new MouseWheelListener() {
            public void mouseWheelMoved(MouseWheelEvent e) {
                this_mouseWheelMoved(e);
            }
        });
        this.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseMoved(MouseEvent e) {
                this_mouseMoved(e);
            }
            public void mouseDragged(MouseEvent e) {
                this_mouseDragged(e);
            }
        });
        this.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                this_componentResized(e);
            }
        });
        this.addMouseListener(new MouseAdapter() {
            public void mouseReleased(MouseEvent e) {
                this_mouseReleased(e);
            }
        });


        mFormat = NumberFormat.getInstance();
        mFormat.setMinimumFractionDigits(2);
        mFormat.setMaximumFractionDigits(2);
        mFormat.setGroupingUsed(true);

        mImageBoard = new ImageIcon(Class.class.getResource("/net/magicstudios/jdart/board.jpg"));

    }

    /**
     * Paint the fence
     * @param g Graphics
     */
    public void paint(Graphics g) {
        int diameter = SIZE_BOARD;
        int radius   = diameter / 2;

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g.setColor(Color.black);
        g.fillOval(mCenterX - mWidth / 2, mCenterY - mHeight / 2, mWidth, mWidth);
//        g.setColor(Color.green);
//        g.fillOval(mCenterX - mWidth / 2, mCenterY - mHeight / 2, mWidth, mWidth);
//        g.setColor(Color.black);
//        g.fillOval(mCenterX - mWidth / 2, mCenterY - mHeight / 2, mWidth, mWidth);
//        g.setColor(Color.red);
//        g.fillOval(mCenterX - mWidth / 2, mCenterY - mHeight / 2, mWidth, mWidth);
//        g.setColor(Color.black);
//        g.fillOval(mCenterX - mWidth / 2, mCenterY - mHeight / 2, mWidth, mWidth);







        if (false) {

            // clear screen
            g.setColor(Color.white);
            g.fillRect(0, 0, mWidth, mHeight);

            // border and cursor position
            g.setColor(Color.black);
            g.drawRect(0, 0, mWidth - 1, mHeight - 1);
            g.drawString("Cursor   " + mMousePoint.x + ", " + mMousePoint.y, 10, 20);
            g.drawString("Zoom     " + mFormat.format(mScale), 10, 35);

            // translate and scale to view
            //g2.translate(mCenterX, mCenterY);
            //g2.scale(mScale, mScale);

            if (mImageBoard != null && mDrawBoardImage) {
                Composite cp = null;
                if (mDrawTransparent) {
                    cp = g2.getComposite();
                    g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, .1f));
                }
                g.drawImage(mImageBoard.getImage(),
                            -SIZE_BOARD / 2, -SIZE_BOARD / 2,
                            SIZE_BOARD, SIZE_BOARD,
                            mImageBoard.getImageObserver());
                if (mDrawTransparent) {
                    g2.setComposite(cp);
                }
            }

            // board background
            g.setColor(Color.black);
            drawOval(g, diameter);

            // rings
            g.setColor(Color.black);
            drawOval(g, diameter);
            drawOval(g, SIZE_DOUBLE_OUTSIDE);
            drawOval(g, SIZE_DOUBLE_INSIDE);
            drawOval(g, SIZE_TRIPLE_OUTSIDE);
            drawOval(g, SIZE_TRIPLE_INSIDE);
            drawOval(g, SIZE_BULLSEYE_OUTSIDE);
            drawOval(g, SIZE_BULLSEYE_INSIDE);

            // draw wedges
            for (int i = 9; i < 360; i += 18) {
                double x = Math.cos(Math.toRadians(i));
                double y = Math.sin(Math.toRadians(i));
                g.drawLine((int) (x * SIZE_BULLSEYE_OUTSIDE / 2),
                           -(int) (y * SIZE_BULLSEYE_OUTSIDE / 2),
                           (int) (x * SIZE_DOUBLE_OUTSIDE / 2),
                           -(int) (y * SIZE_DOUBLE_OUTSIDE / 2));
            }

            // draw cameras
        //        drawCamera(g, camLeft, Color.red);
        //        drawCamera(g, camRight, Color.orange);
        //
        //        int dartLeft  = 640;
        //        int dartRight = 640;
        //
        //        double dartSlopeLeft = camLeft.getSlope(dartLeft);
        //        double dartSlopeRight = camRight.getSlope(dartRight);
        //
        //        double yIntLeft = camLeft.getY() - (dartSlopeLeft * (double)camLeft.getX());
        //        double yIntRight = camRight.getY() - (dartSlopeRight * (double)camRight.getX());
        //
        //
        //        int x = (int) ((yIntRight - yIntLeft) / (double) (dartSlopeLeft - dartSlopeRight));
        //        int y = (int) ((dartSlopeLeft * x) + yIntLeft);

            Point p = new Point(100, 100);
            drawDart(g, p);

        //        for (int i = 1; i < 2; i++) {
        //            Point dart = darts[i];
        //            if (dart != null) {
        //                g.setColor(Color.yellow);
        //                g.fillOval(dart.x - SIZE_DART / 2, dart.y - SIZE_DART / 2, SIZE_DART, SIZE_DART);
        //                g.setColor(Color.black);
        //                g.drawOval(dart.x - SIZE_DART / 2, dart.y - SIZE_DART / 2, SIZE_DART, SIZE_DART);
        //                g.drawLine(mCamAlpha.x, mCamAlpha.y, dart.x, dart.y);
        //                g.drawLine(mCamBeta.x, mCamBeta.y, dart.x, dart.y);
        //            }
        //        }
        }
    }

    private void drawDart(Graphics g, Point p) {

        g.setColor(Color.yellow);
        g.fillOval(p.x - SIZE_DART / 2, -p.y - SIZE_DART / 2, SIZE_DART, SIZE_DART);
        g.setColor(Color.black);
        g.drawOval(p.x - SIZE_DART / 2, -p.y - SIZE_DART / 2, SIZE_DART, SIZE_DART);
//        g.drawLine(mCamAlpha.x, mCamAlpha.y, p.x, p.y);
//        g.drawLine(mCamBeta.x, mCamBeta.y, p.x, p.y);

    }

    class Camera {
        public double m_dAngleToBullseye = 60;
        public double m_dAngleVisible = 30;
        private Point m_point = null;

        public Camera(int x, int y, double visible) {
            m_point = new Point(x, y);
            double b = ((double) m_point.y) / ((double) m_point.x);
            m_dAngleToBullseye = Math.toDegrees(Math.atan(b));
            m_dAngleVisible = visible;
        }

        public int getX() {
            return m_point.x;
        }

        public int getY() {
            return m_point.y;
        }

        public double getVisibleAngle() {
            return m_dAngleVisible;
        }

        public double distance(int x, int y) {
            return m_point.distance(x, y);
        }

        public double getSlope(int x) {
            double angle = m_dAngleToBullseye + (m_dAngleVisible / 2.0) - (x / 640.0 * m_dAngleVisible);
            double rise = Math.sin(Math.toRadians(angle));
            double run = Math.cos(Math.toRadians(angle));
            return rise / run;
        }
    }

    /**
     * Draw a camera
     * @param g Graphics
     * @param cam Point
     */
    private void drawCamera(Graphics g, Camera cam, Color c) {
        if (cam.getX() != 0 || cam.getY() != 0) { // don't draw 0,0 camera

            double angle = Math.atan(- cam.getY() / (double) cam.getX());
            int degrees = (int) Math.toDegrees(angle);
            degrees = (cam.getX() > 0) ? 180 + degrees : degrees;

            g.setColor(c);
            Graphics2D g2 = (Graphics2D) g;
            Composite cp = g2.getComposite();
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, .05f));
            int iCamView = ((int) cam.distance(0, 0)) * 2 + SIZE_BOARD;
            g.fillArc(cam.getX() - iCamView / 2,
                      -cam.getY() - iCamView / 2,
                      iCamView,
                      iCamView,
                      (int) (degrees - cam.getVisibleAngle() / 2.0),
                      (int) cam.getVisibleAngle());
            g2.setComposite(cp);

            g.fillOval(cam.getX() - SIZE_CAMERA, -cam.getY() - SIZE_CAMERA, 2 * SIZE_CAMERA, 2 * SIZE_CAMERA);
            g.fillOval(- SIZE_POINT, -cam.getY() - SIZE_POINT, 2 * SIZE_POINT, 2 * SIZE_POINT);
            g.fillOval(cam.getX() - SIZE_POINT, - SIZE_POINT, 2 * SIZE_POINT, 2 * SIZE_POINT);

            //drawLabelLine(g, 0, 0, cam.x, cam.y, Color.blue, c);
            drawLabelLine(g, cam.getX(), 0, cam.getX(), -cam.getY(), Color.blue, c);
            drawLabelLine(g, 0, cam.getY(), cam.getX(), -cam.getY(), Color.blue, c);
        }
    }

    private void drawLabelLine(Graphics g, int x1, int y1, int x2, int y2, Color c1, Color c2) {
        Graphics2D g2 = (Graphics2D) g;
        g.setFont(g.getFont().deriveFont(Font.BOLD, 72));
        g.setColor(c1);
        g.drawLine(x1, y1, x2, y2);
        g.setColor(c2);
        g.drawString(mFormat.format(Point.distance(x1, y1, x2, y2)), (x2 + x1) / 2, ((y2 + y1) / 2));
    }

    private void drawOval(Graphics g, int d) {
        int r = d / 2;
        g.drawOval(- r, - r, d, d);
    }

    private void fillOval(Graphics g, int d) {
        int r = d / 2;
        g.drawOval(- r, - r, d, d);
    }

    /**
     *
     * @param e MouseWheelEvent
     */
    public void this_mouseWheelMoved(MouseWheelEvent e) {
        mScale -= e.getWheelRotation() / 500.0;
        repaint();
    }

    public void this_mouseMoved(MouseEvent e) {
        mMousePoint = screenToWorld(e.getPoint());
        repaint();
    }

    public void this_componentResized(ComponentEvent e) {
        mWidth = this.getWidth();
        mHeight = this.getHeight();
        mCenterX = mWidth / 2;
        mCenterY = mHeight / 2;
    }

    public void this_mouseReleased(MouseEvent e) {
        if (!e.isControlDown()) {
            mCamAlpha = screenToWorld(e.getPoint());
        } else {
            mCamBeta = screenToWorld(e.getPoint());
        }
        repaint();
    }

    public void this_mouseDragged(MouseEvent e) {
        if (! e.isControlDown()) {
            mCamAlpha = screenToWorld(e.getPoint());
        } else {
            mCamBeta = screenToWorld(e.getPoint());
        }
        repaint();
    }

    public Point screenToWorld(Point p) {
        return screenToWorld(p.x, p.y);
    }

    public Point screenToWorld(int x, int y) {
        return new Point((int) ((x - mCenterX) / mScale), (int) ((y - mCenterY) / mScale));
    }

    public Point worldToScreen(int x, int y) {
        return new Point((int) ((x * mScale) + mCenterX), (int) ((y * mScale) + mCenterY));
    }

    public void setDrawBoardImage(boolean b) {
        mDrawBoardImage = b;
        repaint();
    }

    /**
     * setTransparent
     *
     * @param b boolean
     */
    public void setTransparent(boolean b) {
        mDrawTransparent = b;
        repaint();

    }
}

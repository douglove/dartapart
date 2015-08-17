package net.magicstudios.jdart.ui;

import java.awt.*;
import javax.swing.*;

import java.util.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import net.magicstudios.jdart.data.*;
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
public class DartBoardViz
    extends JPanel {

  private int m_iScale = 1;
  private int m_iBuffer = 20;
  private BorderLayout borderLayout1 = new BorderLayout();

  private Color m_clrRed   = new Color(125, 0, 0);
  private Color m_clrGreen = new Color(0, 95, 0);
  private Color m_clrTan   = new Color(215, 215, 165);

  private Vector m_vecDarts = new Vector();
  private Vector m_vecListeners = new Vector();

  private Point2D target = null;
  private String targetLabel = "Target";

  public DartBoardViz() {
    try {
      jbInit();
    }
    catch (Exception exception) {
      exception.printStackTrace();
    }
  }

  /**
   * Add a dart to the visualization
   * @param x double
   * @param y double
   */
  public void addDart(double x, double y) {
    addDart(new Point2D(x, y));
  }

  public void addTarget(double x, double y, String label) {
    target = new Point2D(x, y);
    this.targetLabel = label;
  }

  /**
   * Clear darts from visualization
   */
  public void clearDarts() {
    m_vecDarts.removeAllElements();
    repaint();
  }




  /**
   *
   * @param g Graphics
   */
  public void paint(Graphics g) {

    m_iScale = (Math.min(this.getWidth(), this.getHeight()) / 2) - m_iBuffer;
    int iCenterX = this.getWidth() / 2;
    int iCenterY = this.getHeight() / 2;

    Graphics2D g2 = (Graphics2D) g;
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

    g2.setColor(Color.white);
    g2.fillRect(0, 0, this.getWidth(), this.getHeight());


    // Center all drawings in the view
    g2.translate(iCenterX, iCenterY);

    g2.setColor(Color.black);
    g2.fillOval(- scale(1.0), - scale(1.0), scale2(1.0), scale2(1.0));

    // Draw double ring
    Polygon p3 = generateWedge(DartBoardDimensions.PERCENT_DOUBLE_OUTSIDE);
    drawPolygonCircle(g2, p3, m_clrGreen, m_clrRed);

    // Draw double ring
    Polygon p4 = generateWedge(DartBoardDimensions.PERCENT_DOUBLE_INSIDE);
    drawPolygonCircle(g2, p4, m_clrTan, Color.black);

    // Draw triple ring
    Polygon p1 = generateWedge(DartBoardDimensions.PERCENT_TRIPLE_OUTSIDE);
    drawPolygonCircle(g2, p1, m_clrGreen, m_clrRed);

    Polygon p2 = generateWedge(DartBoardDimensions.PERCENT_TRIPLE_INSIDE);
    drawPolygonCircle(g2, p2, m_clrTan, Color.black);

    // Draw the single bullseye
    g2.setColor(m_clrRed);
    int iBullseyeRadius = scale(DartBoardDimensions.PERCENT_BULLSEYE_OUTSIDE);
    g2.fillOval(-iBullseyeRadius, -iBullseyeRadius, 2 * iBullseyeRadius, 2 * iBullseyeRadius);

    // Draw the double bullseye
    g2.setColor(Color.black);
    int iDblBullseyeRadius = scale(DartBoardDimensions.PERCENT_BULLSEYE_INSIDE);
    g2.fillOval(-iDblBullseyeRadius, -iDblBullseyeRadius, 2 * iDblBullseyeRadius, 2 * iDblBullseyeRadius);



    int count = m_vecDarts.size();
    for (int i = 0; i < count; i++) {
      Point2D pt = (Point2D) m_vecDarts.elementAt(i);
      drawDart(g2, pt, i + 1, Integer.toString(i + 1));
    }

    if (target != null) {
      drawDart(g2, target, 0, targetLabel);
    }
  }

  private void drawDart(Graphics2D g2, Point2D pt, int number, String label) {

    int x = scale(pt.x);
    int y = scale(pt.y);
    int radius   = 3;
    int diameter = 6;


    Font f = g2.getFont().deriveFont(Font.BOLD, 16.0f);
    g2.setFont(f);

    FontMetrics metrics = g2.getFontMetrics(f);
    int labelWidth = metrics.stringWidth(label);

    int bubbleWidth = labelWidth + 15;

    Color c = Color.black;
    switch (number % 3) {
      case 1:
        c = Color.red;
        break;
      case 2:
        c = Color.green;
        break;
      case 0:
        c = Color.blue;
        break;
    }

    switch (number) {
      case 1:
        Polygon p1 = new Polygon();
        p1.addPoint(x, y);
        p1.addPoint(x + 5, y - 10);
        p1.addPoint(x + 15, y - 10);

        g2.setColor(c);
        g2.fillOval(x - radius, y - radius, diameter, diameter);

        g2.setColor(Color.white);
        g2.fillRoundRect(x, y - 30, bubbleWidth, 20, 5, 5);
        g2.setColor(Color.black);
        g2.drawRoundRect(x, y - 30, bubbleWidth, 20, 5, 5);

        g2.setColor(Color.white);
        g2.fillPolygon(p1);

        g2.setColor(Color.black);
        g2.drawLine(x, y, x + 5, y - 10);
        g2.drawLine(x, y, x + 15, y - 10);

        g2.drawString(label, x + 8, y - 14);
        break;

      case 2:
        Polygon p2 = new Polygon();
        p2.addPoint(x, y);
        p2.addPoint(x - 5, y - 10);
        p2.addPoint(x - 15, y - 10);

        g2.setColor(c);
        g2.fillOval(x - radius, y - radius, diameter, diameter);

        g2.setColor(Color.white);
        g2.fillRoundRect(x - bubbleWidth, y - 30, bubbleWidth, 20, 5, 5);
        g2.setColor(Color.black);
        g2.drawRoundRect(x - bubbleWidth, y - 30, bubbleWidth, 20, 5, 5);

        g2.setColor(Color.white);
        g2.fillPolygon(p2);

        g2.setColor(Color.black);
        g2.drawLine(x, y, x - 5, y - 10);
        g2.drawLine(x, y, x - 15, y - 10);

        g2.drawString(label, x - 16, y - 14);
        break;

      case 3:
        Polygon p3 = new Polygon();
        p3.addPoint(x, y);
        p3.addPoint(x + 5, y + 11);
        p3.addPoint(x + 15, y + 11);

        g2.setColor(c);
        g2.fillOval(x - radius, y - radius, diameter, diameter);

        g2.setColor(Color.white);
        g2.fillRoundRect(x, y + 10, bubbleWidth, 20, 5, 5);
        g2.setColor(Color.black);
        g2.drawRoundRect(x, y + 10, bubbleWidth, 20, 5, 5);

        g2.setColor(Color.white);
        g2.fillPolygon(p3);

        g2.setColor(Color.black);
        g2.drawLine(x, y, x + 5, y + 10);
        g2.drawLine(x, y, x + 15, y + 10);

        g2.drawString(label, x + 8, y + 26);
        break;

      default:
        Polygon p4 = new Polygon();
        p4.addPoint(x, y);
        p4.addPoint(x + 5, y - 10);
        p4.addPoint(x + 15, y - 10);

        g2.setColor(c);
        g2.fillOval(x - radius, y - radius, diameter, diameter);

        g2.setColor(Color.white);
        g2.fillRoundRect(x, y - 30, bubbleWidth, 20, 5, 5);
        g2.setColor(Color.black);
        g2.drawRoundRect(x, y - 30, bubbleWidth, 20, 5, 5);

        g2.setColor(Color.white);
        g2.fillPolygon(p4);

        g2.setColor(Color.black);
        g2.drawLine(x, y, x + 5, y - 10);
        g2.drawLine(x, y, x + 15, y - 10);

        g2.drawString(label, x + 8, y - 14);
        break;
    }
  }

  /**
   *
   * @throws Exception
   */
  private void jbInit() throws Exception {
    this.setLayout(borderLayout1);
    this.addMouseListener(new MouseAdapter() {
      public void mouseClicked(MouseEvent e) {
        this_mouseClicked(e);
      }
    });
  }

  private int scale2(double dblScale) {
    return scale(dblScale * 2.0);
  }

  private int scale(double dblScale) {
    return scale(dblScale, 1.0);
  }

  private int scale(double dblScale, double value) {
    return (int) (value * m_iScale * dblScale);
  }

  private void drawPolygonCircle(Graphics2D g2, Polygon p, Color c1, Color c2) {

    double dblZoneDegrees = Math.toRadians(18.0);

    // Draw the 20 zones
    for (int i = 0; i < 20; i++) {
      g2.setColor((i % 2 == 0) ? c1 : c2);
      g2.rotate(dblZoneDegrees);
      g2.fillPolygon(p);
    }
  }

  private Polygon generateWedge(double dblScale) {

    Polygon p = new Polygon();
    p.addPoint(scale(dblScale, 0), scale(dblScale, 0));

    int iPoints = 50;
    double dblZoneDegrees  = Math.toRadians(18.0);
    double dblSliceDegrees = dblZoneDegrees / iPoints;

    for (int i = 0; i < iPoints + 1; i++) {
      double theta = dblSliceDegrees * (i) - (dblZoneDegrees / 2.0);
      int x = scale(dblScale, Math.sin(theta));
      int y = scale(dblScale, Math.cos(theta));
      p.addPoint(x, y);
    }

    return p;
  }


  /**
   *
   * @param dart Point2D
   */
  private void addDart(Point2D dart) {
    m_vecDarts.add(dart);
    repaint();
  }

  public void this_mouseClicked(MouseEvent e) {

    int x = e.getX();
    int y = e.getY();

    int centerX = (getWidth() / 2);
    int centerY = (getHeight() / 2);

    double xd = (x - centerX) / (double) m_iScale;
    double yd = (y - centerY) / (double) m_iScale;

    this.addDart(xd, yd);
  }

  public void addDartListener(DartListener listener) {
    m_vecListeners.add(listener);
  }

  private void fireDartEvent(Zone z) {
    DartEvent evt = new DartEvent(z);
    Object [] listeners = m_vecListeners.toArray();
    for (int i = 0; i < listeners.length; i++) {
      ((DartListener) listeners[i]).dartsAdded(evt);
    }
  }
}



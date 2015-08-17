package net.magicstudios.jdart.ui;

import java.awt.*;
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
public class VizTestFrame
    extends JFrame {
  private BorderLayout borderLayout1 = new BorderLayout();
  private DartBoardViz jPanel1 = new DartBoardViz();

  public VizTestFrame() {
    try {
      jbInit();
    }
    catch (Exception exception) {
      exception.printStackTrace();
    }
  }

  private void jbInit() throws Exception {
    getContentPane().setLayout(borderLayout1);
    this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    this.getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

    jPanel1.addDart(.25, .25);
  }

  public static void main(String[] args) {
    VizTestFrame viztestframe = new VizTestFrame();
    viztestframe.setSize(500, 500);

    viztestframe.show();
  }
}

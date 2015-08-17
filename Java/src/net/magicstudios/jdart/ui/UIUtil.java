package net.magicstudios.jdart.ui;

import java.awt.*;

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
public class UIUtil {

  public UIUtil() {
  }

  static public void centerWindow(Window win) {
    Toolkit toolkit = Toolkit.getDefaultToolkit();
    Dimension screenSize = toolkit.getScreenSize();

    //Calculate the frame location
    int x = (screenSize.width - win.getWidth()) / 2;
    int y = (screenSize.height - win.getHeight()) / 2;

    //Set the new frame location
    win.setLocation(x, y);
  }
}

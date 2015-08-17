package net.magicstudios.jdart.game;

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
 * @author Nick Cramer (d3k199)
 * @version 1.0
 */
public class JDart {
    public JDart() {

        int width  = 1200;
        int height = 950;

        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();

        DartMain main = new DartMain();
        main.setSize(width, height);
        main.setLocation((d.width - width) / 2, (d.height - height) / 2);
        main.show();
    }

    public static void main(String[] args) {
        JDart jdart = new JDart();
    }
}

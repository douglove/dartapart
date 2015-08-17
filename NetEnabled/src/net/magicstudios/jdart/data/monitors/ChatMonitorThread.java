package net.magicstudios.jdart.data.monitors;

import net.magicstudios.jdart.data.*;
import java.util.*;
import javax.swing.*;
import net.magicstudios.jdart.data.service.*;

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
public class ChatMonitorThread extends Thread {

  private DartApartModel model = null;
  private boolean monitoring = true;

  private int gameID = -1;
  private int messageID = 0;

  public ChatMonitorThread(DartApartModel model) {
    this.model = model;
  }

  public void setMonitoring(boolean state) {
    this.monitoring = state;
  }

  public void run() {

    while (monitoring) {
      final List<DAMessage> messages = model.getRecentMessages();

      SwingUtilities.invokeLater(new Runnable() {
        public void run() {
          model.addMessages(messages);
        }
      });

      try {
        Thread.sleep(1000);
      } catch (InterruptedException ex) {
      }
    }
  }
}

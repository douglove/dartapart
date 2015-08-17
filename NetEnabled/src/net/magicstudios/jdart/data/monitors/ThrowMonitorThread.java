
package net.magicstudios.jdart.data.monitors;

import java.util.*;

import javax.swing.*;
import net.magicstudios.jdart.data.*;

public class ThrowMonitorThread extends Thread {

  private DartApartModel model = null;
  private boolean monitoring = true;

  public ThrowMonitorThread(DartApartModel model) {
    this.model = model;
  }

  public void setMonitoring(boolean state) {
    this.monitoring = state;
  }

  public void run() {

    while (monitoring) {
      final List<Zone> dartThrows = model.getRecentThrows();

      SwingUtilities.invokeLater(new Runnable() {
        public void run() {
          model.addThrows(dartThrows);
        }
      });

      try {
        Thread.sleep(1000);
      } catch (InterruptedException ex) {
      }
    }
  }
}

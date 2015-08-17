
package net.magicstudios.jdart.data.monitors;

import net.magicstudios.jdart.data.service.*;
import net.magicstudios.jdart.data.*;

public class StatusMonitorThread extends Thread {

  private DartApartModel model = null;
  private boolean monitoring = true;

  public StatusMonitorThread(DartApartModel model) {
    this.model = model;
  }

  public void setMonitoring(boolean state) {
    this.monitoring = state;
  }

  public void run() {

    while (monitoring) {

      DAGame game = model.getGame();

      // fetch the list of game players from the service
      DAGame gameInfo = model.getService().getGameInfo(game.getID());

      if (gameInfo.getStatus() != game.getStatus()) {
        game.setStatus(gameInfo.getStatus());
        game.setSharedData(gameInfo.getSharedData());
        model.fireStatusChanged();
      }

      try {
        Thread.sleep(1000);
      } catch (InterruptedException ex) {
      }
    }
  }
}

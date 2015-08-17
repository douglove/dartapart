
package net.magicstudios.jdart.data.monitors;

import net.magicstudios.jdart.data.service.*;
import java.util.*;
import net.magicstudios.jdart.data.games.*;
import net.magicstudios.jdart.data.*;

public class PlayerMonitorThread extends Thread {

  private DartApartModel model = null;
  private boolean monitoring = true;

  public PlayerMonitorThread(DartApartModel model) {
    this.model = model;
  }

  public void setMonitoring(boolean state) {
    this.monitoring = state;
  }

  public void run() {

    while (monitoring) {
      // fetch the list of game players from the service
      List<DAPlayer> players = model.getService().getGamePlayers(model.getGame().getID());

      // see if any of them are new
      for (Iterator iter = players.iterator(); iter.hasNext(); ) {
        DAPlayer item = (DAPlayer) iter.next();

        // if player is new, add to list of players
        if (model.isPlayerPresent(item) == false) {
          model.getGameModel().addPlayer(item);
        }
      }

      try {
        Thread.sleep(5000);
      } catch (InterruptedException ex) {
      }
    }
  }
}

package net.magicstudios.jdart.data.games;

import java.util.*;
import net.magicstudios.jdart.data.service.*;

import net.magicstudios.jdart.data.*;

public class RandomPointGameModel extends GameModel {

  private Zone randomPoint = null;
  private DAPlayer m_playerWinner = null;
  private boolean m_bWinner = false;


  public RandomPointGameModel() {
  }


  /**
   *
   */
  public void initialize() {
    randomPoint = getRandomPoint();
    m_bWinner = false;
  }

  public String getSharedData() {
    return randomPoint.getX() + "," + randomPoint.getY();
  }

  public void setSharedData(String data) {
    String [] values = data.split(",");
    ZoneConverter zc = new ZoneConverter();
    randomPoint = zc.zone(Double.parseDouble(values[0]), Double.parseDouble(values[1]));
  }

  /**
   * getTies
   *
   * @return Vector
   * @todo Implement this net.magicstudios.jdart.data.GameModel method
   */
  public Vector getTies() {
    return null;
  }

  /**
   * getWinner
   *
   * @return Player
   * @todo Implement this net.magicstudios.jdart.data.GameModel method
   */
  public DAPlayer getWinner() {
    return m_playerWinner;
  }

  /**
   * isTie
   *
   * @return boolean
   * @todo Implement this net.magicstudios.jdart.data.GameModel method
   */
  public boolean isTie() {
    return false;
  }

  public Zone getTarget() {
    return randomPoint;
  }

  /**
   * isWinner
   *
   * @return boolean
   * @todo Implement this net.magicstudios.jdart.data.GameModel method
   */
  public boolean isWinner() {
    return m_bWinner;
  }

  public boolean isScrolling() {
    return true;
  }

  private Zone getRandomPoint() {
    Zone point = null;
    while (point == null || point.isMiss()) {
      double rand1 = Math.random();
      double rand2 = Math.random();

      double delta = (1.0 - DartBoardDimensions.PERCENT_DOUBLE_OUTSIDE) / 2.0;
      double left = delta + (DartBoardDimensions.PERCENT_DOUBLE_OUTSIDE * rand1);
      double right = delta + (DartBoardDimensions.PERCENT_DOUBLE_OUTSIDE * rand2);

      point = this.getTriangulator().triangulate(left, right);
    }
    return point;
  }


  public void roundEnding() {

    double worstDistance = Double.MIN_VALUE;
    DAPlayer worstPlayer = null;

    int count = this.getPlayerCount();
    for (int i = 0; i < count; i++) {
      DAPlayer p = getPlayer(i);
      if (p.isEliminated() == false) {
        p.clearScore();

        /** @todo We need history to know about ROUNDS.  This is important if not all darts register. */
        double playerBestDistance = Double.MAX_VALUE;
        Zone[] history = p.getThrowHistory();
        for (int j = 0; j < 3; j++) {
          Zone current = history[history.length - j - 1];
          if (current != null && current.isMiss() == false) {
            double distance = current.getDistanceTo(randomPoint);
            if (distance < playerBestDistance) {
              playerBestDistance = distance;
            }
          }
        }

        if (playerBestDistance >= worstDistance) {
          worstPlayer = p;
          worstDistance = playerBestDistance;
        }
      }
    }

    worstPlayer.setEliminated(true);

    int eliminated = getPlayerCountNotEliminated();
    worstPlayer.setScore(eliminated + 1);

    System.out.println("Worst was " + worstPlayer.getName() + " with distance " + worstDistance);
    if (eliminated == 1) {
      m_bWinner = true;
      m_playerWinner = getPlayerNotEliminated();
      m_playerWinner.setScore(1);
    }

    randomPoint = getRandomPoint();
  }


  /**
   * score
   *
   * @param z Zone
   * @todo Implement this net.magicstudios.jdart.data.GameModel method
   */
  public void score(Zone z) {
    DAPlayer currentPlayer = getCurrentPlayer();
    currentPlayer.addZoneToHistory(z);

    if (z.isMiss() == false) {
      int scoreThrow = (int) (z.getDistanceTo(getTarget()) * 10);
      int scoreBest = currentPlayer.getScore();
      if (scoreBest == 0 || scoreThrow < scoreBest) {
        currentPlayer.setScore(scoreThrow);
      }
    }

    if (getPlayerCountNotEliminated() == 2) {

      DAPlayer lastPlayer = null;
      DAPlayer otherPlayer = null;
      int count = this.getPlayerCount();
      for (int i = 0; i < count; i++) {
        DAPlayer p = getPlayer(i);
        if (p.isEliminated() == false) {
          if (otherPlayer == null) {
            otherPlayer = p;
          }
          lastPlayer = p;
        }
      }

      if (lastPlayer == currentPlayer) {
        if (otherPlayer.getScore() > currentPlayer.getScore()) {

          // set ranking
          otherPlayer.setEliminated(true);
          otherPlayer.setScore(2);

          m_bWinner = true;
          m_playerWinner = currentPlayer;
          currentPlayer.setScore(1);
        }
      }
    }
  }
}



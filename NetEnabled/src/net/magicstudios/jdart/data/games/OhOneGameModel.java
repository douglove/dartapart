package net.magicstudios.jdart.data.games;

import java.util.Vector;
import net.magicstudios.jdart.data.service.*;

import net.magicstudios.jdart.data.*;

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
public class OhOneGameModel extends GameModel {

  private DAPlayer m_playerWinner = null;
  private boolean m_bWinner = false;
  private boolean m_bEndDouble = false;

  private int m_iOhOneValue = 301;

  public OhOneGameModel(int ohOneValue, boolean endOnDouble) {
    m_iOhOneValue = ohOneValue;
    m_bEndDouble = endOnDouble;
  }

  public void initialize() {

  }

  public boolean addPlayer(DAPlayer p) {
    p.setScore(m_iOhOneValue);
    return super.addPlayer(p);
  }

  public void score(Zone z) {

    DAPlayer currentPlayer = getCurrentPlayer();
    currentPlayer.addZoneToHistory(z);

    int iCurrentScore = currentPlayer.getScore();
    int iNewScore = iCurrentScore - z.getPoints();


    if (m_bEndDouble) {

      if (iNewScore == 0 && z.isDouble()) {
        m_bWinner = true;
        m_playerWinner = currentPlayer;

        currentPlayer.subtractScore(z.getPoints());
      } else if (iNewScore > 1) {
        currentPlayer.subtractScore(z.getPoints());
      }
    } else {
      if (iNewScore > 0) {
        currentPlayer.subtractScore(z.getPoints());
      } else if (iNewScore == 0) {

        m_bWinner = true;
        m_playerWinner = currentPlayer;

        currentPlayer.subtractScore(z.getPoints());
      }
    }
  }

  public boolean isScrolling() {
    return true;
  }

  public boolean isWinner() {
    return m_bWinner;
  }

  public DAPlayer getWinner() {
    return m_playerWinner;
  }

  public boolean isTie() {
    return false;
  }

  public Vector getTies() {
    return null;
  }
}

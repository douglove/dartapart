package net.magicstudios.jdart.data.games;

import java.util.*;
import net.magicstudios.jdart.event.*;
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
 * @author Nick Cramer (d3k199)
 * @version 1.0
 */
abstract public class GameModel {

  private Vector mPlayers = new Vector();
  private DAPlayer mPlayerCurrent = null;
  private Triangulator mTriangulator = null;

  private boolean mGameRunning = false;
  private int mThrowCount = 0;
  private int mRoundThrowCount = 0;

  private Vector mPlayerListeners = new Vector();
  private Vector mGameListeners = new Vector();

  public GameModel() {
  }


  abstract public void initialize();

  abstract public void score(Zone z);

  abstract public boolean isWinner();

  abstract public DAPlayer getWinner();

  abstract public boolean isTie();

  abstract public Vector getTies();



  /**
   *
   */
  public void startGame() {
    if (this.mPlayers.size() > 0) {
      nextPlayer();
      fireGameStarting();
      fireRoundStarting();
      mGameRunning = true;
    }
  }

  public boolean isGameRunning() {
    return mGameRunning;
  }

  public void stopGame() {
    mGameRunning = false;
  }


  /**
   *
   * @param z Zone
   */
  public void addThrow(Zone z) {
    score(z);

    boolean roundEnd = mThrowCount != 0 && (mRoundThrowCount + 1) % (3 * getPlayerCountNotEliminated()) == 0;
    if (roundEnd) {
      roundEnding();
    }

    if (mRoundThrowCount != 0 && (mRoundThrowCount + 1) % 3 == 0 && isWinner() == false) {
      nextPlayer();
    }

    if (roundEnd) {
      fireRoundEnding();
      if (isWinner() == false) {
        fireRoundStarting();
      }
      mRoundThrowCount = 0;
    }

    if (isWinner()) {
      clearPlayer();
      stopGame();
      fireGameEnding();
    }

    mThrowCount++;
    if (roundEnd == false) {
      mRoundThrowCount++;
    }
  }


  /**
   *
   * @return boolean
   */
  public boolean isScrolling() {
    return false;
  }


  public void addGameListener(GameListener listener) {
    mGameListeners.add(listener);
  }


  /**
   *
   */
  public void fireGameStarting() {
    Object [] listeners = mGameListeners.toArray();
    for (int i = 0; i < listeners.length; i++) {
      ((GameListener) listeners[i]).gameStarting();
    }
  }

  public void fireRoundStarting() {
    Object [] listeners = mGameListeners.toArray();
    for (int i = 0; i < listeners.length; i++) {
      ((GameListener) listeners[i]).roundStarting();
    }
  }

  public void fireRoundEnding() {
    Object [] listeners = mGameListeners.toArray();
    for (int i = 0; i < listeners.length; i++) {
      ((GameListener) listeners[i]).roundEnding();
    }
  }

  public void fireGameEnding() {
    Object [] listeners = mGameListeners.toArray();
    for (int i = 0; i < listeners.length; i++) {
      ((GameListener) listeners[i]).gameEnding();
    }
  }

  /**
   *
   */
  public void roundEnding() {
  }


  /**
   *
   * @param t Triangulator
   */
  public void setTriangulator(Triangulator t) {
    mTriangulator = t;
  }

  public Triangulator getTriangulator() {
    return mTriangulator;
  }

  /**
   *
   * @return Zone
   */
  public Zone getTarget() {
    return null;
  }

  /**
   *
   * @return String
   */
  public String getSharedData() {
    return null;
  }

  public void setSharedData(String data) {

  }

  /**
   *
   * @return Vector
   */
  public Vector getPlayers() {
    return mPlayers;
  }

  public DAPlayer getPlayer(int index) {
    return (DAPlayer) mPlayers.elementAt(index);
  }

  public int getPlayerCount() {
    return mPlayers.size();
  }

  public int getPlayerCountNotEliminated() {
    int count = 0;
    for (Iterator iter = mPlayers.iterator(); iter.hasNext(); ) {
      DAPlayer item = (DAPlayer) iter.next();
      if (item.isEliminated() == false) {
        count++;
      }
    }
    return count;
  }

  public DAPlayer getPlayerNotEliminated() {
    for (Iterator iter = mPlayers.iterator(); iter.hasNext(); ) {
      DAPlayer item = (DAPlayer) iter.next();
      if (item.isEliminated() == false) {
        return item;
      }
    }
    return null;
  }

  public boolean addPlayer(DAPlayer p) {
    DAPlayer pl;

    for (Iterator i = mPlayers.iterator(); i.hasNext(); ) {
      pl = (DAPlayer) i.next();
      if (p.getName().compareToIgnoreCase(pl.getName()) == 0) {
        return false;
      }
    }

    mPlayers.add(p);
    firePlayersAdded(p);

    return true;
  }

  public void removePlayer(DAPlayer p) {
    mPlayers.remove(p);
    firePlayersRemoved(p);
  }

  public void removeAllPlayers() {
    for (int i = mPlayers.size() - 1; i >= 0; i--) {
      DAPlayer item = (DAPlayer) mPlayers.elementAt(i);
      removePlayer(item);
    }
  }

  public void renamePlayer(DAPlayer p, String name) {
    p.setName(name);
    firePlayersRenamed(p);
  }


  public String getNextPlayerName() {
    int index = getPlayers().indexOf(mPlayerCurrent);
    if (index < getPlayerCount() - 1) {
      return getPlayer(index + 1).getName();
    } else {
      return getPlayer(0).getName();
    }
  }

  public void nextPlayer() {

    System.out.println("nextPlayer");

    int index = getPlayers().indexOf(mPlayerCurrent);
    if (index < getPlayerCount() - 1) {
      mPlayerCurrent = getPlayer(index + 1);
      int counter = index + 1;
      while (mPlayerCurrent.isEliminated()) {
        mPlayerCurrent = getPlayer(counter++);
      }

    } else {

      int counter = 1;
      if (getPlayerCount() > 0) {
        mPlayerCurrent = getPlayer(0);
        if (getPlayerCount() > 1) {
          while (mPlayerCurrent.isEliminated()) {
            if (counter >= getPlayerCount()) {
              System.out.println("uh oh");
            }
            mPlayerCurrent = getPlayer(counter++);
          }
        }
      }
    }
    firePlayersChanged();
  }

  public void clearPlayer() {
    mPlayerCurrent = null;
    firePlayersChanged();
  }

  public DAPlayer getCurrentPlayer() {
    return mPlayerCurrent;
  }

  /**
   *
   */
  public void addPlayerListener(PlayerListener listener) {
    mPlayerListeners.add(listener);
  }

  private void firePlayersChanged() {
    PlayerEvent evt = new PlayerEvent();
    int count = mPlayerListeners.size();
    for (int i = 0; i < count; i++) {
      ( (PlayerListener) mPlayerListeners.elementAt(i)).playerChanged(evt);
    }
  }

  private void firePlayersAdded(DAPlayer p) {
    PlayerEvent evt = new PlayerEvent(p);
    int count = mPlayerListeners.size();
    for (int i = 0; i < count; i++) {
      ( (PlayerListener) mPlayerListeners.elementAt(i)).playerAdded(evt);
    }
  }

  private void firePlayersRemoved(DAPlayer p) {
    PlayerEvent evt = new PlayerEvent(p);
    int count = mPlayerListeners.size();
    for (int i = 0; i < count; i++) {
      ( (PlayerListener) mPlayerListeners.elementAt(i)).playerRemoved(evt);
    }
  }

  private void firePlayersRenamed(DAPlayer p) {
    PlayerEvent evt = new PlayerEvent(p);
    int count = mPlayerListeners.size();
    for (int i = 0; i < count; i++) {
      ( (PlayerListener) mPlayerListeners.elementAt(i)).playerRenamed(evt);
    }
  }
}


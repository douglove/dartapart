
package net.magicstudios.jdart.data;

import java.util.*;

import net.magicstudios.jdart.data.service.*;
import net.magicstudios.jdart.event.*;
import net.magicstudios.jdart.data.games.*;
import net.magicstudios.jdart.data.monitors.*;
import net.magicstudios.jdart.util.*;
import javax.sound.sampled.*;
import java.io.*;
import javax.sound.sampled.*;
import java.net.*;

public class DartApartModel {

  private DartApartService mService = null;

  private GameModel mGameModel = null;

  private Announcer mAnnouncer = new Announcer();

  private PlayerMonitorThread mPlayerMonitor = null;
  private StatusMonitorThread mStatusMonitor = null;
  private ThrowMonitorThread mThrowMonitor = null;
  private ChatMonitorThread mChatMonitor = null;

  private boolean mLoggedIn = false;
  private String mSessionID = null;

  private DAGame mGame = null;
  private DAPlayer mPlayer = null;
  private int mThrowID = 0;
  private int mChatID = 0;

  private List<Zone> mVisibleThrows = new ArrayList<Zone>();
  private List<DAMessage> mMessages = null;

  private List<StatusListener> mStatusListeners = new ArrayList<StatusListener>();
  private List<ThrowListener> mThrowListeners = new ArrayList<ThrowListener>();
  private List<ChatListener> mChatListeners = new ArrayList<ChatListener>();

  public DartApartModel() {
    setService(new DartApartService());
  }

  /**
   *
   * @return boolean
   */
  public boolean isScrolling() {
    return false;
  }

  /**
   *
   * @param email String
   * @param password String
   * @return boolean
   */
  public boolean login(String email, String password) {
    DAPlayer player = mService.login(email, password);
    mService.setSessionID(player.getSessionID());
    mLoggedIn = player != null;

    if (mLoggedIn) {
      setPlayer(player);
    }

    return mLoggedIn;
  }

  public boolean isLoggedIn() {
    return mLoggedIn;
  }

  /**
   * setService
   * @param service DartApartService
   */
  public void setService(DartApartService service) {
    mService = service;
  }

  public DartApartService getService() {
    return mService;
  }

  /**
   * setGame
   * @param game DAGame
   */
  public void setGame(DAGame game) {
    mGame = game;
    mGameModel = GameModelFactory.getGameModel(game);
    mGameModel.initialize();
  }

  public DAGame getGame() {
    return mGame;
  }

  public GameModel getGameModel() {
    return mGameModel;
  }


  /**
   *
   * @param player DAPlayer
   */
  public void setPlayer(DAPlayer player) {
    mPlayer = player;
  }

  public DAPlayer getPlayer() {
    return mPlayer;
  }


  /**
   *
   * @param dartThrows List
   */
  public void addThrows(List<Zone> dartThrows) {

    if (dartThrows != null && dartThrows.size() > 0) {

      for (Iterator iter = dartThrows.iterator(); iter.hasNext(); ) {
        // Get a throw zone
        Zone item = (Zone) iter.next();

        // if at any point we get to three throws, reset for next player
        if (mVisibleThrows.size() == 3) {

          // clear visible throws
          mVisibleThrows.clear();
          fireThrowChanged();
        }

        // Record throws
        mVisibleThrows.add(item);
        fireThrowChanged();

        mGameModel.addThrow(item);
      }
    }
  }

  public List<Zone> getVisibleThrows() {
    return mVisibleThrows;
  }



  public void addMessages(List<DAMessage> messages) {
    for (int i = 0; i < messages.size(); i++) {
      fireMessageReceived(messages.get(i));
    }
  }


  /**
   *
   * @return String
   */
  public String getSessionID() {
    return mSessionID;
  }

  public void setSessionID(String value) {
    mSessionID = value;
  }

  public void join(DAGame game) {
    mService.joinGame(game.getID(), getPlayer().getId());
    setGame(game);

    if (mPlayerMonitor != null) {
      mPlayerMonitor.setMonitoring(false);
    }
    if (mStatusMonitor != null) {
      mStatusMonitor.setMonitoring(false);
    }

    mPlayerMonitor = new PlayerMonitorThread(this);
    mPlayerMonitor.start();

    mStatusMonitor = new StatusMonitorThread(this);
    mStatusMonitor.start();

    mChatMonitor = new ChatMonitorThread(this);
    mChatMonitor.start();
  }

  public void setGameStatus(int status) {
    DAGame game = getGame();
    mService.setGameStatus(game.getID(), status);
  }

  public void setGameStatusInprogress() {
    DAGame game = getGame();
    mService.startGame(game.getID(), getGameModel().getSharedData());
  }

  public void startGame() {

    mGameModel.startGame();

    fireThrowChanged();

    if (mThrowMonitor != null) {
      mThrowMonitor.setMonitoring(false);
    }
    mThrowMonitor = new ThrowMonitorThread(this);
    mThrowMonitor.start();
  }


  /**
   *
   * @param name String
   * @param type int
   */
  public DAGame createGame(String name, int type) {
    return mService.createGame(name, type);
  }


  /**
   *
   * @param msg String
   */
  public void postMessage(String msg) {

  }

  public void postTaunt(int taunt) {

  }



  public void announce(int taunt) {
    mAnnouncer.taunt(taunt);
  }

  public void announce(Zone z) {

    try {
      mAnnouncer.play(z);
    } catch (UnsupportedAudioFileException ex) {
    } catch (IOException ex) {
    } catch (LineUnavailableException ex) {
    }
  }


  /**
   *
   * @return int
   */
  public int getRecentThrowID() {
    return mThrowID;
  }

  public void setRecentThrowID(int throwID) {
    mThrowID = throwID;
  }


  /**
   *
   * @return int
   */
  public int getRecentChatID() {
    return mChatID;
  }

  public void setRecentChatID(int id) {
    mChatID = id;
  }


  /**
   *
   * @param z Zone
   */
  public void addThrow(Zone z) {
    mService.addThrow(getGame().getID(), getPlayer().getId(), z);
  }

  public List<Zone> getRecentThrows() {
    List<Zone> dartThrows = new ArrayList<Zone>();
    int recentThrowID = mService.getRecentThrows(getGame().getID(), getRecentThrowID(), dartThrows);
    setRecentThrowID(recentThrowID);
    return dartThrows;
  }


  /**
   *
   * @return List
   */
  public List<DAMessage> getRecentMessages() {
    List<DAMessage> messages = new ArrayList<DAMessage>();
    int id = mService.getRecentMessages(getGame().getID(), getRecentChatID(), messages);
    setRecentChatID(id);
    return messages;
  }

  /**
   *
   * @return Vector
   */
//  public Collection<DAPlayer> getPlayers() {
//    return mPlayers;
//  }
//
//  public DAPlayer getPlayer(int index) {
//    return (DAPlayer) mPlayers.toArray()[index];
//  }
//
//  public int getPlayerCount() {
//    return mPlayers.size();
//  }
//
//  public int getPlayerCountNotEliminated() {
//    int count = 0;
//    for (Iterator iter = mPlayers.iterator(); iter.hasNext(); ) {
//      Player item = (Player) iter.next();
//      if (item.isEliminated() == false) {
//        count++;
//      }
//    }
//    return count;
//  }
//
//  public Player getPlayerNotEliminated() {
//    for (Iterator iter = mPlayers.iterator(); iter.hasNext(); ) {
//      Player item = (Player) iter.next();
//      if (item.isEliminated() == false) {
//        return item;
//      }
//    }
//    return null;
//  }
//
//  public boolean addPlayer(DAPlayer p) {
//    DAPlayer pl;
//
//    for (Iterator<DAPlayer> i = mPlayers.iterator(); i.hasNext(); ) {
//      pl = i.next();
//      if (p.getName().compareToIgnoreCase(pl.getName()) == 0) {
//        return false;
//      }
//    }
//
//    mPlayers.add(p);
//    firePlayersAdded(p);
//
//    return true;
//  }
//
//  public void removePlayer(DAPlayer p) {
//    mPlayers.remove(p);
//    firePlayersRemoved(p);
//  }
//
//  public void removeAllPlayers() {
//    DAPlayer[] players = (DAPlayer[]) mPlayers.toArray();
//    for (int i = 0; i < players.length; i++) {
//      removePlayer(players[i]);
//    }
//  }
//
//  public void renamePlayer(DAPlayer p, String name) {
//    p.setName(name);
//    firePlayersRenamed(p);
//  }



  /**
   *
   * @param listener ChatListener
   */
  public void addChatListener(ChatListener listener) {
    mChatListeners.add(listener);
  }

  public void fireMessageReceived(DAMessage msg) {
    int count = mChatListeners.size();
    for (int i = 0; i < count; i++) {
      ((ChatListener) mChatListeners.get(i)).messageReceived(msg);
    }
  }



  /**
   *
   * @param listener StatusListener
   */
  public void addStatusListener(StatusListener listener) {
    mStatusListeners.add(listener);
  }

  public void fireStatusChanged() {
    StatusEvent evt = new StatusEvent();
    int count = mStatusListeners.size();
    for (int i = 0; i < count; i++) {
      ((StatusListener) mStatusListeners.get(i)).statusChanged(evt);
    }
  }


  /**
   *
   * @param listener ThrowListener
   */
  public void addThrowListener(ThrowListener listener) {
    mThrowListeners.add(listener);
  }

  private void fireThrowChanged() {
    int count = mThrowListeners.size();
    for (int i = 0; i < count; i++) {
      ((ThrowListener) mThrowListeners.get(i)).throwChanged();
    }
  }


  /**
   * isPlayerPresent
   *
   * @param item DAPlayer
   * @return boolean
   */
  public boolean isPlayerPresent(DAPlayer player) {

    Collection<DAPlayer> players = mGameModel.getPlayers();
    for (Iterator iter = players.iterator(); iter.hasNext(); ) {
      DAPlayer item = (DAPlayer) iter.next();
      if (item.getId() == player.getId()) {
        return true;
      }
    }
    return false;
  }

  class PlayerComparator implements Comparator {
    public int compare(Object o1, Object o2) {
      DAPlayer p1 = (DAPlayer) o1;
      DAPlayer p2 = (DAPlayer) o2;
      return p1.getJoinOrder() - p2.getJoinOrder();
    }

    public boolean equals(DAPlayer obj) {
      return false;
    }
  }
}


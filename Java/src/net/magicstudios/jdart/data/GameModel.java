

package net.magicstudios.jdart.data;

import java.util.*;
import net.magicstudios.jdart.event.*;



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

    private Vector m_players = new Vector();
    private Player m_playerCurrent = null;
    private Triangulator m_triangulator = null;

    private boolean m_bGameRunning = false;

    private Vector m_playerListeners = new Vector();

    public GameModel() {
    }

    abstract public void score(Zone z);

    abstract public boolean isWinner();
    abstract public Player getWinner();

    abstract public boolean isTie();
    abstract public Vector getTies();


    /**
     *
     * @param t Triangulator
     */
    public void setTriangulator(Triangulator t) {
        m_triangulator = t;
    }

    public Triangulator getTriangulator() {
        return m_triangulator;
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
     * @return Vector
     */
    public Vector getPlayers() {
        return m_players;
    }

    public Player getPlayer(int index) {
        return (Player) m_players.elementAt(index);
    }

    public int getPlayerCount() {
        return m_players.size();
    }

    public int getPlayerCountNotEliminated() {
      int count = 0;
      for (Iterator iter = m_players.iterator(); iter.hasNext(); ) {
        Player item = (Player) iter.next();
        if (item.isEliminated() == false) {
          count++;
        }
      }
      return count;
    }

    public Player getPlayerNotEliminated() {
      for (Iterator iter = m_players.iterator(); iter.hasNext(); ) {
        Player item = (Player) iter.next();
        if (item.isEliminated() == false) {
          return item;
        }
      }
      return null;
    }

    public boolean addPlayer(Player p) {
        Player pl;

        for(Iterator i = m_players.iterator(); i.hasNext();){
            pl = (Player)i.next();
            if(p.getName().compareToIgnoreCase(pl.getName()) == 0){
                return false;
            }
        }

        m_players.add(p);
        firePlayersAdded(p);

        return true;
    }

    public void removePlayer(Player p) {
        m_players.remove(p);
        firePlayersRemoved(p);
    }

    public void removeAllPlayers() {
      for (int i = m_players.size() - 1; i >= 0; i--) {
        Player item = (Player) m_players.elementAt(i);
        removePlayer(item);
      }
    }

    public void renamePlayer(Player p, String name) {
        p.setName(name);
        firePlayersRenamed(p);
    }

    public boolean isScrolling() {
      return false;
    }

    /**
     *
     */
    public void startGame() {
        if(this.m_players.size() > 0){
            m_playerCurrent = getPlayer(0);
            m_bGameRunning = true;
        }
    }

    public void stopGame() {
      m_bGameRunning = false;
    }

    public boolean isGameRunning(){
       return m_bGameRunning;
    }

    public String getNextPlayerName(){
        int index = getPlayers().indexOf(m_playerCurrent);
        if (index < getPlayerCount() - 1) {
            return getPlayer(index + 1).getName();
        } else {
            return getPlayer(0).getName();
        }
    }

    public void nextPlayer() {
        int index = getPlayers().indexOf(m_playerCurrent);
        if (index < getPlayerCount() - 1) {
          m_playerCurrent = getPlayer(index + 1);
          int counter = index + 1;
          while (m_playerCurrent.isEliminated()) {
            m_playerCurrent = getPlayer(counter++);
          }

        } else {
          nextRound();

          int counter = 1;
          if (getPlayerCount() > 0) {
            m_playerCurrent = getPlayer(0);
            while (m_playerCurrent.isEliminated()) {
              m_playerCurrent = getPlayer(counter++);
            }
          }
        }
        firePlayersChanged();
    }

    public Player getCurrentPlayer() {
        return m_playerCurrent;
    }


    public void nextRound() {
    }

    /**
     *
     */
    public void addPlayerListener(PlayerListener listener) {
        m_playerListeners.add(listener);
    }

    private void firePlayersChanged() {
        PlayerEvent evt = new PlayerEvent();
        int count = m_playerListeners.size();
        for (int i = 0; i < count; i++) {
            ((PlayerListener) m_playerListeners.elementAt(i)).playerChanged(evt);
        }
    }

    private void firePlayersAdded(Player p) {
        PlayerEvent evt = new PlayerEvent(p);
        int count = m_playerListeners.size();
        for (int i = 0; i < count; i++) {
            ((PlayerListener) m_playerListeners.elementAt(i)).playerAdded(evt);
        }
    }

    private void firePlayersRemoved(Player p) {
        PlayerEvent evt = new PlayerEvent(p);
        int count = m_playerListeners.size();
        for (int i = 0; i < count; i++) {
            ((PlayerListener) m_playerListeners.elementAt(i)).playerRemoved(evt);
        }
    }

    private void firePlayersRenamed(Player p) {
        PlayerEvent evt = new PlayerEvent(p);
        int count = m_playerListeners.size();
        for (int i = 0; i < count; i++) {
            ((PlayerListener) m_playerListeners.elementAt(i)).playerRenamed(evt);
        }
    }
}



package net.magicstudios.jdart.data;

import javax.swing.*;

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
public class Player {

    private String m_sName = "Player";
    private ImageIcon m_icon = null;
    private boolean m_isEliminated = false;
    private int m_iRank = -1;
    private int m_iScore = 0;
    private int [] m_iCloses = new int[21];
    private ArrayList arrThrowHistory = new ArrayList();

    private Vector m_scoreListeners = new Vector();

    public Player(String name) {
        m_sName = name;
    }

    public Player(String name, String icon) {
        this(name, new ImageIcon(Class.class.getResource(icon)));
    }

    public Player(String name, ImageIcon icon) {
        m_sName = name;
        m_icon = icon;
    }

    /**
     *
     * @param state boolean
     */
    public void setEliminated(boolean state) {
      m_isEliminated = state;
    }

    public boolean isEliminated() {
      return m_isEliminated;
    }

    /**
     * This is used to rank a player as they go out.  It is useful for sorting players for post game stats.
     * @param rank int
     */
    public void setRank(int rank) {
      m_iRank = rank;
    }

    public int getRank() {
      return m_iRank;
    }

    /**
     *
     * @return String[]
     */
    public Zone [] getThrowHistory() {
      Zone [] arrHist = new Zone[7];
      int i = 6;
      for (Iterator iter = arrThrowHistory.iterator(); iter.hasNext(); ) {
        arrHist[i] = (Zone) iter.next();
        i--;
      }
      return arrHist;
    }

    public String [] getThrowHistoryAsStrings() {
      String [] arrHist = new String[7];
      int i = 6;
      for (Iterator iter = arrThrowHistory.iterator(); iter.hasNext(); ) {
        arrHist[i] = iter.next().toString();
        i--;
      }
      return arrHist;

    }

    /**
     *
     * @return String
     */
    public String getName() {
        return m_sName;
    }

    /**
     *
     * @return ImageIcon
     */
    public ImageIcon getIcon() {
        return m_icon;
    }

    /**
     *
     * @return String
     */
    public void setName(String name) {
        m_sName = name;
    }

    /**
     *
     * @return int
     */
    public int getScore() {
        return m_iScore;
    }

    /**
     *
     * @return ImageIcon
     */
    public void setIcon(String icon) {
        setIcon(new ImageIcon(Class.class.getResource(icon)));
    }

    /**
     *
     * @param icon ImageIcon
     */
    public void setIcon(ImageIcon icon) {
        m_icon = icon;
    }

    /**
     *
     * @param score int
     */
    public void setScore(int score) {
      m_iScore = score;
      fireScoreChanged();
    }

    public void addScore(int points) {
      m_iScore += points;
      fireScoreChanged();
    }

    public void subtractScore(int points) {
      m_iScore -= points;
      fireScoreChanged();
    }

    /**
     *
     * @param zone int
     * @return int
     */
    public int getZone(int zone) {
        return m_iCloses[zone];
    }

    public void addZoneToHistory(Zone zone) {

      arrThrowHistory.add(0, zone);
      if (arrThrowHistory.size() > 7) {
        arrThrowHistory.remove(7);
      }

      fireScoreChanged();
    }

    public int addZone(Zone zone) {

      int remainder = 0;
      if (zone.getSlice() >= 15 || zone.getSlice() == 0) {
        int ring = zone.getRingMultiplier();
        if (m_iCloses[zone.getSlice()] + ring > 3) {
          remainder = m_iCloses[zone.getSlice()] + ring - 3;
          m_iCloses[zone.getSlice()] = 3;
        }
        else {
          m_iCloses[zone.getSlice()] += ring;
        }
        fireScoreChanged();
      }

      return remainder * zone.getSlice();
    }

    public void resetZones() {
        for (int i = 0; i < m_iCloses.length; i++) {
            m_iCloses[i] = 0;
        }
        fireScoreChanged();
    }

    public boolean isZoneClosed(Zone zone) {
        return m_iCloses[zone.getSlice()] == 3;
    }

    public boolean isAllZonesClosed() {
        boolean bAllClosed = true;
        // is 15 - 20 closed?
        for (int i = 15; i <= 20; i++) {
            if (m_iCloses[i] != 3) {
                bAllClosed = false;
                break;
            }
        }
        // and bullseye?
        return bAllClosed && m_iCloses[0] == 3;
    }


    /**
     *
     * @param listener ScoreListener
     */
    public void addScoreListener(ScoreListener listener) {
        m_scoreListeners.add(listener);
    }

    private void fireScoreChanged() {
        ScoreEvent evt = new ScoreEvent();
        int count = m_scoreListeners.size();
        for (int i = 0; i < count; i++) {
            ((ScoreListener) m_scoreListeners.elementAt(i)).scoreChanged(evt);
        }
    }
}

package net.magicstudios.jdart.data.service;

import java.text.*;
import java.util.*;

import org.jdom.*;
import net.magicstudios.jdart.data.*;
import net.magicstudios.jdart.event.*;

public class DAPlayer {

  private int id;
  private String name;
  private String description;
  private String email;
  private String geoloc;
  private String icon;
  private Date createDate;
  private Date lastLoginDate;
  private String sessionID;
  private int joinOrder;

  private boolean m_isEliminated = false;
  private int m_iScore = Integer.MAX_VALUE;
  private int[] m_iCloses = new int[21];
  private ArrayList arrThrowHistory = new ArrayList();

  private Vector m_scoreListeners = new Vector();

  public DAPlayer(Element el) throws ParseException {
    this.id = Integer.parseInt(el.getChildText("id"));
    this.name = el.getChildText("name");
    this.description = el.getChildText("description");
    this.email = el.getChildText("email");
    this.geoloc = el.getChildText("geoloc");
    this.icon = el.getChildText("icon");
    this.createDate = DAGame.FORMATTER_DB.parse(el.getChildText("create_date"));
    this.lastLoginDate = DAGame.FORMATTER_DB.parse(el.getChildText("last_login_date"));

    String sJoin = el.getChildText("join_order");
    if (sJoin != null) {
      this.joinOrder = Integer.parseInt(sJoin);
    }
  }

  /**
   *
   * @param id int
   */
  public void setId(int id) {
    this.id = id;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setGeoloc(String geoloc) {
    this.geoloc = geoloc;
  }

  public void setIcon(String icon) {
    this.icon = icon;
  }

  public void setCreateDate(Date createDate) {

    this.createDate = createDate;
  }

  public void setLastLoginDate(Date lastLoginDate) {
    this.lastLoginDate = lastLoginDate;
  }

  public void setSessionID(String sessionID) {
    this.sessionID = sessionID;
  }

  public void setJoinOrder(int joinOrder) {
    this.joinOrder = joinOrder;
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  public String getEmail() {
    return email;
  }

  public String getGeoloc() {
    return geoloc;
  }

  public String getIcon() {
    return icon;
  }

  public Date getCreateDate() {

    return createDate;
  }

  public Date getLastLoginDate() {
    return lastLoginDate;
  }

  public String getSessionID() {
    return sessionID;
  }

  public int getJoinOrder() {
    return joinOrder;
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
   *
   * @return String[]
   */
  public Zone[] getThrowHistory() {
    Zone[] arrHist = new Zone[7];
    int i = 6;
    for (Iterator iter = arrThrowHistory.iterator(); iter.hasNext(); ) {
      arrHist[i] = (Zone) iter.next();
      i--;
    }
    return arrHist;
  }

  public String[] getThrowHistoryAsStrings() {
    String[] arrHist = new String[7];
    int i = 6;
    for (Iterator iter = arrThrowHistory.iterator(); iter.hasNext(); ) {
      arrHist[i] = iter.next().toString();
      i--;
    }
    return arrHist;
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
      } else {
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
   */
  public int getScore() {
    return m_iScore;
  }

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

  public void clearScore() {
    m_iScore = Integer.MAX_VALUE;
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
      ( (ScoreListener) m_scoreListeners.elementAt(i)).scoreChanged(evt);
    }
  }

  public boolean equals(DAPlayer player) {
    return getEmail().equals(player.getEmail());
  }
}

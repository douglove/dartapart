package net.magicstudios.jdart.data.service;

import java.util.*;
import java.text.*;
import org.jdom.*;
import javax.swing.*;
import net.magicstudios.jdart.ui.icons.*;

public class DAGame {

  public static final Icon [] ICON_GAME_TYPE = new Icon[] {
      IconLoader.loadImage("lastman-small.jpg"),
      IconLoader.loadImage("301-small.jpg"),
      IconLoader.loadImage("201-small.jpg"),
      IconLoader.loadImage("101-small.jpg"),
      IconLoader.loadImage("cricket-give-small.jpg"),
      IconLoader.loadImage("cricket-take-small.jpg"),
  };

  public static final String [] GAME_TYPES = new String [] {
      "Last Man Standing",
      "301",
      "201",
      "101",
      "Cricket Give Points",
      "Cricket Take Points"};

  public static final int GAME_LAST_MAN_STANDING = 0;
  public static final int GAME_301 = 1;
  public static final int GAME_201 = 2;
  public static final int GAME_101 = 3;
  public static final int GAME_CRICKET_GIVE = 4;
  public static final int GAME_CRICKET_TAKE = 5;


  public static final int STATUS_AWAITING_PLAYERS = 0;
  public static final int STATUS_INPROGRESS = 1;
  public static final int STATUS_COMPLETE   = 2;


  public static final String DATE_FORMAT = "MMM d, yyyy hh:mm a";
  public static final String DATE_FORMAT_DB = "yyyy-MM-dd HH:mm:ss";
  public static final SimpleDateFormat FORMATTER = new SimpleDateFormat(DATE_FORMAT);
  public static final SimpleDateFormat FORMATTER_DB = new SimpleDateFormat(DATE_FORMAT_DB);

  private int mID = 0;
  private int mStatus = 0;
  private int mType = DAGame.GAME_LAST_MAN_STANDING;
  private String mName = "Dart Game";
  private Date mCreated = new Date();
  private Date mModified = new Date();
  private String mPlayers = "";
  private int mPlayerCount = 0;
  private String mSharedData = "";


  public DAGame(Element el) throws ParseException {
    mID = Integer.parseInt(el.getChildText("id"));
    mType = Integer.parseInt(el.getChildText("type"));
    mStatus = Integer.parseInt(el.getChildText("status"));
    mName = el.getChildText("name");
    mCreated = FORMATTER_DB.parse(el.getChildText("create_date"));
    mModified = FORMATTER_DB.parse(el.getChildText("modify_date"));
    mPlayers = el.getChildText("players");
    mPlayerCount = Integer.parseInt(el.getChildText("count"));
    mSharedData = el.getChildText("data");
  }

  public DAGame(int id, int type, int status, String name, long create, long mod) {
    mID = id;
    mType = type;
    mStatus = status;
    mName = name;
    mCreated = new Date(create);
    mModified = new Date(mod);
  }


  public int getID() {
    return mID;
  }

  public int getStatus() {
    return mStatus;
  }

  public void setStatus(int status) {
    mStatus = status;
  }

  /**
   *
   * @return int
   */
  public int getType() {
    return mType;
  }

  public String getName() {
    return mName;
  }

  public String getPlayers() {
    return mPlayers;
  }

  public int getPlayerCount() {
    return mPlayerCount;
  }

  /**
   *
   * @return Date
   */
  public Date getCreatedDate() {
    return mCreated;
  }

  public String getCreatedDateString() {
    return formatDate(getCreatedDate());
  }


  /**
   *
   * @return Date
   */
  public Date getModifiedDate() {
    return mModified;
  }

  public String getModifiedDateString() {
    return formatDate(getModifiedDate());
  }



  /**
   *
   * @param date Date
   * @return String
   */
  public static String formatDate(Date date) {
    return FORMATTER.format(date);
  }



  /**
   *
   * @return String
   */
  public String getGameTypeName() {
    return GAME_TYPES[getType()];
  }

  /**
   *
   * @return Icon
   */
  public Icon getIcon() {
    return DAGame.ICON_GAME_TYPE[getType()];
  }

  /**
   *
   * @return String
   */
  public String getSharedData() {
    return mSharedData;
  }

  public void setSharedData(String data) {
    mSharedData = data;
  }

}

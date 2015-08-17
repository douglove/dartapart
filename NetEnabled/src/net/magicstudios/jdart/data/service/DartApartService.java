


package net.magicstudios.jdart.data.service;

import java.io.*;
import java.net.*;
import java.text.*;
import java.util.*;

import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.methods.*;
import org.apache.commons.httpclient.params.*;
import org.jdom.*;
import org.jdom.input.*;
import net.magicstudios.jdart.data.*;

public class DartApartService {

  private String mSessionID = null;
  private String mURL = "http://localhost/dartapart/da.php";

    // Create an instance of HttpClient.
  private HttpClient mClient = new HttpClient();


  public DartApartService() {
  }

  public DartApartService(String url) {
    mURL = url;
  }

  /**
   *
   * @param id int
   */
  public DAPlayer login(String email, String password) {

    DAPlayer player = null;

    HashMap<String, String> params = new HashMap();
    params.put("email", email);
    params.put("password", password);

    String sMessage = fetch("login", params);

    SAXBuilder builder = new SAXBuilder();

    try {
      Document doc = builder.build(new StringReader(sMessage));
      String sessionID = doc.getRootElement().getChildText("session");

      player = new DAPlayer(doc.getRootElement().getChild("player"));
      player.setSessionID(sessionID);

    } catch (ParseException ex1) {
    } catch (IOException ex) {
    } catch (JDOMException ex) {
    }

    return player;
  }

  /**
   *
   * @return String
   */
  public String getSessionID() {
    return mSessionID;
  }

  public void setSessionID(String id) {
    mSessionID = id;
  }


  /**
   *
   * @param id int
   */
  public void joinGame(int gameID, int playerID) {

    HashMap<String, String> params = new HashMap();
    params.put("gameid", Integer.toString(gameID));
    params.put("playerid", Integer.toString(playerID));

    fetch("joingame", params);
  }


  /**
   *
   * @param id int
   */
  public void startGame(int gameID, String sharedData) {

    HashMap<String, String> params = new HashMap();
    params.put("gameid", Integer.toString(gameID));
    params.put("status", Integer.toString(DAGame.STATUS_INPROGRESS));
    params.put("data", sharedData);

    fetch("updatestatus", params);
  }


  /**
   *
   * @param id int
   */
  public void setGameStatus(int gameID, int status) {

    HashMap<String, String> params = new HashMap();
    params.put("gameid", Integer.toString(gameID));
    params.put("status", Integer.toString(status));
    params.put("data", "");

    fetch("updatestatus", params);
  }


  /**
   *
   * @param name String
   * @param type int
   */
  public DAGame createGame(String name, int type) {

    HashMap<String, String> params = new HashMap();
    params.put("name", name);
    params.put("type", Integer.toString(type));
    params.put("status", Integer.toString(DAGame.STATUS_AWAITING_PLAYERS));

    String sMessage = fetch("creategame", params);

    SAXBuilder builder = new SAXBuilder();
    try {
      Document doc = builder.build(new StringReader(sMessage));
      List games = doc.getRootElement().getChildren("game");

      for (Iterator iter = games.iterator(); iter.hasNext(); ) {
        Element item = (Element) iter.next();
        try {

          DAGame game = new DAGame(item);
          return game;

        } catch (ParseException ex1) {
          ex1.printStackTrace();
        }
      }
    } catch (IOException ex) {
    } catch (JDOMException ex) {
    }

    return null;

  }


  public void addThrow(int gameID, int playerID, Zone z) {

    HashMap<String, String> params = new HashMap();
    params.put("gameid", Integer.toString(gameID));
    params.put("playerid", Integer.toString(playerID));
    params.put("x", Double.toString(z.getX()));
    params.put("y", Double.toString(z.getY()));

    fetch("addthrow", params);
  }


  /**
   *
   * @param msg String
   */
  public void postMessage(int gameID, String msg) {

System.out.println("posting message");
    HashMap<String, String> params = new HashMap();
    params.put("gameid", Integer.toString(gameID));
    params.put("message", msg);

    fetch("addmessage", params);
  }

  public void postTaunt(int gameID, int taunt) {

System.out.println("posting taunt");
    HashMap<String, String> params = new HashMap();
    params.put("gameid", Integer.toString(gameID));
    params.put("taunt", Integer.toString(taunt));

    fetch("addtaunt", params);
  }


  /**
   *
   * @param gameID int
   * @param recentThrowID int
   * @param dartThrows List
   * @return int
   */
  public int getRecentThrows(int gameID, int recentThrowID, List<Zone> dartThrows) {

    HashMap<String, String> params = new HashMap();
    params.put("gameid", Integer.toString(gameID));
    params.put("throwid", Integer.toString(recentThrowID));


    String sMessage = fetch("throws", params);


    SAXBuilder builder = new SAXBuilder();
    try {
      Document doc = builder.build(new StringReader(sMessage));
      List games = doc.getRootElement().getChildren("throw");

      for (Iterator iter = games.iterator(); iter.hasNext(); ) {
        Element item = (Element) iter.next();

        recentThrowID = Math.max(recentThrowID, Integer.parseInt(item.getChildText("id")));

        double x = Double.parseDouble(item.getChildText("x"));
        double y = Double.parseDouble(item.getChildText("y"));

        //Zone z = new Zone(1, 1, x, y);
        ZoneConverter zc = new ZoneConverter();
        Zone z = zc.zone(x, y);
        System.out.println("Throw: " + z.toString());
        dartThrows.add(z);
      }
    } catch (IOException ex) {
    } catch (JDOMException ex) {
    }

    return recentThrowID;

 }


 /**
  *
  * @param gameID int
  * @param recentThrowID int
  * @param dartThrows List
  * @return int
  */
 public int getRecentMessages(int gameID, int recentID, List<DAMessage> messages) {

   HashMap<String, String> params = new HashMap();
   params.put("gameid", Integer.toString(gameID));
   params.put("chatid", Integer.toString(recentID));

   String sMessage = fetch("chats", params);

   SAXBuilder builder = new SAXBuilder();
   try {
     Document doc = builder.build(new StringReader(sMessage));
     List games = doc.getRootElement().getChildren("throw");

     for (Iterator iter = games.iterator(); iter.hasNext(); ) {
       Element item = (Element) iter.next();

       recentID = Math.max(recentID, Integer.parseInt(item.getChildText("id")));

       DAMessage m = null;
       String message = item.getChildText("message");
       if (message == null) {
         m = new DAMessage(Integer.parseInt(item.getChildText("taunt")));
       } else {
         m = new DAMessage(message);
       }

System.out.println("got message");
       messages.add(m);
     }
   } catch (IOException ex) {
   } catch (JDOMException ex) {
   }

   return recentID;
 }



  /**
   *
   * @param name String
   * @param type int
   */
  public DAGame getGameInfo(int gameID) {

    HashMap<String, String> params = new HashMap();
    params.put("gameid", Integer.toString(gameID));

    String sMessage = fetch("gamestatus", params);

    SAXBuilder builder = new SAXBuilder();
    try {
      Document doc = builder.build(new StringReader(sMessage));
      List games = doc.getRootElement().getChildren("game");

      for (Iterator iter = games.iterator(); iter.hasNext(); ) {
        Element item = (Element) iter.next();
        try {

          DAGame game = new DAGame(item);
          return game;

        } catch (ParseException ex1) {
          ex1.printStackTrace();
        }
      }
    } catch (IOException ex) {
    } catch (JDOMException ex) {
    }

    return null;
  }


  /**
   *
   * @return List
   */
  public List<DAGame> getGameList() {

    ArrayList<DAGame> list = new ArrayList<DAGame>();

    String sMessage = fetch();

    SAXBuilder builder = new SAXBuilder();
    try {
      Document doc = builder.build(new StringReader(sMessage));
      List games = doc.getRootElement().getChildren("game");

      for (Iterator iter = games.iterator(); iter.hasNext(); ) {
        Element item = (Element) iter.next();
        try {
          DAGame game = new DAGame(item);
          list.add(game);
        } catch (ParseException ex1) {
          ex1.printStackTrace();
        }
      }
    } catch (IOException ex) {
    } catch (JDOMException ex) {
    }
    return list;
  }

  /**
   * getGamePlayers
   *
   * @param gameID int
   * @return List
   */
  public List<DAPlayer> getGamePlayers(int gameID) {

    List<DAPlayer> players = new ArrayList<DAPlayer>();

    HashMap<String, String> params = new HashMap();
    params.put("gameid", Integer.toString(gameID));

    String sMessage = fetch("gameplayers", params);

    SAXBuilder builder = new SAXBuilder();

    try {
      Document doc = builder.build(new StringReader(sMessage));
      List playerElements = doc.getRootElement().getChildren("player");

      for (Iterator iter = playerElements.iterator(); iter.hasNext(); ) {
        Element item = (Element) iter.next();

        DAPlayer player = new DAPlayer(item);
        players.add(player);
      }

    } catch (ParseException ex1) {
    } catch (IOException ex) {
    } catch (JDOMException ex) {
    }

    return players;
  }



  public String fetch() {
    return fetch("games");
  }

  public String fetch(String action) {
    return fetch(action, null);
  }

  public synchronized String fetch(String action, HashMap<String, String> params) {

    String sMessage = null;

    String qs = "?action=" + action;

    if (params != null) {
      for (Iterator<Map.Entry<String, String>> iter = params.entrySet().iterator(); iter.hasNext(); ) {
        Map.Entry<String, String> item = iter.next();
        try {
          qs += "&" + URLEncoder.encode(item.getKey(), "UTF-8") + "=" + URLEncoder.encode(item.getValue(), "UTF-8");
        } catch (UnsupportedEncodingException ex) {
        }
      }
    }

    // Create a method instance.
    GetMethod method = new GetMethod(mURL + "?" + qs);

    // Provide custom retry handler is necessary
    method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
                                    new DefaultHttpMethodRetryHandler(1, false));

    try {
      // Execute the method.
      int statusCode = mClient.executeMethod(method);

      if (statusCode != HttpStatus.SC_OK) {
        System.err.println("Method failed: " + method.getStatusLine());
      }

      // Read the response body.
      byte[] responseBody = method.getResponseBody();

      // Deal with the response.
      // Use caution: ensure correct character encoding and is not binary data
      sMessage = new String(responseBody);

    } catch (HttpException e) {
      System.err.println("Fatal protocol violation: " + e.getMessage());
      e.printStackTrace();
    } catch (IOException e) {
      System.err.println("Fatal transport error: " + e.getMessage());
      e.printStackTrace();
    } finally {
      // Release the connection.
      method.releaseConnection();
    }

    return sMessage;
  }
}

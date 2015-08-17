package net.magicstudios.jdart.data.service;

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
public class DAMessage {

  private String message = null;
  private int taunt = 0;

  public DAMessage(String message) {
    this.message = message;
  }

  public DAMessage(int taunt) {
    this.taunt = taunt;
  }

  /**
   *
   * @return boolean
   */
  public boolean isTaunt() {
    return message == null;
  }

  public boolean isMessage() {
    return message != null;
  }

  /**
   *
   * @return String
   */
  public String getMessage() {
    return message;
  }

  public int getTaunt() {
    return taunt;
  }
}

package net.magicstudios.jdart.event;

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
public interface GameListener {

  public void gameStarting();
  public void roundStarting();
  public void roundEnding();
  public void gameEnding();
}

package net.magicstudios.jdart.event;

import net.magicstudios.jdart.data.service.*;

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
public interface ChatListener {

  public void messagePosted(String msg);
  public void tauntPosted(int taunt);

  public void messageReceived(DAMessage msg);
}

package net.magicstudios.jdart.event;

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
public class PlayerEvent {

    private Player m_player = null;

    public PlayerEvent() {
    }

    public PlayerEvent(Player p) {
        m_player = p;
    }

    public Player getPlayer() {
        return m_player;
    }
}

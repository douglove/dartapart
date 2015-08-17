package net.magicstudios.jdart.event;

import net.magicstudios.jdart.data.*;
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
 * @author Nick Cramer (d3k199)
 * @version 1.0
 */
public class PlayerEvent {

    private DAPlayer m_player = null;

    public PlayerEvent() {
    }

    public PlayerEvent(DAPlayer p) {
        m_player = p;
    }

    public DAPlayer getPlayer() {
        return m_player;
    }
}

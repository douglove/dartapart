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
public class DartEvent {

    private Zone m_zone = null;

    public DartEvent(Zone z) {
        m_zone = z;
    }

    public Zone getZone() {
        return m_zone;
    }
}

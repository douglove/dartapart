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
 * @author Nick Cramer (d3k199) Doug Love (d3m431)
 * @version 1.0
 */
public class CorrectModeEvent {
    private boolean m_bCorrectMode = false;

    public CorrectModeEvent(boolean correctMode) {
        m_bCorrectMode = correctMode;
    }

    public boolean getCorrectMode(){
        return m_bCorrectMode;
    }
}

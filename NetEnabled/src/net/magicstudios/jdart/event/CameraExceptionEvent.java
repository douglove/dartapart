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
public class CameraExceptionEvent {
    private Exception m_CameraException;

    public CameraExceptionEvent(Exception cameraException) {
        m_CameraException = cameraException;
    }

    public Exception getCameraException(){
        return m_CameraException;
    }
}

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
 * @author Nick Cramer (d3k199)
 * @version 1.0
 */
public interface DartListener {
    public void dartsAdded(DartEvent evt);
    public void dartsCleared();
    public void cameraException(CameraExceptionEvent cam);
    public void occlusionDetected();
}

package net.magicstudios.jdart.comm;

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
public interface ServerCommListener {

    public void dartDetected(double left, double right);
    public void occlusionDetected();
    public void boardCleared();

}

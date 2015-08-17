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
public class CameraTester implements ServerCommListener {
    public CameraTester() {

        ServerCommModel model = new ServerCommModel("localhost", 12000);
        model.addBoardListener(this);
        model.connect();
    }

    public static void main(String[] args) {
        CameraTester cameratester = new CameraTester();
    }

    public void dartDetected(double left, double right) {
        System.out.println("Dart Detected: " + left + ", " + right);
    }

    public void occlusionDetected() {
        System.out.println("Occlusion detected");
    }

    public void boardCleared() {
        System.out.println("Dart board cleared");
    }
}

package net.magicstudios.jdart.game;

import net.magicstudios.jdart.data.*;
import net.magicstudios.jdart.event.*;
import javax.swing.*;


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
public class TestCricketGame implements PlayerListener, ScoreListener, DartListener {

    private CricketGameModel m_model = new CricketGameModel();
    private int m_iThrows = 0;

    public TestCricketGame() {

        m_model.addPlayerListener(this);

        m_model.addPlayer(new Player("Nick"));
        m_model.addPlayer(new Player("Doug"));

        for (int i = 0; i < m_model.getPlayerCount(); i++) {
            m_model.getPlayer(i).addScoreListener(this);
        }

        int rc = JOptionPane.showConfirmDialog(null, "Swap ports?", "Swap", JOptionPane.YES_NO_OPTION);
        String host = JOptionPane.showInputDialog("Host?", "localhost");

        if (host != null) {
            Triangulator tri = new Triangulator(host);
            tri.addDartListener(this);

            m_model.setGivePoints(true);
            m_model.startGame();

            tri.start();
        }
    }

    public static void main(String[] args) {
        TestCricketGame testcricketgame = new TestCricketGame();
    }

    public void playerAdded(PlayerEvent evt) {
        System.out.println("Player Added: " + evt.getPlayer().getName());
    }

    public void playerRemoved(PlayerEvent evt) {
    }

    public void playerChanged(PlayerEvent evt) {
    }

    public void scoreChanged(ScoreEvent evt) {

        System.out.println("Score List");
        for (int i = 0; i < m_model.getPlayerCount(); i++) {
            Player p = m_model.getPlayer(i);
            System.out.println(p.getName() + ": " + p.getScore());
            for (int j = 15; j <= 20; j++) {
                System.out.println("Zone " + j + ": " + p.getZone(j));
            }
            System.out.println("Zone BE: " + p.getZone(0));
        }

        if (m_model.isWinner()) {
            System.out.println("************ WINNER! ***********");
            System.out.println("     " + m_model.getWinner().getName());
            System.out.println("************ WINNER! ***********");
        }
    }

    public void dartsAdded(DartEvent evt) {

        System.out.println(evt.getZone().getSlice() + " " + evt.getZone().getRing());
        m_model.score(evt.getZone());
        m_iThrows++;

        if (m_iThrows == 3) {
            m_iThrows = 0;
            m_model.nextPlayer();
            System.out.println("\nNext Player: " + m_model.getCurrentPlayer().getName());
            JOptionPane.showMessageDialog(null, "Your turn " + m_model.getCurrentPlayer().getName());
        }
    }

    public void dartsCleared() {

    }

    public void playerRenamed(PlayerEvent evt) {
    }

    public void cameraException(CameraExceptionEvent cam) {
    }

    public void occlusionDetected() {
    }
}

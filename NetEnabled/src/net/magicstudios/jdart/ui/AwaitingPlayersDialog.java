package net.magicstudios.jdart.ui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import net.magicstudios.jdart.data.*;
import net.magicstudios.jdart.event.*;
import net.magicstudios.jdart.ui.icons.*;
import net.magicstudios.jdart.data.games.*;

public class AwaitingPlayersDialog extends JDialog implements PlayerListener {

  private DartApartModel model = null;

  private Icon icoLoading = IconLoader.loadImage("waiting.gif");
  private JPanel panel1 = new JPanel();
  private GridBagLayout gridBagLayout1 = new GridBagLayout();
  private JLabel lblWaiting = new JLabel();
  private JButton btnStartGame = new JButton();
  private JLabel lblPlayerCount = new JLabel();

  public AwaitingPlayersDialog(Frame owner, String title, boolean modal) {
    super(owner, title, modal);
    try {
      setDefaultCloseOperation(DISPOSE_ON_CLOSE);
      jbInit();
      pack();
    } catch (Exception exception) {
      exception.printStackTrace();
    }
  }

  public AwaitingPlayersDialog() {
    this(new Frame(), "Awaiting Players", false);
  }

  private void jbInit() throws Exception {
    panel1.setLayout(gridBagLayout1);
    lblWaiting.setFont(new java.awt.Font("Tahoma", Font.PLAIN, 20));
    lblWaiting.setIcon(icoLoading);
    lblWaiting.setText("   Awaiting other players ...");
    btnStartGame.setText("Start Game");
    btnStartGame.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        btnStartGame_actionPerformed(e);
      }
    });
    panel1.setBackground(new Color(239, 239, 239));
    lblPlayerCount.setText("X player present");
    getContentPane().add(panel1);
    panel1.add(lblWaiting, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
                                               , GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(20, 20, 0, 20), 0, 0));
    panel1.add(btnStartGame, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
        , GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(10, 5, 20, 5), 0, 0));
    panel1.add(lblPlayerCount, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
                                               , GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(10, 0, 20, 0), 0, 0));
    this.getRootPane().setDefaultButton(btnStartGame);
  }

  public void setModel(DartApartModel model) {
    this.model = model;

    updatePlayerCountLabel();
    model.getGameModel().addPlayerListener(this);
  }

  private void updatePlayerCountLabel() {
    int playerCount = model.getGameModel().getPlayerCount();
    lblPlayerCount.setText(playerCount + ((playerCount > 1) ? " players present" : " player present"));
    btnStartGame.setEnabled(playerCount > 1);
  }

  public void btnStartGame_actionPerformed(ActionEvent e) {
    model.setGameStatusInprogress();
    setVisible(false);
  }

  public void playerChanged(PlayerEvent evt) {
  }

  public void playerAdded(PlayerEvent evt) {
    updatePlayerCountLabel();
  }

  public void playerRemoved(PlayerEvent evt) {
    updatePlayerCountLabel();
  }

  public void playerRenamed(PlayerEvent evt) {
  }
}

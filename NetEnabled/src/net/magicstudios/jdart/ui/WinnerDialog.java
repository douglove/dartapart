package net.magicstudios.jdart.ui;

import java.awt.*;
import javax.swing.*;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class WinnerDialog extends JDialog {
  private JPanel panel1 = new JPanel();
  private GridBagLayout gridBagLayout1 = new GridBagLayout();
  private JLabel jLabel1 = new JLabel();
  private JLabel lblPlayerName = new JLabel();
  private JButton btnNewGame = new JButton();
  private JButton btnReplayGame = new JButton();

  private boolean replay = true;

  public WinnerDialog(Frame owner, String title, boolean modal) {
    super(owner, title, modal);
    try {
      setDefaultCloseOperation(DISPOSE_ON_CLOSE);
      jbInit();
    }
    catch (Exception exception) {
      exception.printStackTrace();
    }
  }

  public WinnerDialog() {
    this(new Frame(), "WinnerDialog", false);
  }

  private void jbInit() throws Exception {
    panel1.setLayout(gridBagLayout1);
    jLabel1.setFont(new java.awt.Font("Dialog", Font.PLAIN, 36));
    jLabel1.setHorizontalAlignment(SwingConstants.CENTER);
    jLabel1.setHorizontalTextPosition(SwingConstants.CENTER);
    jLabel1.setText("Winner!");
    lblPlayerName.setFont(new java.awt.Font("Dialog", Font.BOLD, 52));
    lblPlayerName.setHorizontalAlignment(SwingConstants.CENTER);
    lblPlayerName.setHorizontalTextPosition(SwingConstants.CENTER);
    lblPlayerName.setText("Player 1");
    btnNewGame.setFont(new java.awt.Font("Dialog", Font.PLAIN, 18));
    btnNewGame.setPreferredSize(new Dimension(10, 50));
    btnNewGame.setText("New Game");
    btnNewGame.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        btnNewGame_actionPerformed(e);
      }
    });
    btnReplayGame.setFont(new java.awt.Font("Dialog", Font.PLAIN, 18));
    btnReplayGame.setPreferredSize(new Dimension(10, 50));
    btnReplayGame.setText("Replay Game");
    btnReplayGame.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        btnReplayGame_actionPerformed(e);
      }
    });
    getContentPane().add(panel1);
    panel1.add(lblPlayerName, new GridBagConstraints(0, 1, 2, 1, 1.0, 1.0
                                               , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
    panel1.add(jLabel1, new GridBagConstraints(0, 0, 2, 1, 0.0, 0.0
                                               , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(10, 0, 0, 0), 0, 0));
    panel1.add(btnNewGame, new GridBagConstraints(0, 2, 1, 1, 1.0, 0.0
                                                  , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
    panel1.add(btnReplayGame, new GridBagConstraints(1, 2, 1, 1, 1.0, 0.0
        , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));

    getRootPane().setDefaultButton(btnReplayGame);
    setSize(400, 250);
  }

  public void setVisible(boolean state) {
    final JDialog parent = this;
    Thread t = new Thread() {
      public void run() {

        for (int i = 0; i < 10; i++) {
          try {
            Thread.sleep(1000);
          }
          catch (InterruptedException ex) {
          }
          btnReplayGame.setText("Replay game in " + (10 - i));
        }

        if (parent.isVisible()) {
          setVisible(false);
        }
      }
    };

    if (state) {
      t.start();
    }
    UIUtil.centerWindow(this);
    super.setVisible(state);
  }

  public void setTitle(String title) {
    jLabel1.setText(title);
  }

  public void setWinnerName(String winner) {
    lblPlayerName.setText(winner);
  }

  public boolean isReplay() {
    return replay;
  }

  public void btnNewGame_actionPerformed(ActionEvent e) {
    replay = false;
    setVisible(false);
  }

  public void btnReplayGame_actionPerformed(ActionEvent e) {
    replay = true;
    setVisible(false);
  }
}



package net.magicstudios.jdart.ui;

import java.awt.*;
import javax.swing.*;
import java.awt.Font;
import net.magicstudios.jdart.ui.icons.*;
import net.magicstudios.jdart.data.service.*;
import java.util.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import net.magicstudios.jdart.event.JoinListener;

public class GamePanel extends JPanel {

  private DAGame mGame = null;
  private JoinListener listener = null;
  private Icon icoGameType = IconLoader.loadImage("301-small.jpg");
  private GridBagLayout gridBagLayout1 = new GridBagLayout();
  private JLabel lblGameName = new JLabel();
  private JLabel lblGameType = new JLabel();
  private JLabel lblIcon = new JLabel();
  private JLabel lblPlayers = new JLabel();
  private JLabel lblTime = new JLabel();
  private JLabel lblJoin = new JLabel();
  private Cursor curHand = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);
  private Cursor curDef = null;

  public GamePanel() {
    try {
      jbInit();
    } catch (Exception exception) {
      exception.printStackTrace();
    }
  }

  private void jbInit() throws Exception {
    this.setLayout(gridBagLayout1);
    lblGameName.setText("My Game");
    lblGameType.setFont(new java.awt.Font("Tahoma", Font.BOLD, 14));
    lblGameType.setText("Last Man Standing");
    curDef = lblIcon.getCursor();
    lblIcon.setIcon(icoGameType);
    lblIcon.setCursor(curHand);
    lblIcon.addMouseListener(new MouseAdapter() {
      public void mouseClicked(MouseEvent e) {
        lblIcon_mouseClicked(e);
      }
    });
    lblPlayers.setFont(new java.awt.Font("Tahoma", Font.PLAIN, 10));
    lblPlayers.setText("Nick, Doug, Carolyn");
    lblTime.setFont(new java.awt.Font("Tahoma", Font.PLAIN, 10));
    lblTime.setText("Created 15 minutes ago by Doug");
    lblJoin.setFont(new java.awt.Font("Tahoma", Font.BOLD, 12));
    lblJoin.setText("<html><a href=\"#\">Join Game</a></html>");
    lblJoin.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    lblJoin.addMouseListener(new MouseAdapter() {
      public void mouseClicked(MouseEvent e) {
        lblJoin_mouseClicked(e);
      }
    });
    this.add(lblGameName, new GridBagConstraints(1, 1, 1, 1, 1.0, 0.0
                                                 , GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
    this.add(lblIcon, new GridBagConstraints(0, 0, 1, 3, 0.0, 0.0
                                             , GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    this.add(lblGameType, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
                                                 , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 0, 0, 0), 0, 0));
    this.add(lblJoin, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0
                                             , GridBagConstraints.NORTHEAST, GridBagConstraints.NONE, new Insets(5, 0, 0, 5), 0, 0));
    this.add(lblTime, new GridBagConstraints(2, 2, 1, 1, 0.0, 0.0
                                             , GridBagConstraints.SOUTHEAST, GridBagConstraints.NONE, new Insets(0, 0, 5, 5), 0, 0));
    this.add(lblPlayers, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0
                                                , GridBagConstraints.SOUTHWEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 5, 0), 0, 0));
  }

  public void setGameInfo(DAGame game) {
    mGame = game;

    lblGameType.setText(game.getGameTypeName());
    lblGameName.setText(game.getName());
    lblIcon.setIcon(mGame.getIcon());
    lblPlayers.setText(mGame.getPlayers());

    String message = null;
    Date d = game.getCreatedDate();
    long delta = System.currentTimeMillis() - d.getTime();

    if (delta / (60L * 1000L) < 100) {
      int x = (int) ((delta / (60L * 1000L)) + 1);
      message = "Created " + x + " minute" + ((x == 1) ? "" : "s") + " ago";
    } else if (delta / (60L * 60L * 1000L) < 100) {
      int x = (int) ((delta / (60L * 60L * 1000L)) + 1);
      message = "Created " + x + " hour" + ((x == 1) ? "" : "s") + " ago";
    } else if (delta / (24L * 60L * 60L * 1000L) < 100) {
      int x = (int) ((delta / (24L * 60L * 60L * 1000L)) + 1);
      message = "Created " + x + " day" + ((x == 1) ? "" : "s") + " ago";
    }

    lblTime.setText(message);

    switch (mGame.getStatus()) {
      case DAGame.STATUS_AWAITING_PLAYERS:
        lblJoin.setText("<html><a href=\"#\">Join Game</a></html>");
        lblJoin.setCursor(curHand);
        lblIcon.setCursor(curHand);
        break;
      case DAGame.STATUS_INPROGRESS:
        lblJoin.setText("In Progress");
        lblJoin.setCursor(curDef);
        lblIcon.setCursor(curDef);
        break;
      case DAGame.STATUS_COMPLETE:
        lblJoin.setText("Game Over");
        lblJoin.setCursor(curDef);
        lblIcon.setCursor(curDef);
        break;
    }
  }

  public void setJoinListener(JoinListener listener) {
    this.listener = listener;
  }

  public void lblJoin_mouseClicked(MouseEvent e) {
    listener.joinRequested(mGame);
  }

  public void setColor(Color c) {
    lblGameName.setBackground(c);
    lblGameType.setBackground(c);
    lblIcon.setBackground(c);
    lblPlayers.setBackground(c);
    lblTime.setBackground(c);
    lblJoin.setBackground(c);
    setBackground(c);
  }

  public void lblIcon_mouseClicked(MouseEvent e) {
    listener.joinRequested(mGame);
  }
}

package net.magicstudios.jdart.ui;

import java.awt.*;
import javax.swing.*;
import net.magicstudios.jdart.ui.icons.*;
import java.awt.Font;
import java.awt.BorderLayout;
import java.awt.Insets;
import java.awt.Dimension;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import net.magicstudios.jdart.data.service.*;

public class CreateGameDialog extends JDialog {

  private int mGameType = DAGame.GAME_LAST_MAN_STANDING;
  private boolean mCancelled = true;

  private JPanel panel1 = new JPanel();
  private GridBagLayout gridBagLayout1 = new GridBagLayout();
  private JLabel jLabel1 = new JLabel();
  private JTextField txtGameName = new JTextField();
  private JButton btnLastMan = new JButton();
  private JButton btnCricketGive = new JButton();
  private JButton btnCricketTake = new JButton();
  private JButton btn301 = new JButton();
  private JScrollPane jScrollPane1 = new JScrollPane();
  private JTextArea jTextArea1 = new JTextArea();
  private JPanel jPanel1 = new JPanel();
  private GridBagLayout gridBagLayout2 = new GridBagLayout();
  private JScrollPane jScrollPane2 = new JScrollPane();
  private JTextArea jTextArea2 = new JTextArea();
  private JScrollPane jScrollPane3 = new JScrollPane();
  private JScrollPane jScrollPane4 = new JScrollPane();
  private JButton btn201 = new JButton();
  private JButton btn101 = new JButton();
  private JTextArea jTextArea3 = new JTextArea();
  private JTextArea jTextArea4 = new JTextArea();
  private JScrollPane jScrollPane5 = new JScrollPane();
  private JScrollPane jScrollPane6 = new JScrollPane();
  private JTextArea jTextArea5 = new JTextArea();
  private JTextArea jTextArea6 = new JTextArea();
  private GridBagLayout gridBagLayout3 = new GridBagLayout();

  public CreateGameDialog(Dialog owner, String title, boolean modal) {
    super(owner, title, modal);
    try {
      setDefaultCloseOperation(DISPOSE_ON_CLOSE);
      jbInit();
      pack();
    } catch (Exception exception) {
      exception.printStackTrace();
    }
  }

  public CreateGameDialog() {
    this(new JDialog(), "CreateGameDialog", false);
  }

  private void jbInit() throws Exception {
    panel1.setLayout(gridBagLayout1);
    jLabel1.setText("Game Name:");
    txtGameName.setText("My Game");
    jTextArea1.setFont(new java.awt.Font("Arial", Font.PLAIN, 13));
    jTextArea1.setOpaque(false);
    jTextArea1.setEditable(false);
    jTextArea1.setText("Last Man Standing - Throw 3 darts at a randomly selected target. " +
                       " Each round the player furthest away.");
    jTextArea1.setLineWrap(true);
    jTextArea1.setWrapStyleWord(true);
    jPanel1.setLayout(gridBagLayout2);
    jTextArea2.setFont(new java.awt.Font("Arial", Font.PLAIN, 13));
    jTextArea2.setOpaque(false);
    jTextArea2.setEditable(false);
    jTextArea2.setText("301 - Throw darts to count down from 301. First player to zero wins. " +
                       "The final throw must be in the double ring.");
    jTextArea2.setLineWrap(true);
    jTextArea2.setWrapStyleWord(true);
    jTextArea3.setFont(new java.awt.Font("Arial", Font.PLAIN, 13));
    jTextArea3.setOpaque(false);
    jTextArea3.setEditable(false);
    jTextArea3.setText("Cricket Give Points - Throw darts to close out zones by hitting them " +
                       "3 times each. Once closed, you can hit a zone to give those points " +
                       "to other players. Lowest score wins.");
    jTextArea3.setLineWrap(true);
    jTextArea3.setWrapStyleWord(true);
    jTextArea4.setFont(new java.awt.Font("Arial", Font.PLAIN, 13));
    jTextArea4.setOpaque(false);
    jTextArea4.setEditable(false);
    jTextArea4.setText("201 - Throw darts to count down from 201. First player to zero wins. " +
                       "The final throw must be in the double ring.");
    jTextArea4.setLineWrap(true);
    jTextArea4.setWrapStyleWord(true);
    jTextArea5.setFont(new java.awt.Font("Arial", Font.PLAIN, 13));
    jTextArea5.setOpaque(false);
    jTextArea5.setEditable(false);
    jTextArea5.setText("Cricket Take Points - Throw darts to close out zones by hitting them " +
                       "3 times each. Once closed, you can hit a zone to give those points " +
                       "to other players. Highest score wins.");
    jTextArea5.setLineWrap(true);
    jTextArea5.setWrapStyleWord(true);
    jTextArea6.setFont(new java.awt.Font("Arial", Font.PLAIN, 13));
    jTextArea6.setOpaque(false);
    jTextArea6.setEditable(false);
    jTextArea6.setText("101 - Throw darts to count down from 101. First player to zero wins. " +
                       "The final throw must be in the double ring.");
    jTextArea6.setLineWrap(true);
    jTextArea6.setWrapStyleWord(true);

    btnLastMan.setIcon(IconLoader.loadImage("lastman-small.jpg"));
    btnLastMan.setMargin(new Insets(2, 2, 2, 2));
    btnLastMan.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        btnLastMan_actionPerformed(e);
      }
    });
    btnCricketGive.setIcon(IconLoader.loadImage("cricket-give-small.jpg"));
    btnCricketGive.setMargin(new Insets(2, 2, 2, 2));
    btnCricketGive.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        btnCricketGive_actionPerformed(e);
      }
    });
    btnCricketTake.setIcon(IconLoader.loadImage("cricket-take-small.jpg"));
    btnCricketTake.setMargin(new Insets(2, 2, 2, 2));
    btnCricketTake.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        btnCricketTake_actionPerformed(e);
      }
    });
    btn301.setIcon(IconLoader.loadImage("301-small.jpg"));
    btn301.setMargin(new Insets(2, 2, 2, 2));
    btn301.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        btn301_actionPerformed(e);
      }
    });
    btn201.setIcon(IconLoader.loadImage("201-small.jpg"));
    btn201.setMargin(new Insets(2, 2, 2, 2));
    btn201.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        btn201_actionPerformed(e);
      }
    });
    btn101.setIcon(IconLoader.loadImage("101-small.jpg"));
    btn101.setMargin(new Insets(2, 2, 2, 2));
    btn101.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        btn101_actionPerformed(e);
      }
    });
    this.getContentPane().setLayout(gridBagLayout3);
    jScrollPane5.setPreferredSize(new Dimension(250, 80));
    jScrollPane6.setPreferredSize(new Dimension(250, 80));
    jScrollPane3.setPreferredSize(new Dimension(250, 80));
    jScrollPane4.setPreferredSize(new Dimension(250, 80));
    jScrollPane2.setPreferredSize(new Dimension(250, 80));
    jScrollPane1.setPreferredSize(new Dimension(250, 80));
    this.addComponentListener(new ComponentAdapter() {
      public void componentShown(ComponentEvent e) {
        this_componentShown(e);
      }
    });

    panel1.add(jScrollPane1, new GridBagConstraints(1, 1, 1, 1, 0.5, 0.0
        , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
    panel1.add(jScrollPane2, new GridBagConstraints(3, 1, 1, 1, 0.5, 0.0
        , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
    panel1.add(jScrollPane3, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0
        , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
    panel1.add(jScrollPane5, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0
        , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
    panel1.add(jScrollPane6, new GridBagConstraints(3, 3, 1, 1, 0.0, 0.0
        , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
    panel1.add(jScrollPane4, new GridBagConstraints(3, 2, 1, 1, 0.0, 0.0
        , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
    jScrollPane4.getViewport().add(jTextArea4);
    jScrollPane6.getViewport().add(jTextArea6);
    jScrollPane5.getViewport().add(jTextArea5);
    jScrollPane3.getViewport().add(jTextArea3);
    jScrollPane2.getViewport().add(jTextArea2);
    jScrollPane1.getViewport().add(jTextArea1);

    panel1.add(btn201, new GridBagConstraints(2, 2, 1, 1, 0.0, 0.0
                                              , GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(5, 15, 5, 5), 0, 0));
    panel1.add(btn101, new GridBagConstraints(2, 3, 1, 1, 0.0, 0.0
                                              , GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(5, 15, 5, 5), 0, 0));
    panel1.add(btn301, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0
                                              , GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(5, 15, 5, 5), 0, 0));
    panel1.add(btnCricketGive, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
        , GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    panel1.add(btnCricketTake, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0
        , GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    jPanel1.add(jLabel1, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
                                                , GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 0, 5, 5), 0, 0));
    panel1.add(jPanel1, new GridBagConstraints(0, 0, 5, 1, 0.0, 0.0
                                               , GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(0, 5, 10, 5), 0, 0));
    jPanel1.add(txtGameName, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
        , GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 150, 0));
    this.getContentPane().add(panel1, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
        , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(10, 10, 10, 10), 0, 2));
    panel1.add(btnLastMan, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
                                                  , GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
  }

  public void this_componentShown(ComponentEvent e) {
    txtGameName.requestFocus();
    txtGameName.selectAll();
    mCancelled = true;
  }

  public void btnLastMan_actionPerformed(ActionEvent e) {
    mGameType = DAGame.GAME_LAST_MAN_STANDING;
    mCancelled = false;
    setVisible(false);
  }

  public void btnCricketGive_actionPerformed(ActionEvent e) {
    mGameType = DAGame.GAME_LAST_MAN_STANDING;
    mCancelled = false;
    setVisible(false);
  }

  public void btnCricketTake_actionPerformed(ActionEvent e) {
    mGameType = DAGame.GAME_LAST_MAN_STANDING;
    mCancelled = false;
    setVisible(false);
  }

  public void btn301_actionPerformed(ActionEvent e) {
    mGameType = DAGame.GAME_LAST_MAN_STANDING;
    mCancelled = false;
    setVisible(false);
  }

  public void btn201_actionPerformed(ActionEvent e) {
    mGameType = DAGame.GAME_LAST_MAN_STANDING;
    mCancelled = false;
    setVisible(false);
  }

  public void btn101_actionPerformed(ActionEvent e) {
    mGameType = DAGame.GAME_LAST_MAN_STANDING;
    mCancelled = false;
    setVisible(false);
  }


  /**
   *
   * @return boolean
   */
  public boolean isCancelled() {
    return mCancelled;
  }

  public int getGameType() {
    return mGameType;
  }

  public String getGameName() {
    return txtGameName.getText();
  }
}


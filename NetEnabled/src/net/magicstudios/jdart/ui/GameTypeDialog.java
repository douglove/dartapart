package net.magicstudios.jdart.ui;

import javax.swing.*;
import net.magicstudios.jdart.ui.icons.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import net.magicstudios.jdart.data.*;
import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Vector;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import net.magicstudios.jdart.data.games.*;

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
public class GameTypeDialog extends JDialog {

  private JPanel panel1 = new JPanel();
  private JToggleButton btnCricketTake = new JToggleButton();
  private JToggleButton btnCricketGive = new JToggleButton();
  private JPanel jPanel1 = new JPanel();
  private GridBagLayout gridBagLayout1 = new GridBagLayout();
  private ImageIcon ico301 = IconLoader.loadImage("301.jpg");
  private ImageIcon ico201 = IconLoader.loadImage("201.jpg");
  private ImageIcon ico101 = IconLoader.loadImage("101.jpg");
  private ImageIcon icoCricketGive = IconLoader.loadImage("cricket-give.jpg");
  private ImageIcon icoCricketTake = IconLoader.loadImage("cricket-take.jpg");
  private GridBagLayout gridBagLayout2 = new GridBagLayout();
  private JToggleButton btn301 = new JToggleButton();
  private JToggleButton btn201 = new JToggleButton();
  private JToggleButton btn101 = new JToggleButton();
  private GameModel m_gameModel = null;
  private JToggleButton btnClosest = new JToggleButton();
  private JPanel jPanel2 = new JPanel();
  private JScrollPane jScrollPane1 = new JScrollPane();
  private JTextArea jTextArea1 = new JTextArea();
  private BorderLayout borderLayout1 = new BorderLayout();
  private JScrollPane jScrollPane2 = new JScrollPane();
  private JPanel jPanel3 = new JPanel();
  private GridBagLayout gridBagLayout3 = new GridBagLayout();
  private JToggleButton JToggleButton1 = new JToggleButton();
  private JTextField jTextField1 = new JTextField();
  private JPanel jPanel4 = new JPanel();
  private JToggleButton JToggleButton2 = new JToggleButton();
  private JToggleButton JToggleButton3 = new JToggleButton();
  private JToggleButton JToggleButton4 = new JToggleButton();
  private JToggleButton JToggleButton5 = new JToggleButton();
  private JTextField jTextField2 = new JTextField();
  private JTextField jTextField3 = new JTextField();
  private JTextField jTextField4 = new JTextField();
  private JTextField jTextField5 = new JTextField();
  private JToggleButton JToggleButton6 = new JToggleButton();
  private JTextField jTextField6 = new JTextField();
  private JPanel jPanel5 = new JPanel();
  private BorderLayout borderLayout2 = new BorderLayout();
  private JButton btnPlayDarts = new JButton();
  private JPanel jPanel6 = new JPanel();
  private JScrollPane jScrollPane3 = new JScrollPane();
  private BorderLayout borderLayout3 = new BorderLayout();
  private JPanel jPanel7 = new JPanel();
  private JScrollPane jScrollPane4 = new JScrollPane();
  private JTextArea jTextArea2 = new JTextArea();
  private JScrollPane jScrollPane5 = new JScrollPane();
  private JScrollPane jScrollPane6 = new JScrollPane();
  private JScrollPane jScrollPane7 = new JScrollPane();
  private JScrollPane jScrollPane8 = new JScrollPane();
  private JTextArea jTextArea3 = new JTextArea();
  private JTextArea jTextArea4 = new JTextArea();
  private JTextArea jTextArea5 = new JTextArea();
  private JTextArea jTextArea6 = new JTextArea();
  private ButtonGroup group1 = new ButtonGroup();
  private ButtonGroup group2 = new ButtonGroup();
  private Vector nameBoxes = new Vector();

  public GameTypeDialog(Frame owner, String title, boolean modal) {
    super(owner, title, modal);
    try {
      setDefaultCloseOperation(DISPOSE_ON_CLOSE);
      jbInit();
      init();
      pack();
    }
    catch (Exception exception) {
      exception.printStackTrace();
    }
  }

  public GameTypeDialog() {
    this(new Frame(), "GameTypeDialog", false);
  }

  private void jbInit() throws Exception {
    panel1.setLayout(gridBagLayout1);
    btnCricketTake.setMinimumSize(new Dimension(110, 106));
    btnCricketTake.setPreferredSize(new Dimension(110, 106));
    btnCricketTake.setIcon(icoCricketTake);
    btnCricketTake.setText("");
    btnCricketGive.setMinimumSize(new Dimension(110, 106));
    btnCricketGive.setPreferredSize(new Dimension(110, 106));
    btnCricketGive.setIcon(icoCricketGive);
    btnCricketGive.setText("");
    jPanel1.setLayout(gridBagLayout2);
    btn301.setMinimumSize(new Dimension(110, 106));
    btn301.setPreferredSize(new Dimension(110, 106));
    btn301.setIcon(ico301);
    btn301.setText("");
    btn201.setMinimumSize(new Dimension(110, 106));
    btn201.setPreferredSize(new Dimension(110, 106));
    btn201.setIcon(ico201);
    btn201.setText("");
    btn101.setMinimumSize(new Dimension(110, 106));
    btn101.setPreferredSize(new Dimension(110, 106));
    btn101.setIcon(ico101);
    btnClosest.setMinimumSize(new Dimension(110, 106));
    btnClosest.setPreferredSize(new Dimension(110, 106));
    btnClosest.setSelected(true);
    btnClosest.setText("<html><center>Last Man Standing</center></html>");
    jTextArea1.setBackground(UIManager.getColor("Panel.background"));
    jTextArea1.setFont(new java.awt.Font("Arial", Font.PLAIN, 12));
    jTextArea1.setBorder(null);
    jTextArea1.setEditable(false);
    jTextArea1.setText("Each player throws 3 darts attempting to get closest to a randomly " +
                       "positioned target.  The player furthest from the target is eliminated " +
                       "at the end of each round. Last player standing wins!");
    jTextArea1.setLineWrap(true);
    jTextArea1.setWrapStyleWord(true);
    jPanel2.setLayout(borderLayout1);
    jPanel3.setLayout(gridBagLayout3);
    JToggleButton1.setFont(new java.awt.Font("Dialog", Font.BOLD, 36));
    JToggleButton1.setMinimumSize(new Dimension(110, 106));
    JToggleButton1.setPreferredSize(new Dimension(110, 106));
    JToggleButton1.setActionCommand("1");
    JToggleButton1.setText("1");
    jTextField1.setFont(new java.awt.Font("Dialog", Font.PLAIN, 36));
//    jTextField1.setNextFocusableComponent(jTextField2);
    jTextField1.setText("Player 1");
    jTextField1.addFocusListener(new FocusAdapter() {
      public void focusGained(FocusEvent e) {
        jTextField1_focusGained(e);
      }
    });
    jPanel2.setPreferredSize(new Dimension(10, 10));
    JToggleButton2.setFont(new java.awt.Font("Dialog", Font.BOLD, 36));
    JToggleButton2.setMinimumSize(new Dimension(110, 106));
    JToggleButton2.setPreferredSize(new Dimension(110, 106));
    JToggleButton2.setActionCommand("2");
    JToggleButton2.setText("2");
    JToggleButton3.setFont(new java.awt.Font("Dialog", Font.BOLD, 36));
    JToggleButton3.setMinimumSize(new Dimension(110, 106));
    JToggleButton3.setPreferredSize(new Dimension(110, 106));
    JToggleButton3.setActionCommand("3");
    JToggleButton3.setSelected(true);
    JToggleButton3.setText("3");
    JToggleButton4.setFont(new java.awt.Font("Dialog", Font.BOLD, 36));
    JToggleButton4.setMinimumSize(new Dimension(110, 106));
    JToggleButton4.setPreferredSize(new Dimension(110, 106));
    JToggleButton4.setActionCommand("4");
    JToggleButton4.setText("4");
    JToggleButton5.setFont(new java.awt.Font("Dialog", Font.BOLD, 36));
    JToggleButton5.setMinimumSize(new Dimension(110, 106));
    JToggleButton5.setPreferredSize(new Dimension(110, 106));
    JToggleButton5.setActionCommand("5");
    JToggleButton5.setText("5");
    jTextField2.setFont(new java.awt.Font("Dialog", Font.PLAIN, 36));
//    jTextField2.setNextFocusableComponent(jTextField3);
    jTextField2.setText("Player 2");
    jTextField2.addFocusListener(new FocusAdapter() {
      public void focusGained(FocusEvent e) {
        jTextField2_focusGained(e);
      }
    });
    jTextField3.setFont(new java.awt.Font("Dialog", Font.PLAIN, 36));
//    jTextField3.setNextFocusableComponent(jTextField4);
    jTextField3.setText("Player 3");
    jTextField3.addFocusListener(new FocusAdapter() {
      public void focusGained(FocusEvent e) {
        jTextField3_focusGained(e);
      }
    });
    jTextField4.setFont(new java.awt.Font("Dialog", Font.PLAIN, 36));
//    jTextField4.setNextFocusableComponent(jTextField5);
    jTextField4.setText("Player 4");
    jTextField4.addFocusListener(new FocusAdapter() {
      public void focusGained(FocusEvent e) {
        jTextField4_focusGained(e);
      }
    });
    jTextField5.setFont(new java.awt.Font("Dialog", Font.PLAIN, 36));
//    jTextField5.setNextFocusableComponent(jTextField6);
    jTextField5.setText("Player 5");
    jTextField5.addFocusListener(new FocusAdapter() {
      public void focusGained(FocusEvent e) {
        jTextField5_focusGained(e);
      }
    });
    JToggleButton6.setFont(new java.awt.Font("Dialog", Font.BOLD, 36));
    JToggleButton6.setMinimumSize(new Dimension(110, 106));
    JToggleButton6.setPreferredSize(new Dimension(110, 106));
    JToggleButton6.setActionCommand("6");
    JToggleButton6.setText("6");
    jTextField6.setFont(new java.awt.Font("Dialog", Font.PLAIN, 36));
//    jTextField6.setNextFocusableComponent(jTextField1);
    jTextField6.setText("Player 6");
    jTextField6.addFocusListener(new FocusAdapter() {
      public void focusGained(FocusEvent e) {
        jTextField6_focusGained(e);
      }
    });
    jPanel5.setLayout(borderLayout2);
    btnPlayDarts.setFont(new java.awt.Font("Dialog", Font.BOLD, 36));
    btnPlayDarts.setText("Play Darts!");
    btnPlayDarts.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        btnPlayDarts_actionPerformed(e);
      }
    });
    jPanel6.setLayout(borderLayout3);
    jScrollPane2.setBorder(null);
    jScrollPane3.setBorder(null);
    jTextArea2.setBackground(UIManager.getColor("Panel.background"));
    jTextArea2.setFont(new java.awt.Font("Arial", Font.PLAIN, 12));
    jTextArea2.setEditable(false);
    jTextArea2.setText("jTextArea2");
    jTextArea3.setBackground(UIManager.getColor("Panel.background"));
    jTextArea3.setFont(new java.awt.Font("Arial", Font.PLAIN, 12));
    jTextArea3.setEditable(false);
    jTextArea3.setText("jTextArea3");
    jTextArea4.setBackground(UIManager.getColor("Panel.background"));
    jTextArea4.setFont(new java.awt.Font("Arial", Font.PLAIN, 12));
    jTextArea4.setEditable(false);
    jTextArea4.setText("jTextArea4");
    jTextArea5.setBackground(UIManager.getColor("Panel.background"));
    jTextArea5.setFont(new java.awt.Font("Arial", Font.PLAIN, 12));
    jTextArea5.setEditable(false);
    jTextArea5.setText("jTextArea5");
    jTextArea6.setBackground(UIManager.getColor("Panel.background"));
    jTextArea6.setFont(new java.awt.Font("Arial", Font.PLAIN, 12));
    jTextArea6.setEditable(false);
    jTextArea6.setText("jTextArea6");
    jPanel6.setPreferredSize(new Dimension(10, 10));
    jScrollPane1.setBorder(null);
    jScrollPane4.setBorder(null);
    jScrollPane5.setBorder(null);
    jScrollPane6.setBorder(null);
    jScrollPane7.setBorder(null);
    jScrollPane8.setBorder(null);
    getContentPane().add(panel1);

    jScrollPane1.getViewport().add(jTextArea1);
    jPanel2.add(jScrollPane2, java.awt.BorderLayout.CENTER);
    jScrollPane2.getViewport().add(jPanel3);
    jPanel3.add(jTextField1, new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0
        , GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
    jPanel3.add(jTextField2, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0
        , GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
    jPanel3.add(jTextField3, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0
        , GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
    jPanel3.add(jTextField4, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0
        , GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
    jPanel3.add(jTextField5, new GridBagConstraints(1, 4, 1, 1, 0.0, 0.0
        , GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
    jPanel3.add(jTextField6, new GridBagConstraints(1, 5, 1, 1, 0.0, 0.0
        , GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
    jPanel3.add(JToggleButton1, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
                                                 , GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    jPanel3.add(JToggleButton2, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
                                                 , GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    jPanel3.add(JToggleButton3, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
                                                 , GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 5, 5, 5), 0, 0));
    jPanel3.add(JToggleButton4, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0
                                                 , GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    jPanel3.add(JToggleButton5, new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0
                                                 , GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    jPanel3.add(JToggleButton6, new GridBagConstraints(0, 5, 1, 1, 0.0, 0.0
                                                 , GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    jPanel5.add(btnPlayDarts, java.awt.BorderLayout.CENTER);
    jScrollPane3.getViewport().add(jPanel1);

    jPanel6.add(jScrollPane3, java.awt.BorderLayout.CENTER);
    panel1.add(jPanel6, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
                                               , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
    panel1.add(jPanel5, new GridBagConstraints(0, 1, 2, 1, 0.0, 0.0
                                               , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(10, 10, 10, 10), 0, 0));
    jPanel1.add(btnClosest, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
        , GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    jScrollPane5.getViewport().add(jTextArea3);
    jScrollPane6.getViewport().add(jTextArea4);
    jScrollPane7.getViewport().add(jTextArea5);
    jPanel1.add(jScrollPane8, new GridBagConstraints(1, 5, 1, 1, 0.0, 0.0
        , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
    jPanel1.add(jScrollPane7, new GridBagConstraints(1, 4, 1, 1, 0.0, 0.0
        , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
    jPanel1.add(jScrollPane6, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0
        , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
    jPanel1.add(jScrollPane5, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0
        , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
    jPanel1.add(jScrollPane1, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
        , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
    jPanel1.add(btnCricketGive, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
        , GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    jPanel1.add(btnCricketTake, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
        , GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    jPanel1.add(btn101, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0
                                               , GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    jPanel1.add(btn201, new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0
                                               , GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    jPanel1.add(btn301, new GridBagConstraints(0, 5, 1, 1, 0.0, 0.0
                                               , GridBagConstraints.NORTH, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    jPanel1.add(jScrollPane4, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0
        , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
    jScrollPane8.getViewport().add(jTextArea6);
    jScrollPane4.getViewport().add(jTextArea2);
    jPanel1.add(jPanel7, new GridBagConstraints(0, 128, 2, 1, 1.0, 1.0
                                                , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
    jPanel3.add(jPanel4, new GridBagConstraints(0, 133, 2, 1, 1.0, 1.0
                                                , GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    panel1.add(jPanel2, new GridBagConstraints(1, 0, 1, 1, 1.0, 1.0
                                               , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 30, 0, 0), 0, 0));
    group1.add(btn301);
    group1.add(btn201);
    group1.add(btn101);
    group1.add(btnCricketTake);
    group1.add(btnCricketGive);
    group1.add(btnClosest);
    group2.add(JToggleButton1);
    group2.add(JToggleButton2);
    group2.add(JToggleButton3);
    group2.add(JToggleButton4);
    group2.add(JToggleButton5);
    group2.add(JToggleButton6);

    this.getRootPane().setDefaultButton(btnPlayDarts);
  }

  public void init() {
    nameBoxes.add(jTextField1);
    nameBoxes.add(jTextField2);
    nameBoxes.add(jTextField3);
    nameBoxes.add(jTextField4);
    nameBoxes.add(jTextField5);
    nameBoxes.add(jTextField6);
  }

  public void setVisible(boolean state) {

    UIUtil.centerWindow(this);
    jTextField1.requestFocus();

    super.setVisible(state);
  }

  public GameModel getGameModel() {
    if (btn101.isSelected()) {
      m_gameModel = new OhOneGameModel(101, true);
    } else if (btn201.isSelected()) {
      m_gameModel = new OhOneGameModel(101, true);
    } else if (btn301.isSelected()) {
      m_gameModel = new OhOneGameModel(101, true);
    } else if (btnClosest.isSelected()) {
      m_gameModel = new RandomPointGameModel();
    } else if (btnCricketGive.isSelected()) {
      m_gameModel = new CricketGameModel(true);
    } else if (btnCricketTake.isSelected()) {
      m_gameModel = new CricketGameModel(false);
    }

    return m_gameModel;
  }

  public int getNumberOfPlayers() {
    return Integer.parseInt(group2.getSelection().getActionCommand());
  }

  public java.util.List getPlayerNames() {
    ArrayList names = new ArrayList();
    int num = getNumberOfPlayers();

    for (int i = 0; i < num; i++) {
      names.add(((JTextField) nameBoxes.elementAt(i)).getText().trim());
    }

    return names;
  }

  public void btnPlayDarts_actionPerformed(ActionEvent e) {
    setVisible(false);
  }

  private void selectAll(JTextField field) {
    field.selectAll();
  }

  public void jTextField1_focusGained(FocusEvent e) {
    selectAll(jTextField1);
  }

  public void jTextField2_focusGained(FocusEvent e) {
    selectAll(jTextField2);
  }

  public void jTextField3_focusGained(FocusEvent e) {
    selectAll(jTextField3);
  }

  public void jTextField4_focusGained(FocusEvent e) {
    selectAll(jTextField4);
  }

  public void jTextField5_focusGained(FocusEvent e) {
    selectAll(jTextField5);
  }

  public void jTextField6_focusGained(FocusEvent e) {
    selectAll(jTextField6);
  }
}

package net.magicstudios.jdart.ui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.BorderLayout;
import net.magicstudios.jdart.data.service.*;
import net.magicstudios.jdart.data.*;
import javax.swing.event.*;
import net.magicstudios.jdart.event.*;

public class GameListDialog extends JDialog implements JoinListener {

  private DartApartModel mModel = null;
  private CreateGameDialog mCreateGameDlg = null;

  private DAGame selectedGame = null;
  private boolean mIsCancelled = true;
  private boolean mCreateGame = false;

  private JPanel panel1 = new JPanel();
  private BorderLayout borderLayout1 = new BorderLayout();
  private GameListPanel panGameList = new GameListPanel();
  private JPanel jPanel1 = new JPanel();
  private GridBagLayout gridBagLayout1 = new GridBagLayout();
  private JButton btnJoin = new JButton();
  private JButton btnCreate = new JButton();
  private JButton btnCancel = new JButton();

  public GameListDialog(Frame owner, String title, boolean modal) {
    super(owner, title, modal);
    try {
      setDefaultCloseOperation(DISPOSE_ON_CLOSE);
      jbInit();
    }
    catch (Exception exception) {
      exception.printStackTrace();
    }
  }

  public GameListDialog() {
    this(new Frame(), "Game List", false);
  }

  private void jbInit() throws Exception {
    panel1.setLayout(borderLayout1);
    this.addComponentListener(new ComponentAdapter() {
      public void componentHidden(ComponentEvent componentEvent) {
        this_componentHidden(componentEvent);
      }

      public void componentShown(ComponentEvent componentEvent) {
        this_componentShown(componentEvent);
      }
    }); jPanel1.setLayout(gridBagLayout1);
    btnJoin.setText("Join");
    btnJoin.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        btnJoin_actionPerformed(e);
      }
    });
    btnCreate.setText("Create Game");
    btnCreate.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        btnCreate_actionPerformed(e);
      }
    });
    btnCancel.setText("  Cancel  ");
    btnCancel.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        btnCancel_actionPerformed(e);
      }
    });
    panel1.add(panGameList, BorderLayout.CENTER);
    getContentPane().add(panel1);
    panel1.add(jPanel1, java.awt.BorderLayout.SOUTH);
//    jPanel1.add(btnJoin, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
//                                                 , GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 5, 0), 0, 0));
    jPanel1.add(btnCancel, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0
                                                 , GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 5, 10), 0, 0));
    jPanel1.add(btnCreate, new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0
                                                  , GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 10, 5, 5), 0, 0));
    panGameList.getTable().getSelectionModel().addListSelectionListener(new ListSelectionListener() {
      public void valueChanged(ListSelectionEvent e) {
        tabGameList_valueChanged(e);
      }
    });

    this.getRootPane().setDefaultButton(btnJoin);
    setSize(450, 300);
  }


  public void tabGameList_valueChanged(ListSelectionEvent e) {
    int row = panGameList.getTable().getSelectedRow();
    btnJoin.setEnabled(row != -1);
  }

  public void setModel(DartApartModel model) {
    mModel = model;
    panGameList.setJoinListener(this);
    panGameList.setModel(model);
  }

  public static void main(String [] args) {
    GameListDialog gld = new GameListDialog();
    gld.setSize(500, 300);
    gld.setVisible(true);
  }

  public void this_componentShown(ComponentEvent componentEvent) {
    panGameList.startGameWatchThread();
  }

  public void this_componentHidden(ComponentEvent componentEvent) {
    panGameList.pauseGameWatchThread();
  }


  /**
   *
   * @return DAGame
   */
  public DAGame getSelectedGame() {

    if (isCreateGame()) {

      String name = mCreateGameDlg.getGameName();
      int type = mCreateGameDlg.getGameType();

      selectedGame = mModel.createGame(name, type);
    }

    return selectedGame;
  }


  /**
   *
   * @return boolean
   */
  public boolean isCancelled() {
    return mIsCancelled;
  }

  public boolean isCreateGame() {
    return mCreateGame;
  }


  /**
   *
   * @param e ActionEvent
   */
  public void btnCreate_actionPerformed(ActionEvent e) {

    if (mCreateGameDlg == null) {
      mCreateGameDlg = new CreateGameDialog(this, "Create Game", true);
    }
    mCreateGameDlg.setLocationRelativeTo(this);
    mCreateGameDlg.setVisible(true);

    if (mCreateGameDlg.isCancelled() == false) {
      mIsCancelled = false;
      mCreateGame = true;
      setVisible(false);
    }
  }

  public void btnJoin_actionPerformed(ActionEvent e) {
    mIsCancelled = false;
    mCreateGame = false;
    setVisible(false);
  }

  public void btnCancel_actionPerformed(ActionEvent e) {
    mIsCancelled = true;
    mCreateGame = false;
    setVisible(false);
  }

  public void joinRequested(DAGame game) {
    selectedGame = game;

    mIsCancelled = false;
    mCreateGame = false;
    setVisible(false);

  }
}

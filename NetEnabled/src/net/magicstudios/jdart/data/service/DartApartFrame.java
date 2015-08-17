package net.magicstudios.jdart.data.service;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import net.magicstudios.jdart.data.*;
import net.magicstudios.jdart.event.*;
import net.magicstudios.jdart.ui.*;
import java.awt.Dimension;

public class DartApartFrame extends JFrame implements StatusListener, DartListener, ThrowListener, GameListener, ChatListener {

  private DartApartModel mModel = new DartApartModel();

  private GameListDialog mGameListDialog = null;
  private LoginDialog mLoginDialog = null;
  private AwaitingPlayersDialog mAwaitingPlayersDialog = null;

  private DartBoardViz panDartBoardView = new DartBoardViz();
  private PlayersView panPlayersView = new PlayersView();
  private ChatPanel panChat = new ChatPanel();

  private BorderLayout borderLayout1 = new BorderLayout();
  private JToolBar jToolBar1 = new JToolBar();
  private JPanel jPanel1 = new JPanel();
  private GridBagLayout gridBagLayout1 = new GridBagLayout();

  public DartApartFrame() {
    try {
      jbInit();
    } catch (Exception exception) {
      exception.printStackTrace();
    }
  }

  private void jbInit() throws Exception {
    setTitle("Dart Apart 1.0");
    this.addWindowListener(new WindowAdapter() {
      public void windowOpened(WindowEvent e) {
        this_windowOpened(e);
      }
    });
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    getContentPane().setLayout(borderLayout1);
    jToolBar1.setFloatable(false);
    jPanel1.setLayout(gridBagLayout1);
    jPanel1.setBackground(new Color(100, 136, 110));
    panChat.setPreferredSize(new Dimension(40, 40));
    //this.getContentPane().add(jToolBar1, java.awt.BorderLayout.NORTH);
    this.getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);
    jPanel1.add(panPlayersView, new GridBagConstraints(1, 0, 1, 2, 1.0, 1.0
        , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
    jPanel1.add(panDartBoardView, new GridBagConstraints(0, 0, 1, 1, 0.7, 1.0
        , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
    jPanel1.add(panChat, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
                                                , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 150));
    Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
    setSize(screen.width, screen.height - 100);
    this.setExtendedState(this.getExtendedState() | this.MAXIMIZED_BOTH);
  }

  public static void main(String[] args) {
    DartApartFrame dartapartframe = new DartApartFrame();
    dartapartframe.setLocation(0, 0);
    dartapartframe.setVisible(true);
  }

  public void btnNewGame_actionPerformed(ActionEvent e) {
    getStarted();
  }

  public void getStarted() {

    panDartBoardView.addDartListener(this);
    panChat.addChatListener(this);


    int attempts = 0;

    while (mModel.isLoggedIn() == false && attempts < 3) {

      if (mLoginDialog == null) {
        mLoginDialog = new LoginDialog(this, "Login", true);
      }
      mLoginDialog.setLocationRelativeTo(this);
      mLoginDialog.setVisible(true);

      if (mLoginDialog.isCancelled() == false) {
        String email = mLoginDialog.getEmail();
        String password = mLoginDialog.getPassword();

        // try to login
        try {
          mModel.login(email, password);
        } catch (Throwable ex) {
          JOptionPane.showMessageDialog(this, "Unable to connect to DartApart service: " + ex.toString());
          return;
        }
      } else {
        // cancel so break out of attempt loop
        break;
      }

      attempts++;
    }

    if (mModel.isLoggedIn()) {
      this.setTitle("Dart Apart 1.0 - Logged in as: " + mModel.getPlayer().getEmail());

      if (mGameListDialog == null) {
        mGameListDialog = new GameListDialog(this, "Game List", true);
        mGameListDialog.setModel(mModel);
        mModel.addStatusListener(this);
        mModel.addThrowListener(this);
      }

      // show the list of games
      mGameListDialog.setLocationRelativeTo(this);
      mGameListDialog.setVisible(true);

      if (mGameListDialog.isCancelled() == false) {
        DAGame game = mGameListDialog.getSelectedGame();

        // join the game
        mModel.join(game);

        panPlayersView.setGameModel(mModel.getGameModel());

        // show the awaiting players dialog
        if (mAwaitingPlayersDialog == null) {
          mAwaitingPlayersDialog = new AwaitingPlayersDialog(this, "Awaiting Players", false);
          mAwaitingPlayersDialog.setModel(mModel);
          mAwaitingPlayersDialog.setLocationRelativeTo(this);
          mAwaitingPlayersDialog.setVisible(true);
        }
      }
    }
  }

  public void statusChanged(StatusEvent evt) {
    if (mModel.getGame().getStatus() == DAGame.STATUS_INPROGRESS) {
      mAwaitingPlayersDialog.setVisible(false);
      mModel.getGameModel().addGameListener(this);
      mModel.getGameModel().setSharedData(mModel.getGame().getSharedData());
      mModel.startGame();
    }
  }

  public void this_windowOpened(WindowEvent e) {
    getStarted();
  }



  /**
   *
   * @param evt DartEvent
   */
  public void dartsAdded(DartEvent evt) {
    if (mModel.getGameModel().isGameRunning() &&
        mModel.getGameModel().getCurrentPlayer().equals(mModel.getPlayer())) {

      mModel.addThrow(evt.getZone());
    }
  }

  public void dartsCleared() {
  }

  public void cameraException(CameraExceptionEvent cam) {
  }

  public void occlusionDetected() {
  }


  /**
   *
   */
  public void throwChanged() {

    panDartBoardView.clearDarts();

    Zone target = mModel.getGameModel().getTarget();
    if (target != null) {
      panDartBoardView.addTarget(target.getXPercent(), target.getYPercent(), "Target");
    }

    java.util.List<Zone> dartThrows = mModel.getVisibleThrows();

    int count = dartThrows.size();
    for (int i = 0; i < count; i++) {

      Zone item = (Zone) dartThrows.get(i);
      panDartBoardView.addDart(item.getXPercent(), item.getYPercent());

      if (i == count - 1) {
        mModel.announce(item);
      }
    }
  }




  /**
   *
   */
  public void gameStarting() {
    System.out.println("gameStarting");
  }

  public void roundStarting() {
    System.out.println("roundStarting");
  }

  public void roundEnding() {
    System.out.println("roundEnding");
  }

  public void gameEnding() {

    System.out.println("gameEnding");

    mModel.setGameStatus(DAGame.STATUS_COMPLETE);

    WinnerDialog winnerDlg = new WinnerDialog(this, "Winner!", true);
    winnerDlg.setWinnerName(mModel.getGameModel().getWinner().getName());
    winnerDlg.setLocationRelativeTo(this);
    winnerDlg.setVisible(true);
  }


  /**
   *
   * @param msg String
   */
  public void messagePosted(String msg) {
    mModel.postMessage(msg);
  }

  public void tauntPosted(int taunt) {
    mModel.postTaunt(taunt);
  }

  public void messageReceived(DAMessage msg) {
    if (msg.isMessage()) {
      panChat.appendMessage(msg.getMessage());
    } else {
      mModel.announce(msg.getTaunt());
    }
  }
}



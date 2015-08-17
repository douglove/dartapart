
package net.magicstudios.jdart.ui;

import java.util.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import javax.swing.*;

import net.magicstudios.jdart.data.*;
import net.magicstudios.jdart.event.*;
import net.magicstudios.jdart.util.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.*;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author Nick Cramer (d3k199) Doug Love (d3m431)
 * @version 1.0
 */
public class GameFrame extends JFrame implements DartListener, ScoreListener, PlayerListener {

  private PlayersView m_PlayersView;
  private GameModel m_gameModel;
  private DartBoardViz m_DartBoardView;
  private Triangulator m_triangulator;
  private Announcer m_Announcer;
  private int m_iThrows = 0;
  private boolean m_bInCorrectMode = true;
  private boolean m_bAnnounceZone = true;
  private Vector m_vCorrectModeListeners = new Vector();
  private Properties m_props = null;

  private GridBagLayout gridBagLayout1 = new GridBagLayout();
  private JMenuBar menuBar = new JMenuBar();
  private JMenu mnuFile = new JMenu();
  private JMenuItem jmiFileExit = new JMenuItem();
  private JMenu mnuGame = new JMenu();
  private JMenuItem jmiGameNew = new JMenuItem();
  private JMenuItem jmiGameReplay = new JMenuItem();
  private JMenuItem jmiGameStart = new JMenuItem();
  private JMenuItem jmiSwitchCams = new JMenuItem();
  private JToolBar toolBar = new JToolBar();
  private JButton btnAddPlayer = new JButton();
  private JButton btnRemovePlayer = new JButton();
  private JButton btnRenamePlayer = new JButton();
  private JToggleButton btnCorrectDart = new JToggleButton();
  private JButton btnNextPlayer = new JButton();
  private JMenuItem jmiAddPlayer = new JMenuItem();
  private JMenuItem jniNextPlayer = new JMenuItem();
  private JMenu jMenu1 = new JMenu();
  private JMenuItem mnuNickvsDoug = new JMenuItem();
  private JMenuItem jMenuItem2 = new JMenuItem();
  private JMenuItem jMenuItem1 = new JMenuItem();
  private JButton btnNewGame = new JButton();
  private GameTypeDialog gameDialog = null;

  public GameFrame() {
    try {
      init();
      jbInit();

      fireCorrectModeChanged();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  static public void main(String[] args) {
    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    } catch (Exception e) {
    }

    GameFrame gf = new GameFrame();
    gf.setSize(400, 400);
    gf.setVisible(true);
  }

  public void init() {

    m_PlayersView = new PlayersView();
    m_DartBoardView = new DartBoardViz();
    m_Announcer = new Announcer();

    if (m_triangulator == null) {

      loadProperties();
      m_triangulator = new Triangulator("localhost", m_props);
      m_triangulator.addDartListener(this);

      if (m_triangulator.connectCameras() == true) {
        m_triangulator.start();
      }
    }
  }

  public void setVisible(boolean state) {
    super.setVisible(state);

    newGame();
  }

  private void jbInit() throws Exception {
    this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    this.setExtendedState(this.getExtendedState() | this.MAXIMIZED_BOTH);
    this.setJMenuBar(menuBar);
    this.setTitle("Darts");
    this.getContentPane().setLayout(gridBagLayout1);
    mnuFile.setText("File");
    mnuGame.setText("Setup");
    jmiGameNew.setText("New");
    jmiGameNew.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.KeyEvent.CTRL_MASK, false));
    jmiGameNew.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jmiGameNew_actionPerformed(e);
      }
    });
    jmiSwitchCams.setText("Switch Camera Ports");
    jmiSwitchCams.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jmiSwitchCams_actionPerformed(e);
      }
    });

    jmiGameReplay.setText("Replay");
    jmiGameReplay.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_R, java.awt.event.KeyEvent.CTRL_MASK, false));
    jmiGameReplay.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jmiGameReplay_actionPerformed(e);
      }
    });
    jmiGameStart.setMnemonic('S');
    jmiGameStart.setText("Start");
    jmiGameStart.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F5, 0, false));
    jmiGameStart.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jmiGameStart_actionPerformed(e);
      }
    });
    mnuFile.setMnemonic('F');
    mnuGame.setMnemonic('G');
    jmiFileExit.setMnemonic('X');
    jmiGameNew.setMnemonic('N');
    jmiGameReplay.setMnemonic('R');
    jmiFileExit.setText("Exit");
    jmiFileExit.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jmiFileExit_actionPerformed(e);
      }
    });
    btnAddPlayer.setText("Add");
    btnAddPlayer.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        btnAddPlayer_actionPerformed(e);
      }
    });
    toolBar.setFloatable(false);
    btnRemovePlayer.setText("Remove");
    btnRemovePlayer.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        btnRemovePlayer_actionPerformed(e);
      }
    });
    btnRenamePlayer.setText("Rename");
    btnRenamePlayer.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        btnRenamePlayer_actionPerformed(e);
      }
    });
    btnCorrectDart.setEnabled(false);
    btnCorrectDart.setText("Correct Dart");
    btnCorrectDart.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        btnCorrectDart_actionPerformed(e);
      }
    });
    btnNextPlayer.setEnabled(false);
    btnNextPlayer.setText("Next Player");
    btnNextPlayer.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        btnNextPlayer_actionPerformed(e);
      }
    });
    jmiAddPlayer.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jmiAddPlayer_actionPerformed(e);
      }
    });
    jmiAddPlayer.setText("Add Player");
    jmiAddPlayer.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, java.awt.event.KeyEvent.CTRL_MASK, false));
    jniNextPlayer.setText("Next Player");
    jniNextPlayer.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_SPACE, 0, false));
    jniNextPlayer.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jniNextPlayer_actionPerformed(e);
      }
    });
    jMenu1.setText("QuickGame");
    mnuNickvsDoug.setText("Doug vs Nick");
    mnuNickvsDoug.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_1, 0, false));
    mnuNickvsDoug.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItem1_actionPerformed(e);
      }
    });
    jMenuItem2.setText("Manage...");
    jMenuItem1.setText("Doug vs Naomi");
    btnNewGame.setText("New Game");
    btnNewGame.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        btnNewGame_actionPerformed(e);
      }
    });
    menuBar.add(mnuFile);
    menuBar.add(mnuGame);
    menuBar.add(jMenu1);
    mnuFile.add(jmiFileExit);
    mnuGame.add(jmiGameNew);
    mnuGame.add(jmiGameReplay);
    mnuGame.add(jmiGameStart);
    mnuGame.addSeparator();
    mnuGame.add(jmiAddPlayer);
    mnuGame.add(jniNextPlayer);
    mnuGame.addSeparator();
    mnuGame.add(jmiSwitchCams);
    this.getContentPane().add(m_PlayersView, new GridBagConstraints(1, 1, 1, 1, 1.0, 1.0
        , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
    this.getContentPane().add(m_DartBoardView, new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0
        , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
    toolBar.add(btnNewGame);
    toolBar.add(btnAddPlayer);
    toolBar.add(btnRemovePlayer);
    toolBar.add(btnRenamePlayer);
    //toolBar.add(btnCorrectDart);
    toolBar.add(btnNextPlayer);
    this.getContentPane().add(toolBar, new GridBagConstraints(0, 0, 2, 1, 1.0, 0.0
        , GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
    jMenu1.add(jMenuItem1);
    jMenu1.add(mnuNickvsDoug);
    jMenu1.addSeparator();
    jMenu1.add(jMenuItem2);
  }

  public void jmiFileExit_actionPerformed(ActionEvent e) {
    System.exit(0);
  }

  public void btnAddPlayer_actionPerformed(ActionEvent e) {
    addPlayer();
  }

  public void addPlayer() {
    Rectangle2D rec;
    rec = m_DartBoardView.getBounds();

    AddPlayerDlg addDlg = new AddPlayerDlg(m_gameModel, rec);
  }

  public void btnRemovePlayer_actionPerformed(ActionEvent e) {
    Rectangle2D rec;
    rec = m_DartBoardView.getBounds();

    RemoveRenamePlayerDlg dlg = new RemoveRenamePlayerDlg(this, false, m_gameModel.getPlayers(), rec);
    dlg.show();

    if (dlg.isCanceled() == true) {
      return;
    }

    String name = dlg.getSelectedPlayerName();

    if (name == null || name.compareTo("") == 0) {
      return;
    }

    Player p;
    for (Iterator i = this.m_gameModel.getPlayers().iterator(); i.hasNext(); ) {
      p = (Player) i.next();

      if (name.compareToIgnoreCase(p.getName()) == 0) {
        m_gameModel.removePlayer(p);
        validate();
        return;
      }
    }
  }

  public void btnRenamePlayer_actionPerformed(ActionEvent e) {
    Rectangle2D rec;
    rec = m_DartBoardView.getBounds();

    RemoveRenamePlayerDlg dlg = new RemoveRenamePlayerDlg(this, true, m_gameModel.getPlayers(), rec);
    dlg.show();

    if (dlg.isCanceled() == true) {
      return;
    }

    String name = dlg.getSelectedPlayerName();
    String newName = dlg.getNewPlayerName();

    if (name == null || name.compareTo("") == 0 || newName == null || newName.compareTo("") == 0) {
      return;
    }

    Player p;
    for (Iterator i = m_gameModel.getPlayers().iterator(); i.hasNext(); ) {
      p = (Player) i.next();

      if (name.compareToIgnoreCase(p.getName()) == 0) {
        m_gameModel.renamePlayer(p, newName);
        validate();
        return;
      }
    }
  }

  public void btnCorrectDart_actionPerformed(ActionEvent e) {
    m_bInCorrectMode = !m_bInCorrectMode;
    fireCorrectModeChanged();
  }

  public void jmiGameStart_actionPerformed(ActionEvent e) {
    startGame();
  }

  public void dartsAdded(DartEvent evt) {
    if (m_iThrows == 3) {
      return;
    }

    Zone z = evt.getZone();
    m_DartBoardView.addDart(z.getXPercent(), z.getYPercent());

    if (m_bAnnounceZone) {
      try {
        m_Announcer.play(evt.getZone());
      }
      catch (Exception e) {
        System.out.println("Failed to play sound for zone: slice = " +
                           evt.getZone().getSlice() + " ring = " +
                           evt.getZone().getRingMultiplier());
        e.printStackTrace();
      }
    }

    if (m_gameModel.isGameRunning()) {
      m_gameModel.score(evt.getZone());
      m_iThrows++;
    }
  }

  public void dartsCleared() {
    if (m_iThrows == 3) {
      nextPlayer();
    }
  }

  public void addCorrectModeListener(CorrectModeListener listener) {
    if (m_vCorrectModeListeners.contains(listener) == false) {
      m_vCorrectModeListeners.add(listener);
    }
  }

  public void fireCorrectModeChanged() {
    Iterator i;

    for (i = m_vCorrectModeListeners.iterator(); i.hasNext(); ) {
      ( (CorrectModeListener) i.next()).correctModeChanged(new CorrectModeEvent(m_bInCorrectMode));
    }
  }

  public void btnNextPlayer_actionPerformed(ActionEvent e) {
    nextPlayer();
  }

  public void nextPlayer() {
    m_iThrows = 0;
    m_triangulator.resetProblems();
    resetDartViz();
    m_gameModel.nextPlayer();
    checkWinner();

    String name = m_gameModel.getCurrentPlayer().getName();
    m_Announcer.nextPlayer();
  }

  private void resetDartViz() {
    m_DartBoardView.clearDarts();
    m_DartBoardView.addTarget(m_gameModel.getTarget().getXPercent(), m_gameModel.getTarget().getYPercent(), "Target");
  }

  public void jmiGameNew_actionPerformed(ActionEvent e) {
    newGame();
  }

  public void jmiGameReplay_actionPerformed(ActionEvent e) {
    Player p;
    for (Iterator i = m_gameModel.getPlayers().iterator(); i.hasNext(); ) {
      p = (Player) i.next();

      p.resetZones();
      p.setScore(0);
    }

    validate();

    m_iThrows = 0;
    m_DartBoardView.clearDarts();
  }

  public void jmiAddPlayer_actionPerformed(ActionEvent e) {
    addPlayer();
  }

  public void jniNextPlayer_actionPerformed(ActionEvent e) {
    nextPlayer();
  }

  public void jmiSwitchCams_actionPerformed(ActionEvent e) {
    m_triangulator.swapPorts();
    saveProperties();
  }

  public void scoreChanged(ScoreEvent evt) {
    checkWinner();
  }


  public void checkWinner() {

    String winners = "";
    String title = "";

    if (m_gameModel.isWinner()) {
      title = "Winner!";
      winners = m_gameModel.getWinner().getName();
    }

    if (m_gameModel.isTie()) {
      title = "Tied!";
      String connecter = " ";

      for (Iterator i = m_gameModel.getTies().iterator(); i.hasNext(); ) {
        Player p = (Player) i.next();
        if (winners.compareTo("") != 0) {
          connecter = " and ";
        }

        winners = winners + connecter + p.getName();
      }
    }

    if (m_gameModel.isWinner() || m_gameModel.isTie()) {

      m_gameModel.stopGame();

      WinnerDialog winnerDialog = new WinnerDialog(this, title, true);
      winnerDialog.setWinnerName(winners);

      try {
        Thread.sleep(500);
      }
      catch (InterruptedException ex) {
      }
      m_Announcer.winner();
      try {
        Thread.sleep(500);
      }
      catch (InterruptedException ex) {
      }
      m_Announcer.winner();
      try {
        Thread.sleep(500);
      }
      catch (InterruptedException ex) {
      }
      m_Announcer.winner();

      winnerDialog.setVisible(true);

      if (winnerDialog.isReplay()) {
        initializeGame();
      }
      else {
        newGame();
      }
    }
  }

  public void cameraException(CameraExceptionEvent cam) {
    JOptionPane.showMessageDialog(this, cam.getCameraException().getMessage());
    System.exit(1);
  }

  public Announcer getAnnouncer() {
    return m_Announcer;
  }

  public void jMenuItem1_actionPerformed(ActionEvent e) {
    if (System.currentTimeMillis() % 2 == 0) {
      m_gameModel.addPlayer(new Player("Nick"));
      m_gameModel.addPlayer(new Player("Doug"));
    }
    else {
      m_gameModel.addPlayer(new Player("Doug"));
      m_gameModel.addPlayer(new Player("Nick"));
    }

    startGame();
  }

  private void startGame() {

    btnAddPlayer.setEnabled(false);
    btnRemovePlayer.setEnabled(false);
    btnRenamePlayer.setEnabled(false);
    btnCorrectDart.setEnabled(true);
    btnNextPlayer.setEnabled(true);

    m_gameModel.startGame();
    m_PlayersView.setCurrentPlayer(m_gameModel.getCurrentPlayer());
    m_Announcer.nextPlayer();
    resetDartViz();
  }

  public void occlusionDetected() {
    m_Announcer.occlusion();
  }

  public void btnNewGame_actionPerformed(ActionEvent e) {
    newGame();
  }

  private void newGame() {

    if (gameDialog == null) {
      gameDialog = new GameTypeDialog(this, "Select Game Type", true);
      gameDialog.setSize(800, 825);
    }

    gameDialog.setVisible(true);

    initializeGame();
  }

  private void initializeGame() {

    // Get the new game selected
    GameModel model = gameDialog.getGameModel();
    if (model != null) {

      // clear previous game model of players
      if (m_gameModel != null) {
        m_gameModel.removeAllPlayers();
      }

      m_gameModel = model;
      m_gameModel.setTriangulator(m_triangulator);
      m_gameModel.addPlayerListener(this);
      m_PlayersView.setGameModel(m_gameModel);

      java.util.List players = gameDialog.getPlayerNames();
      for (int i = 0; i < players.size(); i++) {
        String name = (String) players.get(i);
        m_gameModel.addPlayer(new Player(name));
      }

      Player p;
      for (Iterator i = this.m_gameModel.getPlayers().iterator(); i.hasNext(); ) {
        p = (Player) i.next();
        p.addScoreListener(this);
      }

      validate();

      m_iThrows = 0;
      m_DartBoardView.clearDarts();

      startGame();
    }
  }

  private void loadProperties() {

    m_props = new Properties();
    File fileCameras = new File("cameras.ini");
    if (fileCameras.exists()) {
      try {
        m_props.load(new FileInputStream(fileCameras));
      } catch (IOException ex) {
        JOptionPane.showMessageDialog(null, "File error: " + fileCameras.getAbsolutePath() + ". " + ex.toString(), "File Missing", JOptionPane.ERROR_MESSAGE);
      }
    } else {
      JOptionPane.showMessageDialog(null, "File missing: " + fileCameras.getAbsolutePath(), "File Missing", JOptionPane.ERROR_MESSAGE);
    }
  }

  private void saveProperties() {

    File fileCameras = new File("cameras.ini");
    try {
      m_props.store(new FileOutputStream(fileCameras), "");
    }
    catch (IOException ex) {
    }
  }

  public void playerChanged(PlayerEvent evt) {
    resetDartViz();
  }

  public void playerAdded(PlayerEvent evt) {
  }

  public void playerRemoved(PlayerEvent evt) {
  }

  public void playerRenamed(PlayerEvent evt) {
  }
}


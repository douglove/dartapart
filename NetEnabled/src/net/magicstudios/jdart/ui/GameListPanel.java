package net.magicstudios.jdart.ui;

import java.util.*;
import java.util.List;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;

import net.magicstudios.jdart.data.*;
import net.magicstudios.jdart.data.service.*;
import net.magicstudios.jdart.event.JoinListener;
import java.awt.Font;

public class GameListPanel extends JPanel {

  private DartApartModel mModel = null;
  private GameWatchThread mGameWatch = null;
  private List<DAGame> mGameList = null;
  private JoinListener listener = null;

  private GridBagLayout gridBagLayout1 = new GridBagLayout();
  private GridBagLayout gridBagLayout2 = new GridBagLayout();
  private JPanel panGameList = new JPanel();
  private JScrollPane scrollGameList = new JScrollPane();
  private JTable tabGameList = new JTable();
  private DefaultTableModel modelGameList = new DefaultTableModel();
  private JLabel lblNoGames = new JLabel();
  private JLabel jLabel1 = new JLabel();

  public GameListPanel() {
    try {
      jbInit();
    }
    catch (Exception exception) {
      exception.printStackTrace();
    }
  }

  private void jbInit() throws Exception {
    this.setLayout(gridBagLayout1);
    panGameList.setLayout(gridBagLayout2);
    lblNoGames.setFont(new java.awt.Font("Tahoma", Font.PLAIN, 20));
    lblNoGames.setForeground(SystemColor.textInactiveText);
    lblNoGames.setText("No Games Currently Available");
    jLabel1.setFont(new java.awt.Font("Tahoma", Font.PLAIN, 14));
    jLabel1.setForeground(SystemColor.textInactiveText);
    jLabel1.setText("Click Create Game");

    this.add(scrollGameList, new GridBagConstraints(0, 0, 2, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 0, 5), 0, 0));

//    scrollGameList.getViewport().add(tabGameList);
    scrollGameList.getViewport().add(panGameList);
    panGameList.add(lblNoGames, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
        , GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    panGameList.add(jLabel1, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
        , GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(10, 0, 0, 0), 0, 0));
    modelGameList.addColumn("Name");
    modelGameList.addColumn("Type");
    modelGameList.addColumn("Created");
    modelGameList.addColumn("Modified");

    tabGameList.setModel(modelGameList);
    tabGameList.setColumnSelectionAllowed(false);
    tabGameList.setRowSelectionAllowed(true);
    tabGameList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
  }


  public void setJoinListener(JoinListener listener) {
    this.listener = listener;
  }

  public JTable getTable() {
    return tabGameList;
  }

  public void setModel(DartApartModel model) {
    mModel = model;
    loadGames();
  }

  public void loadGames() {

    final List<DAGame> list = mModel.getService().getGameList();
    if (SwingUtilities.isEventDispatchThread()) {
      loadGames(list);
    } else {
      SwingUtilities.invokeLater(new Runnable() {
        public void run() {
          loadGames(list);
        }
      });
    }
  }

  public void loadGames(List<DAGame> games) {
    Collections.sort(games, new Comparator() {
      public int compare(Object o1, Object o2) {
        DAGame g1 = (DAGame) o1;
        DAGame g2 = (DAGame) o2;
        return g2.getCreatedDate().compareTo(g1.getCreatedDate());
      }

      public boolean equals(Object obj) {
        return false;
      }
    });
    mGameList = games;

    Point p = scrollGameList.getViewport().getViewPosition();
    panGameList.removeAll();

    if (mGameList == null || mGameList.size() == 0) {

      panGameList.add(lblNoGames, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
          , GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
      panGameList.add(jLabel1, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
          , GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(10, 0, 0, 0), 0, 0));

    } else {

      int i = 0;
      for (Iterator<DAGame> iter = games.iterator(); iter.hasNext(); ) {
        DAGame item = iter.next();

        GamePanel gp = new GamePanel();
        gp.setJoinListener(listener);
        gp.setGameInfo(item);
        gp.setColor( (i % 2 == 0) ? new Color(219, 219, 219) : new Color(239, 239, 239));

        panGameList.add(gp, new GridBagConstraints(0, i, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
        i++;
      }
      panGameList.add(new JPanel(), new GridBagConstraints(0, i, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
      validate();

      try {
        scrollGameList.getViewport().setViewPosition(p);
      } catch (Exception ex) {
      }
    }
  }


  public DAGame getSelectedGame() {
    DAGame game = null;
    int row = tabGameList.getSelectedRow();
    if (row >= 0) {
      game = mGameList.get(row);
    }
    return game;
  }

  /**
   *
   */
  public void startGameWatchThread() {
    if (mGameWatch == null) {
      mGameWatch = new GameWatchThread();
      mGameWatch.start();
    }
    mGameWatch.setWatching(true);
  }

  public void pauseGameWatchThread() {
    mGameWatch.setWatching(false);
  }


  /**
   *
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
  class GameWatchThread extends Thread {
    boolean mRunning = true;

    public void run() {
      while (mRunning) {
        try {
          Thread.sleep(1000);
        } catch (InterruptedException ex) {
        }

        if (mRunning) {
          loadGames();
        }
      }
    }

    public void setWatching(boolean state) {
      mRunning = state;
    }
  }
}

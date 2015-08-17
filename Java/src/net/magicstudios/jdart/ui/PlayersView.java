package net.magicstudios.jdart.ui;

import javax.swing.JPanel;
import java.util.Vector;
import net.magicstudios.jdart.data.Player;
import net.magicstudios.jdart.event.PlayerListener;
import net.magicstudios.jdart.event.PlayerEvent;
import net.magicstudios.jdart.data.GameModel;
import java.util.HashMap;
import java.awt.GridBagLayout;
import javax.swing.JButton;
import java.awt.*;
import java.util.Collections;
import java.util.Iterator;

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
public class PlayersView extends JPanel implements PlayerListener{
    private GameModel m_Model;
    private HashMap m_PlayersMap = new HashMap();
    private GridBagLayout gridBagLayout1 = new GridBagLayout();
    private PlayerView m_legend = new PlayerView();

    public PlayersView() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void setGameModel(GameModel model) {
      m_Model = model;
      m_Model.addPlayerListener(this);
      removeLegend();

      if (! m_Model.isScrolling()) {
        addLegend();
      }
    }

    public void playerChanged(PlayerEvent evt) {
        Player currentPlayer = m_Model.getCurrentPlayer();
        setCurrentPlayer(currentPlayer);
        repaint();
    }

    public void playerAdded(PlayerEvent evt) {
        addPlayerView(evt.getPlayer());
    }

    public void playerRemoved(PlayerEvent evt) {
        removePlayerView(evt.getPlayer());
    }

    public void playerRenamed(PlayerEvent evt) {
        repaint();
    }

    public void setCurrentPlayer(Player currentPlayer){
        Player p;
        PlayerView pv;

        for(Iterator i = m_PlayersMap.keySet().iterator(); i.hasNext();){
            p = (Player)i.next();
            pv = (PlayerView)m_PlayersMap.get(p);

            pv.setCurrentPlayerFlag(p == currentPlayer);
         }
    }

    private void addLegend(){
        this.add(m_legend, new GridBagConstraints(0, 0,
                                            1, 1,
                                            1.0, 1.0,
                                            GridBagConstraints.CENTER,
                                            GridBagConstraints.BOTH,
                                            new Insets(0, 0, 0, 0), 0, 0));
    }

    private void removeLegend() {
      this.remove(m_legend);
    }

    private void addPlayerView(Player p) {
      if (m_PlayersMap.containsKey(p) == false) {
        PlayerView pv = new PlayerView(p);
        pv.setScrolling(m_Model.isScrolling());
        m_PlayersMap.put(p, pv);

        this.add(pv, new GridBagConstraints(m_PlayersMap.size(), 0,
                                            1, 1,
                                            1.0, 1.0,
                                            GridBagConstraints.CENTER,
                                            GridBagConstraints.BOTH,
                                            new Insets(0, 0, 0, 0), 0, 0));

        validate();
      }
    }

    private void removePlayerView(Player p){
        remove((PlayerView)m_PlayersMap.get(p));
        m_PlayersMap.remove(p);
    }

    private void jbInit() throws Exception {
        this.setLayout(gridBagLayout1);
    }

}

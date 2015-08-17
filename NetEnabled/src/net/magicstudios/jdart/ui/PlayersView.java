package net.magicstudios.jdart.ui;

import java.util.*;

import java.awt.*;
import javax.swing.*;

import net.magicstudios.jdart.data.*;
import net.magicstudios.jdart.data.service.*;
import net.magicstudios.jdart.event.*;
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
 * @author Nick Cramer (d3k199) Doug Love (d3m431)
 * @version 1.0
 */
public class PlayersView extends JPanel implements PlayerListener {

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
        DAPlayer currentPlayer = m_Model.getCurrentPlayer();
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

    public void setCurrentPlayer(DAPlayer currentPlayer){
        DAPlayer p;
        PlayerView pv;

        for(Iterator i = m_PlayersMap.keySet().iterator(); i.hasNext();){
            p = (DAPlayer)i.next();
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

    private void addPlayerView(DAPlayer p) {
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

    private void removePlayerView(DAPlayer p){
        remove((PlayerView)m_PlayersMap.get(p));
        m_PlayersMap.remove(p);
    }

    private void jbInit() throws Exception {
        this.setLayout(gridBagLayout1);
        addLegend();
    }

}

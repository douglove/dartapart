package net.magicstudios.jdart.ui;

import javax.swing.JFrame;
import java.awt.*;
import net.magicstudios.jdart.data.Player;
import net.magicstudios.jdart.data.GameModel;
import net.magicstudios.jdart.data.CricketGameModel;

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
public class testFrame extends JFrame{
    private GridBagLayout gridBagLayout1 = new GridBagLayout();
    PlayersView pv;

    public testFrame() {
        try {
            CricketGameModel game = new CricketGameModel();
            pv = new PlayersView(game);

            Player player = new Player("Doug");
            game.addPlayer(player);

//            player.addZone(Player.ZONE_20, 3);
//            player.addZone(Player.ZONE_19, 2);
//            player.addZone(Player.ZONE_18, 1);
//            player.addZone(Player.ZONE_17, 3);
//            player.addZone(Player.ZONE_16, 2);
//            player.addZone(Player.ZONE_15, 1);
//            player.addZone(Player.ZONE_BE, 1);
//            player.addScore(Player.ZONE_20, 3);
//            player.addScore(Player.ZONE_20, 3);
//            player = new Player("nick");
            game.addPlayer(player);



//            player.addZone(Player.ZONE_20, 3);
//            player.addZone(Player.ZONE_19, 2);
//            player.addZone(Player.ZONE_18, 1);
//            player.addZone(Player.ZONE_17, 3);
//            player.addZone(Player.ZONE_16, 2);
//            player.addZone(Player.ZONE_15, 1);
//            player.addZone(Player.ZONE_BE, 1);
//            player.addScore(Player.ZONE_20, 3);
//            player.addScore(Player.ZONE_20, 3);
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.getContentPane().setLayout(gridBagLayout1);
        this.getContentPane().add(pv, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
    }


    static public void main(String[] args){
        testFrame tf = new testFrame();
        tf.setSize(400, 400);
        tf.show();
    }
}

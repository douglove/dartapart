package net.magicstudios.jdart.game;

import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;

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
public class DartMain extends JFrame {
    JLabel jLabel1 = new JLabel();
    GridBagLayout gridBagLayout1 = new GridBagLayout();
    JButton btnCameras = new JButton();
    DartBoard jBoard = new DartBoard();
    private JComboBox cmbGames = new JComboBox();
    private JButton jButton1 = new JButton();
    private JMenuBar jMenuBar1 = new JMenuBar();
    private JMenu jMenu1 = new JMenu();
    private JMenu jMenu2 = new JMenu();
    private JMenuItem jMenuItem1 = new JMenuItem();
    private JMenuItem jMenuItem2 = new JMenuItem();
    private JMenuItem jMenuItem3 = new JMenuItem();
    private JMenu jMenu3 = new JMenu();
    private JCheckBoxMenuItem jCheckBoxMenuItem2 = new JCheckBoxMenuItem();
    private JCheckBoxMenuItem jCheckBoxMenuItem3 = new JCheckBoxMenuItem();
    public DartMain() {
        try {
            jbInit();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        getContentPane().setLayout(gridBagLayout1);
        jLabel1.setFont(new java.awt.Font("Dialog", Font.BOLD, 14));
        jLabel1.setToolTipText("");
        jLabel1.setText("What would you like to play?");
        btnCameras.setText("Calibrate Camera");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setJMenuBar(jMenuBar1);
        this.setTitle("Tungsten - dart detection system by www.magicstudios.net");

        jButton1.setText("Let\'s Play!");
        jMenu1.setText("Game");
        jMenu2.setText("Help");
        jMenuItem1.setText("About Tungsten");
        jMenuItem2.setText("Let\'s Play");
        jMenuItem3.setText("Exit");
        jMenu3.setText("Edit");
        jCheckBoxMenuItem2.setSelected(false);
        jCheckBoxMenuItem2.setText("Show Board");
        jCheckBoxMenuItem2.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                jCheckBoxMenuItem2_stateChanged(e);
            }
        });
        jCheckBoxMenuItem3.setText("Transparent");
        jCheckBoxMenuItem3.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                jCheckBoxMenuItem3_stateChanged(e);
            }
        });
        this.getContentPane().add(btnCameras, new GridBagConstraints(4, 0, 1, 1, 0.0, 0.0
                , GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 0, 5, 5), 0, 0));
        this.getContentPane().add(jBoard, new GridBagConstraints(0, 1, 5, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 5, 5, 5), 0, 0));
        this.getContentPane().add(jLabel1, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
                , GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
        this.getContentPane().add(cmbGames, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0
                , GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        this.getContentPane().add(jButton1, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0
                , GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 15, 5, 5), 0, 0));
        jMenuBar1.add(jMenu1);
        jMenuBar1.add(jMenu3);
        jMenuBar1.add(jMenu2);
        jMenu2.add(jMenuItem1);
        jMenu1.add(jMenuItem2);
        jMenu1.addSeparator();
        jMenu1.add(jMenuItem3);
        jMenu3.add(jCheckBoxMenuItem2);
        jMenu3.add(jCheckBoxMenuItem3);
        DefaultComboBoxModel model = new DefaultComboBoxModel();
        model.addElement("Cricket");
        model.addElement("101");
        model.addElement("201");
        model.addElement("301");
        cmbGames.setModel(model);
    }

    public void jCheckBoxMenuItem2_stateChanged(ChangeEvent e) {
        jBoard.setDrawBoardImage(jCheckBoxMenuItem2.isSelected());
    }

    public void jCheckBoxMenuItem3_stateChanged(ChangeEvent e) {
        jBoard.setTransparent(jCheckBoxMenuItem3.isSelected());
    }
}

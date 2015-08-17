package net.magicstudios.jdart.ui;

import javax.swing.*;
import java.awt.GridBagLayout;
import java.awt.*;
import net.magicstudios.jdart.data.GameModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import net.magicstudios.jdart.data.Player;
import net.magicstudios.jdart.data.CricketGameModel;
import java.awt.geom.Rectangle2D;

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
public class AddPlayerDlg extends JDialog {
    GameModel m_GameModel;
    Rectangle2D m_parentRec;

    public AddPlayerDlg(GameModel gameModel, Rectangle2D rec) {
        try {
            m_GameModel = gameModel;
            m_parentRec = rec;

            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        this.setModal(true);
        //        this.setModal(true);
        this.setResizable(false);
        this.setTitle("Add Players");
        this.setSize(300, 150);
        this.getContentPane().setLayout(gridBagLayout1);
        lblName.setText("Name : ");
        txtName.setText("");
        pnlButtons.setLayout(gridBagLayout2);
        btnAdd.setText("Add");
        btnAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnAdd_actionPerformed(e);
            }
        });
        btnClose.setText("Close");
        btnClose.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnClose_actionPerformed(e);
            }
        });
        this.getRootPane().setDefaultButton(btnAdd);
        lblSpacer.setText("");
        this.getContentPane().add(lblName, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
                , GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(3, 3, 3, 3), 0, 0));
        this.getContentPane().add(txtName, new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0
                , GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(3, 3, 3, 3), 0, 0));
        pnlButtons.add(btnAdd, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
                , GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(3, 3, 3, 3), 0, 0));
        pnlButtons.add(btnClose, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0
                , GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL, new Insets(3, 3, 3, 3), 0, 0));
        pnlButtons.add(lblSpacer, new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0
                , GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
        this.getContentPane().add(pnlButtons, new GridBagConstraints(0, 1, 2, 1, 1.0, 0.0
                , GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(15, 0, 0, 0), 0, 0));

        this.setLocation((int)(m_parentRec.getCenterX()) - (getWidth() / 2),
                         (int)(m_parentRec.getCenterY()) - (getHeight() /2));
        this.show();

    }

    private GridBagLayout gridBagLayout1 = new GridBagLayout();
    private JLabel lblName = new JLabel();
    private JTextField txtName = new JTextField();
    private JPanel pnlButtons = new JPanel();
    private GridBagLayout gridBagLayout2 = new GridBagLayout();
    private JButton btnAdd = new JButton();
    private JButton btnClose = new JButton();
    private JLabel lblSpacer = new JLabel();

    public void btnClose_actionPerformed(ActionEvent e) {
        hide();
    }

    public void btnAdd_actionPerformed(ActionEvent e) {
        boolean result;
        String name = txtName.getText();

        if (name == null || name.compareTo("") == 0) {
            return;
        }

        result = m_GameModel.addPlayer(new Player(name));

        if(result == true){
            txtName.setText("");
        }
        else{
            JOptionPane.showMessageDialog(this, "Player : " + name + " already exists");
        }
    }
}

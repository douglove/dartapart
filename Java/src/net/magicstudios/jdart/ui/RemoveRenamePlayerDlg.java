package net.magicstudios.jdart.ui;

import javax.swing.*;
import java.awt.GridBagLayout;
import java.awt.*;
import java.util.Vector;
import java.util.Iterator;
import net.magicstudios.jdart.data.Player;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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
public class RemoveRenamePlayerDlg extends JDialog {
    private boolean m_IsRename = true;
    private boolean m_IsCanceled = true;
    Rectangle2D m_parentRec;

    private GridBagLayout gridBagLayout1 = new GridBagLayout();
    private JLabel lblName = new JLabel();
    private JComboBox cboNames = new JComboBox();
    private JLabel lblNewName = new JLabel();
    private JTextField txtNewName = new JTextField();
    private JPanel pnlButtons = new JPanel();
    private GridBagLayout gridBagLayout2 = new GridBagLayout();
    private JButton btnOK = new JButton();
    private JButton btnCancel = new JButton();

    public RemoveRenamePlayerDlg(JFrame parent, boolean isRename, Vector playersVec, Rectangle2D rec) {
        try {
            m_parentRec = rec;
            m_IsRename = isRename;
            jbInit();

            if(m_IsRename){
                setTitle("Rename Player");
            }
            else{
                setTitle("Remove Player");
            }

            setLocation((parent.getWidth() / 2) - (getWidth() / 2), (parent.getHeight() / 2) - (getHeight() / 2));

            Player p;

            for(Iterator i = playersVec.iterator(); i.hasNext();){
                p = (Player)i.next();
                cboNames.addItem(p.getName());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public String getSelectedPlayerName(){
        return cboNames.getSelectedItem().toString();
    }

    public String getNewPlayerName(){
        return this.txtNewName.getText();
    }

    public boolean isCanceled(){
        return m_IsCanceled;
    }

    private void jbInit() throws Exception {
        this.getContentPane().setLayout(gridBagLayout1);
        lblName.setText("Name : ");
        lblNewName.setText("New Name : ");
        txtNewName.setText("");
        pnlButtons.setLayout(gridBagLayout2);
        btnOK.setText("OK");
        btnOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnOK_actionPerformed(e);
            }
        });
        btnCancel.setText("Cancel");
        btnCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnCancel_actionPerformed(e);
            }
        });
        this.setModal(true);
        this.setResizable(false);
        this.setTitle("");
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });
        cboNames.setPreferredSize(new Dimension(150, 19));
        this.getContentPane().add(lblName, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
                , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(3, 3, 3, 3), 0, 0));
        this.getContentPane().add(cboNames, new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0
                , GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(3, 3, 3, 3), 0, 0));

        if(m_IsRename == true){
            this.getContentPane().add(lblNewName, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
                    , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(3, 3, 3, 3), 0, 0));
            this.getContentPane().add(txtNewName, new GridBagConstraints(1, 1, 1, 1, 1.0, 0.0
                    , GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(3, 3, 3, 3), 0, 0));
        }
        pnlButtons.add(btnCancel, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
                , GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(3, 3, 3, 3), 0, 0));
        pnlButtons.add(btnOK, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
                , GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(3, 3, 3, 3), 0, 0));
        this.getContentPane().add(pnlButtons, new GridBagConstraints(0, 2, 2, 1, 0.0, 0.0
                , GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));

        this.getRootPane().setDefaultButton(btnOK);
        this.pack();
        this.setLocation((int)(m_parentRec.getCenterX()) - (getWidth() / 2),
                         (int)(m_parentRec.getCenterY()) - (getHeight() /2));
    }

    public void btnCancel_actionPerformed(ActionEvent e) {
        m_IsCanceled = true;
        this.hide();
    }

    public void btnOK_actionPerformed(ActionEvent e) {
        m_IsCanceled = false;
        this.hide();
    }

    public void this_windowClosing(WindowEvent e) {
        m_IsCanceled = true;
    }

}

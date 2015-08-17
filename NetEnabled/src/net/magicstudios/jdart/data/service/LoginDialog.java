package net.magicstudios.jdart.data.service;

import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import net.magicstudios.jdart.data.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class LoginDialog extends JDialog {

  private boolean mIsCancelled = true;

  private JPanel panel1 = new JPanel();
  private BorderLayout borderLayout2 = new BorderLayout();
  private GridBagLayout gridBagLayout1 = new GridBagLayout();
  private JLabel lblEmail = new JLabel();
  private JLabel lblPassword = new JLabel();
  private JTextField txtEmail = new JTextField();
  private JPasswordField txtPassword = new JPasswordField();
  private JPanel jPanel1 = new JPanel();
  private GridBagLayout gridBagLayout2 = new GridBagLayout();
  private JButton btnCancel = new JButton();
  private JButton btnLogin = new JButton();

  public LoginDialog(Frame owner, String title, boolean modal) {
    super(owner, title, modal);
    try {
      setDefaultCloseOperation(DISPOSE_ON_CLOSE);
      jbInit();
    } catch (Exception exception) {
      exception.printStackTrace();
    }
  }

  public LoginDialog() {
    this(new Frame(), "LoginDialog", false);
  }

  private void jbInit() throws Exception {
    panel1.setLayout(gridBagLayout1);
    this.getContentPane().setLayout(borderLayout2);
    btnLogin.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        btnLogin_actionPerformed(e);
      }
    });
    btnCancel.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        btnCancel_actionPerformed(e);
      }
    });
    txtEmail.addFocusListener(new FocusAdapter() {
      public void focusGained(FocusEvent focusEvent) {
        txtEmail_focusGained(focusEvent);
      }
    });
    txtPassword.addFocusListener(new FocusAdapter() {
      public void focusGained(FocusEvent focusEvent) {
        txtPassword_focusGained(focusEvent);
      }
    });
    this.getContentPane().add(panel1, java.awt.BorderLayout.CENTER);
    lblPassword.setText("Password:");
    txtEmail.setText("nick.cramer@gmail.com");
    txtPassword.setText("password");
    jPanel1.setLayout(gridBagLayout2);
    btnCancel.setText("Cancel");
    btnLogin.setText("Login");
    panel1.add(lblPassword, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
                                               , GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    panel1.add(lblEmail, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
                                               , GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
    jPanel1.add(btnLogin, new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0
                                                 , GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    panel1.add(jPanel1, new GridBagConstraints(1, 2, 1, 1, 1.0, 1.0
                                               , GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    jPanel1.add(btnCancel, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
                                                 , GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 5, 10), 0, 0));
    panel1.add(txtEmail, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
        , GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
    panel1.add(txtPassword, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0
        , GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
    lblEmail.setText("Email:");
    this.getRootPane().setDefaultButton(btnLogin);
    setSize(300, 140);
  }

  public void setVisible(boolean state) {
    txtEmail.selectAll();
    super.setVisible(state);
  }

  public void btnLogin_actionPerformed(ActionEvent e) {
    mIsCancelled = false;
    setVisible(false);
  }

  public void btnCancel_actionPerformed(ActionEvent e) {
    mIsCancelled = true;
    setVisible(false);
  }

  /**
   *
   * @return String
   */
  public String getEmail() {
    return txtEmail.getText();
  }

  /**
   *
   * @return String
   */
  public String getPassword() {
    return new String(txtPassword.getPassword());
  }

  /**
   *
   * @return boolean
   */
  public boolean isCancelled() {
    return mIsCancelled;
  }

  public void txtEmail_focusGained(FocusEvent focusEvent) {
    txtEmail.selectAll();
  }

  public void txtPassword_focusGained(FocusEvent focusEvent) {
    txtPassword.selectAll();
  }
}

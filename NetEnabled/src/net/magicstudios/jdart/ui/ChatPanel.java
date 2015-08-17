package net.magicstudios.jdart.ui;

import java.awt.*;
import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Vector;
import net.magicstudios.jdart.event.*;
import net.magicstudios.jdart.util.*;

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
public class ChatPanel extends JPanel {

  private Vector m_vecListeners = new Vector();

  private JScrollPane sclChat = new JScrollPane();
  private JTextArea txtChat = new JTextArea();
  private JPanel jPanel1 = new JPanel();
  private GridBagLayout gridBagLayout1 = new GridBagLayout();
  private JTextField txtMsg = new JTextField();
  private JButton btnSend = new JButton();
  private JPanel jPanel2 = new JPanel();
  private GridBagLayout gridBagLayout2 = new GridBagLayout();
  private JButton btnTaunt = new JButton();
  private GridBagLayout gridBagLayout3 = new GridBagLayout();

  public ChatPanel() {
    try {
      jbInit();
    } catch (Exception exception) {
      exception.printStackTrace();
    }
  }

  private void jbInit() throws Exception {
    this.setLayout(gridBagLayout3);
    txtChat.setBackground(new Color(100, 136, 110));
    txtChat.setFont(new java.awt.Font("Arial", Font.PLAIN, 16));
    txtChat.setForeground(Color.white);
    txtChat.setBorder(null);
    txtChat.setEditable(false);
    txtChat.setText("Welcome to Dart Apart!");
    txtChat.setLineWrap(true);
    txtChat.setWrapStyleWord(true);
    jPanel1.setLayout(gridBagLayout1);
    btnSend.setText("Send");
    btnSend.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        btnSend_actionPerformed(e);
      }
    });
    jPanel2.setLayout(gridBagLayout2);
    btnTaunt.setText("Taunt");
    btnTaunt.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        btnTaunt_actionPerformed(e);
      }
    });
    txtMsg.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        txtMsg_actionPerformed(e);
      }
    });
    sclChat.setBorder(null);
    this.setBackground(new Color(100, 136, 110));
    jPanel1.setBackground(new Color(100, 136, 110));
    jPanel2.setBackground(new Color(100, 136, 110));
    sclChat.getViewport().add(txtChat);
    jPanel2.add(sclChat, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
        , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
    jPanel1.add(btnTaunt, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0
                                                 , GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    jPanel1.add(txtMsg, new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0
                                               , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
    this.add(jPanel2, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
                                             , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 10, 0, 0), 0, 150));
    jPanel1.add(btnSend, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
                                                , GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 2, 0, 2), 0, 0));
    jPanel2.add(jPanel1, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
                                                , GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 10, 0), 0, 0));
  }

  public void btnSend_actionPerformed(ActionEvent e) {
    processMessage();
  }

  public void txtMsg_actionPerformed(ActionEvent e) {
    processMessage();
  }


  public void btnTaunt_actionPerformed(ActionEvent e) {
    fireTauntPosted(Announcer.TAUNT_THROW_ALREADY);
  }



  public void appendMessage(String msg) {
    txtChat.append(msg);
  }


  private void processMessage() {
    String msg = txtMsg.getText();
    txtMsg.setText("");
    fireMessagePosted(msg);
  }


  public void addChatListener(ChatListener listener) {
    m_vecListeners.add(listener);
  }

  private void fireMessagePosted(String msg) {
    Object [] listeners = m_vecListeners.toArray();
    for (int i = 0; i < listeners.length; i++) {
      ((ChatListener) listeners[i]).messagePosted(msg);
    }
  }

  private void fireTauntPosted(int taunt) {
    Object [] listeners = m_vecListeners.toArray();
    for (int i = 0; i < listeners.length; i++) {
      ((ChatListener) listeners[i]).tauntPosted(taunt);
    }
  }
}


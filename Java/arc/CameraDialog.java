package net.magicstudios.jdart;

import java.awt.*;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
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
 * @author Nick Cramer (d3k199)
 * @version 1.0
 */
public class CameraDialog extends JFrame {

    private CameraAnalyzer framePanel = new CameraAnalyzer();
    private GridBagLayout gridBagLayout1 = new GridBagLayout();
    private JToggleButton jToggleButton1 = new JToggleButton();
    private JSlider jSlider1 = new JSlider();
    private JToolBar jToolBar1 = new JToolBar();
    private JToggleButton btnProcess = new JToggleButton();
    private JButton btnSaveLeft = new JButton();
    private JButton btnRecallLeft = new JButton();
    private JButton btnSaveRight = new JButton();
    private JButton btnRecallRight = new JButton();
    private JButton btnCalibrate = new JButton();
    private JToggleButton btnCenterLine = new JToggleButton();
    private int m_iPort = 10111;

    public CameraDialog(int port) {
        this();
        m_iPort = port;
        setTitle("Camera Analyzer " + m_iPort);
        framePanel.start(m_iPort);
        if (m_iPort == 10111) {
            setLocation(0, 0);
        } else {
            setLocation(0, 575);
        }

        try {
            Thread.sleep(5000);
        } catch (InterruptedException ex) {
        }

        process(true);
        recallRight();
    }

    public CameraDialog() {
        try {
            jbInit();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        getContentPane().setLayout(gridBagLayout1);
        jToggleButton1.setText("Start Webcam");
        jToggleButton1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jToggleButton1_actionPerformed(e);
            }
        });
        framePanel.setMinimumSize(new Dimension(640, 480));
        framePanel.setPreferredSize(new Dimension(640, 480));
        framePanel.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                framePanel_mouseDragged(e);
            }
        });
        framePanel.addMouseListener(new MouseAdapter() {
            public void mouseReleased(MouseEvent e) {
                framePanel_mouseReleased(e);
            }
        });
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });
        jSlider1.setOrientation(JSlider.VERTICAL);
        jSlider1.setMajorTickSpacing(15);
        jSlider1.setMaximum(255);
        jSlider1.setMinorTickSpacing(3);
        jSlider1.setPaintLabels(true);
        jSlider1.setPaintTicks(true);
        jSlider1.setValue(155);
        jSlider1.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                jSlider1_stateChanged(e);
            }
        });
        pack();
        btnProcess.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnProcess_actionPerformed(e);
            }
        });
        btnSaveLeft.setText("Save Left");
        btnSaveLeft.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveLeft_actionPerformed(e);
            }
        });
        btnRecallLeft.setText("Recall Left");
        btnRecallLeft.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnRecallLeft_actionPerformed(e);
            }
        });
        btnSaveRight.setText("Save Right");
        btnSaveRight.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnSaveRight_actionPerformed(e);
            }
        });
        btnRecallRight.setText("Recall Right");
        btnRecallRight.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnRecallRight_actionPerformed(e);
            }
        });
        btnCalibrate.setText("Calibrate");
        btnCalibrate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnCalibrate_actionPerformed(e);
            }
        });
        btnCenterLine.setText("Center");
        btnCenterLine.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnCenterLine_actionPerformed(e);
            }
        });
        jToolBar1.add(btnProcess);
        jToolBar1.add(btnCalibrate);
        jToolBar1.add(btnRecallLeft);
        jToolBar1.add(btnRecallRight);
        jToolBar1.add(btnSaveLeft);
        jToolBar1.add(btnSaveRight);
        jToolBar1.add(btnCenterLine);
        btnProcess.setText("Process");

        this.getContentPane().add(jSlider1, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0
                , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
        this.getContentPane().add(framePanel, new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
        this.getContentPane().add(jToggleButton1, new GridBagConstraints(0, 2, 2, 1, 0.0, 0.0
                , GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
        this.getContentPane().add(jToolBar1, new GridBagConstraints(0, 0, 2, 1, 0.0, 0.0
                , GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
        setSize(700, 550);
        setLocation(200, 200);
    }

    public void jToggleButton1_actionPerformed(ActionEvent e) {
        if (jToggleButton1.isSelected()) {
            jToggleButton1.setText("Stop Webcam");
            framePanel.start(m_iPort);
        } else {
            jToggleButton1.setText("Start Webcam");
            framePanel.stop();
        }
    }

    public void this_windowClosing(WindowEvent e) {
        framePanel.stop();
        this.dispose();
        System.exit(0);
    }

    public static void main(String[] args) {
        int port = 10111;
        if (args.length == 1) {
            port = Integer.parseInt(args[0]);
        }
        CameraDialog frameanalyzer2 = new CameraDialog(port);
        frameanalyzer2.show();
    }

    public void jSlider1_stateChanged(ChangeEvent e) {
        framePanel.setTolerance(jSlider1.getValue());
    }

    public void framePanel_mouseReleased(MouseEvent e) {
        if (e.isControlDown()) {
            framePanel.setScanLineTop(e.getY());
        } else {
            framePanel.setScanLineBottom(e.getY());
        }
    }

    public void framePanel_mouseDragged(MouseEvent e) {
        if (e.isControlDown()) {
            framePanel.setScanLineTop(e.getY());
        } else {
            framePanel.setScanLineBottom(e.getY());
        }
    }

    public void btnProcess_actionPerformed(ActionEvent e) {
        process(btnProcess.isSelected());
    }


    public void saveLeft_actionPerformed(ActionEvent e) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("left.txt"));
            writer.write(framePanel.getTolerance() + "\n");
            writer.close();
        } catch (IOException ex) {
        }
    }

    public void btnRecallLeft_actionPerformed(ActionEvent e) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("left.txt"));
            jSlider1.setValue(Integer.parseInt(reader.readLine()));
            reader.close();
            framePanel.calibrate();
        } catch (Exception ex) {
        }
    }

    public void btnSaveRight_actionPerformed(ActionEvent e) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("right.txt"));
            writer.write(framePanel.getTolerance() + "\n");
            writer.close();
        } catch (IOException ex) {
        }
    }

    public void btnRecallRight_actionPerformed(ActionEvent e) {
        recallRight();
    }

    public void btnCalibrate_actionPerformed(ActionEvent e) {
        framePanel.calibrate();
    }

    public void btnCenterLine_actionPerformed(ActionEvent e) {
        framePanel.setCenterLine(btnCenterLine.isSelected());
    }

    private void process(boolean state) {
        framePanel.setProcessImage(state);
    }

    private void recallRight() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("right.txt"));
            jSlider1.setValue(Integer.parseInt(reader.readLine()));
            reader.close();
            framePanel.calibrate();
        } catch (Exception ex) {
        }
    }
}

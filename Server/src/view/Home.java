package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Home extends javax.swing.JFrame {

    public Home() {
        initComponents();
    }

    private void initComponents() {
        actionInfo = new ArrayList<>();
        java.awt.GridBagConstraints gridBagConstraints;

        topPanel = new javax.swing.JPanel();
        openButton = new javax.swing.JButton();
        closeButton = new javax.swing.JButton();
        ipLabel = new javax.swing.JLabel();
        remoteControlLabel = new javax.swing.JLabel();
        actionPanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        actionTable = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new java.awt.GridBagLayout());

        topPanel.setBackground(new java.awt.Color(41, 50, 65));
        topPanel.setLayout(new java.awt.GridBagLayout());

        openButton.setBackground(new java.awt.Color(41, 50, 65));
        openButton.setFont(new java.awt.Font("STXihei", 0, 18)); // NOI18N
        openButton.setForeground(new java.awt.Color(131, 197, 190));
        openButton.setText("Open");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipady = 50;
        gridBagConstraints.weightx = 0.3;
        gridBagConstraints.weighty = 0.5;
        topPanel.add(openButton, gridBagConstraints);

        closeButton.setBackground(new java.awt.Color(41, 50, 65));
        closeButton.setFont(new java.awt.Font("STXihei", 0, 18)); // NOI18N
        closeButton.setForeground(new java.awt.Color(131, 197, 190));
        closeButton.setText("Close");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipady = 50;
        gridBagConstraints.weightx = 0.3;
        gridBagConstraints.weighty = 0.5;
        topPanel.add(closeButton, gridBagConstraints);

        ipLabel.setFont(new java.awt.Font("STXihei", 0, 22)); // NOI18N
        ipLabel.setForeground(new java.awt.Color(255, 255, 255));
        ipLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.7;
        gridBagConstraints.weighty = 0.5;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 30, 0);
        topPanel.add(ipLabel, gridBagConstraints);

        remoteControlLabel.setFont(new java.awt.Font("STXihei", 0, 36)); // NOI18N
        remoteControlLabel.setForeground(new java.awt.Color(137, 197, 190));
        remoteControlLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        remoteControlLabel.setText("Remote Control");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.7;
        gridBagConstraints.weighty = 0.5;
        gridBagConstraints.insets = new java.awt.Insets(30, 0, 0, 0);
        topPanel.add(remoteControlLabel, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 0.3;
        getContentPane().add(topPanel, gridBagConstraints);

        actionPanel.setLayout(new java.awt.GridBagLayout());


        actionTable.setDefaultEditor(Object.class, null);
        actionTable.getTableHeader().setBackground(new Color(40, 50, 65));
        actionTable.getTableHeader().setForeground(new Color(255,255,255));
        actionTable.getTableHeader().setFont(new Font("STXihei", 0, 18));
        actionTable.setFont(new Font("STXihei", 0, 16)); // NOI18N
        actionTable.setModel(new javax.swing.table.DefaultTableModel(
                new Object [][] {
                        { null, null },
                        { null, null },
                        { null, null },
                        { null, null },
                        { null, null },
                        { null, null },
                        { null, null },
                        { null, null },
                        { null, null },
                        { null, null },
                        { null, null },
                        { null, null },
                        { null, null }
                },
                new String [] {
                        "Action", "Time"
                }
        ));

        actionTable.setRowHeight(30);
        actionTable.getTableHeader().setReorderingAllowed(false);

        jScrollPane1.setViewportView(actionTable);

        if (actionTable.getColumnModel().getColumnCount() > 0) {
            actionTable.getColumnModel().getColumn(0).setPreferredWidth(250);
            actionTable.getColumnModel().getColumn(1).setPreferredWidth(150);
        }

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        actionPanel.add(jScrollPane1, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 0.7;
        getContentPane().add(actionPanel, gridBagConstraints);

        pack();
    }

    public JLabel getIpLabel() {
        return ipLabel;
    }

    public JButton getCloseButton() {
        return closeButton;
    }

    public void setOpen(Boolean open) {
        isOpen = open;
    }

    public Boolean getOpen() {
        return isOpen;
    }

    public void changeStateButton()
    {
        if (isOpen)
        {
            openButton.setForeground(new java.awt.Color(41, 50, 65));
            openButton.setBackground(new java.awt.Color(131, 197, 190));
        }
        else
        {
            openButton.setBackground(new java.awt.Color(41, 50, 65));
            openButton.setForeground(new java.awt.Color(131, 197, 190));
        }
    }

    public JButton getOpenButton() {
        return openButton;
    }

    private javax.swing.JButton closeButton;
    private javax.swing.JLabel ipLabel;
    private javax.swing.JPanel actionPanel;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable actionTable;
    private javax.swing.JButton openButton;
    private javax.swing.JLabel remoteControlLabel;
    private javax.swing.JPanel topPanel;
    private Boolean isOpen = false;
    private ArrayList<ArrayList<String>> actionInfo;
    public void updateTable(String action)
    {
        Date time = new java.util.Date(System.currentTimeMillis());
        String time_ = new SimpleDateFormat("HH:mm:ss").format(time);
        ArrayList<String> temp = new ArrayList<>();
        temp.add("   " + action);
        temp.add(time_);

        actionInfo.add(temp);
        String[][] infoProcessArray = actionInfo.stream().map(u -> u.toArray(new String[0])).toArray(String[][]::new);
        actionTable.repaint();
        actionTable.setModel(new DefaultTableModel(infoProcessArray, new String[] { "Action", "Time" }));
        if (actionTable.getColumnModel().getColumnCount() > 0) {
            actionTable.getColumnModel().getColumn(0).setPreferredWidth(250);
            actionTable.getColumnModel().getColumn(1).setPreferredWidth(150);
        }
    }

    public static void main(String[] args)
    {
        Home home = new Home();
        home.setVisible(true);
    }


}


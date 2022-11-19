package view.card;

import javax.swing.*;
import java.awt.*;

public class Application extends JPanel {
    public Application()
    {
        initComponents();
    }

    private void initComponents()
    {
        GridBagConstraints gBC;

        setLayout(new GridBagLayout());
        appButtonPanel = new JPanel();
        listButton = new JButton();
        startButton = new JButton();
        stopButton = new JButton();
        appListPanel = new JPanel();
        appScrollPanel = new JScrollPane();
        appListTable = new JTable();

        appButtonPanel.setLayout(new GridBagLayout());

        listButton.setBackground(new Color(40, 50, 65));
        listButton.setFont(new Font("STXihei", -1, 22)); // NOI18N
        listButton.setForeground(new Color(130, 197, 190));
        listButton.setText("List");
        gBC = new GridBagConstraints();
        gBC.gridx = 0;
        gBC.gridy = 0;
        gBC.ipadx = 60;
        gBC.ipady = 25;
        gBC.weightx = 0.2;
        gBC.weighty = 1.0;
//        gBC.insets = new Insets(0, 50, 0, 0);

        appButtonPanel.add(listButton, gBC);

        startButton.setBackground(new Color(40, 50, 65));
        startButton.setFont(new Font("STXihei", 0, 22)); // NOI18N
        startButton.setForeground(new Color(130, 197, 190));
        startButton.setText("Start");
        gBC = new GridBagConstraints();
        gBC.gridx = 1;
        gBC.gridy = 0;
        gBC.ipadx = 60;
        gBC.ipady = 25;
        gBC.weightx = 0.2;
        gBC.weighty = 1.0;
        appButtonPanel.add(startButton, gBC);

        stopButton.setBackground(new Color(40, 50, 65));
        stopButton.setFont(new Font("STXihei", -1, 22)); // NOI18N
        stopButton.setForeground(new Color(130, 197, 190));
        stopButton.setText("Stop");
        gBC = new GridBagConstraints();
        gBC.gridx = 2;
        gBC.gridy = 0;
        gBC.ipadx = 60;
        gBC.ipady = 25;
        gBC.weightx = 0.2;
        gBC.weighty = 1.0;
//        gBC.insets = new Insets(0, 0, 0, 200);
        appButtonPanel.add(stopButton, gBC);

        gBC = new GridBagConstraints();
        gBC.gridx = 0;
        gBC.gridy = 0;
        gBC.fill = GridBagConstraints.BOTH;
        gBC.weightx = 1;
        gBC.weighty = 0.5;
        gBC.insets = new Insets(0, 40, 0, 400);
        add(appButtonPanel, gBC);

        appListPanel.setLayout(new java.awt.GridBagLayout());

        appListTable.getTableHeader().setBackground(new java.awt.Color(40, 50, 65));
        appListTable.getTableHeader().setForeground(new java.awt.Color(255,255,255));
        appListTable.getTableHeader().setFont(new Font("STXihei", 0, 18));
        appListTable.setFont(new java.awt.Font("STXihei", 0, 16)); // NOI18N
        appListTable.setModel(new javax.swing.table.DefaultTableModel(
                new Object [][] {
                        {"aaaa", "bbbbb", "ccccccc", "dddddddddd"},
                        {null, null, null, null},
                        {null, null, null, null},
                        {null, null, null, null},
                        {null, null, null, null},
                        {null, null, null, null},
                        {null, null, null, null},
                        {null, null, null, null},
                        {null, null, null, null},
                        {null, null, null, null},
                        {null, null, null, null},
                        {null, null, null, null},
                        {null, null, null, null},
                        {null, null, null, null},
                        {null, null, null, null},
                        {null, null, null, null},
                        {null, null, null, null},

                },
                new String [] {
                        "Application", "ID", "Status", "Title 4"
                }
        ) {
            Class[] types = new Class [] {
                    java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                    false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });

        appListTable.setRowHeight(30);
        appListTable.getTableHeader().setReorderingAllowed(false);
        appScrollPanel.setViewportView(appListTable);
        if (appListTable.getColumnModel().getColumnCount() > 0) {
            appListTable.getColumnModel().getColumn(0).setPreferredWidth(100);
            appListTable.getColumnModel().getColumn(1).setPreferredWidth(30);
            appListTable.getColumnModel().getColumn(2).setPreferredWidth(100);
            appListTable.getColumnModel().getColumn(3).setPreferredWidth(100);
        }

        gBC = new java.awt.GridBagConstraints();
        gBC.gridx = 0;
        gBC.gridy = 0;
        gBC.fill = java.awt.GridBagConstraints.BOTH;
        gBC.weightx = 1.0;
        gBC.weighty = 1.0;
        appListPanel.add(appScrollPanel, gBC);

        gBC = new GridBagConstraints();
        gBC.gridx = 0;
        gBC.gridy = 1;
        gBC.fill = GridBagConstraints.BOTH;
        gBC.weightx = 1.0;
        gBC.weighty = 0.5;
        add(appListPanel, gBC);
    }

    public JButton getListButton() {
        return listButton;
    }

    private JButton listButton;
    private JButton startButton;
    private JButton stopButton;
    private JPanel appButtonPanel;
    private JPanel appListPanel;
    private JScrollPane appScrollPanel;
    private JTable appListTable;
}

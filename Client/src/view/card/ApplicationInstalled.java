package view.card;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class ApplicationInstalled extends JPanel {
    public ApplicationInstalled()
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
        appInstalledListPanel = new JPanel();
        appScrollPanel = new JScrollPane();
        appInstalledListTable = new JTable();

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

        startClicked = false;
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

        gBC = new GridBagConstraints();
        gBC.gridx = 0;
        gBC.gridy = 0;
        gBC.fill = GridBagConstraints.BOTH;
        gBC.weightx = 1;
        gBC.weighty = 0.5;
        gBC.insets = new Insets(0, 40, 0, 600);
        add(appButtonPanel, gBC);

        appInstalledListPanel.setLayout(new java.awt.GridBagLayout());

        appInstalledListTable.setDefaultEditor(Object.class, null);
        appInstalledListTable.getTableHeader().setBackground(new java.awt.Color(40, 50, 65));
        appInstalledListTable.getTableHeader().setForeground(new java.awt.Color(255,255,255));
        appInstalledListTable.getTableHeader().setFont(new Font("STXihei", 0, 18));
        appInstalledListTable.setFont(new java.awt.Font("STXihei", 0, 16)); // NOI18N
        appInstalledListTable.setModel(new javax.swing.table.DefaultTableModel(
                new Object [][] {
                        { null, null},
                        { null, null},
                        { null, null},
                        { null, null},
                        { null, null},
                        { null, null},
                        { null, null},
                        { null, null},
                        { null, null},
                        { null, null},
                        { null, null},
                        { null, null},
                        { null, null},
                        { null, null},
                        { null, null},
                        { null, null},
                        { null, null}
                },
                headerAppInstall
        ) {
            Class[] types = new Class [] {
                    java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                    false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });

        appInstalledListTable.setRowHeight(30);
        appInstalledListTable.getTableHeader().setReorderingAllowed(false);
        appScrollPanel.setViewportView(appInstalledListTable);
        if (appInstalledListTable.getColumnModel().getColumnCount() > 0) {
            appInstalledListTable.getColumnModel().getColumn(0).setPreferredWidth(100);
            appInstalledListTable.getColumnModel().getColumn(1).setPreferredWidth(300);
        }
        appInstalledListTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                clickedAppName = appInstalledListTable.getValueAt(appInstalledListTable.getSelectedRow(), 0).toString().trim();
                clickedAppDirectory = appInstalledListTable.getValueAt(appInstalledListTable.getSelectedRow(), 1).toString();
                startClicked = true;
            }
        });

        gBC = new java.awt.GridBagConstraints();
        gBC.gridx = 0;
        gBC.gridy = 0;
        gBC.fill = java.awt.GridBagConstraints.BOTH;
        gBC.weightx = 1.0;
        gBC.weighty = 1.0;
        appInstalledListPanel.add(appScrollPanel, gBC);

        gBC = new GridBagConstraints();
        gBC.gridx = 0;
        gBC.gridy = 1;
        gBC.fill = GridBagConstraints.BOTH;
        gBC.weightx = 1.0;
        gBC.weighty = 0.5;
        add(appInstalledListPanel, gBC);
    }

    public JButton getListButton() {
        return listButton;
    }

    public JButton getStartButton() {
        return startButton;
    }

    public String getClickedAppName() {
        return clickedAppName;
    }

    public void updateAppInstalledPanel(ArrayList<ArrayList<String>> infoAppInstalled)
    {
        String[][] infoAppInstalledArray = infoAppInstalled.stream().map(u -> u.toArray(new String[0])).toArray(String[][]::new);

        appInstalledListTable.repaint();
        appInstalledListTable.setModel(new DefaultTableModel(infoAppInstalledArray, headerAppInstall));
        if (appInstalledListTable.getColumnModel().getColumnCount() > 0) {
            appInstalledListTable.getColumnModel().getColumn(0).setPreferredWidth(100);
            appInstalledListTable.getColumnModel().getColumn(1).setPreferredWidth(300);
        }
    }

    public String getClickedAppDirectory() {
        return clickedAppDirectory;
    }

    public Boolean getStartClicked() {
        return startClicked;
    }



    private JButton listButton;
    private JButton startButton;
    private JPanel appButtonPanel;
    private JPanel appInstalledListPanel;
    private JScrollPane appScrollPanel;
    private JTable appInstalledListTable;

    private String[] headerAppInstall = {"Application", "Directory"};
    private String clickedAppName;
    private String clickedAppDirectory;
    private Boolean startClicked;
}

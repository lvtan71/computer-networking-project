package view.card;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class ApplicationRunning extends JPanel {
    public ApplicationRunning()
    {
        initComponents();
    }

    private void initComponents()
    {
        GridBagConstraints gBC;

        setLayout(new GridBagLayout());
        appButtonPanel = new JPanel();
        listButton = new JButton();
        stopButton = new JButton();
        appListPanel = new JPanel();
        appScrollPanel = new JScrollPane();
        appRunningListTable = new JTable();

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
        gBC.insets = new Insets(0, 40, 0, 600);
        add(appButtonPanel, gBC);

        appListPanel.setLayout(new GridBagLayout());

        appRunningListTable.getTableHeader().setBackground(new Color(40, 50, 65));
        appRunningListTable.getTableHeader().setForeground(new Color(255,255,255));
        appRunningListTable.getTableHeader().setFont(new Font("STXihei", 0, 18));
        appRunningListTable.setFont(new Font("STXihei", 0, 16)); // NOI18N
        appRunningListTable.setModel(new javax.swing.table.DefaultTableModel(
                new Object [][] {
                        {null, null, null},
                        {null, null, null},
                        {null, null, null},
                        {null, null, null},
                        {null, null, null},
                        {null, null, null},
                        {null, null, null},
                        {null, null, null},
                        {null, null, null},
                        {null, null, null},
                        {null, null, null},
                        {null, null, null},
                        {null, null, null},
                        {null, null, null},
                        {null, null, null},
                        {null, null, null}
                },
                headerAppRunning
        ) {
            Class[] types = new Class [] {
                    String.class, String.class, String.class
            };
            boolean[] canEdit = new boolean [] {
                    false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });

        appRunningListTable.setRowHeight(30);
        appRunningListTable.getTableHeader().setReorderingAllowed(false);
        appScrollPanel.setViewportView(appRunningListTable);
        if (appRunningListTable.getColumnModel().getColumnCount() > 0) {
            appRunningListTable.getColumnModel().getColumn(0).setPreferredWidth(200);
            appRunningListTable.getColumnModel().getColumn(1).setPreferredWidth(50);
            appRunningListTable.getColumnModel().getColumn(2).setPreferredWidth(400);
        }
        appRunningListTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                clickedAppRunningName = appRunningListTable.getValueAt(appRunningListTable.getSelectedRow(), 0).toString().trim();
                clickedAppRunningID = appRunningListTable.getValueAt(appRunningListTable.getSelectedRow(), 1).toString().trim();
                isClickedAppRunning = true;
            }
        });

        gBC = new GridBagConstraints();
        gBC.gridx = 0;
        gBC.gridy = 0;
        gBC.fill = GridBagConstraints.BOTH;
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

    public void updateAppRunningTable(ArrayList<ArrayList<String>> infoAppRunning)
    {
        String[][] infoAppRunningArray = infoAppRunning.stream().map(u -> u.toArray(new String[0])).toArray(String[][]::new);

        appRunningListTable.repaint();
        appRunningListTable.setModel(new DefaultTableModel(infoAppRunningArray, headerAppRunning));
        if (appRunningListTable.getColumnModel().getColumnCount() > 0) {
            appRunningListTable.getColumnModel().getColumn(0).setPreferredWidth(200);
            appRunningListTable.getColumnModel().getColumn(1).setPreferredWidth(50);
            appRunningListTable.getColumnModel().getColumn(2).setPreferredWidth(400);
        }
    }

    public JButton getStopButton() {
        return stopButton;
    }

    public Boolean getClickedAppRunning() {
        return isClickedAppRunning;
    }

    public String getClickedAppRunningID() {
        return clickedAppRunningID;
    }

    public String getClickedAppRunningName() {
        return clickedAppRunningName;
    }

    private JButton listButton;
    private JButton stopButton;
    private JPanel appButtonPanel;
    private JPanel appListPanel;
    private JScrollPane appScrollPanel;
    private JTable appRunningListTable;
    private String[] headerAppRunning = {"Application", "ID", "Title"};
    private String clickedAppRunningName;
    private String clickedAppRunningID;
    private Boolean isClickedAppRunning = false;
}

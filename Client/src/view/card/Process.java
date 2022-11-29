package view.card;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class Process extends JPanel {
    public Process()
    {
        initComponents();
    }

    private void initComponents()
    {
        GridBagConstraints gBC;

        setLayout(new GridBagLayout());
        processButtonPanel = new JPanel();
        listButton = new JButton();
        stopButton = new JButton();
        processListPanel = new JPanel();
        processScrollPanel = new JScrollPane();
        processListTable = new JTable();

        processButtonPanel.setLayout(new GridBagLayout());

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

        processButtonPanel.add(listButton, gBC);


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
        processButtonPanel.add(stopButton, gBC);

        gBC = new GridBagConstraints();
        gBC.gridx = 0;
        gBC.gridy = 0;
        gBC.fill = GridBagConstraints.BOTH;
        gBC.weightx = 1;
        gBC.weighty = 0.5;
        gBC.insets = new Insets(0, 40, 0, 600);
        add(processButtonPanel, gBC);

        processListPanel.setLayout(new GridBagLayout());

        processListTable.setDefaultEditor(Object.class, null);
        processListTable.getTableHeader().setBackground(new Color(40, 50, 65));
        processListTable.getTableHeader().setForeground(new Color(255,255,255));
        processListTable.getTableHeader().setFont(new Font("STXihei", 0, 18));
        processListTable.setFont(new Font("STXihei", 0, 16)); // NOI18N
        processListTable.setModel(new javax.swing.table.DefaultTableModel(
                new Object [][] {
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
                headerProcess
        ) {
            Class[] types = new Class [] {
                    String.class, String.class, String.class, String.class
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

        processListTable.setRowHeight(30);
        processListTable.getTableHeader().setReorderingAllowed(false);
        processListTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                    clickedProcessID = processListTable.getValueAt(processListTable.getSelectedRow(), 1).toString();
                    clickedProcessName = processListTable.getValueAt(processListTable.getSelectedRow(), 0).toString();
                    System.out.println(clickedProcessID);
            }
        });
        processScrollPanel.setViewportView(processListTable);

        if (processListTable.getColumnModel().getColumnCount() > 0) {
            processListTable.getColumnModel().getColumn(0).setPreferredWidth(400);
            processListTable.getColumnModel().getColumn(1).setPreferredWidth(70);
            processListTable.getColumnModel().getColumn(2).setPreferredWidth(70);
            processListTable.getColumnModel().getColumn(3).setPreferredWidth(70);
        }

        gBC = new GridBagConstraints();
        gBC.gridx = 0;
        gBC.gridy = 0;
        gBC.fill = GridBagConstraints.BOTH;
        gBC.weightx = 1.0;
        gBC.weighty = 1.0;
        processListPanel.add(processScrollPanel, gBC);

        gBC = new GridBagConstraints();
        gBC.gridx = 0;
        gBC.gridy = 1;
        gBC.fill = GridBagConstraints.BOTH;
        gBC.weightx = 1.0;
        gBC.weighty = 0.5;
        add(processListPanel, gBC);
    }

    public void updateProcessTable(ArrayList<ArrayList<String>> infoProcess)
    {
        String[][] infoProcessArray = infoProcess.stream().map(u -> u.toArray(new String[0])).toArray(String[][]::new);

        processListTable.repaint();
        processListTable.setModel(new DefaultTableModel(infoProcessArray, headerProcess));
        if (processListTable.getColumnModel().getColumnCount() > 0) {
            processListTable.getColumnModel().getColumn(0).setPreferredWidth(400);
            processListTable.getColumnModel().getColumn(1).setPreferredWidth(70);
            processListTable.getColumnModel().getColumn(2).setPreferredWidth(70);
            processListTable.getColumnModel().getColumn(3).setPreferredWidth(70);
        }
    }

    public String getClickedProcessID() {
        return clickedProcessID;
    }

    public JPanel getProcessListPanel() {
        return processListPanel;
    }

    public JButton getListButton() {
        return listButton;
    }


    public String getClickedProcessName() {
        return clickedProcessName;
    }

    public JButton getStopButton() {
        return stopButton;
    }

    private JButton listButton;
    private JButton stopButton;
    private JPanel processButtonPanel;
    private JPanel processListPanel;
    private JScrollPane processScrollPanel;
    private JTable processListTable;
    private String[] headerProcess = {"Process", "ID", "Session", "Mem Usage"};
    private String clickedProcessID;
    private String clickedProcessName;
}

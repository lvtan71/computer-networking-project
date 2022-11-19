package view.card;

import java.awt.*;
import javax.swing.*;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

public class Keylogger extends JPanel {
    public Keylogger()
    {
        initComponents();
    }

    private void initComponents()
    {
        GridBagConstraints gBC;

        setLayout(new GridBagLayout());
        logButtonPanel = new JPanel();
        hookButton = new JButton();
        unHookButton = new JButton();
        printButton = new JButton();
        keyPanel = new JPanel();
        keyScrollPane = new JScrollPane();
        keyTextArea = new JTextArea();

        logButtonPanel.setForeground(new java.awt.Color(101, 0, 0));
        logButtonPanel.setLayout(new java.awt.GridBagLayout());

        hookButton.setBackground(new java.awt.Color(40, 50, 65));
        hookButton.setFont(new java.awt.Font("STXihei", -1, 22)); // NOI18N
        hookButton.setForeground(new java.awt.Color(130, 197, 190));
        hookButton.setText("Hook");
        gBC = new java.awt.GridBagConstraints();
        gBC.gridx = 0;
        gBC.gridy = 0;
        gBC.ipadx = 60;
        gBC.ipady = 25;
        gBC.weightx = 0.2;
        gBC.weighty = 1.0;
//        gBC.insets = new Insets(0, 50, 0, 0);

        logButtonPanel.add(hookButton, gBC);

        unHookButton.setBackground(new java.awt.Color(40, 50, 65));
        unHookButton.setFont(new java.awt.Font("STXihei", 0, 22)); // NOI18N
        unHookButton.setForeground(new java.awt.Color(130, 197, 190));
        unHookButton.setText("Unhook");
        gBC = new java.awt.GridBagConstraints();
        gBC.gridx = 1;
        gBC.gridy = 0;
        gBC.ipadx = 60;
        gBC.ipady = 25;
        gBC.weightx = 0.2;
        gBC.weighty = 1.0;
        logButtonPanel.add(unHookButton, gBC);

        printButton.setBackground(new java.awt.Color(40, 50, 65));
        printButton.setFont(new java.awt.Font("STXihei", -1, 22)); // NOI18N
        printButton.setForeground(new java.awt.Color(130, 197, 190));
        printButton.setText("Print");
        gBC = new java.awt.GridBagConstraints();
        gBC.gridx = 2;
        gBC.gridy = 0;
        gBC.ipadx = 60;
        gBC.ipady = 25;
        gBC.weightx = 0.2;
        gBC.weighty = 1.0;
//        gBC.insets = new Insets(0, 0, 0, 200);
        logButtonPanel.add(printButton, gBC);

        gBC = new java.awt.GridBagConstraints();
        gBC.gridx = 0;
        gBC.gridy = 0;
        gBC.fill = java.awt.GridBagConstraints.BOTH;
        gBC.weightx = 1;
        gBC.weighty = 0.2;
        gBC.insets = new Insets(0, 40, 0, 175);
        add(logButtonPanel, gBC);

        keyPanel.setLayout(new java.awt.GridBagLayout());

        keyTextArea.setColumns(20);
        keyTextArea.setRows(5);
        keyTextArea.setFont(new java.awt.Font("STXihei", 0, 18)); // NOI18N
        keyTextArea.setLineWrap(true);
        keyTextArea.setWrapStyleWord(true);
        keyTextArea.setEditable(false);
        keyScrollPane.setViewportView(keyTextArea);

        gBC = new java.awt.GridBagConstraints();
        gBC.gridx = 0;
        gBC.gridy = 0;
        gBC.fill = java.awt.GridBagConstraints.BOTH;
        gBC.weightx = 1;
        gBC.weighty = 1;
        gBC.insets = new java.awt.Insets(0, 0, 0, 0);
        keyPanel.add(keyScrollPane, gBC);

        gBC = new java.awt.GridBagConstraints();
        gBC.gridx = 0;
        gBC.gridy = 1;
        gBC.fill = java.awt.GridBagConstraints.BOTH;
        gBC.weightx = 1.0;
        gBC.weighty = 0.8;
        add(keyPanel, gBC);
    }

    public JButton getHookButton() {
        return hookButton;
    }

    public JButton getUnHookButton() {
        return unHookButton;
    }

    public JButton getPrintButton() {
        return printButton;
    }

    public JTextArea getKeyTextArea() {
        return keyTextArea;
    }

    private JButton hookButton;
    private JButton unHookButton;
    private JButton printButton;
    private JPanel logButtonPanel;
    private JPanel keyPanel;
    private JScrollPane keyScrollPane;
    private JTextArea keyTextArea;
}

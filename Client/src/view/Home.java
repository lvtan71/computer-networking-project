package view;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.JLabel;
import java.awt.CardLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import view.card.Keylogger;

public class Home extends JFrame
{
    public Home() {
        initComponents();
        cardLayout = (CardLayout)(mainPanel.getLayout());
    }

    private void initComponents() {
        GridBagConstraints gBC;

        buttonPanel = new JPanel();
        appButton = new JButton();
        processButton = new JButton();
        screenButton = new JButton();
        mainPanel = new JPanel();
        logButton = new JButton();
        appPanel = new JPanel();
        processPanel = new JPanel();
        screenPanel = new JPanel();
        logPanel = new Keylogger();

        setAutoRequestFocus(false);
        setBackground(new Color(255, 255, 255));
        setPreferredSize(new Dimension(1050, 700));
        getContentPane().setLayout(new GridBagLayout());

        buttonPanel.setBackground(new Color(41, 50, 65));
        buttonPanel.setForeground(new Color(41, 50, 65));
        buttonPanel.setLayout(new GridBagLayout());

        appButton.setBackground(new Color(41, 50, 65));
        appButton.setFont(new Font("STXihei", 0, 24)); // NOI18N
        appButton.setForeground(new Color(131, 197, 190));
        appButton.setText("Application");
        appButton.setBorder(null);
        appButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                appButtonActionPerformed(evt);
            }
        });
        gBC = new GridBagConstraints();
        gBC.gridx = 0;
        gBC.gridy = 0;
        gBC.fill = GridBagConstraints.BOTH;
        gBC.anchor = GridBagConstraints.NORTHWEST;
        gBC.weightx = 1.0;
        gBC.weighty = 0.2;
        gBC.insets = new Insets(50, 0, 0, 0);
        buttonPanel.add(appButton, gBC);

        processButton.setBackground(new Color(41, 50, 65));
        processButton.setFont(new Font("STXihei", 0, 24)); // NOI18N
        processButton.setForeground(new Color(131, 197, 190));
        processButton.setText("Process");
        processButton.setBorder(null);
        processButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                processButtonActionPerformed(evt);
            }
        });
        gBC = new GridBagConstraints();
        gBC.gridx = 0;
        gBC.gridy = 1;
        gBC.fill = GridBagConstraints.BOTH;
        gBC.weightx = 1.0;
        gBC.weighty = 0.2;
        buttonPanel.add(processButton, gBC);

        screenButton.setBackground(new Color(41, 50, 65));
        screenButton.setFont(new Font("STXihei", 0, 24)); // NOI18N
        screenButton.setForeground(new Color(131, 197, 190));
        screenButton.setText("Screen Shot");
        screenButton.setBorder(null);
        screenButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                screenButtonActionPerformed(evt);
            }
        });
        gBC = new GridBagConstraints();
        gBC.gridx = 0;
        gBC.gridy = 2;
        gBC.fill = GridBagConstraints.BOTH;
        gBC.anchor = GridBagConstraints.NORTHWEST;
        gBC.weightx = 1.0;
        gBC.weighty = 0.2;
        gBC.insets = new Insets(0, 0, 0, 0);
        buttonPanel.add(screenButton, gBC);

        // Logger Button
        logButton.setBackground(new Color(41, 50, 65));
        logButton.setFont(new Font("STXihei", 0, 24)); // NOI18N
        logButton.setForeground(new Color(131, 197, 190));
        logButton.setText("Keylogger");
        logButton.setBorder(null);
        logButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                logButtonActionPerformed(evt);
            }
        });

        gBC = new GridBagConstraints();
        gBC.gridx = 0;
        gBC.gridy = 3;
        gBC.fill = GridBagConstraints.BOTH;
        gBC.anchor = GridBagConstraints.NORTHWEST;
        gBC.weightx = 1.0;
        gBC.weighty = 0.2;
        gBC.insets = new Insets(0, 0, 250, 0);
        buttonPanel.add(logButton, gBC);

        // Button Panel
        gBC = new GridBagConstraints();
        gBC.gridx = 0;
        gBC.gridy = 0;
        gBC.fill = GridBagConstraints.BOTH;
        gBC.weightx = 0.4;
        gBC.weighty = 1.0;
        getContentPane().add(buttonPanel, gBC);

        mainPanel.setLayout(new CardLayout());

        // Application Panel
        appPanel.setBackground(new Color(153, 153, 0));
        mainPanel.add(appPanel, "appCard");

        // Process Panel
        processPanel.setBackground(new Color(204, 51, 0));
        mainPanel.add(processPanel, "processCard");

        // Screen Shot Panel
        screenPanel.setBackground(new Color(51, 102, 255));
        mainPanel.add(screenPanel, "screenCard");

        // Keylogger Shot Panel
        mainPanel.add(logPanel, "logCard");

        gBC = new GridBagConstraints();
        gBC.gridx = 1;
        gBC.gridy = 0;
        gBC.fill = GridBagConstraints.BOTH;
        gBC.weightx = 0.6;
        gBC.weighty = 1;
        getContentPane().add(mainPanel, gBC);

        pack();
    }

    private void appButtonActionPerformed(ActionEvent evt) {
        cardLayout.show(mainPanel, "appCard");
    }

    private void processButtonActionPerformed(ActionEvent evt) {
        cardLayout.show(mainPanel, "processCard");
    }

    private void screenButtonActionPerformed(ActionEvent evt) {
        cardLayout.show(mainPanel, "screenCard");
    }

    private void logButtonActionPerformed(ActionEvent evt) {
        cardLayout.show(mainPanel, "logCard");
    }

    public Keylogger getLogPanel() {
        return logPanel;
    }

    public JButton getLogButton() {
        return logButton;
    }

    private CardLayout cardLayout;

    // Variables declaration - do not modify
    private JButton appButton;
    private JPanel appPanel;
    private JPanel buttonPanel;
    private JPanel mainPanel;
    private JButton processButton;
    private JPanel processPanel;
    private JButton screenButton;
    private JPanel screenPanel;
    private JButton logButton;
    private Keylogger logPanel;

    public static void main(String[] args)
    {
        new Home();
    }
}

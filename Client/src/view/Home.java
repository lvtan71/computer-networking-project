package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import view.card.*;
import view.card.Process;

public class Home extends JFrame
{
    public Home() {
        initComponents();
        cardLayout = (CardLayout)(mainPanel.getLayout());
    }

    private void initComponents() {
        GridBagConstraints gBC;

        buttonPanel = new JPanel();
        appInstallButton = new JButton();
        appRunningButton = new JButton();
        processButton = new JButton();
        screenButton = new JButton();
        mainPanel = new JPanel();
        logButton = new JButton();
        appInstallPanel = new ApplicationInstalled();
        appRunningPanel = new ApplicationRunning();
        processPanel = new Process();
        screenPanel = new ScreenShot();
        logPanel = new Keylogger();

        setAutoRequestFocus(false);
        setBackground(new Color(255, 255, 255));
        setPreferredSize(new Dimension(1200, 700));
        getContentPane().setLayout(new GridBagLayout());

        buttonPanel.setBackground(new Color(41, 50, 65));
        buttonPanel.setForeground(new Color(41, 50, 65));
        buttonPanel.setLayout(new GridBagLayout());

        appRunningButton.setBackground(new Color(53, 79, 82));
        appRunningButton.setFont(new Font("STXihei", 0, 24)); // NOI18N
        appRunningButton.setForeground(new Color(131, 197, 190));
        appRunningButton.setText("<html>App Running</html>");
        appRunningButton.setBorder(null);
        appRunningButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                appRunningButtonActionPerformed(evt);
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
        buttonPanel.add(appRunningButton, gBC);

        appInstallButton.setBackground(new Color(41, 50, 65));
        appInstallButton.setFont(new Font("STXihei", 0, 24)); // NOI18N
        appInstallButton.setForeground(new Color(131, 197, 190));
        appInstallButton.setText("<html>App Installed</html>");
        appInstallButton.setBorder(null);
        appInstallButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                appInstallButtonActionPerformed(evt);
            }
        });
        gBC = new GridBagConstraints();
        gBC.gridx = 0;
        gBC.gridy = 1;
        gBC.fill = GridBagConstraints.BOTH;
        gBC.anchor = GridBagConstraints.NORTHWEST;
        gBC.weightx = 1.0;
        gBC.weighty = 0.2;
        gBC.insets = new Insets(0, 0, 0, 0);
        buttonPanel.add(appInstallButton, gBC);

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
        gBC.gridy = 2;
        gBC.fill = GridBagConstraints.BOTH;
        gBC.weightx = 1.0;
        gBC.weighty = 0.2;
        buttonPanel.add(processButton, gBC);

        // Screenshot Button
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
        gBC.gridy = 3;
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
        gBC.gridy = 4;
        gBC.fill = GridBagConstraints.BOTH;
        gBC.weightx = 1.0;
        gBC.weighty = 0.2;
        gBC.insets = new Insets(0, 0, 150, 0);
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

        // Application Running Panel
        mainPanel.add(appInstallPanel, "appInstallCard");

        // Application Install Panel
        mainPanel.add(appRunningPanel, "appRunningCard");

        // Process Panel
        mainPanel.add(processPanel, "processCard");

        // Screen Shot Panel
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

    private void changeStateBackground(JButton button)
    {
        button.setBackground(new Color(41, 50, 65));
    }

    private void appRunningButtonActionPerformed(ActionEvent evt) {
        cardLayout.show(mainPanel, "appRunningCard");
        appInstallButton.setBackground(new Color(53, 79, 82));
        changeStateBackground(processButton);
        changeStateBackground(screenButton);
        changeStateBackground(logButton);
        changeStateBackground(appInstallButton);
    }

    private void appInstallButtonActionPerformed(ActionEvent evt) {
        cardLayout.show(mainPanel, "appInstallCard");
        appInstallButton.setBackground(new Color(53, 79, 82));
        changeStateBackground(processButton);
        changeStateBackground(screenButton);
        changeStateBackground(logButton);
        changeStateBackground(appRunningButton);
    }

    private void processButtonActionPerformed(ActionEvent evt) {
        cardLayout.show(mainPanel, "processCard");
        processButton.setBackground(new Color(53, 79, 82));
        changeStateBackground(appInstallButton);
        changeStateBackground(screenButton);
        changeStateBackground(logButton);
        changeStateBackground(appRunningButton);
    }

    private void screenButtonActionPerformed(ActionEvent evt) {
        cardLayout.show(mainPanel, "screenCard");
        screenButton.setBackground(new Color(53, 79, 82));
        changeStateBackground(appInstallButton);
        changeStateBackground(processButton);
        changeStateBackground(logButton);
        changeStateBackground(appRunningButton);
    }

    private void logButtonActionPerformed(ActionEvent evt) {
        cardLayout.show(mainPanel, "logCard");
        logButton.setBackground(new Color(53, 79, 82));
        changeStateBackground(appInstallButton);
        changeStateBackground(processButton);
        changeStateBackground(screenButton);
    }

    public JButton getScreenButton() {
        return screenButton;
    }

    public ApplicationInstalled getAppInstallPanel() {
        return appInstallPanel;
    }

    public ApplicationRunning getAppRunningPanel() {
        return appRunningPanel;
    }

    public ScreenShot getScreenPanel() {
        return screenPanel;
    }

    public Keylogger getLogPanel() {
        return logPanel;
    }

    public Process getProcessPanel() {
        return processPanel;
    }

    public void notify(String title, String notify)
    {
        notifyDialog = new JDialog();
        notifyLabel = new JLabel();

        notifyDialog.setMinimumSize(new Dimension(320, 150));
        notifyDialog.setLayout(new GridBagLayout());
        notifyDialog.setTitle(title);
        notifyDialog.setLocationRelativeTo(this);

        notifyLabel.setFont(new Font("STXihei", 0, 18)); //
        notifyLabel.setText(notify);
        GridBagConstraints gBC = new GridBagConstraints();
        gBC.gridx = 0;
        gBC.gridy = 0;
        notifyDialog.add(notifyLabel);

        notifyDialog.pack();
        notifyDialog.setVisible(true);
        notifyDialog.setModal(true);
    }


    private CardLayout cardLayout;

    // Variables declaration - do not modify
    private JButton appInstallButton;
    private JButton appRunningButton;
    private ApplicationInstalled appInstallPanel;
    private ApplicationRunning appRunningPanel;
    private JPanel buttonPanel;
    private JPanel mainPanel;
    private JButton processButton;
    private Process processPanel;
    private JButton screenButton;
    private ScreenShot screenPanel;
    private JButton logButton;
    private Keylogger logPanel;
    private Boolean[] activeButton = {true, false, false, false};
    private int clickedProcessID;
    public static void main(String[] args)
    {
        Home a = new Home();
        a.setVisible(true);
    }

    private JDialog notifyDialog;
    private JLabel notifyLabel;
}

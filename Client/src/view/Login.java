package view;

import javax.swing.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;

public class Login extends JFrame
{
    public Login() {
        initComponents();
        setVisible(true);
    }

    private void initComponents() {
        GridBagConstraints gBC;

        topPanel = new JPanel();
        jLabel1 = new JLabel();
        botPanel = new JPanel();
        connectButton = new JButton();
        ipTextField = new JTextField();
        ipLabel = new JLabel();
        statusLabel = new JLabel();

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setBackground(new Color(255, 255, 255));
        setMinimumSize(new Dimension(400, 650));
        getContentPane().setLayout(new GridBagLayout());


        topPanel.setBackground(new Color(41, 50, 65));
        topPanel.setLayout(new GridBagLayout());

        //statusLabel.setBackground(new Color(233, 196, 106));
        jLabel1.setFont(new Font("STXihei", Font.BOLD, 28));
        jLabel1.setForeground(new Color(131, 197, 190));
        jLabel1.setText("Remote Controller");
        topPanel.add(jLabel1, new GridBagConstraints());

        gBC = new GridBagConstraints();
        gBC.gridx = 0;
        gBC.gridy = 0;
        gBC.fill = GridBagConstraints.BOTH;
        gBC.weightx = 1.0;
        gBC.weighty = 0.4;
        getContentPane().add(topPanel, gBC);

        botPanel.setLayout(new GridBagLayout());

        // Connect Button
        connectButton.setBackground(new Color(41, 50, 65));
        connectButton.setFont(new Font("STXinwei", 0, 20));
        connectButton.setForeground(new Color(131, 197, 190));
        connectButton.setText("Connect");
        connectButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                connectButtonMouseEntered(evt);
            }
            public void mouseExited(MouseEvent evt)
            {
                connectButtonMouseExited(evt);
            }
        });
        gBC = new GridBagConstraints();
        gBC.gridx = 0;
        gBC.gridy = 2;
        gBC.gridwidth = 2;
        gBC.ipadx = 60;
        gBC.ipady = 30;
        gBC.weighty = 0.4;
        gBC.insets = new Insets(0, 0, 30, 0);
        botPanel.add(connectButton, gBC);

        // IP Text Field
        ipTextField.setFont(new Font("STXihei", 0, 18));
        ipTextField.setText("Enter IP ");
        ipTextField.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt)
            {
                ipTextFieldMouseClicked(evt);
            }
        });
        ipTextField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                ipTextFieldActionPerformed(evt);
            }
        });
        gBC = new GridBagConstraints();
        gBC.gridx = 1;
        gBC.gridy = 0;
        gBC.ipady = 20;
        gBC.fill = GridBagConstraints.HORIZONTAL;
        gBC.weightx = 0.7;
        gBC.weighty = 0.4;
        gBC.insets = new Insets(0, 0, 0, 18);
        botPanel.add(ipTextField, gBC);

        // IP Label
        ipLabel.setFont(new Font("STXihei", 0, 18)); //
        ipLabel.setText("IP Address");
        gBC = new GridBagConstraints();
        gBC.gridx = 0;
        gBC.gridy = 0;
        gBC.weightx = 0.3;
        gBC.weighty = 0.4;
        gBC.insets = new Insets(0, 0, 0, 0);
        botPanel.add(ipLabel, gBC);

        // Notification
        statusLabel.setFont(new Font("STXihei", 0, 14)); //
        statusLabel.setText("");
        gBC = new GridBagConstraints();
        gBC.gridx = 0;
        gBC.gridy = 1;
        gBC.gridwidth = 2;
        gBC.weightx = 1;
        gBC.weighty = 0.2;
        gBC.insets = new Insets(0, 0, 0, 0);
        botPanel.add(statusLabel, gBC);

        // Bot Panel
        gBC = new GridBagConstraints();
        gBC.gridx = 0;
        gBC.gridy = 1;
        gBC.fill = GridBagConstraints.BOTH;
        gBC.weightx = 1.0;
        gBC.weighty = 0.6;
        getContentPane().add(botPanel, gBC);

        pack();
    }

    private void ipTextFieldActionPerformed(ActionEvent evt) {
    }

    private void ipTextFieldMouseClicked(MouseEvent evt) {
        if(firstClick)
        {
            ipTextField.setText("");

            firstClick = false;
        }
    }

    private void connectButtonMouseEntered(MouseEvent evt)
    {
        connectButton.setForeground(new Color(41, 50, 65));

        connectButton.setBackground(new Color(131, 197, 190));
    }

    private void connectButtonMouseExited(MouseEvent evt)
    {
        connectButton.setBackground(new Color(41, 50, 65));

        connectButton.setForeground(new Color(131, 197, 190));
    }

    public JButton getConnectButton() {
        return connectButton;
    }

    public JTextField getIpTextField() {
        return ipTextField;
    }

    public JLabel getStatusLabel() {
        return statusLabel;
    }

    private Boolean firstClick = true;

    private JLabel statusLabel;
    private JPanel botPanel;
    private JButton connectButton;
    private JLabel ipLabel;
    private JTextField ipTextField;
    private JLabel jLabel1;
    private JPanel topPanel;
}

package view.card;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class ScreenShot extends JPanel {
    public ScreenShot()
    {
        initComponents();
    }

    private void initComponents()
    {
        GridBagConstraints gBC;

        setLayout(new GridBagLayout());
        screenButtonPanel = new JPanel();
        takeButton = new JButton();
        screenShotPanel = new JPanel();
        screenLabel = new JLabel();
        screenButtonPanel.setLayout(new GridBagLayout());

        takeButton.setBackground(new Color(40, 50, 65));
        takeButton.setFont(new Font("STXihei", -1, 22)); // NOI18N
        takeButton.setForeground(new Color(130, 197, 190));
        takeButton.setText("Take Screenshot");
        gBC = new GridBagConstraints();
        gBC.gridx = 0;
        gBC.gridy = 0;
        gBC.ipadx = 60;
        gBC.ipady = 25;
        gBC.weightx = 0.2;
        gBC.weighty = 1.0;

        screenButtonPanel.add(takeButton, gBC);

        gBC = new GridBagConstraints();
        gBC.gridx = 0;
        gBC.gridy = 0;
        gBC.fill = GridBagConstraints.BOTH;
        gBC.weightx = 1;
        gBC.weighty = 0.5;
        gBC.insets = new Insets(20, 40, 0, 580);
        add(screenButtonPanel, gBC);

        screenShotPanel.setLayout(new GridBagLayout());

        screenLabel.setHorizontalAlignment(JLabel.CENTER);
        screenLabel.setVerticalAlignment(JLabel.BOTTOM);
        gBC = new GridBagConstraints();
        gBC.gridx = 0;
        gBC.gridy = 0;
        gBC.anchor = GridBagConstraints.CENTER;
        gBC.fill = GridBagConstraints.HORIZONTAL;
        gBC.weightx = 1.0;
        gBC.weighty = 1.0;
        gBC.insets = new Insets(0, 0, 0, 0);
        screenShotPanel.add(screenLabel, gBC);


        gBC = new GridBagConstraints();
        gBC.gridx = 0;
        gBC.gridy = 1;
        gBC.fill = GridBagConstraints.BOTH;
        gBC.weightx = 1.0;
        gBC.weighty = 0.5;
        add(screenShotPanel, gBC);
    }

    public JButton getTakeButton() {
        return takeButton;
    }

    public void addImageLabel(BufferedImage image)
    {
        int factor = 55;
        ImageIcon imageIcon = new ImageIcon(image);
        Image tempImage = imageIcon.getImage();
        Image newImage = tempImage.getScaledInstance(16 * factor, 9 * factor, Image.SCALE_SMOOTH);
        screenLabel.setIcon(new ImageIcon(newImage));
    }

    private JButton takeButton;
    private JPanel screenButtonPanel;
    private JPanel screenShotPanel;
    private JLabel screenLabel;
}

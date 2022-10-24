import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.BorderFactory;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Color;
import java.awt.Insets;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class Dialog
{
    Boolean firstClick = false;
    private JDialog frame;
    private JPanel panel;
    private JLabel label;
    private JButton button;
    private JTextField textField;

    Dialog()
    {
        this.frame = prepareFrame();
        this.frame.setSize(550, 200);
        //this.panel = preparePanel();
        this.button = prepareButton();
        this.label = prepareLabel();
        this.textField = prepareTextField();

        prepareLayout();
    }

    private void prepareLayout()
    {
        this.frame.setLayout(new GridBagLayout());
        GridBagConstraints gBC = new GridBagConstraints();

        gBC.gridx = 0;
        gBC.gridy = 0;
        gBC.gridwidth = 2;
        gBC.gridheight = 1;
        gBC.weightx = 1;
        gBC.weighty = 0.3;
        gBC.insets = new Insets(0, 0, 0, 0);
        gBC.anchor = GridBagConstraints.PAGE_END;
        this.frame.add(this.label, gBC);



        gBC.gridx = 1;
        gBC.gridy = 1;
        gBC.gridwidth = 1;
        gBC.gridheight = 1;
        gBC.weightx = 0.3;
        gBC.weighty = 0.7;
        gBC.insets = new Insets(0, 0, 0, 0);
        gBC.anchor = GridBagConstraints.CENTER;
        gBC.fill = GridBagConstraints.NONE;
        this.frame.add(this.button, gBC);

        gBC.gridx = 0;
        gBC.gridy = 1;
        gBC.gridwidth = 1;
        gBC.gridheight = 1;
        gBC.weightx = 0.7;
        gBC.weighty = 0.7;
        gBC.insets = new Insets(0, 40, 0, 0);
        gBC.anchor = GridBagConstraints.CENTER;
        gBC.fill = GridBagConstraints.HORIZONTAL;
        this.frame.add(this.textField, gBC);
        
        return;
    }

    private JTextField prepareTextField()
    {
        this.textField = new JTextField(12);
        this.textField.setText("Enter IP Address");
        this.textField.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e)
                {
                    if (!firstClick)
                    {
                        textField.setText("");

                        firstClick = true;
                    }
                }
            }); 

        this.textField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                String IP = textField.getText();
                connectServer(IP);
            }
        });
        
        return this.textField;
    }

    private JButton prepareButton()
    {
        this.button = new JButton();
        this.button.setText("Enter");
        this.button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                String IP = textField.getText();
                connectServer(IP);
            }
        });

        return this.button;
    }

    private JLabel prepareLabel()
    {
        this.label = new JLabel();
        this.label.setText("CONNECT TO AIM SYSTEM");
        this.label.setForeground(Color.GRAY);
        this.label.setFont(new Font("SANS_SERIF", Font.BOLD, 20));

        return this.label;
    }

    private JDialog prepareFrame()
    {
        this.frame = new JDialog();
        this.frame.setTitle("Remote Controlling");
        this.frame.setVisible(true);

        return this.frame;
    }

    public void connectServer(String IP)
    {
        JOptionPane.showMessageDialog(null, "vailon connect");
    }

    public static void main(String[] args)
    {
        new Dialog();
    }
}    

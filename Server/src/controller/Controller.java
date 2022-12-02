package controller;

import java.io.*;
import java.net.*;

import model.Server;
import view.Home;

import javax.net.ssl.SSLEngineResult;

public class Controller {
    private Server server;
    private Home home;

    public Controller(Server server, Home home) {
        this.server = server;
        this.home = home;
        home.setVisible(true);
        this.server.addController(this);

        initController();
    }

    public void initController()
    {
        home.getOpenButton().addActionListener(e -> start());
        home.getCloseButton().addActionListener(e -> stop());
    }

    public void start() {
        if (!home.getOpen())
        {
            server.createServerSocket(9998, 1);
            server.run();
            home.getIpLabel().setText(server.getIP());
            home.setOpen(true);
            home.changeStateButton();
        }
    }

    public void stop()
    {
        if (home.getOpen())
        {
            try
            {
                server.getServerSock().close();
            }
            catch (Exception e)
            {
                //
            }

            server.setStatus(Server.Status.STOP);
            home.setOpen(false);
            home.changeStateButton();
        }
    }

    public void handleAction(String action) {
        home.updateTable(action);
    }
}
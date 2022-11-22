package controller;

import model.Client;
import view.Login;
import view.Home;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

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
        home.getLogPanel().getUnHookButton().addActionListener(e -> handleKeyLogger("Unhook"));
        home.getLogPanel().getPrintButton().addActionListener(e -> handleKeyLogger("Print"));
        home.getScreenPanel().getTakeButton().addActionListener(e -> handleScreenShot("ScreenShot", 1));
        home.getScreenButton().addActionListener(e -> handleScreenShot("ScreenShot", 2));

        home.getProcessPanel().getStartButton().addActionListener(e -> handleProcess("StartProcess"));
        home.getProcessPanel().getStopButton().addActionListener(e -> handleProcess("StopProcess"));
        home.getProcessPanel().getListButton().addActionListener(e -> handleProcess("ListProcess"));

        home.getAppPanel().getListButton().addActionListener(e -> handleApp("ListApp"));
        //////////
        home.getAppPanel().getListButton().addActionListener(e -> handleApp("RunningApp"));
        home.getAppPanel().getListButton().addActionListener((e -> handleApp("StopApp")));
        home.getAppPanel().getListButton().addActionListener(e -> handleApp("StartApp"));
    }
////////////////////////////
    private void handleApp(String command)
    {
        client.sendCommand(command);
        switch (command){
            case("ListApp"):{
                ArrayList<ArrayList<String>> appInstalled = client.getApplication().listAppInstalled();
                //home.getAppPanel()
            }
            case("RunningApp"):{
                ArrayList<ArrayList<String>> appRunning = client.getApplication().listAppRunning();
                //home.getAppPanel()
            }
            case("StopApp"):{
                // Thêm Click Stop App
//                String AppID = home.getAppPanel().getClickAppID();
//                client.getApplication().stopApp(AppID);
            }
            case("StartApp"):{
                // Thêm Click Start App
//                String AppLocation = home.getAppPanel().getClickAppLocation();
//                client.getApplication().startApp(AppLocation);
            }
        }
    }
///////////////////////////////
    private void handleProcess(String command)
    {
        client.sendCommand(command);
        switch (command)
        {
            case ("ListProcess"):
            {
                ArrayList<ArrayList<String>> infoProcess = client.getProcess().listProcess();
                home.getProcessPanel().updateProcessTable(infoProcess);
                break;
            }

            case ("StopProcess"):
            {
                String IDProcess = home.getProcessPanel().getClickedProcessID();
                String notifyStopStatus = client.getProcess().stopProcess(IDProcess);
                notifyStopStatus = notifyStopStatus.trim();

                System.out.println(notifyStopStatus);
            }
        }
    }

    private void handleScreenShot(String command, int type)
    {
        if (type == 2 && !firstTakeScreen)
        {
            return;
        }

        BufferedImage image = client.handleScreenShot(command);

//        try
//        {
            //ImageIO.write(image, "jpg", new File("./screenshot/image.jpg"));

            home.getScreenPanel().addImageLabel(image);
//        }
//        catch (IOException ioE)
//        {
//            ioE.printStackTrace();
//        }

        firstTakeScreen = false;
    }
    private void handleKeyLogger(String command)
    {
        String keylogger = client.handleKeyLogger(command);

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
    private Boolean firstTakeScreen = true;
}

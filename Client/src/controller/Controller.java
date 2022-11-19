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

        this.client.addController(this);
    }

    public void initController()
    {
        login.getConnectButton().addActionListener(e -> connectServer());
        home.getLogPanel().getHookButton().addActionListener(e -> handleAction("Hook"));
        home.getLogPanel().getUnHookButton().addActionListener(e -> handleAction("Unhook"));
        home.getLogPanel().getPrintButton().addActionListener(e -> handleAction("Print"));

        home.getScreenPanel().getTakeButton().addActionListener(e -> handleScreenShot("ScreenShot", 1));
        home.getScreenButton().addActionListener(e -> handleScreenShot("ScreenShot", 2));

        home.getProcessPanel().getStartButton().addActionListener(e -> handleProcess("StartProcess"));
        home.getProcessPanel().getStopButton().addActionListener(e -> handleProcess("StopProcess"));
        home.getProcessPanel().getListButton().addActionListener(e -> handleProcess("ListProcess"));

        home.getAppPanel().getListButton().addActionListener(e -> handleApp("ListApp"));
    }

    private void handleAction(String action) {
        switch(action) {
            case "Hook":
            case "Unhook":
            case "Print":
                client.handleAction(action);
                break;
        }
    }

    public void handleReturnedValue(String action, String out) {
        home.getLogPanel().getKeyTextArea().setText(out);
    }

    public void handleReturnedValue(String action, BufferedImage out) {

    }

    private void handleApp(String command)
    {
        System.out.println("Vuighe");
        client.handleApp(command);
    }

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

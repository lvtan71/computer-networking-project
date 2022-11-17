import java.io.*;
import java.net.*;

import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.GlobalScreen;

import model.Server;
import view.Terminal;
import controller.Controller;

public class App {
    public static void main(String[] args) {
        Server server = new Server(9999, 5);
        Terminal terminal = new Terminal();
        Controller controller = new Controller(server, terminal);


        controller.start();
    }
}
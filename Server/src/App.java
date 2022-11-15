import java.io.*;
import java.net.*;

import model.Server;

public class App {
    public static void main(String[] args) {
        Server server = new Server(9999, 5);
        System.out.println("OK");
    }
}

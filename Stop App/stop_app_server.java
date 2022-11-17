import java.io.InputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class stop_app_server {
    public static void main(String[] args){
        try {
            // Create Socket
            ServerSocket svSock = new ServerSocket(9999,5,InetAddress.getLocalHost());

            System.out.println("Created Socket");

            System.out.println("Connecting...");

            Socket clSocket = svSock.accept();

            System.out.println("Connected");

            // Stop App
            InputStream is = clSocket.getInputStream();
            byte [] BufferedReader = new byte[1024];

            is.read(BufferedReader);

            String appID = new String(BufferedReader,StandardCharsets.UTF_8);

            Runtime appRuntime = Runtime.getRuntime();

            System.out.println(appID.trim());

            appRuntime.exec("taskkill /PID " + appID.trim());

            is.close();

            clSocket.close();

            svSock.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

import java.io.InputStream;
import java.lang.String;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class start_app_server {
    public static void main(String[] args){
        try {
            ServerSocket svsock = new ServerSocket(9999,5, InetAddress.getLocalHost());
            System.out.println("Created socket");
            System.out.println("Connecting...");
            Socket clSocket = svsock.accept();
            System.out.println("Connected");

            InputStream is = clSocket.getInputStream();

            byte[] buffer = new byte[100];

            is.read(buffer);

            String apploc = new String(buffer, StandardCharsets.UTF_8);

            ProcessBuilder pb = new ProcessBuilder(apploc);
            pb.start();

            svsock.close();
            clSocket.close();

            System.out.println("Disconnected");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

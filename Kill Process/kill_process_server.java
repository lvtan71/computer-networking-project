import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class kill_process_server {
    public static void main(String[] args){
        try {
            ServerSocket svsock = new ServerSocket(9999,5, InetAddress.getLocalHost());

            System.out.println("Created socket");

            System.out.println("Connecting...");

            Socket clSocket = svsock.accept();

            System.out.println("Connected");

            InputStream is = clSocket.getInputStream();

            byte[] buffer = new byte[1024];

            is.read(buffer);

            String processname = new String(buffer);

            System.out.println("Process need to kill: " + processname);

            Runtime process = Runtime.getRuntime();
            process.exec("taskkill /F /IM " + processname + ".exe");

            svsock.close();
            clSocket.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;


public class kill_process_server {
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

            String processID = new String(buffer, StandardCharsets.UTF_8);
            
            // Vi mang byte co 100 phan tu ma processname chi can nhung ki tu ten chuong trinh, ton tai nhung khoang trong
            // can phai loai bocd 
            String removeEmpty = processID.replaceAll("\u0000.*", "");

            System.out.println("Process need to kill: " + removeEmpty);

            Runtime process = Runtime.getRuntime();

            process.exec("taskkill /F /PID " + removeEmpty);
        
            svsock.close();
            clSocket.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}

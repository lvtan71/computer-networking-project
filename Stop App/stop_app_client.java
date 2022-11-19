import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class stop_app_client {
    public static void main(String[] args){
        try {
            // Create Socket
            Socket sock = new Socket(InetAddress.getLocalHost(),9999);
            System.out.println("Connected");

            stopApp(sock);

            //Socket socket = new Socket(InetAddress.getLocalHost(), 9999);
            receiveStatus(sock);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void stopApp(Socket sock){
        try {
            // Stop App
            System.out.print("Input ID of the App need to stop: ");
            Scanner input = new Scanner(System.in);

            String appID = "";
            appID = input.nextLine();

            OutputStream os = sock.getOutputStream();

            byte [] BufferedWriter  = appID.getBytes(StandardCharsets.UTF_8);

            os.write(BufferedWriter);
  
            input.close();

            os.close();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void receiveStatus(Socket sock){
        try {
            InputStream is = sock.getInputStream();

            byte [] buffer = new byte[1024];

            is.read(buffer);

            String status = new String(buffer, StandardCharsets.UTF_8);

            System.out.println(status.trim());

            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

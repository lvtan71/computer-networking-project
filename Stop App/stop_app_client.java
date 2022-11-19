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

            OutputStream os = sock.getOutputStream();
            InputStream is = sock.getInputStream();

            stopApp(os);

            receiveStatus(is);

            os.close();
            is.close();
            sock.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void stopApp(OutputStream os){
        try {
            // Stop App
            System.out.print("Input ID of the App need to stop: ");
            Scanner input = new Scanner(System.in);

            String appID = "";
            appID = input.nextLine();

            byte [] BufferedWriter  = appID.getBytes(StandardCharsets.UTF_8);

            os.write(BufferedWriter);
  
            input.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void receiveStatus(InputStream is){
        try {
            byte [] buffer = new byte[1024];

            is.read(buffer);

            String status = new String(buffer, StandardCharsets.UTF_8);

            System.out.println(status.trim());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

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

            sock.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

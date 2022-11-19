import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class start_app_client {
    public static void main(String[] args){
        try {
            Socket sock = new Socket(InetAddress.getLocalHost(),9999);

            System.out.println("Connected");

            OutputStream os = sock.getOutputStream();
            System.out.print("Input location of app need to start: ");
            Scanner input = new Scanner(System.in);

            String apploc = "";
            apploc = input.nextLine();

            byte[] buffer = apploc.getBytes(StandardCharsets.UTF_8);

            os.write(buffer);
            input.close();
            sock.close();

            System.out.print("Disconnected");
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
}

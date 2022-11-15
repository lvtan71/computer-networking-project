import java.io.InputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;


public class app_running_client {
    public static void main(String[] args){
        try {
            Socket sock = new Socket(InetAddress.getLocalHost(),9999);

            System.out.println("Connected");

            InputStream is = sock.getInputStream();

            byte [] buffer = new byte[1024];

            is.read(buffer);

            String line = new String(buffer,StandardCharsets.UTF_8);

            int a = 0;

            while(a != -1){
                System.out.println(line.trim());
                a = is.read(buffer);
                line = new String(buffer,StandardCharsets.UTF_8);
            }

            sock.close();

            System.out.println("Disconnected");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

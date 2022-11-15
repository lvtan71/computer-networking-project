import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class kill_process_client {
    public static void main(String[] args){
        try {
            // Thiet lap socket
            Socket sock = new Socket(InetAddress.getLocalHost(),9999);

            System.out.println("Connected");

            OutputStream os = sock.getOutputStream();
            // Nhap ten chuong trinh can kill tu ban phim
            Scanner input = new Scanner(System.in);

            String processname = "";
            processname = input.nextLine();

            byte[] buffer = processname.getBytes();

            os.write(buffer);
            // Dong ban phim
            input.close();
            // Dong socket
            sock.close();

            System.out.print("Disconnected");
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}

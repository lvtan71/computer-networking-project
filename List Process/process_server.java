import java.io.ObjectInputStream;
import java.lang.Process;
import java.net.Socket;
import java.net.ServerSocket;
import java.net.InetAddress;
import java.io.InputStream;
import java.io.ByteArrayInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class process_server {
    public static void main(String[] args){
        try {
            ServerSocket svSocket = new ServerSocket(9999, 1, InetAddress.getLocalHost());

            System.out.println("Created socket");

            System.out.println("Connecting...");

            Socket clSock = svSocket.accept();

            InputStream is = clSock.getInputStream();

            byte [] buffer = new byte[1024];
            // Doc byte vao buffer
            is.read(buffer);
            // Dua mang byte vao buffer
            ByteArrayInputStream bInputStream = new ByteArrayInputStream(buffer);
            // Dua buffer ve doi tuong
            ObjectInputStream oInputStream = new ObjectInputStream(bInputStream);

            Process process = (Process) oInputStream.readObject();

            String line;

            BufferedReader processReader =  new BufferedReader(new InputStreamReader(process.getInputStream()));
            // Read from BufferedReader
            while ((line = processReader.readLine()) != null) { // readline: hàm đọc buffer
                System.out.println(line); // <-- Print all Process here line
                                                // by line
            }
            System.out.println("Disconnected");
            processReader.close();
            clSock.close();
            svSocket.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

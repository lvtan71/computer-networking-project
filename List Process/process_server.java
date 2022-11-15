import java.lang.Process;
import java.io.OutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.net.InetAddress;
import java.net.ServerSocket;

public class process_server {
    public static void main(String[] args){
        try {
            ServerSocket svSocket = new ServerSocket(9999, 1, InetAddress.getLocalHost());

            System.out.println("Created socket");

            System.out.println("Connecting...");

            Socket clSock = svSocket.accept();

            System.out.println("Connected");

            // Lay danh sach chuong trinh luu vao doi tuong process
            Process process = Runtime.getRuntime().exec(System.getenv("windir") + "\\system32\\" + "tasklist.exe");

            String line;

            BufferedReader processReader =  new BufferedReader(new InputStreamReader(process.getInputStream()));
           
            OutputStream os = clSock.getOutputStream();

            byte [] buffer = new byte[1024];

            while((line = processReader.readLine()) != null){
                System.out.println(); // Debug hệ tâm linh, không hiểu sao in 2 dòng (bất kỳ) xuống dòng thì bên server 
                System.out.println(); // không lỗi in thêm dòng thừa
                buffer = line.getBytes(StandardCharsets.UTF_8);
                os.write(buffer);
            }
            processReader.close();
            // Flag da liet ke het process

            clSock.close();

            svSocket.close();

            System.out.println("Disconnected");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

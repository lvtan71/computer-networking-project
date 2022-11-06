import java.io.ObjectOutputStream;
import java.lang.Process;
import java.io.OutputStream;
import java.io.ByteArrayOutputStream;
import java.net.Socket;
import java.net.InetAddress;

public class process_client {
    public static void main(String[] args){
        try {
            // Tao doi tuong socket
            Socket sock = new Socket(InetAddress.getLocalHost(),9999);

            // Hien thi da ket noi den server
            System.out.println("Connected");

            // Lay danh sach chuong trinh luu vao doi tuong process
            Process process = Runtime.getRuntime().exec(System.getenv("windir") + "\\system32\\" + "tasklist.exe");

            byte [] buffer = new byte[1024];

            ByteArrayOutputStream bos = new ByteArrayOutputStream();

            ObjectOutputStream oos = new ObjectOutputStream(bos);

            oos.writeObject(process);

            buffer = bos.toByteArray();

            OutputStream os = sock.getOutputStream();

            os.write(buffer);
            
            System.out.println("Disconnected");

            sock.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Client 
{
    public static void main(String[] args)
    {
        // tat ca cac ham duoi day deu quang ra IOException neu nhu co loi
        // nen gom chung mot ham try-catch
        try
        {
            // Tao socket object 
            // "localhost" la ip cua server
            // 9999 la port cua server
            // ip va port cua client java se tu setup
            Socket sock = new Socket(InetAddress.getLocalHost(), 9999);
            // connect duoc voi server thi in ra OK
            System.out.println("Connect OK"); 
            
            // nhan input tu server
            InputStream is = sock.getInputStream(); 
            // gui output cho server
            OutputStream os = sock.getOutputStream();
            
            // nhap du lieu tu ban phim
            Scanner input = new Scanner(System.in);

            String msg = "";
            while (!msg.equals("exit"))
            {
                System.out.print("Sended: ");
                msg = input.nextLine();
                // convert du lieu sang byte 
                byte[] buffer = msg.getBytes();

                // thong qua OutputStream gui du lieu cho server bang lenh write
                os.write(buffer);    
            }

            

            System.out.println("Close Connection");
            sock.close();
        }
        catch (IOException ioE)
        {
            // in ra exception va end chuong trinh
            ioE.printStackTrace();
        }
    }
}

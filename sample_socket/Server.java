import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
public class Server
{
    public static void main(String[] args)
    {
        // tat ca cac ham duoi day deu quang ra IOException
        // nen gom chung mot ham try-catch
        try 
        {
            // ServerSocket la Socket danh cho server, khong bat buoc su dung
            // 9999 la port cua server 
            // 5 la backlog, so luong ket noi cung mot luc
            // tham so cuoi la ip
            ServerSocket sock = new ServerSocket(9999, 5, InetAddress.getLocalHost());
            // mot loi thuong gap la port da duoc su dung
            // Thanh lap socket thanh cong in ra OK
            System.out.println("Create Socket OK");
            
            // cho doi client connect
            System.out.println("Listen...");
            // neu connect thanh cong, tao mot doi tuong socket dai dien cho client
            Socket cliSock = sock.accept();
            System.out.println("Connect OK");

            // InputStream de nhan input tu client
            InputStream is = cliSock.getInputStream();
            // OutputStream de gui output cho client
            OutputStream os = cliSock.getOutputStream();

            // du lieu duoc nhan ve hoan toan la kieu byte
            // tao mot buffer byte de luu message

            String msg = "";

            while (!msg.trim().equals("exit"))
            {
                byte[] buffer = new byte[100];
                // nhan du lieu tu client bang lenh read() va luu vao buffer
                is.read(buffer);
                // convert byte sang string de doc duoc tin nhan
                msg = new String(buffer);

                System.out.println("Received: " + msg);
            }

            System.out.println("Close Connection");
            // lam xong nho dong lai
            sock.close();
            cliSock.close();
        }
        catch (IOException ioE)
        {
            ioE.printStackTrace();
        }
    }
}

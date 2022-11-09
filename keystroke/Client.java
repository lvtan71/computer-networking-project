import java.net.InetAddress;
import java.net.Socket;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Client {
  public static void main(String[] args) {
    try {
      Socket sock = new Socket(InetAddress.getLocalHost(), 9999);
      System.out.println("Connected");

      InputStream is = sock.getInputStream();
      OutputStream os = sock.getOutputStream();

      sock.close();
      System.out.println("Socket closed");
    }
    catch (IOException ioE) {
      ioE.printStackTrace();
    }
  }
}

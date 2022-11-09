import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Server {
  public static void main(String[] args) {
    try {
      ServerSocket sock = new ServerSocket(9999, 5, InetAddress.getLocalHost());
      System.out.println("Created socket");

      Socket cliSock = sock.accept();
      System.out.println("Connected");
      InputStream is = cliSock.getInputStream();
      OutputStream os = cliSock.getOutputStream();

      sock.close();
      System.out.println("Socket closed");
    }
    catch (IOException ioE) {
      ioE.printStackTrace();
    }
  }
}

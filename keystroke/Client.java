import java.net.InetAddress;
import java.net.Socket;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Scanner;

public class Client {
  private static final String[] kCommands = {"Hook", "Unhook", "Print", "Exit"};

  private static boolean isValidCommand(String s) {
    for (int i = 0; i < 4; i++) {
      if (s.equals(kCommands[i])) return true;
    }
    return false;
  }

  public static void main(String[] args) {
    boolean isHook = false;
    byte[] msg;
    try {
      Socket sock = new Socket(InetAddress.getLocalHost(), 9999);
      System.out.println("Connected");

      InputStream is = sock.getInputStream();
      OutputStream os = sock.getOutputStream();
      Scanner sc = new Scanner(System.in);

      String command = "";
      while (true) {
        command = sc.nextLine();
        if (!isValidCommand(command)) continue;

        msg = command.getBytes();
        os.write(msg);    

        if (command.equals(kCommands[0])) {
          isHook = true;
        }
        if (command.equals(kCommands[1])) {
          isHook = false;
        }

        if (command.equals("Exit")) break;

        if (isHook && command.equals(kCommands[2])) {
          byte[] buffer = new byte[5*1024];
          is.read(buffer);
          String out = new String(buffer);
          out = out.trim();
          System.out.println(out);
        } 
      }
      sock.close();
      System.out.println("Socket closed");
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }
}

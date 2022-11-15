import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class app_running_server {
    public static void main(String[] arrgs){
        try {
            ServerSocket svSock = new ServerSocket(9999,5,InetAddress.getLocalHost());

            System.out.println("Created Socket");

            System.out.println("Connecting...");

            Socket clSocket = svSock.accept();

            System.out.println("Connected");

            OutputStream os = clSocket.getOutputStream();

            byte [] buffer = new byte[1024];

            Process process = Runtime.getRuntime().exec("powershell.exe get-process | where-object {$_.mainwindowhandle -ne 0} | select-object name, Id, mainwindowtitle");

            String line;

            BufferedReader appReader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            while((line = appReader.readLine()) != null){
                System.out.println();
                System.out.println();
                buffer = line.getBytes(StandardCharsets.UTF_8);
                os.write(buffer);
            }
            appReader.close();

            clSocket.close();

            svSock.close();
            
         } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class stop_app_server {
    public static void main(String[] args){
        try {
            // Create Socket
            ServerSocket svSock = new ServerSocket(9999,5,InetAddress.getLocalHost());
            System.out.println("Created Socket");
            System.out.println("Connecting...");
            Socket clSocket = svSock.accept();
            System.out.println("Connected");

            stopApp(clSocket);

            clSocket.close();
            svSock.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void stopApp(Socket clSocket){
        try {
            // Stop App
            InputStream is = clSocket.getInputStream();
            byte [] BufferedReader = new byte[1024];

            is.read(BufferedReader);

            String appID = new String(BufferedReader,StandardCharsets.UTF_8);


            if(isRunning(appID)){
                Runtime appRuntime = Runtime.getRuntime();
                appRuntime.exec("taskkill /PID " + appID.trim());
                String notice = "App" + appID.trim() + " has been stopped";
                System.out.println(notice);
                response(clSocket, notice);
            }
            else{
                String notice = "There is no App with ID " + appID + " is running";
                System.out.println(notice);
                response(clSocket, notice);
            }

            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }

    public static boolean isRunning(String appID){
        try {
            Process process = Runtime.getRuntime().exec("tasklist /FI \"PID eq " + appID.trim() + "\"");

            BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = bufferedreader.readLine()) != null) {
                if (line.contains(" " + appID.trim() + " ")){
                    return true;
                }
            }
            return false;
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return false;
    }

    public static void response(Socket clSocket, String res){
        try {
            OutputStream os = clSocket.getOutputStream();

            byte [] buffer = new byte[1024];

            buffer = res.getBytes(StandardCharsets.UTF_8);

            os.write(buffer);

            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
}

package model;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class Application {
    private Socket sock;
    private InputStream inputStream;
    private OutputStream outputStream;
    private ArrayList<ArrayList<String>> appRunning;
    private ArrayList<ArrayList<String>> appInstalled;

    public Application(Socket sock, InputStream inputStream, OutputStream outputStream){
        initComponents(sock, inputStream, outputStream);
    }

    private void initComponents(Socket sock, InputStream inputStream, OutputStream outputStream) {
        this.sock = sock;
        this.inputStream = inputStream;
        this.outputStream = outputStream;
        appRunning = new ArrayList<>();
        appInstalled = new ArrayList<>();
    }

    public ArrayList<ArrayList<String>> listAppInstalled() {
        appInstalled = new ArrayList<>();
        try {
            sock.setSoTimeout(2000);

            byte[] buffer = new byte[1024];
            String line;
            while(true)
            {
                inputStream.read(buffer);
                line = new String(buffer, StandardCharsets.UTF_8);
                line = line.trim();

                ArrayList<String> row = new ArrayList<String>();
                int length = line.length();
                if (length > 66)
                {
                    row.add("  " + line.substring(0,66).trim());
                    row.add("  " + line.substring(66, length).trim());
                }
                else
                {
                    row.add("  " + line.substring(0, length));
                }

                System.out.println(line);

                appInstalled.add(row);
            }
        }
        catch (IOException ioE)
        {
            ioE.printStackTrace();
        }

        return appInstalled;
    }

    public ArrayList<ArrayList<String>> listAppRunning(){
        appRunning = new ArrayList<>();
        try
        {
            sock.setSoTimeout(3000);
            byte[] buffer = new byte[1024];
            inputStream.read(buffer);
            String line;

            line = new String(buffer, StandardCharsets.UTF_8);

            inputStream.read(buffer);
            line = new String(buffer, StandardCharsets.UTF_8);
            line = line.trim();
            int pos = line.indexOf(" -- ");
//            System.out.println(line);
//            System.out.println(line.indexOf(" -- "));

            while (true)
            {
                inputStream.read(buffer);
                line = new String(buffer, StandardCharsets.UTF_8);
                line = line.trim();

                ArrayList<String> row = new ArrayList<>();
                int length = line.length();
                if (length > pos + 3)
                {
                    row.add("  " + line.substring(0, pos - 2).trim());
                    row.add("  " + line.substring(pos - 2, pos + 3).trim());
                    row.add("  " + line.substring(pos + 3, length).trim());
                }
                else
                {
                    row.add("  " + line.substring(0, pos - 2).trim());
                    row.add("  " + line.substring(pos - 2, length).trim());
                    row.add("");
                }

                System.out.println(line);
                appRunning.add(row);
            }
        }
        catch (IOException ioE)
        {
            ioE.printStackTrace();
        }

        return appRunning;
    }

    public String stopAppRunning(String IDAppRunning)
    {
        try
        {
            byte[] buffer  = IDAppRunning.getBytes(StandardCharsets.UTF_8);
            outputStream.write(buffer);

            buffer = new byte[1024];
            inputStream.read(buffer);
            String notifyStatus = new String(buffer, StandardCharsets.UTF_8);

            return notifyStatus.trim();
        }
        catch (IOException ioE)
        {
            return ioE.toString();
        }
    }

    public String startApp(String appDirectory){
        try
        {
            byte[] buffer = appDirectory.getBytes(StandardCharsets.UTF_8);
            outputStream.write(buffer);

            buffer = new byte[8];
            inputStream.read(buffer);
            String status = new String(buffer, StandardCharsets.UTF_8);

            return status;
        }
        catch (IOException ioException){
            ioException.printStackTrace();
        }

        return "0";
    }
}
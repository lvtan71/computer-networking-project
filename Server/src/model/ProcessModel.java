package model;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class ProcessModel {

    public ProcessModel (InputStream inputStream, OutputStream outputStream)
    {
        initComponents(inputStream, outputStream);
    }

    private void initComponents(InputStream inputStream, OutputStream outputStream)
    {
        this.inputStream = inputStream;
        this.outputStream = outputStream;
    }

    public void listProcess()
    {
        try
        {
            Process process = Runtime.getRuntime().exec(System.getenv("windir") + "\\system32\\" + "tasklist.exe");
            BufferedReader processReader =  new BufferedReader(new InputStreamReader(process.getInputStream()));
            byte[] buffer = new byte[1024];

            processReader.readLine();
            processReader.readLine();

            String line;
            while((line = processReader.readLine()) != null)
            {
                System.out.println();
                System.out.println();
                buffer = line.getBytes(StandardCharsets.UTF_8);
                outputStream.write(buffer);
            }
        }
        catch (IOException ioE)
        {
            ioE.printStackTrace();
        }
    }

    public void stopProcess()
    {
        try {
            byte[] buffer = new byte[1024];
            inputStream.read(buffer);

            String IDProcess = new String(buffer, StandardCharsets.UTF_8);

            String notifyStopProcess;
            if(isRunning(IDProcess)){
                Runtime appRuntime = Runtime.getRuntime();
                appRuntime.exec("taskkill /PID " + IDProcess.trim());
                notifyStopProcess = "App" + IDProcess.trim() + " has been stopped";
                System.out.println(notifyStopProcess);
            }
            else{
                notifyStopProcess = "There is no App with ID " + IDProcess + " is running";
                System.out.println(notifyStopProcess);
            }

            buffer = new byte[1024];
            buffer = notifyStopProcess.getBytes(StandardCharsets.UTF_8);
            outputStream.write(buffer);

        }
        catch (Exception e)
        {
            //
        }
    }

    public static boolean isRunning(String IDProcess){
        try {
            Process process = Runtime.getRuntime().exec("tasklist /FI \"PID eq " + IDProcess.trim() + "\"");

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = bufferedReader.readLine()) != null)
            {
                if (line.contains(" " + IDProcess.trim() + " ")){
                    return true;
                }
            }

            return false;
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return false;
    }

    private InputStream inputStream;
    private OutputStream outputStream;
}

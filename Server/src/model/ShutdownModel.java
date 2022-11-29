package model;

import java.io.IOException;

public class ShutdownModel {
    public ShutdownModel()
    {
        initComponents();
    }

    private void initComponents()
    {
        runtime = Runtime.getRuntime();
    }

    public void handleShutdown()
    {
        try
        {
            System.out.println("234");
            process = runtime.exec("shutdown -s -t 0");
            System.exit(0);
        }
        catch (IOException ioE)
        {
            //
        }
    }

    public void handleReset()
    {
        try
        {
            process = runtime.exec("shutdown.exe -r -t 0");
            System.exit(0);
        }
        catch (IOException ioE)
        {
            ioE.printStackTrace();
        }
    }

    Process process;
    Runtime runtime;
}


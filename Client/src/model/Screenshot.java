package model;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

public class Screenshot {
    public Screenshot(InputStream inputStream, OutputStream outputStream)
    {
        initComponents(inputStream, outputStream);
    }

    private void initComponents(InputStream inputStream, OutputStream outputStream)
    {
        this.inputStream = inputStream;
        this.outputStream = outputStream;
    }

    public BufferedImage takeScreenshot()
    {
        BufferedImage image = new BufferedImage(1, 1, 1);
        try
        {
            byte[] imageAr = new byte[1920 * 1080];
            inputStream.read(imageAr);
            image = ImageIO.read(new ByteArrayInputStream(imageAr));
            ImageIO.write(image, "jpg", new File("./screenshot/screenshot" + Integer.toString(currentFileName) + ".jpg"));
        }
        catch (IOException ioE)
        {
            //
        }

        return image;
    }

    public int getCurrentFileName() {
        return currentFileName;
    }

    public void setCurrentFileName(int currentFileName) {
        this.currentFileName = currentFileName;
    }

    private InputStream inputStream;
    private OutputStream outputStream;
    private int currentFileName = 0;
}


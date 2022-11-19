package model;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ScreenShot {
    public ScreenShot() {
        initComponents();
    }

    private void initComponents() {
        //
    }

    public String takeScreenShot()
    {
        try
        {
            image = new Robot().createScreenCapture(new Rectangle((Toolkit.getDefaultToolkit().getScreenSize())));
        }
        catch (AWTException awtE)
        {
            return awtE.toString();
        }

        return "";
    }

    public BufferedImage getImage() {
        return image;
    }

    private BufferedImage image;
}

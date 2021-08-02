package org.nandao.cap08.p05HandlingAppletInitializationStatusWith;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

// JavaScript code is able to call applet methods. However, this is not possible until the applet
// has been initialized. Any attempt to communicate with the applet will be blocked until
// the applet is loaded. In order to determine when the applet has been loaded, Java 7 has
// introduced a load status variable, which is accessible from JavaScript code. We will explore
// how to set up an HTML file to detect and respond to these events
public class Test extends Applet {
    
    BufferedImage image;
    
    Graphics2D g2d;

    public void init() {
        int width = getWidth();
        int height = getHeight();
        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        g2d = image.createGraphics();
        g2d.setPaint(Color.BLUE);
        g2d.fillRect(0, 0, width, height);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }
    }

    public void paint(Graphics g) {
        g.drawImage(image, 0, 0, this);
    }
}

package org.nandao.cap07.p05ManagingTheShapeOfWindow;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsDevice.WindowTranslucency;
import java.awt.GraphicsEnvironment;
import java.awt.Polygon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

// There are times in application development when it can be fun and useful to create
// specially-shaped windows. This feature is now available in Java as of version 7. In this
// recipe we will develop a stop sign shape window to ensure that the user wants to continue
// some operation.

public class Test extends JFrame {

    private static final long serialVersionUID = 1L;

    public class StopPanel extends JPanel {

        public StopPanel() {
            this.setBackground(Color.red);
            this.setForeground(Color.red);
            this.setLayout(null);
            final JButton okButton = new JButton("YES");
            final JButton cancelButton = new JButton("NO");
            okButton.setBounds(90, 225, 65, 50);
            cancelButton.setBounds(150, 225, 65, 50);

            okButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent event) {
                    System.exit(0);
                }
            });
            cancelButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent event) {
                    System.exit(0);
                }
            });
            
            this.add(okButton);
            this.add(cancelButton);
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            final Graphics2D g2d = (Graphics2D) g;
            final int pageHeight = this.getHeight();
            final int pageWidth = this.getWidth();
            final int bigHeight = (pageHeight + 80) / 2;
            final int bigWidth = (pageWidth - 305) / 2;
            final int smallHeight = (pageHeight + 125) / 2;
            final int smallWidth = (pageWidth - 225) / 2;
            final Font bigFont = new Font("Castellar", Font.BOLD, 112);
            final Font smallFont = new Font("Castellar", Font.PLAIN, 14);
            g2d.setFont(bigFont);
            g2d.setColor(Color.white);
            g2d.drawString("STOP", bigWidth, bigHeight);
            g2d.setFont(smallFont);
            g2d.drawString("Are you sure you want to continue?", smallWidth, smallHeight);
        }
    }

    public Test() {
        this.add(new StopPanel());
        final Polygon myShape = getPolygon();

        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                setShape(myShape);
                ((JFrame) e.getSource()).setForeground(Color.red);
                ((JFrame) e.getSource()).setBackground(Color.red);
            }
        });

        setUndecorated(true);
    }

    private Polygon getPolygon() {
        final int x1Points[] = { 0, 0, 100, 200, 300, 300, 200, 100 };
        final int y1Points[] = { 100, 200, 300, 300, 200, 100, 0, 0 };
        final Polygon polygon = new Polygon();
        for (int i = 0; i < y1Points.length; i++) {
            polygon.addPoint(x1Points[i], y1Points[i]);
        }
        return polygon;
    }

    public static void main(String[] args) {
        final GraphicsEnvironment envmt = GraphicsEnvironment.getLocalGraphicsEnvironment();
        final GraphicsDevice device = envmt.getDefaultScreenDevice();

        if (!device.isWindowTranslucencySupported(WindowTranslucency.PERPIXEL_TRANSLUCENT)) {
            System.out.println("Shaped windows not supported");
            System.exit(0);
        }
        
        JFrame.setDefaultLookAndFeelDecorated(true);

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                final Test window = new Test();
                window.setOpacity(0.75f);
                window.setVisible(true);
            }
        });
    }
}

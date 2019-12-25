package org.nandao.cap07.p04CreatingVaryingGradientTranslucentWindow;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsDevice.WindowTranslucency;
import java.awt.GraphicsEnvironment;
import java.awt.Paint;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class Test extends JFrame {

    private static final long serialVersionUID = 1L;

    public Test() {
        this.setTitle("Gradient Translucent Window");
        setBackground(new Color(0, 0, 0, 0));
        this.setSize(500, 700);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        final JPanel panel = new JPanel() {
            private static final long serialVersionUID = 1L;

            @Override
            protected void paintComponent(Graphics gradient) {
                if (gradient instanceof Graphics2D) {
                    final int Red = 120;
                    final int Green = 50;
                    final int Blue = 150;
                    final Paint paint = new GradientPaint(0.0f, 0.0f, new Color(Red, Green, Blue, 0), getWidth(), getHeight(), new Color(Red, Green, Blue, 255));
                    final Graphics2D gradient2d = (Graphics2D) gradient;
                    gradient2d.setPaint(paint);
                    gradient2d.fillRect(0, 0, getWidth(), getHeight());
                }
            }
        };
        this.setContentPane(panel);
        this.setLayout(new FlowLayout());
        
        final JButton exitButton = new JButton("Exit");
        this.add(exitButton);
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                System.exit(0);
            }
        });
    }

    public static void main(String[] args) {
        final GraphicsEnvironment envmt = GraphicsEnvironment.getLocalGraphicsEnvironment();
        final GraphicsDevice device = envmt.getDefaultScreenDevice();

        if (!device.isWindowTranslucencySupported(WindowTranslucency.PERPIXEL_TRANSLUCENT)) {
            System.out.println("Translucent windows are not supported on your system.");
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

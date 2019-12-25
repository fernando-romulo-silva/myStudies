package org.nandao.cap07.p06UsingTheNewBorderTypesInJava7;

import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

// Borders are used for the outline of swing components. In Java 7, several new border options
// are available. In this recipe we will develop a simple application to demonstrate how to create
// borders and how these borders appear.
public class Test extends JFrame {

    private static final long serialVersionUID = 1L;

    public Test() {
        this.setTitle("Gradient Translucent Window");
        this.setSize(500, 700);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        final JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createRaisedSoftBevelBorder());
        this.setLayout(new FlowLayout());

        final JButton exitButton = new JButton("Exit");
        panel.add(exitButton);
        this.add(panel);
    }

    public static void main(String[] args) {

        JFrame.setDefaultLookAndFeelDecorated(true);

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                final Test window = new Test();
                window.setVisible(true);
            }
        });
    }
}

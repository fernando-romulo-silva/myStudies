package org.nandao.cap07.p03ManagingOpacityofWindow;

import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.SwingUtilities;

public class Test extends JFrame {

    private static final long serialVersionUID = 1L;

    public Test() {
        this.setTitle("Gradient Translucent Window");
        this.setSize(500, 700);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setType(Type.NORMAL);
        
        this.setLayout(new FlowLayout());

        final JMenuBar menuBar = new JMenuBar();
        final JMenu menu = new JMenu("Overlapping Menu");
        final JMenuItem menuItem = new JMenuItem("Overlapping Item");
        menu.add(menuItem);
        menuBar.add(menu);
        this.setJMenuBar(menuBar);
        this.validate();
    }

    public static void main(String[] args) {
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

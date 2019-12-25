package org.nandao.cap07.p02ManagingWindowTypes;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;


// The JFrame class supports a setType method, which configures the general appearance of a
// window to one of the three types. This can simplify the setting of a window's appearance. In this
// recipe we will examine these types and their appearance on Windows and Linux platforms.
public class Test extends JFrame {

    private static final long serialVersionUID = 1L;

    public Test() {
        this.setTitle("Example");
        this.setSize(200, 100);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setType(Type.UTILITY);
        
        final JButton exitButton = new JButton("Exit");

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                System.exit(0);
            }
        });

        this.add(exitButton);

        final JMenuBar menuBar = new JMenuBar();
        final JMenu menu = new JMenu("Overlapping Menu");
        final JMenuItem menuItem = new JMenuItem("Overlapping Item");
        menu.add(menuItem);
        menuBar.add(menu);
        this.setJMenuBar(menuBar);
        this.validate();

        setVisible(true);
    }

    public static void main(String[] args) {
       new Test();
    }
}

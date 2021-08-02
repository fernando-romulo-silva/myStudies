package org.nandao.cap08.p02ControllingFocusWhenDisplayingWindow;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

// The setAutoRequestFocus method has been added to the java.awt.Window class and
// is used to specify whether a window should receive focus when it is displayed using either the
// setVisible or toFront methods. There may be times when a window is made visible, but
// we don't want the window to have focus. For example, if the window being displayed contains
// status information, making it visible will be sufficient. Giving it focus may not make sense and
// may frustrate the user by forcing them to change focus back to the original window.
public class Test extends JFrame {

    private static final long serialVersionUID = 1L;

    public class SecondWindow extends JFrame {
        public SecondWindow() {
            this.setTitle("Second Window");
            this.setBounds(400, 100, 200, 200);
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            this.setAutoRequestFocus(false);
        }
    }

    private SecondWindow second;

    public Test() {
        this.setTitle("Example");
        this.setBounds(100, 100, 200, 200);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new FlowLayout());

        second = new SecondWindow();
        second.setVisible(true);

        JButton secondButton = new JButton("Hide");
        this.add(secondButton);

        secondButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                second.setVisible(false);
            }
        });

        JButton thirdButton = new JButton("Reveal");
        this.add(thirdButton);

        thirdButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                second.setVisible(true);
            }
        });

        JButton exitButton = new JButton("Exit");
        this.add(exitButton);

        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                System.exit(0);
            }
        });
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Test window = new Test();
                window.setVisible(true);
            }
        });
    }

}

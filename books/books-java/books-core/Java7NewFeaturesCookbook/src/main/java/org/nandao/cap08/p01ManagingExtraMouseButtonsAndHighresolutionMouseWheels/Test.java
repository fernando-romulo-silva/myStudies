package org.nandao.cap08.p01ManagingExtraMouseButtonsAndHighresolutionMouseWheels;

import java.awt.FlowLayout;
import java.awt.MouseInfo;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

// Java 7 has provided more options for handling mouse events. The java.awt.Toolkit
// class' areExtraMouseButtonsEnabled method allows you to determine whether more
// than the standard set of buttons is supported by the system. The java.awt.event.
// MouseWheelEvent class' getPreciseWheelRotation method can be used to control
// action on high resolution mouse wheels. In this recipe we will write a simple application to
// determine the number of mouse buttons enabled and test the mouse wheel rotation.

public class Test extends JFrame implements MouseListener, MouseWheelListener {

    private static final long serialVersionUID = 1L;

    public Test() {
        this.setTitle("Example");
        this.setSize(200, 100);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new FlowLayout());
        JButton exitButton = new JButton("Exit");
        this.add(exitButton);

        this.addMouseListener(this);
        this.addMouseWheelListener(this);
        
        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                System.exit(0);
            }
        });

        int totalButtons = MouseInfo.getNumberOfButtons();
        System.out.println(Toolkit.getDefaultToolkit().areExtraMouseButtonsEnabled());
        System.out.println("You have " + totalButtons + " total buttons");
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        System.out.println("" + e.getPreciseWheelRotation() + " - " + e.getWheelRotation());
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // TODO Auto-generated method stub
    }

    @Override
    public void mousePressed(MouseEvent e) {
        System.out.println("" + e.getButton());
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub
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

package org.nandao.cap07.p01MixingHeavyweightAndLightweightComponents;

import java.awt.Button;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

// Java provides two basic sets of components for developing GUI applications: Abstract Window
// Toolkit (AWT) and Swing. AWT is dependent upon the native systems' underlying code, and
// these components are therefore referred to as heavyweight components. Swing components,
// on the other hand, operate fully independent of the native system, are completely
// implemented in Java code, and are thus referred to as lightweight components. In previous
// versions of Java, it was inefficient and troublesome to mix heavyweight and lightweight
// components. In Java 6 Update 12, and continuing into Java 7, the JVM handles the mixing
// of heavyweight and lightweight components.

public class Test extends JFrame {

    private static final long serialVersionUID = 1L;

    public Test() {
        this.setTitle("Example");
        this.setSize(200, 100);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        final Button exitButton = new Button("Exit");

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

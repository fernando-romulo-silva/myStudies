package org.nandao.cap07.p07HandlingMultipleFileSelectionInTheFileDialog;

import java.awt.FileDialog;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

// The ability to select two or more files or directories in a file dialog box is achieved using the
// Ctrl and/or Shift keys in conjunction with the mouse. In Java 7, the file dialog box enables
// or disables this capability using the java.awt.FileDialog class' setMultipleMode Method.
public class Test extends JFrame {

    private static final long serialVersionUID = 1L;

    public Test() {
        this.setTitle("Example");
        this.setSize(200, 100);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setLayout(new FlowLayout());

        final FileDialog fileDialog = new FileDialog(this, "FileDialog");
        fileDialog.setMultipleMode(true);

        final JButton fileDialogButton = new JButton("File Dialog");
        fileDialogButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                fileDialog.setVisible(true);
            }
        });
        this.add(fileDialogButton);

        final JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                System.exit(0);
            }
        });
        this.add(exitButton);
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

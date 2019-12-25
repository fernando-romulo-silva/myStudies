package org.nandao.cap07.p09ControllingThePrintDialogBoxType;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterJob;

import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.DialogTypeSelection;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

// The standard print dialog that comes as part of the java.awt.PrintJob class allows the
// use of both a common and a native dialog box. This provides the ability to better tailor the
// application to a platform. The specification of the dialog box type is simple.
public class Test extends JFrame {

    private static final long serialVersionUID = 1L;

    public Test() {
        this.setTitle("Example");
        this.setSize(200, 100);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new FlowLayout());

        final JButton printDialogButton = new JButton("Print Dialog");

        printDialogButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                final PrintRequestAttributeSet attributes = new HashPrintRequestAttributeSet();
                attributes.add(DialogTypeSelection.NATIVE);
                final PrinterJob printJob = PrinterJob.getPrinterJob();
                printJob.printDialog(attributes);
            }
        });

        this.add(printDialogButton);
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

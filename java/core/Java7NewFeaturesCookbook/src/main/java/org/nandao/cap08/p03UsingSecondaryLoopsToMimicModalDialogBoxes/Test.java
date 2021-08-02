package org.nandao.cap08.p03UsingSecondaryLoopsToMimicModalDialogBoxes;

import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.SecondaryLoop;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

// The java.awt.EventQueue class' SecondaryLoop interface provides a convenient
// technique for mimicking the behavior of a modal dialog box. A modal dialog box has two
// behaviors. The first one is from the user's perspective. The user is not permitted to interact
// with the main window, until the dialog box is complete. The second perspective is from the
// program execution standpoint. The thread in which the dialog box is called is blocked until
// the dialog box is closed.
public class Test extends JFrame implements ActionListener {

    private static final long serialVersionUID = 1L;

    private JButton firstButton;

    private JButton secondButton;

    class WorkerThread extends Thread {
        private String message;
        private SecondaryLoop secondaryLoop;

        public WorkerThread(SecondaryLoop secondaryLoop, String message) {
            this.secondaryLoop = secondaryLoop;
            this.message = message;
        }

        @Override
        public void run() {
            System.out.println(message + " Loop Sleeping ... ");
            
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }

            System.out.println(message + " Secondary loop completed with a result of " + secondaryLoop.exit());
        }
    }

    public Test() {
        this.setTitle("Example");

        this.setBounds(100, 100, 200, 200);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new FlowLayout());

        firstButton = new JButton("First");
        this.add(firstButton);
        firstButton.addActionListener(this);
        
        secondButton = new JButton("Second");
        this.add(secondButton);
        secondButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(final ActionEvent e) {
        Thread worker;
        JButton button = (JButton) e.getSource();
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        
        EventQueue eventQueue = toolkit.getSystemEventQueue();
        SecondaryLoop secondaryLoop = eventQueue.createSecondaryLoop();
        Calendar calendar = Calendar.getInstance();
        String name;

        if (button == firstButton) {
            name = "First-" + calendar.get(Calendar.MILLISECOND);
        } else {
            name = "Second-" + calendar.get(Calendar.MILLISECOND);
        }
        
        worker = new WorkerThread(secondaryLoop, name);
        worker.start();
        
        if (!secondaryLoop.enter()) {
            System.out.println("Error with the secondary loop");
        } else {
            System.out.println(name + " Secondary loop returned");
        }
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

package org.nandao.cap08.p04HandlingSpuriousThreadWakeups;

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

// When multiple threads are used, one thread may need to wait until the completion of one or
// more other threads. When this is necessary, one approach is to use the Object class' wait
// method to wait for the other threads to complete. These other threads need to use either the
// Object class' notify or notifyAll methods to permit the thread that is waiting to continue.
// However, spurious wakeup calls can occur in some situations. In Java 7, the java.awt.
// event.InvocationEvent class' isDispatched method has been introduced to address
// this problem.
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
    public void actionPerformed(final ActionEvent event) {
        Thread worker;
        JButton button = (JButton) event.getSource();

        Toolkit toolkit = null;
        EventQueue eventQueue = null;

        int i = 0;

        //
        //
        //
        //
        //
        synchronized (this) {
            toolkit = Toolkit.getDefaultToolkit();
            eventQueue = toolkit.getSystemEventQueue();

            while ((i++ < 100) && !EventQueue.isDispatchThread()) {
                try {
                    wait();
                } catch (InterruptedException ex) {
                }
            }
            // Continue processing
        }

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

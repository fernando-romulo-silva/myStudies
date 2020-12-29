package br.fernando.ch10_threads.par02_threads_states_and_transitions;

public class Test01 {

    // =========================================================================================================================================
    // Thread States and Transitions
    static void test01_01() {
        // New: This is the state the thread is in after the Thread instance has been created but the start() method has not been invoked on the thread.
        //
        // Runnable: This is the state a thread is in when it’s eligible to run but the scheduler has not selected it to be the running thread.
        //
        // Running: This is it. The “big time.” Where the action is. This is the state a thread is in when the thread scheduler selects it from the 
        // runnable pool to be the currently executing process
        //
        // Waiting/blocket/Sleeping : This is the state a thread is in when it’s not eligible to run. Okay, so this is really three states combined into one, 
        // but they all have one thing in common: the thread is still alive but is currently not eligible to run. In other words, it is not runnable.
        //
        // Dead: A thread is considered dead when its run() method completes.
    }

    // =========================================================================================================================================
    // Preventing Thread Execution
    static void test01_02() {
        // Sleeping
        // When a thread sleeps, it drifts off somewhere and doesn’t return to runnable until it wakes up.
        //
        // You do this by invoking the static Thread.sleep() method, giving it a time in milliseconds as follows:
        try {
            Thread.sleep(1 * 50 * 1000); // sleep for 5 minutes
        } catch (InterruptedException ex) {
            System.out.println(ex);
        }

        NameRunnable nr = new NameRunnable();

        Thread one = new Thread(nr);
        one.setName("Fred");

        Thread two = new Thread(nr);
        two.setName("Lucy");

        Thread three = new Thread(nr);
        three.setName("Ricky");

        one.start();
        two.start();
        three.start();
    }

    static class NameRunnable implements Runnable {

        @Override
        public void run() {
            for (int x = 1; x < 4; x++) {
                System.out.println("Tun by " + Thread.currentThread().getName());

                try {
                    Thread.sleep(1000);
                } catch (Exception ex) {
                    System.out.println(ex);
                }
            }
        }
    }

    // =========================================================================================================================================
    public static void main(String[] args) {
        test01_01();
    }
}

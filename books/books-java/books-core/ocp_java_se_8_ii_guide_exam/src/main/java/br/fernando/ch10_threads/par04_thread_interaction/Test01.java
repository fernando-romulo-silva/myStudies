package br.fernando.ch10_threads.par04_thread_interaction;

public class Test01 {

    // =========================================================================================================================================
    // 
    static void test01_01() throws Exception {
        // The last thing we need to look at is how threads can interact with one another to
        // communicate about—among other things—their locking status. The Object class has
        // three methods, wait() , notify() , and notifyAll() , that help threads communicate
        // the status of an event that the threads care about
        //
        // Methods wait(), notify(), and notifyAll() must be called from within a synchronized context!
        // A thread can’t invoke a wait() or notify() method on an object unless it owns that  object’s lock

        ThreadB b = new ThreadB();
        b.start();

        synchronized (b) { // main thread block 'b' to execute 'wait'

            try {
                System.out.println("Waiting for 'b' to complete... ");

                // When the thread waits, it temporarily releases the lock for other threads to use, but it will
                // need it again to continue execution. It’s common to find code like this

                b.wait();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }

            System.out.println("Total is " + b.total);
        }
    }

    static class ThreadB extends Thread {

        int total;

        public void run() {
            synchronized (this) {
                for (int i = 0; i < 100; i++) {
                    total += i;
                }

                notify(); // notify to main thread
            }
        }
    }

    // =========================================================================================================================================
    public static void main(String[] args) throws Exception {
        test01_01();
    }
}

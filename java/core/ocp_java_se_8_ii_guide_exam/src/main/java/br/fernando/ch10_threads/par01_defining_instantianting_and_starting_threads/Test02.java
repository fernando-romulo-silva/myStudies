package br.fernando.ch10_threads.par01_defining_instantianting_and_starting_threads;

public class Test02 {

    // =========================================================================================================================================
    // Starting and Running Multiple Threads 
    static void test01_01() {
        // This is so crucial that you need to stop right now, take a deep breath, and repeat after me, “The behavior is not guaranteed.”

        // Make one Runnable
        final Runnable nr = () -> {
            for (int x = 1; x <= 1400; x++) {
                System.out.println("Run by " + Thread.currentThread().getName() + ", x is " + x);
            }
        };

        Thread one = new Thread(nr);
        one.setName("one");

        Thread two = new Thread(nr);
        two.setName("two");

        Thread three = new Thread(nr);
        three.setName("three");

        one.start();
        two.start();
        three.start();

        // Nothing is guaranteed in the preceding code except this: Each thread will start, and each thread will run to completion.
        // 
        // A thread is done being a thread when its target run() method completes.
        //
        // Once a thread has been started, it can never be started again.
    }

    // =========================================================================================================================================
    // The Thread Scheduler
    static void test01_02() {

        // The thread scheduler is the part of the JVM (although most JVMs map Java threads directly to native threads on the underlying OS) 
        // that decides which thread should runat any given moment and also takes threads out of the run state.
        //
        // The order in which runnable threads are chosen to run is not guaranteed.
        //
        // Although we don’t control the thread scheduler (we can’t, for example, tell a specific thread to run), we can sometimes influence it. 
        // The following methods give us some tools for influencing the scheduler. Just don’t ever mistake influence for control.
        //
        // Method from Thread.class :
        //
        // static void sleep(long millis) throws InterruptedException
        //
        // static void yield()
        //
        // final void join() throws Interruptedexception
        //
        // final void setPriority(int newPriority)
        //
        // Method from Object.class :
        //
        // final void wait() throws InterruptedException
        //
        // final void notify()
        //
        // final void notifyAll()
    }

    // =========================================================================================================================================
    public static void main(String[] args) {
        test01_01();
    }
}

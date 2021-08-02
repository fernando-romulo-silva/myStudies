package br.fernando.ch10_threads.par04_thread_interaction;

import java.util.ArrayList;
import java.util.List;

public class Test03 {

    // =========================================================================================================================================
    // 
    static void test01_01() throws Exception {
        // But what happens if, for example, the Calculator runs first and calls notify() before the Reader s have started waiting? 
        // This could happen since we can’t guarantee the order in which the different parts of the thread will execute.
        // Unfortunately, when the Readers run, they just start waiting right away.
        //
        // The moral here is that when you use wait() and notify() or notifyAll() , you should almost always also have a while 
        // loop around the wait() that checks a condition and forces continued waiting until the condition is met
    }

    static class Operator extends Thread {
        Machine machine; //

        @Override
        public void run() {
            while (true) {
                Shape shape = getShapeFromUser();
                MachineInstructions job = calculateNewInstructionsFrom(shape);
            }
        }

        private MachineInstructions calculateNewInstructionsFrom(Shape shape) {
            // TODO Auto-generated method stub
            return null;
        }

        private Shape getShapeFromUser() {
            // TODO Auto-generated method stub
            return null;
        }
    }

    static class Machine extends Thread {
        List<MachineInstructions> jobs = new ArrayList<>();

        public void addJob(MachineInstructions job) {
            synchronized (jobs) {
                jobs.add(job);
                jobs.notify();
            }
        }

        @Override
        public void run() {
            while (true) {
                synchronized (jobs) {
                    // wait until at least one job is available
                    while (jobs.isEmpty()) {
                        try {

                            jobs.wait();
                        } catch (final InterruptedException ie) {
                            ie.printStackTrace();
                        }
                    }
                }

                // if we get here, we know that jobs is not empty
                MachineInstructions instructions = jobs.remove(0);
                // send machine steps to hardware
            }
        }
    }

    static class Shape {

    }

    static class MachineInstructions {

    }

    // =========================================================================================================================================
    public static void main(String[] args) throws Exception {
        test01_01();
    }
}

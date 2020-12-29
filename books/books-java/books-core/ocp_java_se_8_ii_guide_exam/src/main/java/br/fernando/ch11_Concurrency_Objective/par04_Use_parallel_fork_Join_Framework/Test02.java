package br.fernando.ch11_Concurrency_Objective.par04_Use_parallel_fork_Join_Framework;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

public class Test02 {

    private static final AtomicInteger ACTION_COUNT = new AtomicInteger(0);

    // =========================================================================================================================================
    // RecursiveAction
    static void test01_01() throws Exception {
        // ForkJoinTask is an abstract base class that outlines most of the methods, such as fork() and join(), in a Fork/Join task. If you need to
        // create a ForkJoinTask that does not return a result, then you should subclass RecursiveAction.

        int[] data = new int[51];

        ForkJoinPool fjPool = new ForkJoinPool();

        RandomInitRecursiveAction action = new RandomInitRecursiveAction(data, 0, data.length, "Action " + ACTION_COUNT.getAndIncrement());

        fjPool.invoke(action);

        // Notice that we do not expect any return values when calling invoke.
        // A RecursiveAction returns nothing.

        System.out.println("Test!");
    }

    public static class RandomInitRecursiveAction extends RecursiveAction {

        private static final long serialVersionUID = 1L;

        private static final int THRESHOLD = 50;

        private final String name;

        private int[] data;

        private int start;

        private int end;

        public RandomInitRecursiveAction(int[] data, int start, int end, String name) {
            super();
            this.data = data;
            this.start = start; // where does our section begi?
            this.end = end; // how large is this section?
            this.name = name;
        }

        @Override
        protected void compute() {
            System.out.println("My name is " + name);

            if (end - start <= THRESHOLD) { // is it a manageable amount of work?
                // do the task

                for (int i = start; i < end; i++) {
                    data[i] = ThreadLocalRandom.current().nextInt();
                }
            } else { // task to big, slipt it
                int halfWay = ((end - start) / 2) + start;

                RandomInitRecursiveAction leftRecursive = new RandomInitRecursiveAction(data, start, halfWay, "Left: " + ACTION_COUNT.getAndIncrement());
                // leftRecursive.fork(); // 1º queue left half of task, will be execute on parallel

                RandomInitRecursiveAction rightRecursive = new RandomInitRecursiveAction(data, halfWay, end, "Right: " + ACTION_COUNT.getAndIncrement());
                // rightRecursive.compute(); // 2º put the rightRecursive on the stack

                // leftRecursive.join(); // 3º this thread will wait for queued task (leftRecursive) to be complete

                ForkJoinTask.invokeAll(leftRecursive, rightRecursive); // shorcut for compute, fork() & join()
                // comments 1º, 2º and 3º and use invokeAll, its works only for Actions
            }

            System.out.println("Ends of " + name);
        }
    }

    // =========================================================================================================================================
    // RecursiveTask
    static void test01_02() throws Exception {

        int[] data = new int[10_000_000];

        ForkJoinPool fjPool = new ForkJoinPool();

        fjPool.invoke(new RandomInitRecursiveAction(data, 0, data.length, "Action " + ACTION_COUNT.getAndIncrement())); // fill the array

        // If you need to create a ForkJoinTask that does return a result, then you should subclass RecursiveTask.

        FindMaxPositionRecursiveTask task = new FindMaxPositionRecursiveTask(data, 0, data.length);

        final Integer position = fjPool.invoke(task);

        System.out.println("Position: " + position + ", value: " + data[position]);

    }

    static class FindMaxPositionRecursiveTask extends RecursiveTask<Integer> {

        private static final long serialVersionUID = 1L;

        private static final int THRESHOLD = 1000;

        private int[] data;

        private int start;

        private int end;

        public FindMaxPositionRecursiveTask(int[] data, int start, int end) {
            super();
            this.data = data;
            this.start = start; // where does our section begi?
            this.end = end; // how large is this section?
        }

        @Override
        protected Integer compute() { // return type matches the <generic> type
            if (end - start <= THRESHOLD) { // is it a manageable amount of work?

                int position = 0; // if all values are equal, return position 0

                // do the task

                for (int i = start; i < end; i++) {
                    if (data[i] > data[position]) {
                        position = i;
                    }
                }

                return position;

            } else { // task to big, slipt it

                int halfWay = ((end - start) / 2) + start;

                FindMaxPositionRecursiveTask leftRecursive = new FindMaxPositionRecursiveTask(data, start, halfWay);
                leftRecursive.fork(); // queue left half of task, will be execute on parallel

                FindMaxPositionRecursiveTask rightRecursive = new FindMaxPositionRecursiveTask(data, halfWay, end);
                int position2 = rightRecursive.compute(); // work ro right half of task, put the rightRecursive on the stack

                int position1 = leftRecursive.join(); // wait for queued task to be complete, we need de result

                if (data[position1] > data[position2]) {
                    return position1;
                } else if (data[position1] < data[position2]) {
                    return position2;
                } else {
                    return position1 < position2 ? position1 : position2;
                }
            }
        }
    }

    // =========================================================================================================================================
    // Summary

    static class DataSet {

    }

    static boolean isSmallEnough(DataSet currentDataSet) {
        return false;
    }

    static void processDirectly(DataSet currentDataSet) {

    }

    static List<DataSet> splitDataSet(DataSet currentDataSet) {
        return null;
    }

    static void invokeAll(List<ExampleRecursiveAction> actions) {
    }

    static void summary() {
        // RecursiveAction

        // This is the general logic of how the fork/join frame work is used:
        //
        // 1. First check whether the task is small enough to be performed directly without forking. If so, perform it without forking.
        //
        // 2. If no, then split the task into multiple small tasks (at least 2) and submit the subtasks back to the pool using invokeAll(list of tasks)
        // or leftRecursive.fork(), rightRecursive.compute(), leftRecursive.join()
    }

    static class ExampleRecursiveAction {

        private DataSet currentDataSet;

        public ExampleRecursiveAction(DataSet currentDataSet) {
            super();
            this.currentDataSet = currentDataSet;
        }

        void compute() {
            if (isSmallEnough(currentDataSet)) {
                processDirectly(currentDataSet); // LINE 10
            } else {
                List<DataSet> list = splitDataSet(currentDataSet); // LINE 13

                // build MyRecursiveAction objects for each DataSet
                ExampleRecursiveAction left = new ExampleRecursiveAction(list.get(0));
                ExampleRecursiveAction right = new ExampleRecursiveAction(list.get(1));

                List<ExampleRecursiveAction> subactions = Arrays.asList(left, right);
                invokeAll(subactions);// LINE 15
            }
        }
    }

    public static void main(String[] args) throws Exception {
        test01_01();
    }
}

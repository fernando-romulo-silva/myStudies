package br.fernando.ch11_Concurrency_Objective.par05_Parallel_Streams;

import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;

import br.fernando.ch11_Concurrency_Objective.par04_Use_parallel_fork_Join_Framework.Test02.RandomInitRecursiveAction;

public class Test02 {

    // =========================================================================================================================================
    // Embarrassingly Parallel
    static void test01_01() throws Exception {
        // Sometimes, solving a problem in parallel adds so much more overhead that the problem can be solved faster serially.
        // The RandomInitRecursiveAction example, which initializes an array to random values, has no additional overhead because what happens
        // when processing one subsection of an array has no bearing on the processing of another subsection.
        //
        // Be aware that it can be difficult to get performance gains that scale with the number of CPUs you have.
        // Typically, four CPUs will result in less than a 4× speedup when moving from a serial to a parallel solution.
        //
        // Some tasks may introduce so much additional work that any advantage of using parallel processing is eliminated
        // (the task runs slower than serial execution).

        int[] data = new int[10_000_000];

        ForkJoinPool fjPool = new ForkJoinPool();

        fjPool.invoke(new RandomInitRecursiveAction(data, 0, data.length, "Streams")); // fill the array

        fjPool.invoke(new SortRecursiveAction(data, 0, data.length)); // sort the array

        System.out.println("Do!");
    }

    static class SortRecursiveAction extends RecursiveAction {

        private static final long serialVersionUID = 1L;

        private static final int THRESHOLD = 1000;

        private int[] data;

        private int start;

        private int end;

        public SortRecursiveAction(int[] data, int start, int end) {
            super();
            this.data = data;
            this.start = start; // where does our section begi?
            this.end = end; // how large is this section?
        }

        @Override
        protected void compute() {

            if (end - start <= THRESHOLD) {
                Arrays.sort(data, start, end);
            } else {

                int halfWay = ((end - start) / 2) + start;

                SortRecursiveAction a1 = new SortRecursiveAction(data, start, halfWay);

                SortRecursiveAction a2 = new SortRecursiveAction(data, halfWay, end);

                ForkJoinTask.invokeAll(a1, a2); // shorcut for a1.fork(), a2.compute() and a1.join()

                if (data[halfWay - 1] <= data[halfWay]) {
                    return; // already sorted
                }

                // meging of sorted subsections begins here

                int[] temp = new int[end - start];

                int s1 = start, s2 = halfWay, d = 0;

                while (s1 < halfWay && s2 < end) {

                    if (data[s1] < data[s2]) {

                        temp[d++] = data[s1++];

                    } else if (data[s2] > data[s1]) {

                        temp[d++] = data[s2];

                    } else {
                        temp[d++] = data[s1++];
                        temp[d++] = data[s2++];
                    }
                }

                if (s1 != halfWay) {
                    System.arraycopy(data, s1, temp, d, temp.length - d);
                } else {
                    System.arraycopy(data, s2, temp, d, temp.length - d);
                }

                System.arraycopy(temp, 0, data, start, temp.length);
            }
        }
    }

    // =========================================================================================================================================
    public static void main(String[] args) throws Exception {
        test01_01();
    }
}

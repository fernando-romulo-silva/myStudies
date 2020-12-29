package br.fernando.ch11_Concurrency_Objective.par04_Use_parallel_fork_Join_Framework;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ForkJoinPool;

@SuppressWarnings("unused")
public class Test01 {

    // =========================================================================================================================================
    // Divide and Conquer
    static void test01_01() {
        // Certain types of large tasks can be split up into smaller subtasks; those subtasks might,
        // in turn, be split up into even smaller tasks. There is no limit to how many times you might subdivide a task.
        //
        // When using the Fork/Join Framework, your tasks will be coded to decide how many levels of recursion
        // (how many times to subdivide) are appropriate.
        // You’ll want to split things up into enough subtasks that you have adequate tasks to keep all of your CPUs utilized.
        //
        // Just because you can use Fork/Join to solve a problem doesn’t always mean you should. If our initial task is to paint eight fence planks,
        // then Joe might just decide to paint them himself. The effort involved in subdividing the problem and assigning those
        // tasks to workers (threads) can sometimes be more than the actual work you want to perform.
    }

    // =========================================================================================================================================
    // ForkJoinPool
    static void test01_02() {
        // Any problem that can be recursively divided can be solved using Fork/Join.
        // Anytime you want to perform the same operation on a collection of elements (painting thousands of fence planks or initializing 100,000,000 array elements),
        // consider using Fork/Join.
        ExecutorService fjPool = new ForkJoinPool();

        // The no-arg ForkJoinPool constructor creates an instance that will use the Runtime.availableProcessors() method to determine
        // the level of parallelism (The number of threads)
        //
        // There is also a ForkJoinPool(int parallelism) constructor that allows you to override the number of threads that will be used.

        ForkJoinPool commonPool = ForkJoinPool.commonPool();
    }

    // =========================================================================================================================================
    // ForkJoinTask
    static void test01_03() {
        // With the Fork/Join Framework, a java.util.concurrent.ForkJoinTask instance (actually a subclass—more on that later) is created
        // to represent the task that should be accomplished.
        //
        // ForkJoinTask is a sub type of the Future
        //
        // A ForkJoinTask concrete subclass has many methods (most of which you will never use), but the following methods are important:
        // compute(), fork(), and join().
        //
    }

    // =========================================================================================================================================
    // Fork
    static void test01_04() {
        // each thread in the ForkJoinPool has a queue of the tasks it is working on; this is unlike most ExecutorService implementations
        // that have a single shared task queue.
        //
        // The fork() method places a ForkJoinTask in the current thread’s task queue.
        //
        // A normal thread does not have a queue of tasks—only the specialized threads in a ForkJoinPool do
        //
        // This is quite an important point to understand. The threads do not share a common queue of task, but each thread has its own queue as shown next.
        //
        // That thread will begin to subdivide the tasks into smaller tasks. Each time a task is subdivided into two subtasks, you fork (or queue)
        // the first task and compute the second task.
        //
        // In the event you need to subdivide a task into more than two subtasks, each time you split a task, you would fork every new subtask except one
        // (which would be computed).
    }

    // =========================================================================================================================================
    // Work Stealing
    static void test01_05() {
        // Work stealing is how the other threads in a ForkJoinPool will obtain tasks. When initially submitting a Fork/Join task
        // for execution, a single thread from a ForkJoinPool begins executing (and subdividing) that task.
        //
        // Of course, if everything was always this evenly distributed, you might not have as much of a need for Fork/Join.
        // You could just presplit the work into a number of tasks equal to the number of threads in your system and use a regular ExecutorService.
        //
        // In practice, each of the four threads will not finish their 25 percent of the work at the same time—one thread will be the slow thread
        // that doesn’t get as much work done.
        //
        // If you only split the tasks into 25 percent of the data (with four threads), then there would be nothing for the faster threads to steal from
        // when they finish early. In the beginning, if the slower thread stole 25 percent of the work and started processing it without further
        // subdividing and queuing, then there would be no work on the slow thread’s queue to steal.
        //
        // You should subdivide the tasks into a few more sections than are needed to evenly distribute the work among the number of threads in your
        // ForkJoinPools because threads will most likely not perform exactly the same.
        //
        // Subdividing the tasks is extra work—if you do it too much, you might hurt performance. Subdivide your tasks enough to keep all CPUs
        // busy, but not more than is needed. Unfortunately, there is no magic number to split your tasks into—it varies based on the complexity of
        // the task, the size of the data, and even the performance characteristics of your CPUs.
    }

    // =========================================================================================================================================
    // Join
    static void test01_06() {
        // When you call join() on the (left) task, it should be one of the last steps in the compute method, after calling fork() and compute().
        // Calling join() says, “I can only proceed when this (left) task is done.” Several possible things can happen when you call join():
        //
        // The task you call join() on might already be done
        //
        // The task you call join() on might be in the middle of being processed. Another thread could have stolen the task, and you’ll have
        // to wait until the joined task is done before continuing.
        //
        // The task you call join() on might still be in the queue (not stolen). In this case, the thread calling join() will execute the joined task.
    }

    // =========================================================================================================================================
    static void summary() {
        // It provides tools to help speed up parallel processing by attempting to use all available processor cores – which is accomplished 
        // through a divide and conquer approach.

        // *********************************************************************************
        // ForkJoinPool
        // *********************************************************************************
        // it's a implementation of the ExecutorService, Worker threads can execute only one task at the time, 
        // but the ForkJoinPool doesn’t create a separate thread for every single subtask.
        // Instead, each thread in the pool has its own double-ended queue (or deque, pronounced deck) which stores tasks.
        //
        ForkJoinPool commonPool = ForkJoinPool.commonPool();

        ForkJoinPool forkJoinPool = new ForkJoinPool(2);

        // *********************************************************************************
        // ForkJoinTask
        // *********************************************************************************
        // ForkJoinTask is the base type for tasks executed inside ForkJoinPool.

        // *********************************************************************************
        // RecursiveAction 
        // *********************************************************************************
        //  A recursive resultless ForkJoinTask.

        // *********************************************************************************
        // RecursiveTask<V>
        // *********************************************************************************
        // A recursive result-bearing ForkJoinTask.
    }

    // =========================================================================================================================================
    public static void main(String[] args) {
        test01_01();
    }
}

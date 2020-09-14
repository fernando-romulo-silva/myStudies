package br.com.fernando.enthuware.useConcurrencyAPIJavaEE7Applications;

import java.util.concurrent.Future;

import javax.enterprise.concurrent.ManagedExecutorService;
import javax.enterprise.concurrent.ManagedTaskListener;

public class Question03 {

    // Which interface should you implement if you want to be alerted to the lifecycle events surrounding your task being executed by a ManagedExecutorService?
    //
    // A
    // the Runnable Interface
    //
    // B
    // the ManagedExecutorTask interface
    //
    // C
    // the TaskEventListener interface
    //
    // D
    // the ManagedTaskListener interface
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //    
    //
    //
    //
    //
    //
    // The correct answer is D
    // Tasks can optionally provide an ManagedTaskListener to receive notifications of lifecycle events, through the use of ManagedTask interface.

    // -------------------------------------------------------------------------------------------------------------------------------
    public static class MyTask implements Runnable, ManagedTaskListener {

	@Override
	public void run() {
	    System.out.println("MyTask.run");
	}

	// ----------------------------------------------------------------------------------------------
	// javax.enterprise.concurrent.ManagedTaskListener
	// A task can also implement the ManagedTaskListener interface to receive life-cycle event notifications.
	// All listeners run without an explicit transaction (they do not enlist in the application component’s transaction).
	// UserTransaction instance can be used if transactions are required.
	//
	// Called before the task is about to start
	@Override
	public void taskStarting(Future<?> future, ManagedExecutorService service, Object object) {
	    System.out.println("taskStarting");
	}

	// Called after the task is submitted to the Executor
	@Override
	public void taskSubmitted(Future<?> future, ManagedExecutorService service, Object object) {
	    System.out.println("taskSubmitted");
	}

	// Called when a task’s Future has been cancelled any time during the life of a task
	@Override
	public void taskAborted(Future<?> future, ManagedExecutorService service, Object object, Throwable exception) {
	    System.out.println("taskAborted");
	}

	// Called when a submitted task has completed running, successful or otherwise
	@Override
	public void taskDone(Future<?> future, ManagedExecutorService service, Object object, Throwable exception) {
	    System.out.println("taskDone");
	}
    }
}

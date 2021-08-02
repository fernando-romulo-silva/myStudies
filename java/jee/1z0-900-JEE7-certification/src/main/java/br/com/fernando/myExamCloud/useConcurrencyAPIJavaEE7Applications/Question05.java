package br.com.fernando.myExamCloud.useConcurrencyAPIJavaEE7Applications;

import java.util.concurrent.Future;

import javax.enterprise.concurrent.ManagedExecutorService;
import javax.enterprise.concurrent.ManagedTaskListener;

public class Question05 {

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
    //
    // A ManagedTaskListener is used to monitor the state of a task's Future.
    // It can be registered with a ManagedExecutorService using the submit methods and will be invoked when the state of the Future changes.
    // Each listener method will run with unspecified context.
    // All listeners run without an explicit transaction (they do not enlist in the application component's transaction).
    //
    // If a transaction is required, use a UserTransaction instance.
    // Each listener instance will be invoked within the same process in which the listener was registered.
    //
    // Each listener method supports a minimum quality of service of at-most-once.
    // A listener is not guaranteed to be invoked due to a process failure or termination.
    //
    // If a single listener is submitted to multiple ManagedExecutorService instances, the listener object may be invoked concurrently by multiple threads.
    // Tasks can optionally provide an ManagedTaskListener to receive notifications of lifecycle events, through the use of ManagedTask interface.
    //
    // Don't exist the TaskEventListener interface
    //
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

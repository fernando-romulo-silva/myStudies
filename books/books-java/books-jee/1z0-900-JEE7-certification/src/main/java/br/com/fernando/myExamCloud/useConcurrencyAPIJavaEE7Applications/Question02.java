package br.com.fernando.myExamCloud.useConcurrencyAPIJavaEE7Applications;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

import javax.annotation.Resource;
import javax.enterprise.concurrent.ManagedExecutorService;

public class Question02 {

    public class SomeClass {

	@Resource(name = "java:comp/env/concurrent/TaskExecutor")
	ManagedExecutorService mes;

	public void createTask() {
	    Future<Integer> result = mes.submit(new MyCallable());
	}
    }

    public class MyCallable implements Callable<Integer> {

	@Override
	public Integer call() throws Exception {
	    // TODO Auto-generated method stub
	    return null;
	}
    }

    // How often does the submitted task run?
    //
    // Choice A
    // need more details to determine
    //
    // Choice B
    // once
    //
    // Choice C
    // as many times as you like
    //
    // Choice D
    // based on a schedule
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
    // Choice B is correct.
    //
    // <T> Future<T> submit(Callable<T> task)
    //
    // Submits a value-returning task for execution and returns a Future representing the pending results of the task.
    //
    // The Future's get method will return the task's result upon successful completion.
    //
    // If you would like to immediately block waiting for a task, you can use constructions of the form result = exec.submit(aCallable).get();
}

package br.com.fernando.myExamCloud.implementBusinessLogicUsingEJBs;

import java.util.concurrent.TimeUnit;

import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Stateful;
import javax.ejb.StatefulTimeout;
import javax.ejb.Stateless;

public class Question04 {

    // Which of the following beans can use EJB 3.x timer service?
    //
    // Choice A
    // Only stateless session beans
    //
    // Choice B
    // Only stateful session beans
    //
    // Choice C
    // Only singleton session beans
    //
    // Choice D
    // Both stateless and singleton session beans
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
    //
    //
    //
    //
    //
    //
    //
    // Choice D is correct.
    //
    // The getTimerService method returns the javax.ejb.TimerService interface.
    // Only stateless session beans and singleton session beans can use this method.
    // Stateful session beans cannot be timed objects.

    @Stateless
    public static class MyTimer01 {

	@Schedule(year = "*", month = "*", hour = "*", minute = "*", second = "*/10", persistent = false) // Timers are persistent by default.
	public void printTime() {
	    System.out.println("MyTimer.printTime!");
	}
    }

    @Singleton
    public static class MyTimer04 {

	@Schedule(year = "*", month = "*", hour = "*", minute = "*", second = "*/10", persistent = false) // Timers are persistent by default.
	public void printTime() {
	    System.out.println("MyTimer.printTime!");
	}
    }

    @Stateful // error
    public static class MyTimer02 {

	@Schedule(year = "*", month = "*", hour = "*", minute = "*", second = "*/10", persistent = false) //
	public void printTime() {
	    System.out.println("MyTimer.printTime!");
	}
    }

    @StatefulTimeout(unit = TimeUnit.MINUTES, value = 0) // error
    public static class MyTimer03 {

	@Schedule(year = "*", month = "*", hour = "*", minute = "*", second = "*/10", persistent = false) // Timers are persistent by default.
	public void printTime() {
	    System.out.println("MyTimer.printTime!");
	}
    }

}

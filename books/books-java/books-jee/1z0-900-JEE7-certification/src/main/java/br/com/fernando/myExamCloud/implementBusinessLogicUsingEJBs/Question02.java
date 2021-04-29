package br.com.fernando.myExamCloud.implementBusinessLogicUsingEJBs;

import java.util.concurrent.TimeUnit;

import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Stateful;
import javax.ejb.StatefulTimeout;
import javax.ejb.Stateless;

public class Question02 {

    // What is true about TimerService infertace?
    //
    // A - The TimerService interface is unavailable to stateless session beans.
    //
    // B - The TimerService interface is unavailable to steful session beans.
    //
    // C - The TimerService interface is unavailable to singleton session beans.
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
    //
    //
    //
    //
    //
    //
    //
    // Choice B is correct:
    //
    // A stateless session bean or singleton session bean can be registered with the EJB Timer Service for time-based event notifications.
    // The container invokes the appropriate bean instance timeout callback method when a timer for the bean has expired.
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

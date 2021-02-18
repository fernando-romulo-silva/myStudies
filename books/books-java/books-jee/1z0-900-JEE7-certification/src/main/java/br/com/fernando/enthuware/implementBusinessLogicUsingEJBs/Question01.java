package br.com.fernando.enthuware.implementBusinessLogicUsingEJBs;

import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Stateless;

public class Question01 {

    @Stateless
    public class HelloService {

	public String sayHello() {
	    return null;
	}
    }

    // Given the following code sample:
    @Singleton
    public class TimerService {
	@EJB
	HelloService helloService;

	@Schedule(minute = "*/15", hour = "6-8", dayOfMonth = "-5")
	public void doWork() {
	    System.out.println("timer: " + helloService.sayHello());
	}
    }
    //
    // When will the timer go off?
    // Select 1 option(s):
    //
    // A - every 15 minutes, between the hours of 6 and 8, on the fifth day from the end of the month
    //
    // B - every 5 days, at 6:15 AM, 7:15 AM, and 8:15 AM
    //
    // C - every 15 minutes, between the hours of 6 and 8 in the morning and again in the evening, on the fifth day of every month
    //
    // D - 5 days from the end of the month, at 6:15 AM, 7:15 AM, and 8:15 AM
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
    // A is the correct answer
    // 
    // Specifying Intervals
    //
    // The forward slash (/) constrains an attribute to a starting point and an interval and is used to specify every N seconds, minutes,
    // or hours within the minute, hour, or day.
    //
    // For an expression of the form x/y, x represents the starting point and y represents the interval.
    // The wildcard character may be used in the x position of an interval and is equivalent to setting x to 0.
    //
    // Intervals may be set only for second, minute, and hour attributes.
    //
    // The following expression represents every 10 minutes within the hour:
    //
    // minute="*/10"
    // It is equivalent to: 
    // minute="0,10,20,30,40,50"
    //
    // The following expression represents every 2 hours starting at noon:
    // hour="12/2"
    //
    //
    // Specifying Range
    //
    // Use a dash character (–) to specify an inclusive range of values for an attribute.
    // Members of a range cannot be wildcards, lists, or intervals.
    //
    // A range of the form x–x, is equivalent to the single-valued expression x.
    // A range of the form x–y where x is greater than y is equivalent to the expression x–maximum value, minimum value–y.
    // That is, the expression begins at x, rolls over to the beginning of the allowable values, and continues up to y.
    //
    // The following expression represents 9:00 a.m. to 5:00 p.m.:
    // hour="9–17"
    //
    // The following expression represents Friday through Monday:
    // dayOfWeek="5–1"
    //
    // The following expression represents the twenty-fifth day of the month to the end of the month, 
    // and the beginning of the month to the fifth day of the month:
    // dayOfMonth="25–5"
    //
    // It is equivalent to the following expression:
    // dayOfMonth="25–Last,1–5"
}

package br.com.fernando.myExamCloud.implementBusinessLogicUsingEJBs;

import java.util.Date;
import java.util.Timer;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.ejb.Timeout;
import javax.ejb.TimerConfig;
import javax.ejb.TimerService;

public class Question10 {

    // Given:
    @Stateless
    public class AlarmManager {
	
	@Resource
	private TimerService timerService;

	public void createAlarm(Date alarmDate) {
	    TimerConfig config = new TimerConfig();
	    config.setInfo(1);

	    // INSERT CODE HERE
	}

	@Timeout
	public void handleTimeout(Timer myTimer) {
	}
    }
    // You are creating an Alarm management system.
    // When you create a new alarm, you want it to create an alert that is triggered once at the specified time.
    //
    // Which method should you call on Line 9 to create the timer? (select two answers)
    //
    // A - timerService.createCalendarTimer(alarmDate, config);
    //
    // B - timerService.createSingleActionTimer(alarmDate, config);
    //
    // C - timerService.createIntervalTimer(alarmDate, -1, config);
    //
    // D - timerService.createTimer(alarmDate, -1);
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
    // The correct answer B and D

    @Stateless
    public class AlarmManager2 {

	@Resource
	private TimerService timerService;

	public void createAlarm(Date alarmDate) {
	    TimerConfig config = new TimerConfig();

	    Integer info = 1;

	    config.setInfo(info);

	    // ONCE
	    timerService.createSingleActionTimer(alarmDate, config); // Create a single-action timer that expires at a given point in time.

	    timerService.createTimer(alarmDate, info); // Create a single-action timer that expires at a given point in time.

	    // INTERVAL
	    //
	    // Create an interval timer whose first expiration occurs at a given point in time and whose subsequent expirations occur after a specified interval.
	    timerService.createIntervalTimer(alarmDate, 0, config);
	    //
	    //
	    // Create a calendar-based timer based on the input schedule expression.
	    timerService.createCalendarTimer(null);
	    //
	    //
	    // Create an interval timer whose first expiration occurs at a given point in time and whose subsequent expirations occur after a specified interval.
	    timerService.createTimer(alarmDate, -1L, info);
	}

	@Timeout
	public void handleTimeout(Timer myTimer) {
	}
    }
}

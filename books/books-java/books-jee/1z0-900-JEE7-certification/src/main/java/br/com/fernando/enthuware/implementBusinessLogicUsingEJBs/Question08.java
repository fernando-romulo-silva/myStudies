package br.com.fernando.enthuware.implementBusinessLogicUsingEJBs;

import java.util.Date;
import java.util.Timer;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.ejb.Timeout;
import javax.ejb.TimerConfig;
import javax.ejb.TimerService;

public class Question08 {

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
    // Which method should you call on Line 9 to create the timer?
    //
    // A - timerService.createCalendarTimer(alarmDate, config);
    //
    // B - timerService.createSingleActionTimer(alarmDate, config);
    //
    // C - timerService.createIntervalTimer(alarmDate, -1, config);
    //
    // D - timerService.createTimer(alarmDate, -1, config);
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
    // The correct answer B

    @Stateless
    public class AlarmManager2 {

	@Resource
	private TimerService timerService;

	public void createAlarm(Date alarmDate) {
	    TimerConfig config = new TimerConfig();
	    config.setInfo(1);
	    timerService.createSingleActionTimer(alarmDate, config);
	}

	@Timeout
	public void handleTimeout(Timer myTimer) {
	}
    }
}

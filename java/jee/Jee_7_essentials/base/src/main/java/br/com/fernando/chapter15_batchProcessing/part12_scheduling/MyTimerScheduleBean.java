package br.com.fernando.chapter15_batchProcessing.part12_scheduling;

import javax.ejb.Singleton;
import javax.ejb.Startup;

@Startup
@Singleton
public class MyTimerScheduleBean extends AbstractTimerBatch {
}

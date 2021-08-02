package br.com.fernando.chapter08_enterpriseJavaBeans.part09_timers;

import static br.com.fernando.Util.APP_FILE_TARGET;
import static br.com.fernando.Util.EMBEDDED_JEE_TEST_APP_NAME;
import static br.com.fernando.Util.HTTP_PORT;
import static br.com.fernando.Util.downVariables;
import static br.com.fernando.Util.startVariables;

import java.io.File;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.ScheduleExpression;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.TimedObject;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerConfig;
import javax.ejb.TimerService;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.myembedded.jeecontainer.MyEmbeddedJeeContainer;
import org.myembedded.pack.EmbeddedEar;
import org.myembedded.pack.EmbeddedEjb;
import org.myembedded.pack.EmbeddedResource;
import org.myembedded.pack.JeeVersion;

public class Timers02 {

    @Startup
    @Singleton
    public static class MyTimer implements TimedObject {

        // Note that the timers are created in the life-cycle callback methods, thus ensuring that
        // they are ready before any business method on the bean is invoked.
        @Resource
        private TimerService timerService;

        // The Timer Service allows for programmatic creation and cancellation of timers.
        // You can create programmatic timers using the createXXX methods on TimerService.
        // The method to be invoked at the scheduled time may be the ejbTimeout method from TimedObject:

        @PostConstruct
        public void initTimer() {

            // The initTimer method is a life-cycle callback method that cleans up any previously created timers and then 
            // creates a new timer that triggers every 10th second.
            // 
            // EJB 3.2 adds the Timer.getAllTimers API, which returns all active timers associated with the beans in the 
            // same module in which the caller bean is packaged. 
            if (timerService.getTimers() != null) {
                for (final Timer timer : timerService.getTimers()) {
                    timer.cancel();
                }
            }

            final ScheduleExpression scheduleExpression = new ScheduleExpression() //
                    .year("*") //
                    .month("*") //                    
                    .hour("*") //
                    .minute("*") //
                    .second("*/10");

            final TimerConfig timerConfig = new TimerConfig("myTimer", true);

            timerService.createCalendarTimer(scheduleExpression, timerConfig);
        }

        // The "ejbTimeout" method, implemented from the TimedObject interface, is invoked every time the timeout occurs.
        @Override
        public void ejbTimeout(final Timer timer) {

            // The timer parameter in this method can be used to cancel the timer, get information on when the next timeout will occur, 
            // get information about the timer itself, and gather other relevant data.

            System.out.println("MyTimer.ejbTimeout!");
        }

    }

    // Timers can be created in stateless session beans, singleton session beans, and message-driven beans, 
    // but not stateful session beans. This functionality may be added to a future version of the specification.

    @Startup
    @Singleton
    public static class ProgrammaticTimerBean {

        @Inject
        private Event<Ping> pingEvent;

        @Resource
        private TimerService timerService;

        @PostConstruct
        public void initialize() { //

            final ScheduleExpression scheduleExpression = new ScheduleExpression() //
                    .year("*") //
                    .month("*") //
                    .hour("*") //
                    .minute("*") //
                    .second("*/5");

            // Timers are persistent by default and can be made nonpersistent programmatically
            final TimerConfig timerConfig = new TimerConfig("myTimer", true);

            timerConfig.setInfo("Every 5 second timer");

            timerService.createCalendarTimer(scheduleExpression, timerConfig);
        }

        // The method needs to be marked with @Timeout:
        @Timeout
        // The third way to create timers is for a method to have the following signatures:
        public void programmaticTimout(final Timer timer) {
            pingEvent.fire(new Ping(timer.getInfo().toString()));
        }
    }

    public static class MyTimerXml {

        public void timeout(final Timer timer) {
            System.out.println("MyTimerXml.timeout!");
        }
    }

    // ----------------------------------------------------------------------------------------------------------------------------------
    @Startup
    @Singleton
    public static class PingsListener {

        final List<Ping> pings = new CopyOnWriteArrayList<>();

        public void listen(@Observes final Ping ping) {
            System.out.println("ping = " + ping);
            pings.add(ping);
        }

        public List<Ping> getPings() {
            return pings;
        }
    }

    public static class Ping {

        private final String timeInfo;

        private final long time = System.currentTimeMillis();

        public Ping(final String s) {
            this.timeInfo = s;
        }

        public long getTime() {
            return time;
        }

        public String getTimeInfo() {
            return timeInfo;
        }

        @Override
        public String toString() {
            return "Ping {" + "timeInfo='" + timeInfo + '\'' + ", time=" + time + '}';
        }
    }

    // ==================================================================================================================================================================
    public static void main(final String[] args) throws Exception {

        startVariables();

        try (final MyEmbeddedJeeContainer embeddedJeeServer = new MyEmbeddedJeeContainer();) {

            final EmbeddedEjb ejb = new EmbeddedEjb(EMBEDDED_JEE_TEST_APP_NAME);
            ejb.addMetaInfFiles(EmbeddedResource.add("beans.xml", "src/main/resources/beans.xml"));
            ejb.addMetaInfFiles(EmbeddedResource.add("ejb-jar.xml", "src/main/resources/chapter08_enterpriseJavaBeans/part09_timers/ejb-jar.xml"));

            ejb.addClasses(Ping.class, PingsListener.class, ProgrammaticTimerBean.class, MyTimer.class, MyTimerXml.class);

            final EmbeddedEar ear = new EmbeddedEar(EMBEDDED_JEE_TEST_APP_NAME, JeeVersion.JEE_7);
            ear.addModules(ejb);

            final File earFile = ear.exportToFile(APP_FILE_TARGET);

            embeddedJeeServer.start(HTTP_PORT);

            embeddedJeeServer.deploy(EMBEDDED_JEE_TEST_APP_NAME, earFile.getAbsolutePath());

            System.out.println("put a break point here!");
        } catch (final Exception ex) {
            System.out.println(ex);
        }

        downVariables();
    }

}

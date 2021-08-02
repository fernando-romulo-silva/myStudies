package br.com.fernando.chapter08_enterpriseJavaBeans.part09_timers;

import static br.com.fernando.Util.APP_FILE_TARGET;
import static br.com.fernando.Util.EMBEDDED_JEE_TEST_APP_NAME;
import static br.com.fernando.Util.downVariables;
import static br.com.fernando.Util.startVariables;

import java.io.File;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.annotation.Resource;
import javax.ejb.Schedule;
import javax.ejb.Schedules;
import javax.ejb.SessionContext;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.Stateless;
import javax.ejb.Timer;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.myembedded.jeecontainer.MyEmbeddedJeeContainer;
import org.myembedded.pack.EmbeddedEar;
import org.myembedded.pack.EmbeddedEjb;
import org.myembedded.pack.EmbeddedResource;
import org.myembedded.pack.JeeVersion;

public class Timers01 {

    // The EJB Timer Service is a container-managed service that allows callbacks to be scheduled for time-based events.
    // These events are scheduled according to a calendar-based schedule at a specific time, after a specific elapsed duration,
    // or at specific recurring intervals
    //
    // The first way to execute time-based methods is by marking any method of the bean with @Schedule:

    @Stateless
    public static class MyTimer {

        // @Schedule also takes year and month fields, with a default value of * indicating to execute this method each month of all years.
        // In this code, the printTime method is called every 10th second of every minute of every hour.

        @Schedule(year = "*", month = "*", hour = "*", minute = "*", second = "*/10", persistent = false) // Timers are persistent by default. 
        // Timers defined via annotations can be made nonpersistent
        public void printTime() {
            // Persistent timers are not allowed in Web Profile.
            // The timer-based events can only be scheduled in stateless session beans and singleton session beans.

            System.out.println("MyTimer.printTime!");
        }

        // @Schedule expressions and meanings
        // hour="1,2,20" -> 1 am, 2 am, and 10 pm on all days of the year
        //
        // dayOfWeek="Mon-Fri" -> Monday, Tuesday, Wednesday, Thursday, and Friday, at midnight (based upon the default values of hour, minute, and second)
        //
        // minute="30", hour="4", time zone="America/Los_Angeles" -> Every morning at 4:30 US Pacific Time
        //
        // dayOfMonth="-1,Last" -> One day before the last day and the last day of the month at midnight
    }

    // You can easily create the single-action timer by specifying fixed values for each field:

    /**
     * <pre>
     *  
     * &#64;Schedule(
     *               year="A",
     *               month="B",
     *               dayOfMonth="C",
     *               hour="D",
     *               minute="E",
     *               second="F"
     *             )
     * </pre>
     */

    // Timers are not for real time, as the container interleaves the calls to a timeout callback method with the calls to the business methods 
    // and the life-cycle callback methods of the bean. 

    @Startup
    @Singleton
    public static class SchedulesTimerBean {

        @Inject
        private Event<Ping> pingEvent;

        // @Schedules may be used to specify multiple timers.

        @Schedules({ //
                @Schedule(year = "*", month = "*", hour = "*", minute = "*", second = "*/5", info = "Every 5 second timer"), //
                @Schedule(year = "*", month = "*", hour = "*", minute = "*", second = "*/10", info = "Every 10 second timer") //
        }) //
        public void automaticallyScheduled(final Timer timer) {
            pingEvent.fire(new Ping(timer.getInfo().toString()));
        }
    }

    // Note that there is no need for an @Startup annotation here, as life-cycle callback methods are not required.
    // Each redeploy of the application will automatically delete and recreate all the schedule-based timers.

    @Startup
    @Singleton
    public static class MultipleScheduleTimerBean {

        @Inject
        Event<Ping> pingEvent;

        @Schedule(year = "*", month = "*", hour = "*", minute = "*", second = "*/5", info = "Every 5 second timer")
        public void fastAutomaticallyScheduled(final Timer timer) {
            fireEvent(timer);
        }

        @Schedule(year = "*", month = "*", hour = "*", minute = "*", second = "*/10", info = "Every 10 second timer")
        public void slowlyAutomaticallyScheduled(final Timer timer) {
            fireEvent(timer);
        }

        private void fireEvent(final Timer timer) {
            pingEvent.fire(new Ping(timer.getInfo().toString()));
        }
    }

    @Startup
    @Singleton
    public static class AutomaticTimerBean {

        @Resource
        private SessionContext ctx;

        @Inject
        private Event<Ping> pingEvent;

        @Schedule(year = "*", month = "*", hour = "*", minute = "*", second = "*/5", info = "Every 5 second timer")
        public void printDate() {

            final Collection<Timer> timers = ctx //
                    .getTimerService() //
                    .getAllTimers();

            for (final Timer t : timers) {
                pingEvent.fire(new Ping(t.getInfo().toString()));
            }
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
            ejb.addClasses(Ping.class, PingsListener.class, MyTimer.class, SchedulesTimerBean.class, MultipleScheduleTimerBean.class, AutomaticTimerBean.class);

            final EmbeddedEar ear = new EmbeddedEar(EMBEDDED_JEE_TEST_APP_NAME, JeeVersion.JEE_7);
            ear.addModules(ejb);

            final File earFile = ear.exportToFile(APP_FILE_TARGET);

            embeddedJeeServer.start();

            embeddedJeeServer.deploy(EMBEDDED_JEE_TEST_APP_NAME, earFile.getAbsolutePath());

            System.out.println("put a break point here!");
        } catch (final Exception ex) {
            System.out.println(ex);
        }

        downVariables();
    }

}

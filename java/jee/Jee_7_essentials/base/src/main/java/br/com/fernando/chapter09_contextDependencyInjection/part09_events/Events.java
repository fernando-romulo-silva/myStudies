package br.com.fernando.chapter09_contextDependencyInjection.part09_events;

import static br.com.fernando.Util.APP_FILE_TARGET;
import static br.com.fernando.Util.EMBEDDED_JEE_TEST_APP_NAME;
import static br.com.fernando.Util.HTTP_PORT;
import static br.com.fernando.Util.downVariables;
import static br.com.fernando.Util.startVariables;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.enterprise.event.Reception;
import javax.enterprise.event.TransactionPhase;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Qualifier;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.myembedded.jeecontainer.MyEmbeddedJeeContainer;
import org.myembedded.pack.EmbeddedResource;
import org.myembedded.pack.EmbeddedWar;

public class Events {

    // Event producers raise events that are consumed by observers.
    // The event object, typically a POJO, carries state from producer to consumer.
    //
    // The producer and the observer are completely decoupled from each other and only communicate using the state.
    // ----------------------------------------------------------------------------------------------------------
    public static interface EventSender {

        void send(String message);
    }

    public static class GreetingSender implements EventSender {

        @Inject
        private Event<String> event;

        @Override
        public void send(final String message) {
            // A producer bean will fire an event using the Event interface:
            event.fire(message);
        }
    }

    // ----------------------------------------------------------------------------------------------------------
    //
    public static interface EventReceiver {

        String getGreet();
    }

    @SessionScoped
    public static class GreetingReceiver implements EventReceiver, Serializable {

        private static final long serialVersionUID = 1L;

        private String greet = "Hey ";

        // An observer bean with the following method signature will receive the event:
        void receive(@Observes final String greet) {
            this.greet += greet;
        }

        @Override
        public String getGreet() {
            return greet;
        }
    }

    public static class Service01 {

        @Inject
        private GreetingSender greetingSender;

        @Inject
        private GreetingReceiver greetingReceiver;

        public void execute() {
            greetingSender.send("Hello");
            System.out.println(greetingReceiver.getGreet());
        }
    }

    // ==========================================================================================================================================================
    //
    // https://dzone.com/articles/an-overview-of-cdi-events
    @Qualifier
    @Retention(RUNTIME)
    @Target({ METHOD, FIELD, PARAMETER, TYPE })
    public static @interface Important {
    }

    @Named
    @RequestScoped
    public static class EventSource {

        @Inject
        private Event<String> simpleMessageEvent;

        public void fireEvent() {
            simpleMessageEvent.fire("Normal Hello");
        }
    }

    @Named
    public static class ImportantEventSource {

        // The producer bean can specify a set of qualifiers when injecting the event:
        @Inject
        @Important
        private Event<String> importantMessageEvent;

        public void fireEvent() {
            importantMessageEvent.fire("Important Hello");
        }
    }

    @Named
    @SessionScoped
    public static class EventObserver implements Serializable {

        private static final long serialVersionUID = 1L;

        private String message;

        // By default, an existing instance of the bean or a new instance of the bean is created in the current context to deliver the event.
        // This behavior can be altered so that the event is delivered only if the bean already exists in the current scope:
        public void observeMessage(@Observes(notifyObserver = Reception.IF_EXISTS) final String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }

    @Named
    @SessionScoped
    public static class ImportantEventObserver implements Serializable {

        private static final long serialVersionUID = 1L;

        private String message;

        // The observer bean's method signature has to match the exact set of qualifiers in order to receive the events fired by this bean:
        public void observeImportantMessage(@Observes @Important final String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }

    @Named
    @SessionScoped
    public static class TransactionEventObserver implements Serializable {

        private static final long serialVersionUID = 1L;

        private String message;

        // Transactional observer methods receive their event notifications during the before completion or after completion phases of the transaction in which the event was fired.
        public void observeMessage(@Observes(during = TransactionPhase.AFTER_SUCCESS) final String message) {
            this.message = message;
        }
        // TransactionPhase identifies the kind of transactional observer methods,
        //
        // TransactionPhase.IN_PROGRESS: Default behavior; observers are called immediately
        //
        // TransactionPhase.BEFORE_COMPLETION: Observers are called during the before completion phase of the transaction
        //
        // TransactionPhase.AFTER_COMPLETION: Observers are called during the after completion phase of the transaction
        //
        // TransactionPhase.AFTER_FAILURE: Observers are called during the after completion phase of the transaction, only when the transaction fails
        //
        // TransactionPhase.AFTER_SUCCESS: Observers are called during the after completion phase of the transaction, only when the transaction succeeds

        public String getMessage() {
            return message;
        }
    }

    public static class Service02 implements Serializable {

        private static final long serialVersionUID = 1L;

        @Inject
        private EventSource eventSource;

        @Inject
        private EventObserver eventObserver;

        @Inject
        private TransactionEventObserver transactionEventObserver;

        // ------------------------------------------------------

        @Inject
        private ImportantEventSource importantEventSource;

        @Inject
        private ImportantEventObserver importantEventObserver;

        public void execute() {

            importantEventSource.fireEvent();
            eventSource.fireEvent();
            method();

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
            }

            System.out.println("Event observer: " + eventObserver.getMessage()); // null, because of GreetingSender that capture string (simple event)
            System.out.println("Important observer: " + importantEventObserver.getMessage());
            System.out.println("Transaction observer: " + transactionEventObserver.getMessage());
        }

        @Transactional
        public void method() {
            eventSource.fireEvent();
        }
    }

    // ==========================================================================================================================================================
    @WebServlet("/Servlet")
    public static class ServletTest extends HttpServlet {

        private static final long serialVersionUID = 1L;

        @Inject
        private Service01 service01;

        @Inject
        private Service02 service02;

        @Override
        protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
            service01.execute();

            service02.execute();
        }
    }

    // ==========================================================================================================================================================
    public static void main(final String[] args) throws Exception {
        startVariables();

        try (final MyEmbeddedJeeContainer embeddedJeeServer = new MyEmbeddedJeeContainer()) {

            final EmbeddedWar war = new EmbeddedWar(EMBEDDED_JEE_TEST_APP_NAME);

            // WEB-INF
            war.addMetaInfFiles(EmbeddedResource.add("beans.xml", "src/main/resources/beans.xml"));

            war.addClasses( //
                    ServletTest.class, Service01.class, Service02.class, //
                    GreetingSender.class, EventSender.class, EventReceiver.class, GreetingReceiver.class, //
                    Important.class, EventSource.class, ImportantEventSource.class, EventObserver.class, ImportantEventObserver.class, TransactionEventObserver.class);

            final File appFile = war.exportToFile(APP_FILE_TARGET);

            embeddedJeeServer.start(HTTP_PORT);

            embeddedJeeServer.deploy(EMBEDDED_JEE_TEST_APP_NAME, appFile.getAbsolutePath());

            // ------------ Client -----------------------------------------------------------
            final HttpClient httpClient = HttpClientBuilder.create().build();
            final HttpResponse response = httpClient.execute(new HttpGet("http://localhost:" + HTTP_PORT + "/" + EMBEDDED_JEE_TEST_APP_NAME + "/Servlet"));

            System.out.println(response);

            Thread.sleep(5000); // 5 seconds only for the server finishs its jobs...

        } catch (final Exception ex) {
            System.out.println(ex);
        }

        downVariables();
    }
}

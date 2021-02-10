package br.com.fernando.myExamCloud.secureJavaEE7Applications;

public class Question02 {

    // What value can replace XXX to ensures that only the intended and authorized recipients should be able to read data?
    //
    /**
     * <pre>
     *     <user-data-constraint>
     *         <transport-guarantee>XXX</transport- guarantee>
     *     </user-data-constraint>
     * </pre>
     */
    //
    // Choice A - NONE
    // Choice B - CONFIDENTIAL
    // Choice C - INTEGRAL
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
    // Choice C is the correct answer.
    //
    // The transport-guarantee element specifies that the communication between client and server should be NONE, INTEGRAL, or CONFIDENTIAL.
    //
    // NONE means that the application does not require any transport guarantee.
    //
    // INTEGRAL means that the application requires that the data sent between the client and server be sent in such a way that it can't be changed in transit.
    //
    // CONFIDENTIAL means that the application requires that the data be transmitted in a fashion that prevents other entities from observing the contents of
    // the transmission.
    //
    // In most cases, the presence of the INTEGRAL or CONFIDENTIAL flag will indicate that the use of SSL is required.
}

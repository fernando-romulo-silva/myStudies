package br.com.fernando.enthuware.developWebApplicationsJSFs;

public class Question03 {

    // On your JSF page, you have a form in which you have a command button:
    //
    /**
     * <pre>
     *      <h:commandButton value="Click Me!" 
     *             actionListener="#{someBean.listenCarefully}"
     *             action="#{someBean.actNow}"/ >
     * 
     * </pre>
     */
    //
    //
    // A user submits the form by clicking the button, and no errors occur while processing the request.
    // Which of the following statements is/are true? You had to select 1 option(s)
    //
    // A
    // The actNow() and ListenCarefully() methods are executed in parallel.
    //
    // B
    // The listenCarefully() method is executed followed by the actNow() method.
    //
    // C
    // The actNow() method controls the condition upon which the listenCarefully() method is allowed to be executed.
    //
    // D
    // The actNow method is executed followed by the listenCarefully() method.
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
    // The correct answer is B
    // The actionListeners are always invoked before the action in the same order as they are been declared in the view and attached to the component.
    // While an f:ajax listener is always invoked before any action listener.
}

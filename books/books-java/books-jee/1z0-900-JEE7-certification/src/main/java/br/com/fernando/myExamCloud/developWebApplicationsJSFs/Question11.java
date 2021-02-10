package br.com.fernando.myExamCloud.developWebApplicationsJSFs;

import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;

public class Question11 {

    // When handling a JSF request, your application code decided to redirect the call to another URL by using HTTP redirect.
    // Which action should you take to correctly complete the handling of the JSF life cycle?
    //
    // You had to select 1 option(s)
    //
    // A
    // Set the immediate="true" attribute on the command button that was used to perform this call.
    //
    // B
    // Invoke the dispatch() method on the ExternalContext object.
    //
    // C
    // Invoke the setCurrentPhaseId(RENDER_RESPONSE) method on the FacesContext object.
    //
    // D
    // Invoke the responseComplete() method on the FacesContext object.
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
    // The correct answer is C
    //
    // FacesContext contains all of the per-request state information related to the processing of a single
    // JavaServer Faces request, and the rendering of the corresponding response.
    //
    // It is passed to, and potentially modified by, each phase of the request processing lifecycle.
    // When an initial JSF request is processed, it only executes the Restore View and Render Response phases, because there is no user input or actions to process.

    public String doSomething() {

	FacesContext facesContext = FacesContext.getCurrentInstance();
	facesContext.setCurrentPhaseId(PhaseId.RENDER_RESPONSE);

	return "Redirect";
    }

}

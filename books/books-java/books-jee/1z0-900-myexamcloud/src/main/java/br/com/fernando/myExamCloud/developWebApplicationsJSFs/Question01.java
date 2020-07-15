package br.com.fernando.myExamCloud.developWebApplicationsJSFs;

public class Question01 {

    // Which JavaServer Faces life cycle will be started when a link or a button component is clicked?
    //
    // Choice A - Update Model Values Phase
    //
    // Choice B - Apply Request Values Phase
    //
    // Choice C - Invoke Application Phase
    //
    // Choice D - Restore View Phase]
    //
    // Choice E - Render Response Phase
    //
    // Choice F - Process Validations Phase
    //
    //
    //
    // Correct Answer :
    // Choice D
    //
    // Explanation :
    // Choice D is correct.
    //
    // When a request for a JavaServer Faces page is made, usually by an action such as when a link or a button component is clicked, the JavaServer Faces
    // implementation begins the Restore View phase.
    // During this phase, the JavaServer Faces implementation builds the view of the page, wires event handlers and validators to components in the view, and
    // saves the view in the FacesContext instance, which contains all the information needed to process a single request.
    //
    // All the application's components, event handlers, converters, and validators have access to the FacesContext instance.
    // If the request for the page is an initial request, the JavaServer Faces implementation creates an empty view during this phase and the lifecycle advances
    // to the Render Response phase, during which the empty view is populated with the components referenced by the tags in the page. If the request for the page
    // is a postback, a view corresponding to this page already exists in the FacesContext instance.
    // During this phase, the JavaServer Faces implementation restores the view by using the state information saved on the client or the server.
    // Exam Objective: Identify the life cycle stages of JSF, flow of request processing, and purpose of FacesContext.
    //
    // Exam Objective: Describe JSF architecture, lifecycle and navigation

}

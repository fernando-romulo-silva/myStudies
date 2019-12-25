package br.com.fernando.chapter03_jsf.part04_requestProcessingLifeCyclePhases;

public class RequestProcessingLifeCyclePhases {

    // ==================================================================================================================================================================
    // A JSF page is represented by a tree of UI components, called a view. When a client makes a request for the page, the life cycle starts.
    //
    // During the life cycle, the JavaServer Faces implementation must build the view while considering the state saved from a previous submission of the page.
    // 
    // When the client submits a page, the JavaServer Faces implementation must perform several tasks, such as validating the data input of components
    // in the view, converting input data to types specified on the server side, and binding data to the backing beans. 
    //
    // The different components of the application go through the following well-defined request processing life-cycle phases:
    //
    // Restore view: Restores and creates a server-side component tree to represent the UI information from a client.
    // 
    // Apply request values: This phase updates the server-side components with request parameters, headers, cookies, and so on from the client.
    //
    // Process validations: This phase will process any validations and data type conversions configured for UIComponents.
    // 
    // Update model values: Reaching this phase means that the request values are syntactically valid.
    //
    // Invoke application: Invokes application logic and performs navigation processing.
    //
    // Render response: Renders the response back to the client application.
    //
    // ==================================================================================================================================================================

}

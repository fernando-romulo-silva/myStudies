package br.com.fernando.myExamCloud.useCdiBeans;

public class Question04 {

    // You are building the User Preferences page of an application.
    // A user can change values, such as his or her name, password, address, company, and so on.
    // These values are sent to a CDI backing bean via AJAX when the user tabs out of each field.
    // However, the values must be retained in the CDI bean and stored in the database only when the user clicks the Save button.
    //
    // Which two scopes will allow your CDI bean to retain its state while the user is interacting with the User Preferences page?
    //
    // Choice A
    // Dependent
    //
    // Choice B
    // View
    //
    // Choice C
    // Session
    //
    // Choice D
    // Request
    //
    // Choice E
    // Application
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
    // Choice C and D are correct answers.
    //
    // For a web application to use a bean that injects another bean class, the bean needs to be able to hold state over the duration of the user’s interaction
    // with the application. The way to define this state is to give the bean a scope.
    //
    // Request -> @RequestScoped : A user’s interaction with a web application in a single HTTP request.
    //
    // Session -> @SessionScoped : A user’s interaction with a web application across multiple HTTP requests.
    //
    // Application -> @ApplicationScoped : Shared state across all users’ interactions with a web application.
    //
    // Dependent -> @Dependent : The default scope if none is specified; it means that an object exists to serve exactly one client (bean)
    // and has the same lifecycle as that client (bean).
    //
    // Conversation -> @ConversationScoped: A user’s interaction with a JavaServer Faces application, within explicit developer-controlled
    // boundaries that extend the scope across multiple invocations of the JavaServer Faces lifecycle. All long-running conversations are scoped to a particular HTTP servlet session and may not cross session boundaries.

}

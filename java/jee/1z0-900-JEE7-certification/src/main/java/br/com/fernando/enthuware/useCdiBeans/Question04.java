package br.com.fernando.enthuware.useCdiBeans;

public class Question04 {

    // You are building the User Preferences page of an application.
    // A user can change values, such as his or her name, password, address, company, and so on.
    // These values are sent to a CDI backing bean via AJAX when the user tabs out of each field.
    // However, the values must be retained in the CDI bean and stored in the database only when the user clicks the Save button.
    //
    // Which of the following scopes will allow your CDI bean to retain its state while the user is interacting with the User Preferences page?
    // You had to select 2 option(s)
    //
    // A
    // Dependent
    //
    // B
    // View
    //
    // C
    // Session
    //
    // D
    // Request
    //
    // E
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
    //
    //
    //
    // B e C are correct answer
    //
    // B is correct because when this annotation, along with javax.inject.Named is found on a class, the runtime must place the bean in a CDI scope such that
    // it remains active as long as NavigationHandler.handleNavigation(javax.faces.context.FacesContext, java.lang.String, java.lang.String) does not cause
    // a navigation to a view with a viewId that is different than the viewId of the current view. (javax.faces.view.ViewScoped) (CDI view for JSF)
    //
    // C is corrrect because this scope exists longer that the view scope but will do the job in this case.
    //
    // For a web application to use a bean that injects another bean class, the bean needs to be able to hold state over the duration of the user’s interaction
    // with the application. The way to define this state is to give the bean a scope.
    //
    //
    // View -> @ViewScoped : @ViewScoped annotation for CDI.
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
    // boundaries that extend the scope across multiple invocations of the JavaServer Faces lifecycle.
    // All long-running conversations are scoped to a particular HTTP servlet session and may not cross session boundaries.

}

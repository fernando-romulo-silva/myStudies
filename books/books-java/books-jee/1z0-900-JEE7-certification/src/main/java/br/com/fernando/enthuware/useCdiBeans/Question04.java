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
    // B e C are correct answer    
    // 
    // B is correct because when this annotation, along with javax.inject.Named is found on a class, the runtime must place the bean in a CDI scope such that 
    // it remains active as long as NavigationHandler.handleNavigation(javax.faces.context.FacesContext, java.lang.String, java.lang.String) does not cause 
    // a navigation to a view with a viewId that is different than the viewId of the current view. (javax.faces.view.ViewScoped) (CDI view for JSF)
    //
    // C is corrrect because this scope exists longer that the view scope but will do the job in this case.

}

package br.com.fernando.enthuware.createJavaWebApplicationsUsingServlets;

public class Question06 {

    // Your web application requires logic to remember items that a user placed into a shopping cart.
    //
    // Which of the following mechanisms should you use to associate that information with the user?
    // You had to select 2 option(s)
    //
    // A
    // HttpServletResponse objects
    //
    // B
    // ServletContext objects
    //
    // C
    // HttpSession objects
    //
    // D
    // a database
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
    // B and D is the correct answer
    // It is not clear how long do you need to remember the items.
    // If you require it to remember only until the user is still browsing, then HttpSession is the right answer. Otherwise, ServletContext is the right answer.
    // You can always store this information in the database.
    //
    // Information stored in a ServletContext remains as long as the servlet exists.
    // So, you could potentially store a map of userid and list of items in the servlet context.
    //
    // C is wrong because If you storing items for a user in a session object,
    // it will be available only until the user session is alive. But the requirement is to remember the items for later use

}

package br.com.fernando.enthuware.understandJavaEEArchitecture;

import javax.annotation.Resource;
import javax.annotation.Resources;

public class Question04 {

    // Your class requires multiple resources defined, as shown in the following:
    //
    /**
     * <pre>
     *       @<XXXX>( { 
     *  	   @Resource(name="aFactory", 
     *  	                     type="javax.jms.ConnectionFactory"),
     *  	   @Resource(name="aSession",
     *  	                     type="javax.mail.Session")
     *  	})
     * </pre>
     */
    //
    // Which annotation do you use to group multiple @Resource declarations together for class-based injection,
    // replacing <XXXX> in the code above?
    //
    // A - @Resources
    //
    // B - @Resource
    //
    // C - @ResourceGroup
    //
    // D - @ResourceCollection
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
    // The correct answer is A
    //
    // Declaring Multiple Resources
    // The @Resources annotation is used to group together multiple @Resource declarations for class-based injection.
    @Resources({ //
	    @Resource(name = "myMessageQueue", //
		    type = javax.jms.ConnectionFactory.class), //
	    @Resource(name = "myMailSession", //
		    type = javax.mail.Session.class) //
    })
    public static class SomeMessageBean {
	// ...
    }

}

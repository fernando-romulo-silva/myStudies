package br.com.fernando.myExamCloud.developWebApplicationsJSFs;

public class Question07 {

    // Given:
    /**
     * <pre>
     *  
     *   <form name="form1"><input type="submit" name="submit></form>
     *   <ui:composition template="template.xhtml">
     *       <ui:define name="title">
     *           Welcome
     *       </ui:define>
     *       <ui:define name="header">
     *           ExamBoat Search Page
     *       </ui:define>
     *       <ui:define name="message">
     *           Reach Exam Destiny
     *       </ui:define>
     *   </ui:composition>
     *   <form name="form2"><input type="submit" name="submit></form>
     * 
     * </pre>
     */
    // What is true about the above code?
    //
    // Choice A
    // Only form1 content will be ignored by view handler
    //
    // Choice B
    // Only form2 content will be ignored by view handler
    //
    // Choice C
    // Both form1 and form2 content will be ignored by view handler
    //
    // Choice D
    // Both form1 and form2 content will be included by view handler
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
    // Choice C is correct.
    //
    // Both form1 and form2 content will be ignored by view handler.
    //
    // The UI Composition tag is a templating tag that wraps content to be included in another Facelet.
    // Any content outside of the UI Composition tag will be ignored by the Facelets view handler.
    // Any content inside of the UI Composition tag will be included when another Facelets page includes the page containing this UI Composition tag.

}

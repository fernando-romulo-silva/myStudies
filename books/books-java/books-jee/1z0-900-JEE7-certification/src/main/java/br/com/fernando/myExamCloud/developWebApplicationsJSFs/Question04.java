package br.com.fernando.myExamCloud.developWebApplicationsJSFs;

public class Question04 {

    // Given the code fragment from a Facelet page:

    /**
     * <pre>
     *      <ui:composition xmlns="http://www.w3.org/1999/xhtml"
     *          xmlns:h="http://xmlns.jcp.org/jsf/html"
     *          xmlns:pt="http://xmlns.jcp.org/jsf/passthrough
     *          xmlns:f="http://xmlns.jcp.org/jsf/core"
     *          xmlns.jsf="http://xmlns.jcp.org/jsf"
     *          xmlns:ui="http://java.sun.com/jsf/facelets">
     *          
     *        <h:form id="searchForm">
     *          <!-- Line 1 -->
     *          <h:commandButton value="Search" action="#{search MB.search}"/>
     *        </h:form>
     *        
     *      </ui:composition>
     * </pre>
     */

    // On Line 1, you are asked to insert a search box that displays the text "Search Here" via a placeholder.
    // Assume searchMB is a valid Managed Bean.
    // Which two options enable you to create a search box with a placeholder attribute on Line 1?
    // [ Choose two ]

    /**
     * <pre>
     * 
     *  Choice A 	
     *  <h:inputText value="#(searchMB.query)">
     *     <f:param name="placeholder" value="Search Here"/>
     *  </h:inputText>
     *  
     *  Choice B 	
     *  <h:inputText value="#(searchMB.query)" placeholder="Search here"/>
     *  
     *  Choice C 	
     *  <input jsf:id="search" placeholder="Search here" jsf:value="# (searchMB.query)"></input>
     *  
     *  Choice D 	
     *  <h:inputText pt:placeholder="Search Here" value="#(searchMB.query)"/>
     *  
     *  Choice E 	
     *  <input id="search" jsf:placeholder="Search Here" value="$(searchMB.query)"></input>
     * 
     * </pre>
     */
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
    // Choice C and D are correct answers. 
    // 
    // D is Pass Through Elements nad C is html5
    //
    // The placeholder attribute specifies a short hint that describes the expected value of an html input field, not a jsf input text!
    //
    // We can use a place holder as follows:
    //
    // xmlns:f5 ="http://xmlns.jcp.org/jsf/passthrough" ...
    //
    // <h:inputText id="email" value="#{userBean.email}" validator="emailValidator" f5:placeholder="Enter your email..." f5:type="email"/>
    //
    // Other ways
    //
    // <h:inputText id="email" value="#{bean.email}">
    //     <f:passThroughAttributes value="#{bean.atributos}" />
    // </h:inputText>
    //
    //
    // <h:inputText id="email" value="#{bean.email}">
    //    <f:passThroughAttributes value="#{bean.atributos}" /> // map 
    // </h:inputText>
}

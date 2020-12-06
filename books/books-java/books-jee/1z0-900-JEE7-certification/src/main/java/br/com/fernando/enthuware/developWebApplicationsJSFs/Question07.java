package br.com.fernando.enthuware.developWebApplicationsJSFs;

public class Question07 {

    // Given the code fragment from a Facelet page:
    /**
     * <pre>
     *    <ui:composition xmins="http://www.w3.org/1999.xhtml"
     *        xmins:h="http://samlns.jcep.org/jsf/html"
     *        xmins:pt="http://xmlns.jep.org/jsf/passthrough"
     *        xmins:f="http://xmlns.jep.org,jsf/core"
     *        xmilns:jsf="http://amlns.jep.org/jst"
     *        xmins:ui="http://java.sun.com/jsf/facelets">
     *    
     *        <h:form id="searchForm">
     *    
     *             <!-- Line 1 -->
     *    
     *             <h:commandButton value="Search" action="#{searchMB.search}" />
     *        </h:form>
     *    </ui:composition>
     * </pre>
     */
    // On line 1, you are asked to insert a search box that displays the text "Search Here" via a placeholder.
    // Assuming that searchMB is a valid Managed Bean, which of the following options enable you to create a search box with a placeholder?
    //
    // A
    // <h:inputText value="#(searchMB.query)"><f:param name="placeholder" value="Search Here" /></h:inputText>
    //
    // B
    // <h:inputText value="#(searchMB.query)" placeholder="Search here"/>
    //
    // C
    // <input jsf:id="search" placeholder="Search here" jsf:value="#(searchMB.query)"></input>
    //
    // D
    // <h:inputText pt:placeholder="Search Here" value="#(searchMB.query)" />
    //
    // E
    // <input id="search" jsf:placeholder="Search Here" value="$(searchMB.query)"></input>
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
    // The correct answer is C and D
    // C is correct because In this approach we are using HTML's placeholder attribute and enhancing the HTML input tag with jsf using jsp:id and jsf:value attributes.
    //
    // D
    // D is correct Since xmins:pt="http://xmlns.jep.org/jsf/passthrough" is included, pt:placeholder will work.
}

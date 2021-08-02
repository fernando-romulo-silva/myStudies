package br.com.fernando.enthuware.developWebApplicationsJSFs;

import java.io.Serializable;
import java.util.Date;

public class Question06 {

    // You have been assigned to the Widget Editor portion of an application.
    // It contains a Widget Editor Facelet page, the Widget class, and a simple WidgetEditor backing bean,
    // which contains a reference to the current Widget instance.
    //
    // Given the code fragment from the Widget class:

    public class Widget implements Serializable {
    	private String name;
    	private Date createdDate;
    	private boolean approved;
    }

    // Given the code fragment from the Facelet page:
    /**
     * <pre>
     *       <h:messages id="messages"/>
     *       <h:form id="widgetForm">
     *             <h:outputLabel for="name" value="Name:"/ >
     *             <h:inputText id="name" value="#{widgetEditor.widget.name}" />
     *             <h:outputLabel for="createdDate" value"Created Date:"/>
     * 
     *             <h:inputText id="createdDate" value="#{widgetEditor.widget.createdDate}"/> <!-- Line 1 -->
     * 
     *             <h:outputLabel for="approved" value="Approved:"/'>
     *             
     *             <h:selectBooleanCheckbox id="approved" value="#{widgetEditor.widget.approved}" />
     *             <h:commandButton value="Save" action="#{widgetEditor.save}"'>
     *       </h:form>
     * </pre>
     */
    //
    // The page displays Conversion Error when a user fills out all the form fields and clicks the Save button.
    //
    // Which step do you perform to fix this problem?
    //
    // A
    // Replace Line 1 with: 
    // <h:inputText id="createDate' value="#{widgetEditor.widget.createdDate}"converter="java.util.Date" />
    //
    // B
    // Enclose the code fragment within the <f:view/> tag
    //
    // C
    // Insert <f:convertDateTime''/> at Line 1
    //
    // D
    // Replace Line 1 with:
    // <h:inputText id="createDate" value="#{ widgetEditor.widget.createdDate}"> <f:convertDateTime pattern="dd-mm-yyyy'/> </h:inputText>
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
    // The correct answer is D    
    // 
    // The f:convertDateTime tag is used to convert a string value to a date of required format. It also acts as a validator, a required date format.
    // 
    // Choice A is incorrect. The converter type must be javax.faces.convert.Converter.
    // Choice B is incorrect. The view tag will be used to display values and not for user inputs.
    // 
    // <f:viewParam name =" date" value ="#{searchCustomerForm.date}" >
    //     <f:convertDateTime pattern =" dd-MM-yyyy" />
    // </f:viewParam >
    // 
    // Choice C is incorrect. Just inserting <f:convertDateTime"/> will not work instead we need to add this into body of <h:inputText> tag. 
}

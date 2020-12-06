package br.com.fernando.myExamCloud.developWebApplicationsJSFs;

import java.io.Serializable;
import java.util.Date;

public class Question05 {

    // You have been assigned to the develop an admin portion of an application, where you need to search customers for the given date.
    // It contains a SearchCustomer Facelet page, the SearchCustomer class, and a simple SearchCustomer backing bean, which contains
    // a reference to the current SearchCustomer instance.
    //
    // Given the code fragment from the SearchCustomer class:
    public class SearchCustomer implements Serializable {
	private String userName;
	private Date date;
	private boolean premiumUser;
    }
    //
    // Given the code fragment from the Facelet page:
    /**
     * <pre>
     *    <h:messages id="messages"/>
     *    <h:form id="searchCustomerForm">
     *      <h:outputLabel for="name" value="Name:"/>
     *      <h:inputText id="name" value="#{searchCustomer.searchCustomerForm.name}"/>
     *      <h:outputLabel for="joinedDate" value="Joined Date:"/>
     *      <h:inputText id="joinedDate" value="#{searchCustomer.searchCustomerForm. searchCustomer.date}"/>
     *    
     *      <!-- Line 1 -->
     *      <h:outputLabel for=" premiumUser" value="PremiumUser:"/>
     *      <h:selectBooleanCheckbox id="premiumUser" value="#{searchCustomer.searchCustomerForm.premiumUser}"/>
     *      <h:commandButton value="Save" action="#{searchCustomer.save}"/>
     *    </h:form>
     * </pre>
     */
    // The page displays Conversion Error when a user fills out all the form fields and clicks the Save button.
    // Which step do you perform to fix this problem?
    //
    //
    // Choice A
    // Replace Line 1 with:
    // <h:inputText id="createDate"value="#{searchCustomer.searchCustomerForm.date}" converter="java.util.Date"/>
    //
    // Choice B
    // Enclose the code fragment within the <f:view/> tag
    //
    // Choice C
    // Insert <f:convertDateTime"/> at Line 1
    //
    // Choice D
    // Replace Line 1 with:
    // <h:inputText id="createDate"value="#{searchCustomer.searchCustomerForm.date}">
    //    <f:convertDateTime pattern="dd-mmyyyy"/>
    // </h:inputText>
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
    // Choice D is correct.
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

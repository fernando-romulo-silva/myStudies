package br.com.fernando.myExamCloud.developWebApplicationsJSFs;

public class Question06 {

    // Given faces-config.xml code snippet:
    /**
     * <pre>
     *    <faces-config>
     *      <application>
     *        <locale-config>
     *          <default-locale>en</default-locale>
     *          <supported-locale>de</supported-locale>
     *          <supported-locale>fr</supported-locale>
     *          <supported-locale>es</supported-locale>
     *        </locale-config>
     *      </application>
     *    </faces-config>
     * </pre>
     */

    // The developer added following code in JSP:
    // <fmt:setLocale value="en_US" scope="session"/>
    //
    // Which JSF code can load myapplication.properties into the JSP?
    // [ Choose two ]
    //
    // Choice A
    // <f:loadBundle basename="myapplication" var="myLabels"/>
    //
    // Choice B
    // <f:loadBundle basename="myapplication.properties" var="myLabels"/>
    //
    // Choice C
    // <f:loadBundle basename="myapplication_en_US" var="myLabels">
    //
    // Choice D
    // <f:loadBundle basename="myapplicationen_US.properties" var="myLabels"/>
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
    // Choice A and C are correct answers.
    //
    // The JSP provides an option to load ResourceBundle into a java.util.Map and stores it in the scoped namespace.
    //
    // Here myapplication resouce bundle is loaded into myLabels map variable.
    //
    // We can access localized content using the normal value expression syntax.
    //
    // <h:outputText value="#{myLabels.pageTitle}" />
}

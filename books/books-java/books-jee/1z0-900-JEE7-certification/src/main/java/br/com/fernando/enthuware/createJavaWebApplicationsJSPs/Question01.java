package br.com.fernando.enthuware.createJavaWebApplicationsJSPs;

public class Question01 {

    // Given:
    /**
     * <pre>
     *    <sql:query var="books" >
     *           select * from PUBLIC.books where id = ?
     *           <sql:param value="${bid}" />
     *    </sql:query>
     * </pre>
     */
    //
    // Which directive would be required to resolve the SQL tag behavior?
    // Select 1 option(s):
    //
    // A
    // <%@taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>
    //
    // B
    // <%@include uri="http://java.sun.com/jsp/jstl/sql"%>
    //
    // C 
    // <%@page prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>
    //
    // D
    // <%@taglib prefix="sql" uri="/WEB-INF/sql.tld"%>
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
    // The correct answer is A
    //
    // JSTL defines the uri http://java.sun.com/jsp/jstl/sql for sql related tags.
    //
    // B and C are invalid directives
    // 
    // D could potentially be a valid directive if there is an sql.tld file in WEB-INF directory. 
    // However, since we are using the JSTL tags, the TLD is present in the JSTL jar itself. 
    //
    //
    // 
    // Note that the value of the uri attribute is just a name that must be mapped somehow with either the actual TLD file or the jar file containing the TLD file.  
    // Even if the value of the uri attribute is an absolute url (beginning with http:// ), the container does not download anything.
    //
    // Usually, the <taglib> element of web.xml ties the uri given in the jsp page with either the actual TLD file or the jar file containing the TLD file. 
    // In this case, <taglib-uri> should be same as the value of the uri attribute of the taglib directive while the <taglib-location> 
    // should point to the TLD file or the jar file.
    //
    // However, a taglib jar file can also specify the URIs in the tag library descriptor for the tag library contains using a <uri> element. 
    // If that is the case, you do not have to explicitly specify the <taglib> element in the web.xml.
    //
    // You can import this library in the jsp pages using the following taglib directory:
    // <%@ taglib prefix="anyprefix" uri="http://www.xyzcorp.com/htmlLib" %>.
    //
    // Note that the uri must be same as the one given in the tld file.
    
    
    
    
    
    
    
    
}

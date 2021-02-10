
package br.com.fernando.myExamCloud.developWebApplicationsJSFs;

public class Question03 {

    // JSF Facelets features includes the following except.
    //
    // Choice A
    // Facelets Tag libraries in addition to JSF JSTL tag libraries
    //
    // Choice B
    // Expression Language (EL) support
    //
    // Choice C
    // Use of XML for creating web pages
    //
    // Choice D
    // Templating for components and pages
    //
    // Choice E
    // Use of XHTML for creating web pages
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
    // Choice C is correct.
    //
    // Facelets is a powerful but lightweight page declaration language that is used to build JavaServer Faces views using HTML
    // style templates and to build component trees. Facelets features include the following:
    //
    // Use of XHTML for creating web pages
    // Support for Facelets tag libraries in addition to JavaServer Faces and JSTL tag libraries
    // Support for the Expression Language (EL)
    // Templating for components and pages
    //
    // Hence Choice C is correct answer for this question.
    
    /**
     * <pre>
     *      // commonLayout.xhtml
     *      <?xml version="1.0" encoding="UTF-8"?>
     *      <!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" 
     *      "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
     *      <html xmlns="http://www.w3.org/1999/xhtml"   
     *            xmlns:h="http://java.sun.com/jsf/html"
     *            xmlns:ui="http://java.sun.com/jsf/facelets">
     *      
     *          <h:head>
     *          	<h:outputStylesheet name="common-style.css" library="css" />
     *          </h:head>
     *          
     *          <h:body>
     *      
     *          <div id="page">
     *              
     *              <div id="header">
     *                   <ui:insert name="header" >
     *                      <ui:include src="/template/common/commonHeader.xhtml" />
     *                   </ui:insert>
     *              </div>
     *                   
     *              <div id="content">
     *                 <ui:insert name="content" >
     *                     <ui:include src="/template/common/commonContent.xhtml" />
     *                 </ui:insert>
     *              </div>
     *                       
     *              <div id="footer">
     *              	<ui:insert name="footer" >
     *                      <ui:include src="/template/common/commonFooter.xhtml" />
     *              	</ui:insert>
     *              </div>
     *      
     *              </div>
     *      
     *          </h:body>
     *      </html>
     * 
     * 
     *      default.xhtml
     *      <?xml version="1.0" encoding="UTF-8"?>
     *      <!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" 
     *      "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
     *      <html xmlns="http://www.w3.org/1999/xhtml"   
     *            xmlns:h="http://java.sun.com/jsf/html"
     *            xmlns:ui="http://java.sun.com/jsf/facelets">
     *          <h:body>
     *          	
     *          	<ui:composition template="template/common/commonLayout.xhtml">
     *          
     *          		<ui:define name="content">
     *              			<h2>This is page1 content</h2>
     *              		</ui:define>
     *              
     *            		<ui:define name="footer">   
     *               			<h2>This is page1 Footer</h2>              
     *               		</ui:define>
     *      
     *          	</ui:composition>
     *          	
     *          </h:body>
     * 
     *      </html>
     * </pre>
     */
}

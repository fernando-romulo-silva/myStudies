package br.com.fernando.enthuware.createJavaWebApplicationsJSPs;

public class Question07 {

    // Which of the following statements are valid JSP directives?
    // You had to select 2 option(s)
    //
    // A - <%! int k = 10 %>
    //
    // B - <% int k = 10; %>
    //
    // C - <%=somevariable%>
    //
    // D - <%@ taglib uri="http://www.abc.com/tags/util" prefix="util" %>
    //
    // E - <%@ page language="java" import="com.abc.*"%>
    //
    //
    //
    //
    //
    // The correct answers are D and C
    //
    // 'A' is a declaration
    //
    // 'B' is a a scriptlet.
    //
    // 'C' is an expression.
    //
    // Directives start with <%@.
    //
    // There are 3 types of directives:
    //
    // 1. page : Affects the overall properties of the jsp page.
    // Here is a list of page directives to refresh your memory:
    // <%@ page attribute = "value" %>
    //
    // 2. taglib :
    // The set of significant tags a JSP container interprets can be extended through a "tag library".
    // The taglib directive in a JSP page declares that the page uses a tag library, uniquely identifies the tag library using a URI and associates a tag prefix
    // that will distinguish usage of the actions in the library.
    // <%@ taglib prefix="c" uri="http://xmlns.jcp.org/jsp/jstl/core" %>
    //
    // 3. include :
    // The include directive is used to substitute text and/or code at JSP page translation-time.
    // Syntax: <%@ include file="relativeURLspec" %>
}

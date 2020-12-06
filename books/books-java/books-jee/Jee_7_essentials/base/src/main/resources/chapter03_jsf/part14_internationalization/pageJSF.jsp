<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<%@taglib uri = "http://java.sun.com/jsp/jstl/fmt" prefix = "fmt" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Mensagens Simples</title>
</head>
<body>
    <fmt:setLocale value="en_US" scope="session"/> 
	<f:view>
		<h1>JSF - Usando Properties Files</h1>
		
		<f:loadBundle basename="recursos.mensagens" var="msgs" />

		<h2>
			<h:outputText value="#{msgs.title}" />
		</h2>

		<BR>
		<h:outputText value="#{msgs.text}" />

		<P>
			<h:form>
				<table border="0" cellspacing="2" cellpadding="2">
					<tbody>
						<tr>
							<td><h:outputText value="#{msgs.firstNamePrompt}" />:</td>
							<td><h:inputText /></td>
						</tr>

						<tr>
							<td><h:outputText value="#{msgs.lastNamePrompt}" />:</td>
							<td><h:inputText /></td>
						</tr>

						<tr>
							<td><h:outputText value="#{msgs.emailAddressPrompt}" />:</td>
							<td><h:inputText /></td>
						</tr>

						<tr>
							<td><h:commandButton value="#{msgs.buttonLabel}" /></td>
							<td></td>
						</tr>
					</tbody>
				</table>
			</h:form>
	</f:view>
</body>
</html>
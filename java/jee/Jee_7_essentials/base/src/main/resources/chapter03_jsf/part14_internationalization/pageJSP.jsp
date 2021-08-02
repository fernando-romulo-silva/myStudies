<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<html>
<head>
<title>JSTL fmt:setLocale Tag</title>
</head>

<body>
	<fmt:bundle basename="com.tutorialspoint.Example">
		<fmt:message key="count.one" />
		<br />
		<fmt:message key="count.two" />
		<br />
		<fmt:message key="count.three" />
		<br />
	</fmt:bundle>

	<!-- Change the Locale -->
	<fmt:setLocale value="es_ES" />
	<fmt:bundle basename="com.tutorialspoint.Example">
		<fmt:message key="count.one" />
		<br />
		<fmt:message key="count.two" />
		<br />
		<fmt:message key="count.three" />
		<br />
	</fmt:bundle>

</body>
</html>
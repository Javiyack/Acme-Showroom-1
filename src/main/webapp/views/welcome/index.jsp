<%--
 * index.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>


	<security:authorize access="isAuthenticated()">
		<security:authentication property="principal.authorities[0]" var="permiso" />
		<spring:message code="actor.authority.${permiso}" var="internationalizedAuth"/>
	</security:authorize>

	<p><spring:message code="welcome.greeting.prefix"/> ${internationalizedAuth}<spring:message code="welcome.greeting.suffix" /></p>

		<h3 class="formPanel w3-padding w3-text-orange w3-xxxlarge" value="" id="wellcomeMsg"></h3>

<p class="w3-bottom w3-padding-64"><spring:message code="welcome.greeting.current.time" />${moment}</p>
<script>
    asynchSearch('${pageContext.request.contextPath}','/asynchronous/msg/welcome.do', 'wellcomeMsg');
</script>
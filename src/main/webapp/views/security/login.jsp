 <%--
 * login.jsp
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
<div class="seccion w3-light-grey w3-display-middle">
<div class="w3-padding">
	<form:form action="j_spring_security_check" modelAttribute="credentials" >

		<form:label path="username">
			<spring:message code="security.username" />
		</form:label>
		<form:input path="username" class="w3-input"/>
		<form:errors class="error" path="username" />
		<br />

		<form:label path="password">
			<spring:message code="security.password" />
		</form:label>
		<form:password path="password"  class="w3-input"/>
		<form:errors class="error" path="password" />
		<br />
		<jstl:if test="${showError == true}">
			<div class="error">
				<spring:message code="security.login.failed" />
			</div>
		</jstl:if>

		<input type="submit" value="<spring:message code="security.login" />" />
		<a href="user/create.do" class="w3-text-blue-gray"><spring:message code="msg.not.registered.yet" /></a>

	</form:form>
</div>

</div>
<%--
 * action-1.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>
<style>
<!--
 ul li.thick {
    font-weight: bold;
    list-style-type: disc;
}

ul,li.terms {
    list-style-type: circle;
	paddin:0%;
}
-->
</style>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<h3>
	<spring:message code="term.cookie.title1" />
</h3>

<p>
	<spring:message code="term.cookie.text1" />
</p>

<ul>
	<li class="thick"><spring:message code="term.cookie.title2" /></li>
	<ul>
		<li class="terms"><spring:message code="term.cookie.text2.1" /></li>
		<li class="terms"><spring:message code="term.cookie.text2.2" /></li>
		<li class="terms"><spring:message code="term.cookie.text2.3" /></li>
	</ul>
	<br />
	<li class="thick"><spring:message code="term.cookie.title3" /></li>
	<ul>
		<li class="terms"><spring:message code="term.cookie.text3.1" /></li>
		<li class="terms"><spring:message code="term.cookie.text3.2" /></li>
	</ul>
	<br />
	<li class="thick"><spring:message code="term.cookie.title4" /></li>
	<ul>
		<li class="terms"><spring:message code="term.cookie.text4" /></li>
	</ul>
</ul>


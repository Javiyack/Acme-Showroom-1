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
ol li.thick {
	font-weight: bold;
	list-style-type: disc;
}

ol,li,ul,li.terms {
    list-style-type: circle;
    paddin:0px;
}
-->
</style>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<h3>
	<spring:message code="term.terms.title" />
</h3>

<p>
	<spring:message code="term.terms.text1" />
</p>

<ol>
	<li class="thick"><spring:message code="term.terms.title1" /></li>
	<spring:message code="term.terms.text1" />
	<br>
	<br>
	<li class="thick"><spring:message code="term.terms.title2" /></li>
	<spring:message code="term.terms.text2" />
	<ul>
		<li class="terms"><spring:message code="term.terms.text2.1" /></li>
		<li class="terms"><spring:message code="term.terms.text2.2" /></li>
	</ul>
	<br>
	<li class="thick"><spring:message code="term.terms.title3" /></li>
	<spring:message code="term.terms.text3" />
	<ul>
		<li class="terms"><spring:message code="term.terms.text3.1" /></li>
		<li class="terms"><spring:message code="term.terms.text3.2" /></li>
		<li class="terms"><spring:message code="term.terms.text3.3" /></li>
		<li class="terms"><spring:message code="term.terms.text3.4" /></li>
	</ul>
	<br>
	<li class="thick"><spring:message code="term.terms.title4" /></li>
	<spring:message code="term.terms.text4" />
	<ul>
		<li class="terms"><spring:message code="term.terms.text4.1" /></li>
		<li class="terms"><spring:message code="term.terms.text4.2" /></li>
	</ul>
	<br>
	<li class="thick"><spring:message code="term.terms.title5" /></li>
	<spring:message code="term.terms.text5" />
	<ul>
		<li class="terms"><spring:message code="term.terms.text5.1" /></li>
		<li class="terms"><spring:message code="term.terms.text5.2" /></li>
		<li class="terms"><spring:message code="term.terms.text5.3" /></li>
		<li class="terms"><spring:message code="term.terms.text5.4" /></li>
		<li class="terms"><spring:message code="term.terms.text5.5" /></li>
		<li class="terms"><spring:message code="term.terms.text5.6" /></li>
		<li class="terms"><spring:message code="term.terms.text5.7" /></li>
	</ul>
	<spring:message code="term.terms.text5.8" />
	<br>
	<br>
	<li class="thick"><spring:message code="term.terms.title6" /></li>
	<spring:message code="term.terms.text6.1" />
	<br>
	<spring:message code="term.terms.text6.2" />
	<br>
	<br>
	<li class="thick"><spring:message code="term.terms.title7" /></li>
	<spring:message code="term.terms.text7.1" />
	<br>
	<spring:message code="term.terms.text7.2" />
</ol>


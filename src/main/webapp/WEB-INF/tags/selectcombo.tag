<%--
 * select.tag
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@ tag language="java" body-content="empty"%>

<%-- Taglibs --%>

<%@ taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<%-- Attributes --%>

<%@ attribute name="path" required="true"%>
<%@ attribute name="code" required="true"%>
<%@ attribute name="items" required="true" type="java.util.Collection"%>

<%@ attribute name="css" required="false"%>
<%@ attribute name="value" required="false"%>
<%@ attribute name="blankValue" required="false"%>
<%@ attribute name="readonly" required="false"%>

<jstl:if test="${blankValue == null}">
	<jstl:set var="blankValue" value="true" />
</jstl:if>

<jstl:if test="${readonly == null}">
	<jstl:set var="readonly" value="false" />
</jstl:if>

<jstl:if test="${id == null}">
	<jstl:set var="id" value="${UUID.randomUUID().toString()}" />
</jstl:if>

<jstl:if test="${css != null}">
	<jstl:set var="cssVar" value="${css}" />
</jstl:if>
<jstl:if test="${css == null}">
	<jstl:set var="cssVar" value="formInput" />
</jstl:if>

<%-- Definition --%>

<div>
	<form:label path="${path}">
		<spring:message code="${code}" />
	</form:label>
	
	<form:select id="${id}" path="${path}" disabled="${readonly}" class="${cssVar}">
		
		<jstl:if test="${blankValue}">
			<form:option value="" label="" />
		</jstl:if>
		
		<form:options items="${items}" />
	</form:select>
	<form:errors path="${path}" cssClass="error" />
</div>



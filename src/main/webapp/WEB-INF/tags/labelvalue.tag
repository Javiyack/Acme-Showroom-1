<%--
 * textbox.tag
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

<%@ attribute name="code" required="true"%>
<%@ attribute name="value" required="true"%>
<%@ attribute name="css" required="false"%>

<%@ attribute name="isDate" required="false"%>
<%@ attribute name="isDatetime" required="false"%>
<%@ attribute name="isCurrency" required="false"%>

<%@ attribute name="href" required="false"%>

<jstl:if test="${isDate == null}">
	<jstl:set var="isDate" value="false" />
</jstl:if>
<jstl:if test="${isDatetime == null}">
	<jstl:set var="isDatetime" value="false" />
</jstl:if>
<jstl:if test="${isCurrency == null}">
	<jstl:set var="isCurrency" value="false" />
</jstl:if>



<%-- Definition --%>
<jstl:if test="${isDate}">
	<fmt:parseDate value="${value}" pattern="yyyy-MM-dd HH:mm:ss" var="value"/>
	<fmt:formatDate value="${value}" pattern="${formdateformat}" var="value" />
</jstl:if>
<jstl:if test="${isDatetime}">
	<fmt:parseDate value="${value}" pattern="yyyy-MM-dd HH:mm:ss" var="value"/>
	<fmt:formatDate value="${value}" pattern="${formdatetimeformat}" var="value" />
</jstl:if>
<jstl:if test="${isCurrency}">
	<fmt:formatNumber value="${value}" pattern="${formpriceformat}" var="value" />
</jstl:if>


<div style="float:left; width: 100%">
	<div style="float:left; width: 35%">
		<spring:message code="${code}" />:
	</div> 

	<div>
		<jstl:if test="${href != null}" >
			<a href="${href}" >
		</jstl:if>
	
			<jstl:out value="${value}" /> 
	
		<jstl:if test="${isCurrency}">
			<spring:message code="currency.symbol" />
		</jstl:if>
	
		<jstl:if test="${href != null}" >
			</a>
		</jstl:if>
	</div>
</div>
	
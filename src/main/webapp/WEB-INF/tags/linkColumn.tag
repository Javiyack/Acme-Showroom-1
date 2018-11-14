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
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<%-- Attributes --%>

<%@ attribute name="title" required="true"%>
<%@ attribute name="linkUrl" required="true"%>

<%@ attribute name="linkName" required="false"%>
<%@ attribute name="sortable" required="false"%>
<%@ attribute name="format" required="false"%>
<%@ attribute name="css" required="false"%>



<%-- Definition --%>
<jstl:if test="${format != null}">
	<spring:message code="${format }" var="formatVar" />
</jstl:if>
<jstl:if test="${linkName == null}">
	<spring:message code="${title}" var="linkName" />
</jstl:if>
<spring:message code="${title}" var="titleVar" />
<display:column title="${titleVar}" sortable="${sortable }" 
				class="${css}" format="${formatVar}" >
	<a href="${linkUrl}"> <jstl:out value="${linkName}"/></a>
</display:column>




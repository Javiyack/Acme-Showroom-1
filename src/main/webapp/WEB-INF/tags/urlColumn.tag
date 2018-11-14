<%--
 * textbox.tag
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@ tag language="java" body-content="empty" %>

<%-- Taglibs --%>

<%@ taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@taglib prefix="display" uri="http://displaytag.sf.net" %>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<%-- Attributes --%>

<%@ attribute name="value" required="true" %>
<%@ attribute name="title" required="true" %>

<%@ attribute name="sortable" required="false" %>
<%@ attribute name="css" required="false" %>
<%@ attribute name="style" required="false" %>
<%@ attribute name="href" required="false" %>
<%@ attribute name="icon" required="false" %>
<%@ attribute name="tooltip" required="false" %>

<%-- Definition --%>

<spring:message code="${title}" var="intercionalizedTitle"/>


<display:column title="${intercionalizedTitle}"
                sortable="${sortable}" class="${css}" style="${style}">
    <div style="height: 100%;margin: 0px;padding: 0.7em 1em 0.7em 0.5em;${style}"
         onclick="relativeRedir('${href}');" title="${tooltip}">
        <i class="${icon}" style="text-align:center;"> <jstl:out value="${value}"/></i>
    </div>
</display:column>






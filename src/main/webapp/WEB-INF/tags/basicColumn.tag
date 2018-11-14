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

<%@ attribute name="property" required="true" %>
<%@ attribute name="title" required="true" %>

<%@ attribute name="sortable" required="false" %>
<%@ attribute name="format" required="false" %>
<%@ attribute name="css" required="false" %>
<%@ attribute name="href" required="false" %>
<%@ attribute name="icon" required="false" %>
<%@ attribute name="value" required="false" %>

<%-- Definition --%>

<spring:message code="${title}" var="intercionalizedTitle"/>


<jstl:if test="${icon != null}">
    <display:column title="${intercionalizedTitle}"
                    sortable="${sortable}" class="${css}"
                    style="height: 100%;padding: 0px;margin: 0px;background-color: #87CEEB !important;">
        <div style="height: 100%;padding: 0px;margin: 0px;background-color: #87CEEB !important;"
             onclick="relativeRedir('${href}');">
            <i class="${icon}"></i>
        </div>

    </display:column></jstl:if>
<jstl:if test="${icon == null}">
    <jstl:if test="${format != null}">
        <spring:message code="${format}.pattern" var="intercionalizedPattern"/>
        <fmt:formatDate value="${row.moment}" pattern="${intercionalizedPattern}" var="ff"/>
    </jstl:if>
    <display:column title="${intercionalizedTitle}"
                    sortable="${sortable}" class="${css}" format="${intercionalizedFormat}"
                    style="height: 100%;padding: 0px;margin: 0px;background-color: #87CEEB !important;">
        <div style="height: 100%;padding: 0px;margin: 0px;background-color: #87CEEB !important;"
             onclick="relativeRedir('${href}');">
            <i class="${icon}"><jstl:out value="${value}"/></i>
        </div>
    </display:column>
</jstl:if>
</div>






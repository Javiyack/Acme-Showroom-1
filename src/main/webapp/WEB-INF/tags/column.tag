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
<%@ attribute name="style" required="false" %>
<%@ attribute name="css" required="false" %>
<%@ attribute name="rowUrl" required="false" %>
<%@ attribute name="onclick" required="false" %>
<%@ attribute name="icon" required="false" %>

<%-- Definition --%>

<spring:message code="${title }" var="titleVar"/>

<jstl:if test="${rowUrl==null}">
    <jstl:if test="${format != null}">
        <spring:message code="${format}" var="formatVar"/>
    </jstl:if>
    <div onclick="${onclick}">
        <display:column property="${property }" title="${ titleVar}"
                        sortable="${sortable }" class="${css}" format="${formatVar}" style="${style}">
        </display:column>
    </div>
</jstl:if>
<jstl:if test="${rowUrl!=null}">
    <jstl:if test="${format != null}">
        <spring:message code="${format}" var="pattern"/>
        <fmt:formatDate value="${property}" pattern="${pattern}" var="property"/>
        <jstl:out value="${date}"/>
    </jstl:if>
    <display:column title="${titleVar}"
                    sortable="${sortable }" class="${css}" style="${style}">
        <div class="iButton" onclick="relativeRedir('${rowUrl}');">
            <jstl:if test="${icon!=null}"><i
                    class="${icon}"></i></jstl:if>
            <jstl:if test="${icon==null}"><jstl:out value="${property}"/></jstl:if>

        </div>
    </display:column>
</jstl:if>







<%--
 * list.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
        pageEncoding="ISO-8859-1" %>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="security"
          uri="http://www.springframework.org/security/tags" %>
<%@taglib prefix="display" uri="http://displaytag.sf.net" %>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<spring:message code="msg.delete.confirmation" var="deleteConfirmation"/>
<div class="seccion w3-light-grey ">
    <legend>
        <spring:message code="label.subscriptions"/>
    </legend>
    <div style="overflow-x:auto;">
        <jstl:if test="${!subscriptions.isEmpty()}">
            <form:form action="${requestUri}" method="GET">
                <spring:message code="pagination.size"/>
                <input hidden="true" name="word" value="${word}">
                <input hidden="true" name="actorId" value="${showroom.id}">
                <input type="number" name="pageSize" min="1" max="100"
                       value="${pageSize}">
                <input type="submit" value=">">
            </form:form>
        </jstl:if>

        <legend>
            <spring:message code="label.actors"/>
        </legend>

        <display:table pagesize="${pageSize}"
                       class="flat-table0 flat-table-1 w3-light-grey" name="subscribedActors"
                       requestURI="${requestUri}" id="row">
            <jstl:set var="url" value="chirp/actor/list.do?subscriptionId=${row.id}"/>
            <acme:urlColumn value="${row.userAccount.username}" title="actor.username"
                            sortable="true" href="${url}" css="iButton" tooltip="Ver sus chirps"/>
            <acme:urlColumn value="${row.surname}, ${row.name}" title="label.name"
                            sortable="true" href="${url}" css="iButton" tooltip="Ver sus chirps"/>
            <acme:urlColumn value=" " title="label.chirp.subscription" icon="fa fa-check w3-xlarge w3-text-orange"
                            style="width: 8em;" tooltip="Ver sus chirps" css="iButton"
                            href="subscription/actor/subscribe.do?actorId=${row.id}&redirectUrl=/subscription/actor/list.do"/>
        </display:table>
        <legend>
            <spring:message code="label.topics"/>
        </legend>

        <form action="subscription/actor/topic/subscribe.do" method="POST">
            <input list="topics" name="topic" placeholder="&#xf02b; Topic" class="font-awesome"
                   id="topic"/>
            <input type="submit" value="&plus;" class="font-awesome" style="">
        </form>
    </div>

    <datalist id="topics">
        <option></option>
        <jstl:forEach items="${topics}" var="topicItem">
            <option>${topicItem}</option>
        </jstl:forEach>
    </datalist>
    <jstl:set var="unsubscribeUrl" value="subscription/actor/topic/unsubscribe.do"/>


    <display:table pagesize="${pageSize}"
                   class="flat-table0 flat-table-1 w3-light-grey" name="topicSubscriptions"
                   requestURI="${requestUri}" id="row2">
        <spring:message code="label.topic" var="label"/>
        <display:column title="${label}">
            <form action="chirp/actor/topic/list.do" method="POST">
                <input type="hidden" name="topic" value="${row2}">
                <input type="submit" value="${row2}" class="font-awesome flat">
            </form>
        </display:column>
        <spring:message code="label.chirp.subscription" var="labelSubscription"/>
        <display:column title="${labelSubscription}" class="iButton" style="width: 8em;">
            <form action="subscription/actor/topic/unsubscribe.do" method="POST">
                <input type="hidden" name="topic" value="${row2}">
                <input type="submit" value="&#xf00c;" class="font-awesome flat w3-text-orange w3-xlarge">
            </form>
        </display:column>

    </display:table>
</div>
</div>
<br>

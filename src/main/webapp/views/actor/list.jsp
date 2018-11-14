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
<jstl:if test="${actors!=null}">
    <div class="seccion w3-light-grey ">

        <legend>
            <spring:message code="${legend}"/>
        </legend>
        <div style="overflow-x: auto;">
            <jstl:if test="${!actors.isEmpty()}">
                <form:form action="${requestUri}" method="GET">
                    <spring:message code="pagination.size"/>
                    <input hidden="true" name="word" value="${word}">
                    <input hidden="true" name="actorId" value="${showroom.id}">
                    <input type="number" name="pageSize" min="1" max="100"
                           value="${pageSize}">
                    <input type="submit" value=">">
                </form:form>
            </jstl:if>
            <display:table pagesize="${pageSize}"
                           class="flat-table0 flat-table-1 w3-light-grey" name="actors"
                           requestURI="${requestUri}" id="row">
                <jstl:set var="url" value="actor/actor/display.do?actorId=${row.id}"/>
                <acme:urlColumn value="${row.userAccount.username}"
                                title="label.user" sortable="true" href="${url}" css="iButton"/>
                <acme:urlColumn value="${row.surname}, ${row.name}"
                                title="label.name" sortable="true" href="${url}" css="iButton"/>
                <jstl:if test="${legend eq 'label.users'}">
                    <acme:urlColumn value="${row.birthdate}, ${row.genere}" title="label.birthdate"
                                    sortable="true" href="${url}" css="iButton"/>
                </jstl:if>
                <jstl:if
                        test="${legend eq 'label.agents' or legend eq 'label.auditors'}">
                    <acme:urlColumn value="company" title="label.company" href="${url}"
                                    sortable="true" css="iButton"/>
                </jstl:if>
                <jstl:if test="${legend eq 'label.actors'}">
                    <acme:urlColumn value="${row.userAccount.authorities[0]}" href="${url}"
                                    title="label.authority" sortable="true" css="iButton"/>
                </jstl:if>
                <jstl:if test="${!userIsFollowedMap[row]}">
                    <jstl:set var="icon" value="fa fa-check w3-xlarge w3-text-gray css-uncheck"/>
                </jstl:if>
                <jstl:if test="${userIsFollowedMap[row]}">
                    <jstl:set var="icon" value="fa fa-check w3-xlarge w3-text-orange"/>
                </jstl:if>
                <acme:urlColumn value="" title="label.chirp.subscription" sortable="true"
                                href="subscription/actor/subscribe.do?actorId=${row.id}&redirectUrl=/actor/actor/list.do"
                                icon="${icon}" css="iButton" style="width: 8em;"/>

            </display:table>
        </div>
    </div>
</jstl:if>
<jstl:if test="${userIsFollowedMap!=null && userIsFollowedMap.size!=0}">
    <spring:message code="date.pattern" var="datePattern"/>

    <div class="seccion w3-light-grey ">
        <legend>
            <spring:message code="label.chirp.subscription"/>
        </legend>
        <div style="overflow-x: auto;">


            <table class="flat-table0 flat-table-1 w3-light-grey">
                <tr>
                    <th><spring:message code="label.user"/></th>
                    <th><spring:message code="label.name"/></th>
                    <th style="width: 8em;"><spring:message code="label.chirp.subscription"/></th>
                </tr>

                <jstl:forEach items="${userIsFollowedMap}" var="entry">
                    <jstl:if test="${entry.value}">
                        <tr onclick="relativeRedir('actor/actor/display.do?actorId=${entry.key.id}')" class="iButton" style="text-decoration: blink;">
                            <td style="height: 100%;margin: 0px;padding: 0.7em 1em 0.7em 0.5em; text-decoration: none !important;"><i>${entry.key.userAccount.username}</i></td>
                            <td style="height: 100%;margin: 0px;padding: 0.7em 1em 0.7em 0.5em; text-decoration: none !important;"><i>${entry.key.surname},${entry.key.name}</i></td>
                            <td style="height: 100%;margin: 0px;padding: 0.7em 1em 0.7em 0.5em; text-decoration: none !important;width: 8em;"><a
                                    href="subscription/actor/subscribe.do?actorId=${entry.key.id}&redirectUrl=/actor/actor/list.do">
                                <i class="fa fa-check w3-xlarge w3-text-orange"></i>
                            </a></td>
                        </tr>
                    </jstl:if>
                </jstl:forEach>
            </table>
        </div>
    </div>
</jstl:if>
<jstl:out value="${userIsFollowerMap}"/>

<security:authorize access="isAuthenticated()">
    <jstl:if test="${followers!=null}">
        <div class="seccion w3-light-grey ">
            <legend>
                <spring:message code="label.followers"/>
            </legend>
            <div style="overflow-x: auto;">
                <jstl:if test="${!followers.isEmpty()}">
                    <form:form action="${requestUri}" method="GET">
                        <spring:message code="pagination.size"/>
                        <input hidden="true" name="word" value="${word}">
                        <input hidden="true" name="actorId" value="${showroom.id}">
                        <input type="number" name="pageSize" min="1" max="100"
                               value="${pageSize}">
                        <input type="submit" value=">">
                    </form:form>
                </jstl:if>
                <display:table pagesize="${pageSize}"
                               class="flat-table0 flat-table-1 w3-light-grey" name="followers"
                               requestURI="${requestUri}" id="row">
                    <jstl:set var="url" value="actor/actor/display.do?actorId=${row.id}"/>
                    <acme:urlColumn value="${row.userAccount.username}"
                                 title="label.user" sortable="true" href="${url}" css="iButton"/>
                    <acme:urlColumn value="${row.surname}, ${row.name}"
                                 title="label.name" sortable="true" href="${url}" css="iButton"/>
                    <acme:urlColumn value=" " title="label.view" sortable="true"
                                    href="${url}" icon="fa fa-eye w3-xlarge" style="width: 8em;" css="iButton"/>
                </display:table>

            </div>
        </div>
    </jstl:if>
</security:authorize>


<div class="seccion w3-light-grey">
    <div class="row">
        <div class="col-50">
            <security:authorize access="isAuthenticated()">
                <acme:button url="/subscription/actor/subscribers/list.do" text="label.subscribers"
                             css="formButton toLeft w3-padding"/>
                <acme:button url="/subscription/actor/list.do" text="label.subscriptions"
                             css="formButton toLeft w3-padding"/>
            </security:authorize>
            <acme:backButton text="actor.back" css="formButton toLeft w3-padding"/>
        </div>
    </div>
</div>

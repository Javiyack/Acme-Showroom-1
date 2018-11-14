<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="security"
          uri="http://www.springframework.org/security/tags" %>
<%@taglib prefix="display" uri="http://displaytag.sf.net" %>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>


<security:authorize access="isAuthenticated()">
    <jstl:set var="colom" value=", "/>
    <security:authentication property="principal.username" var="username"/>
    <security:authentication property="principal" var="logedActor"/>
    <security:authentication property="principal.authorities[0]"
                             var="permiso"/>
    <jstl:set var="rol" value="${fn:toLowerCase(permiso)}"/>

</security:authorize>


<jstl:set var="owns"
          value="${logedActor.id == chirp.actor.userAccount.id}"/>

<jstl:set var="readonly"
          value="${(display || !owns || !edition) && chirp.id != 0}"/>

<jstl:set value="chirp/user/list.do" var="backUrl"/>
<jstl:if test="${owns}">
    <jstl:set value="chirp/user/edit.do?chirpId=${chirp.id}" var="backUrl"/>
</jstl:if>
<div class="seccion w3-light-grey">
    <form action="subscription/actor/topic/subscribe.do" method="POST" id="topicSubs"></form>
    <form action="subscription/actor/topic/unsubscribe.do" method="POST" id="topicUnSubs"></form>

    <form:form action="${requestUri}" modelAttribute="chirp" id="mainForm">

        <form:hidden path="id" form="mainForm"/>
        <form:hidden path="version" form="mainForm"/>
        <div class="row">
            <div class="col-100">
                <legend><spring:message code="label.chirp"/>
                </legend>
                <jstl:if test="${readonly}">
                    <a href="actor/display.do?actorId=${chirp.actor.id}" class="fa fa-user font-awesome"> 
                        <jstl:out value="${chirp.actor.userAccount.username}"/>
                    </a>
                    <jstl:if test="${!subscribedToActor}">
                        <spring:message code="label.follow" var="actorTooltip"/>
                        <a href="subscription/actor/subscribe.do?actorId=${chirp.actor.id}
                        &redirectUrl=/chirp/actor/display.do?chirpId=${chirp.id}">
                            <i class="fa fa-heart-o font-awesome w3-text-gray w3-margin-right" title="${actorTooltip}"></i>
                        </a>
                    </jstl:if>
                    <jstl:if test="${subscribedToActor}">
                        <spring:message code="label.unfollow" var="actorTooltip"/>
                        <a href="subscription/actor/subscribe.do?actorId=${chirp.actor.id}
                        &redirectUrl=/chirp/actor/display.do?chirpId=${chirp.id}">
                            <i class="fa fa-heart font-awesome w3-text-red w3-margin-right" title="${actorTooltip}"></i>
                        </a>
                    </jstl:if>
                </jstl:if>
            </div>
        </div>
        <div class="row">
            <div class="col-25">
                <acme:textbox code="label.none" path="moment" form="mainForm"
                              readonly="true" css="flat"/>
                <jstl:if test="${readonly}">
                    <acme:textbox code="label.title" path="title"
                                  readonly="${readonly}" form="mainForm"/>
                    <jstl:if test="${chirp.topic!=''}">
                        <label class="fa fa-tag font-awesome"> <spring:message code="label.topic"/>
                            <jstl:if test="${!subscribedToTopic}">
                                <spring:message code="label.follow" var="topicTooltip"/>
                                <input form="topicSubs" type="hidden" name="topic" value="${chirp.topic}">
                                <input form="topicSubs" type="hidden" name="redirectUrl"
                                       value="/chirp/actor/display.do?chirpId=${chirp.id}">
                                <i class="fa fa-heart-o font-awesome w3-text-gray w3-margin-right"
                                   onclick="document.getElementById('topicSubs').submit();" title="${topicTooltip}">
                                </i>
                            </jstl:if>
                            <jstl:if test="${subscribedToTopic}">
                                <spring:message code="label.unfollow" var="topicTooltip"/>
                                <input form="topicUnSubs" type="hidden" name="topic" value="${chirp.topic}">
                                <input form="topicUnSubs" type="hidden" name="redirectUrl"
                                       value="/chirp/actor/display.do?chirpId=${chirp.id}">
                                <i class="fa fa-heart font-awesome w3-text-red w3-margin-right"
                                   onclick="document.getElementById('topicUnSubs').submit();" title="${topicTooltip}">
                                </i>
                            </jstl:if>
                        </label>
                    </jstl:if>
                    <input list="topics" type="text" name="topic" placeholder="&#xf02b; Topic"
                           class="font-awesome" id="topic" value="${chirp.topic}" path="topic" form="mainForm"
                           readonly/>

                </jstl:if>
                <jstl:if test="${!readonly}">
                    <spring:message code="label.title" var="placeholder"/>
                    <acme:textbox code="label.none" path="title" form="mainForm"
                                  readonly="${readonly}" placeholder="${placeholder}"/>
                    <input list="topics" type="text" name="topic" placeholder="&#xf02b; Topic"
                           class="font-awesome" id="topic" value="${chirp.topic}" path="topic" form="mainForm"/>

                </jstl:if>


                <form:errors path="topic" cssClass="error"/>

                <datalist id="topics">
                    <option></option>
                    <jstl:forEach items="${topics}" var="topicItem">
                        <option>${topicItem}</option>
                    </jstl:forEach>
                </datalist>
                <jstl:if test="${creation}">
                </jstl:if>
            </div>
            <div class="col-75">
                <spring:message code='label.description' var="descriptionLabel"/>
                <acme:textarea code="label.none" path="description"
                               readonly="${readonly}" css="formTextArea w3-text-black"
                               placeholder="${descriptionLabel}"/>
            </div>
        </div>
        <div class="row">
            <div class="col-100">
                <jstl:if test="${!readonly}">
                    <hr>
                    <acme:submit name="save" code="label.save"
                                 css="formButton toLeft"/>
                </jstl:if>
                <jstl:if test="${readonly}">
                    <hr>
                </jstl:if>
            </div>
        </div>
    </form:form>
</div>
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
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="JSTL" uri="http://java.sun.com/jsp/jstl/core" %>


<div class="form">
    <spring:message code="msg.validate.phone" var="msg"/>
    <form:form action="${requestUri}" modelAttribute="actorForm"
               onsubmit="javascript:return validatePhone('${msg}');"
               id="actorForm">
        <form:hidden path="id"/>
        <br>
        <!-- Datos -->
        <div class="seccion w3-light-grey">

            <div class="row">
                <!-- Datos Personales-->
                <div class="col-60">

                    <legend>
                        <spring:message code="actor.personal.data"/>
                    </legend>

                    <div class="row">
                        <div class="col-50">
                            <acme:textbox code="actor.name" path="name"
                                          readonly="${!edition}"/>
                        </div>
                        <div class="col-50">
                            <acme:textbox code="actor.surname" path="surname"
                                          readonly="${!edition}"/>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-50">
                            <acme:textbox code="actor.email" path="email"
                                          readonly="${!edition}"/>
                        </div>
                        <div class="col-50">
                            <acme:textbox code="label.phone" path="phone"
                                          readonly="${!edition}" id="phone"/>

                        </div>
                    </div>
                    <div class="row">
                        <div class="col-100">
                            <acme:textbox code="label.address" path="address"
                                          readonly="${!edition}"/>
                        </div>
                    </div>

                    <jstl:if
                            test="${actorAuthority=='AGENT' or actorAuthority=='AUDITOR' }">
                        <div class="row" id="companyData">
                            <div class="col-100">
                                <acme:textbox code="label.company" path="company" id="Company"/>
                            </div>
                        </div>
                    </jstl:if>
                    <jstl:if test="${actorAuthority eq 'USER'}">
                        <div id="userData">
                            <div class="row">
                                <div class="col-50">
                                    <acme:date code="label.birthdate" path="birthdate"
                                               readonly="${!edition}" id="birthdate"/>
                                </div>
                                <div class="col-50">
                                    <spring:message code="placeholder.genere" var="placeholder"/>
                                    <acme:textbox code="label.genere" path="genere"
                                                  readonly="${!edition}" id="genere" placeholder="${placeholder}"/>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-100">
                                    <jstl:if test="${edition}">
                                        <acme:textbox code="label.photo" path="photo"
                                                      id="photo"/>
                                    </jstl:if>
                                    <br>
                                    <jstl:if test="${!subscribedToActor and !edition}">
                                        <spring:message code="label.follow" var="actorTooltip"/>
                                        <a href="subscription/actor/subscribe.do?actorId=${actorForm.id}
                                        &redirectUrl=/actor/actor/display.do?actorId=${actorForm.id}">
                                            <i class="fa fa-heart-o font-awesome w3-text-gray w3-margin-right w3-xxlarge"
                                               title="${actorTooltip}"></i>
                                        </a>
                                    </jstl:if>

                                    <jstl:if test="${subscribedToActor and !edition}">
                                        <spring:message code="label.unfollow" var="actorTooltip"/>
                                        <a href="subscription/actor/subscribe.do?actorId=${actorForm.id}
                        &redirectUrl=/actor/actor/display.do?actorId=${actorForm.id}">
                                            <i class="fa fa-heart font-awesome w3-text-red w3-margin-right w3-xxlarge"
                                               title="${actorTooltip}"></i>
                                        </a>

                                    </jstl:if>
                                </div>
                            </div>
                        </div>
                    </jstl:if>

                </div>

                <jstl:if test="${display}">
                    <jstl:if test="${actorAuthority eq 'USER'}">
                        <div class="col-40">
                            <legend class="hideText">
                                <spring:message code="label.photo"/>
                            </legend>
                            <div class="w3-card-4" Style="margin-top: 2em;">
                                <img src="${actorForm.photo}" alt="${actorForm.photo}"
                                     Style="width: 100%">
                                <div class="w3-container">
                                    <h4>
                                        <b>${actorForm.userAccount.username}</b>
                                    </h4>
                                </div>
                            </div>
                        </div>
                    </jstl:if>
                </jstl:if>
                <!-- Datos de la cuenta-->
                <jstl:if test="${edition}">
                    <form:hidden path="account.authority"/>
                    <div class="col-40">
                        <jstl:if test="${creation}">
                            <legend>
                                <spring:message code="label.userAccount"/>
                            </legend>
                            <acme:textbox code="actor.username" path="username"/>
                            <acme:password code="label.userAccount.password" path="password"
                                           id="password" onkeyup="javascript: checkPassword();"/>
                            <acme:password code="label.userAccount.repeatPassword"
                                           path="confirmPassword" id="confirm_password"
                                           onkeyup="javascript: checkPassword();"/>


                        </jstl:if>
                        <jstl:if test="${!creation}">
                            <legend onclick="showUserAccount();"
                                    onmouseenter="overEffect(this);"
                                    onmouseleave="overEffect(this);" class="iButton">
                                <i class="fa fa-eye fa-fw"></i> 
                                <spring:message
                                        code="actor.authority.${actorForm.account.authority}"/>
                            </legend>
                            <jstl:if test="${actorAuthority eq 'USER'}">
                                <div id="photoCard">
                                    <div class="col-40">
                                        <div class="w3-card-4" Style="margin-top: 2em;">
                                            <img src="${actorForm.photo}" alt="${actorForm.photo}"
                                                 Style="width: 100%">
                                            <div class="w3-container">
                                                <h4>
                                                    <b>${actorForm.username}</b>
                                                </h4>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </jstl:if>
                            <div id="changePassword" style="display: none;">
                                <div class="col-33">
                                    <acme:textbox code="actor.username" path="username"
                                                  css="formInput"/>
                                    <acme:password code="label.userAccount.oldPassword"
                                                   path="password" css="formInput" id="password"
                                                   onkeyup="javascript: checkEdition();"/>
                                    <acme:password code="label.userAccount.newPassword"
                                                   path="newPassword" css="formInput" id="new_password"
                                                   onkeyup="javascript: checkEdition();"/>
                                    <acme:password code="label.userAccount.repeatPassword"
                                                   path="confirmPassword" id="confirm_password" css="formInput"
                                                   onkeyup="javascript: checkEdition();"/>
                                </div>
                            </div>
                        </jstl:if>
                    </div>
                </jstl:if>
            </div>
        </div>
        <jstl:if test="${display and actorAuthority eq 'USER'}">
            <!-- Showrooms -->
            <jstl:set value="${actorForm.id}" var="userId"/>
            <%@ include file="/views/showroom/list.jsp" %>
        </jstl:if>
        <security:authorize access="isAuthenticated()">
            <jstl:if test="${display}">
                <!-- Chirps -->
                <jstl:set value="${actorForm.id}" var="actorId"/>
                <%@ include file="/views/chirp/list.jsp" %>
            </jstl:if>
        </security:authorize>

        <jstl:if test="${edition}">
            <div class="seccion w3-light-grey">
                <security:authorize access="isAnonymous()">
                    <p class="terminos w3-text-purple">
                        <acme:checkBox code="term.registration.acept" path="agree"
                                       css="w3-check"/>
                    </p>
                    <p class="terminos">
                        (<a href="term/termsAndConditions.do"
                            class="w3-text-deep-purple w3-hover-text-aqua"><spring:message
                            code="term.terms"/></a> && <a href="term/cookies.do"
                                                          class="w3-text-deep-purple w3-hover-text-aqua"><spring:message
                            code="term.cookie"/></a>)
                    </p>
                </security:authorize>
                <div class="row">
                    <div class="col-50">
                        <input type="submit" name="save" id="save"
                               value='<spring:message code="actor.save"/>'
                               class="formButton toLeft"/>&nbsp; <input type="button"
                                                                        name="cancel"
                                                                        value='<spring:message code="actor.cancel" />'
                                                                        onclick="relativeRedir('/');"
                                                                        class="formButton toLeft"/>
                    </div>
                </div>
            </div>

        </jstl:if>

        <jstl:if test="${!edition}">
            <div class="seccion w3-light-grey">
                <div class="row">
                    <div class="col-50">
                        <acme:backButton text="actor.back" css="formButton toLeft"/>
                        <security:authorize access="isAuthenticated()">
                            <acme:button text="label.follow" css="formButton toLeft"
                                         url="subscription/actor/subscribe.do?actorId=${actorForm.id}&redirectUrl=/actor/actor/display.do?actorId=${actorForm.id}"/>

                        </security:authorize>
                    </div>
                </div>
            </div>
        </jstl:if>


    </form:form>

</div>
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
    <jstl:if test="${rol == 'user' || rol == 'responsable'}">
        <jstl:set var="accesscontrol" value="external"/>
    </jstl:if>
    <jstl:if test="${rol == 'technician' || rol == 'manager'}">
        <jstl:set var="accesscontrol" value="internal"/>
    </jstl:if>
</security:authorize>


<jstl:set var="owns"
          value="${logedActor.id == showroom.user.userAccount.id}"/>

<jstl:set var="readonly"
          value="${(display || !owns || !edition) && showroom.id != 0}"/>

<jstl:set value="showroom/list.do" var="backUrl"/>
<jstl:if test="${owns}">
    <jstl:set value="showroom/user/list.do" var="backUrl"/>
</jstl:if>

<div class="form" <jstl:if test='${!readonly}'>id="dropbox" ondragover="return false"
     ondrop="myDrop(event)"</jstl:if>>
    <form:form action="${requestUri}" modelAttribute="showroom">
        <form:hidden path="id"/>
        <form:hidden path="version"/>
        <form:hidden path="user"/>
        <div class="seccion w3-light-grey">
            <div class="row">
                <div class="col-100">
                    <legend>
                        <spring:message code="label.showroom"/>: <jstl:out value="${showroom.name}"/>
                        <jstl:if test="${showroom.id!=0}">
                            <spring:message var="msgSaveFirst" code="msg.save.first"/>
                            <jstl:set var="url" value="/comment/actor/create.do?objectId=${showroom.id}"/>
                            <spring:message code="label.new" var="newTitle"/>
                            <spring:message code="label.comment" var="itemTitle"/>
                            <a><i class="fa fa-commenting-o font-awesome w3-xxlarge toRight"
                                  onclick="showConditionalAlert('${msgSaveFirst}','${showroom.id}','${url}');"
                                  title="${newTitle} ${itemTitle}"></i></a>
                            <a href="comment/actor/list.do?objectId=${showroom.id}">
                                <i class="fa fa-comments-o font-awesome w3-xxlarge toRight w3-margin-right"></i></a>
                        </jstl:if>
                    </legend>
                    <div class="row">
                        <div class="col-75">
                            <acme:textbox code="label.name" path="name" readonly="${readonly}"/>
                            <acme:textarea code="label.description" path="description" readonly="${readonly}"
                                           css="formTextArea w3-text-black"/>
                            <jstl:if test="${!display}">
                                <acme:textbox code="label.logo.url" path="logo" readonly="${readonly}" id="fotosPath"/>
                            </jstl:if>
                        </div>
                        <div class="col-25">
                            <!-- Carrusel se fotos  -->
                            <div class="">
                                <spring:message code="label.logo"/>
                            </div>
                            <div class="carrusel" style="background-color: black;">
                                <div class="slideshow-container" id="carrusel">
                                    <jstl:if test="${showroom.logo!=null}">
                                        <div class="mySlides">
                                            <img src="${showroom.logo}" style="width: 100%">
                                        </div>
                                    </jstl:if>
                                </div>
                            </div>
                        </div>
                    </div>

                </div>
            </div>
            <hr>
            <div class="row">
                <div class="col-100">
                    <jstl:if test="${owns}">
                        <acme:submit name="save" code="label.save"/>
                        <jstl:if test="${item.id!=0}">
                            <acme:submit name="delete" code="label.delete"
                                         css="formButton toLeft"/>
                        </jstl:if>
                    </jstl:if>
                    <acme:button text="label.user" url="actor/display.do?actorId=${showroom.user.id}"/>

                </div>
            </div>
        </div>
    </form:form>
</div>
<jstl:set value="${showroom.id}" var="showroomId"/>

<%@ include file="/views/item/list.jsp" %>

<%@ include file="/views/comment/list.jsp" %>
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

<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>

<security:authorize access="isAuthenticated()">
    <jstl:set var="colom" value=", "/>
    <security:authentication property="principal.username" var="username"/>
    <security:authentication property="principal" var="logedAccount"/>
    <security:authentication property="principal.authorities[0]"
                             var="permiso"/>
    <jstl:set var="rol" value="${fn:toLowerCase(permiso)}"/>
</security:authorize>

<jstl:set var="readonly"
          value="${comment.id != 0}"/>


<div class="seccion w3-light-grey" <jstl:if test='${!readonly}'>id="dropbox" ondragover="return false"
     ondrop="myDrop(event)"</jstl:if>>
    <form:form action="${requestUri}" modelAttribute="comment">

        <form:hidden path="id"/>
        <form:hidden path="version"/>
        <form:hidden path="commentedObjectId"/>

        <div class="row">
            <div class="col-50">
                <label class="toRight">
                    <fmt:formatDate value="${comment.moment}" type="both" dateStyle="long" timeStyle="short"
                                    var="publicationDate"/>
                    <jstl:out value="${publicationDate}"/></label>
                <acme:textbox code="label.title" path="title"
                              readonly="${readonly}"/>
                <acme:textarea code="label.description" path="text"
                               css="formTextArea" readonly="${readonly}"/>
                <form:hidden path="rating" id="rating"/>

                <jstl:if test="${readonly}">
                    <label><spring:message code="label.stars"/> 
                        <span class="fa fa-star-o w3-xlarge w3-text-gray" id="star1"></span>
                        <span class="fa fa-star-o w3-xlarge w3-text-gray" id="star2"></span>
                        <span class="fa fa-star-o w3-xlarge w3-text-gray" id="star3"></span>
                    </label>
                </jstl:if>
                <jstl:if test="${!readonly}">
                    <label><spring:message code="label.stars"/> 
                        <span class="fa fa-star-o w3-xlarge w3-text-gray" id="star1"
                              onclick="setRating(this, 1, 3);"></span>
                        <span class="fa fa-star-o w3-xlarge w3-text-gray" id="star2"
                              onclick="setRating(this, 2, 3);"></span>
                        <span class="fa fa-star-o w3-xlarge w3-text-gray" id="star3"
                              onclick="setRating(this, 3, 3);"></span>
                    </label>
                </jstl:if>
                <form:errors path="rating" cssClass="error"/>
                <div class="row">
                    <div class="col-100">
                        <spring:message code="placeholder.coma.separated.url.links" var="urlPlacehoolder"/>
                        <spring:message code="title.coma.separated.url.links" var="urlsTitle"/>
                        <acme:textarea path="pictures" code="label.pictures" css="formTextArea collection w3-text-black"
                                       id="fotosPath" readonly="${readonly}" placeholder="${urlPlacehoolder}"
                                       title="${urlsTitle}"/>
                        <br/>
                        <div id="fotos" style="margin-bottom: 0.2em;">
                            <jstl:set var="count" scope="application" value="${0}"/>
                            <jstl:forEach items="${comment.pictures}" var="picture">
                                <jstl:set var="count" scope="application" value="${count + 1}"/>
                                <img src="${picture}" class="tableImg iButton" onclick="currentSlide(${count})">
                            </jstl:forEach>
                        </div>


                        <!-- Carrusel se fotos  -->
                        <div class="carrusel w3-black">
                            <div class="slideshow-container" id="carrusel">
                                <a class="prev" onclick="plusSlides(-1)">&#10094;</a>
                                <a class="next" onclick="plusSlides(1)">&#10095;</a>
                                <jstl:forEach items="${comment.pictures}" var="picture">
                                    <jstl:set var="count" scope="application" value="${count + 1}"/>
                                    <div class="mySlides">
                                        <a href="${picture}"><img src="${picture}"
                                                                  class="w3-border w3-card-4 marco iButton"
                                                                  style="width: 100%">
                                        </a>
                                    </div>
                                </jstl:forEach>
                            </div>
                        </div>
                        <br>
                        <jstl:set var="count" scope="application" value="${0}"/>
                        <div style="text-align: center" id="punto">
                            <jstl:forEach items="${comment.pictures}" var="picture">
                                <jstl:set var="count" scope="application" value="${count + 1}"/>
                                <span class="dot" onclick="currentSlide(${count})"></span>
                            </jstl:forEach>
                        </div>
                    </div>
                </div>
                <br>
            </div>


        </div>
        <div class="row">
            <div class="col-50"></div>

        </div>
        <div class="row">
            <div class="col-100">
                <jstl:if test="${!readonly}">
                    <hr>
                    <acme:submit name="save" code="label.save"
                                 css="formButton toLeft"/>
                </jstl:if>
            </div>

        </div>
    </form:form>

</div>
<script>
    $(function () {
        $(document).tooltip();
    });

    fillRating(${comment.rating});

</script>
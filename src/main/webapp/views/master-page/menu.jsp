<%--
 * header.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="security"
          uri="http://www.springframework.org/security/tags" %>

<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<security:authorize access="isAuthenticated()">
    <jstl:set var="colom" value=", "/>
    <security:authentication property="principal.username" var="username"/>
    <security:authentication property="principal.authorities[0]"
                             var="permiso"/>
    <jstl:set var="rol" value="${fn:toLowerCase(permiso)}"/>
</security:authorize>

<!-- Menu and banner usually + "$") -->
<!-- Sidebar/menu -->
<nav class="w3-sidebar w3-collapse sombra w3-flat-wet-asphalt"
     style="z-index: 3; width: 290px;" id="mySidebar">

    <br>
    <div class="w3-container w3-row">
        <div class="w3-col s4">
            <security:authorize access="isAuthenticated()">
                <a href="j_spring_security_logout" class=""><i
                        class="fa fa-sign-out w3-margin w3-xxlarge"></i></a>
            </security:authorize>
            <security:authorize access="isAnonymous()">
                <a href="security/login.do" class=""><i
                        class="fa fa-sign-in w3-margin w3-xxlarge"></i></a>
            </security:authorize>
        </div>
        <div class="w3-col s8 w3-bar">
            <span> <spring:message code="welcome.greeting.msg"/>${colom}<strong>${username}</strong></span><br>
            <security:authorize access="isAnonymous()">
                <!-- Trigger/Open The Modal -->
                <a id="myBtn" name="menuItem"><i class="fa fa-user-plus w3-bar-item w3-xlarge"></i></a>
                <!-- The Modal -->
                <div id="myModal" class="modal">
                    <!-- Modal content -->
                    <div class="modal-content seccion" style="width: 35%;">
                        <div class="modal-header w3-light-gray">
                            <span class="close fa fa-times font-awesome w3-text-black w3-hover-text-red"></span>
                            <h3 class="w3-text-dark-gray w3-padding"><spring:message code="label.authority"/></h3>
                        </div>
                        <div class="modal-body w3-light-gray">
                            <div class="w3-row">
                                <div class="w3-container w3-half">
                                    <i class="fa fa-user-plus w3-bar-item w3-large iButton
                                       w3-padding w3-margin-right font-awesome w3-text-dark-gray w3-hover-text-orange"
                                       onclick="relativeRedir('/user/create.do');">
                                        <spring:message code="actor.authority.USER"/>
                                    </i>
                                </div>
                                <div class="w3-container w3-half">
                                    <i class="fa fa-user-plus w3-bar-item w3-large iButton
                                       w3-padding w3-margin-right font-awesome w3-text-dark-gray w3-hover-text-orange"
                                       onclick="relativeRedir('/welcome/comingsoon.do');">
                                        <spring:message code="actor.authority.AGENT"/>
                                    </i>
                                </div>
                            </div>
                        </div>
                        <div class="modal-footer w3-light-gray">
                            <h3><br></h3>
                        </div>
                    </div>
                </div>
            </security:authorize>
            <security:authorize access="isAuthenticated()">
                <a href="${rol}/edit.do" name="menuItem"><i
                        class="fa fa-user w3-bar-item w3-xlarge"></i></a>
            </security:authorize>
        </div>
    </div>
    <hr>
    <div class="w3-bar-block button-bar" id="myDIV" style="padding-bottom: 60px">

        <a href="showroom/list.do" name="menuItem"
           class="w3-bar-item w3-button w3-padding w3-xlarge" id="showrooms">
            <i class="fa fa-shopping-bag fa-fw w3-margin-right"></i> <spring:message
                code="label.showrooms"/>
        </a>
        <a href="item/list.do" name="menuItem"
           class="w3-bar-item w3-button w3-padding w3-xlarge"id="items"> <i
                class="fa fa-diamond fa-fw w3-margin-right"></i> <spring:message
                code="label.items"/>
        </a>

        <security:authorize access="isAuthenticated()">
            <a href="actor/actor/list.do" name="menuItem"
               class="w3-bar-item w3-button w3-padding w3-xlarge" id="actors"> <i
                    class="fa fa-users fa-fw w3-margin-right"></i> <spring:message code="label.users"/>
            </a>
            <a href="subscription/actor/list.do" name="menuItem"
               class="w3-bar-item w3-button w3-padding w3-xlarge" id="subscriptions"> <i
                    class="fa fa-heart-o fa-fw w3-margin-right"></i> <spring:message
                    code="label.subscriptions"/>
            </a>
            <a id="chirps" class="w3-bar-item w3-button w3-padding w3-xlarge" onclick="myAccordionFunc('chirpsAcc')" name="menuItem">
                <i class="fa fa-bell-o fa-fw w3-margin-right"></i> <spring:message code="label.chirps"/>
            </a>
            <div id="chirpsAcc" class="w3-hide w3-card sombra" name="accordion">
                <a href="chirp/actor/list.do"
                   class="w3-bar-item w3-button w3-padding w3-large w3-light-gray"
                   style="padding-left: 2em !important;"> <i
                        class="fa fa-edit fa-fw w3-margin-right"></i> <spring:message code="label.my.chirps"/>
                </a>
                <a href="chirp/actor/stream.do"
                   class="w3-bar-item w3-button w3-padding w3-large w3-light-gray"
                   style="padding-left: 2em !important;"> <i
                        class="fa fa-eye fa-fw w3-margin-right"></i> <spring:message code="label.my.subscribed.chirps"/>
                </a>
            </div>
        </security:authorize>

        <security:authorize access="hasRole('USER')">
            <a id="requests" class="w3-bar-item w3-button w3-padding w3-xlarge" onclick="myAccordionFunc('requestsAcc')" name="menuItem">
                <i class="fa fa fa-bank fa-fw w3-margin-right"></i> <spring:message code="label.requests"/>
            </a>
            <div id="requestsAcc" class="w3-hide w3-card sombra" name="accordion">
                <a href="request/user/created/list.do"
                   class="w3-bar-item w3-button w3-padding w3-large w3-light-gray"
                   style="padding-left: 2em !important;"> <i
                        class="fa fa-shopping-cart fa-fw w3-margin-right"></i> <spring:message code="label.requests"/>
                </a>
                <a href="request/user/received/list.do"
                   class="w3-bar-item w3-button w3-padding w3-large w3-light-gray"
                   style="padding-left: 2em !important;"> <i
                        class="fas fa-piggy-bank fa-fw w3-margin-right"></i> <spring:message code="label.sales"/>
                </a>
            </div>

        </security:authorize>
        <security:authorize access="hasRole('ADMINISTRATOR')">
            <a href="dashboard/administrator/display.do" id="dashboard"
               class="w3-bar-item w3-button w3-padding w3-xlarge" name="menuItem"> <i
                    class="fa fa-dashboard fa-fw w3-margin-right"></i> <spring:message
                    code="master.page.dashboard"/>
            </a>
            <!-- Trigger/Open The Modal -->

            <a id="myBtn" class="w3-bar-item w3-button w3-padding w3-xlarge" name="menuItem"> <i
                    class="fa fa-user-plus fa-fw w3-margin-right"></i> <spring:message
                    code="label.new.actor"/>
            </a>
            <!-- The Modal -->
            <div id="myModal" class="modal">
                <!-- Modal content -->
                <div class="modal-content" style="width: 40%;">
                    <div class="modal-header w3-dark-gray">
                        <span class="close fa fa-times font-awesome w3-hover-text-red"></span>
                        <h3 class="w3-padding"><spring:message code="label.authority"/></h3>
                    </div>
                    <div class="modal-body w3-dark-gray w3-container">
                        <div class="w3-row">
                            <div class="w3-container w3-half">
                                <i class="fa fa-user-plus w3-bar-item w3-large iButton
                                       w3-padding w3-margin-left w3-margin-right font-awesome w3-hover-text-orange"
                                   onclick="relativeRedir('/admin/administrator/create.do');">
                                    <spring:message code="actor.authority.ADMINISTRATOR"/>
                                </i>
                            </div>
                            <div class="w3-container w3-half">
                                <i class="fa fa-user-plus w3-bar-item w3-large iButton
                                    w3-padding w3-margin-left font-awesome w3-hover-text-orange"
                                   onclick="relativeRedir('/welcome/comingsoon.do');">
                                    <spring:message code="actor.authority.AUDITOR"/>
                                </i>
                            </div>
                        </div>
                    </div>
                    <div class="modal-footer  w3-dark-gray">
                        <h3><br></h3>
                    </div>
                </div>
            </div>
        </security:authorize>
        <security:authorize access="hasRole('AGENT')">

        </security:authorize>
        <br> <br>
    </div>

</nav>


<!-- Overlay effect when opening sidebar on small screens -->
<div class="w3-overlay w3-hide-large w3-animate-opacity"
     onclick="w3_close()" style="cursor: pointer" title="close side menu"
     id="myOverlay"></div>

<script>
    var mySidebar = document.getElementById("mySidebar");

    //Get the DIV with overlay effect
    var overlayBg = document.getElementById("myOverlay");

    //Toggle between showing and hiding the sidebar, and add overlay effect
    function w3_open() {
        if (mySidebar.style.display === 'block') {
            mySidebar.style.display = 'none';
            overlayBg.style.display = "none";
        } else {
            mySidebar.style.display = 'block';
            overlayBg.style.display = "block";
        }
    }

    //Close the sidebar with the close button
    function w3_close() {
        mySidebar.style.display = "none";
        overlayBg.style.display = "none";
    }

    var btnContainer = document.getElementById("myDIV");

    //Get all buttons with class="btn" inside the container
    var btns = btnContainer.getElementsByClassName("btn");

    //Loop through the buttons and add the active class to the current/clicked button
    for (var i = 0; i < btns.length; i++) {
        btns[i].addEventListener("click", function () {
            var current = document.getElementsByClassName("active");
            current[0].className = current[0].className.replace(" active", "");
            this.className += " active";
        });
    }


    // Get the modal
    var modal = document.getElementById('myModal');

    // Get the button that opens the modal
    var btn = document.getElementById("myBtn");

    // Get the <span> element that closes the modal
    var span = document.getElementsByClassName("close")[0];

    // When the user clicks the button, open the modal
    btn.onclick = function () {
        modal.style.display = "block";
    };

    // When the user clicks on <span> (x), close the modal
    span.onclick = function () {
        modal.style.display = "none";
    };

    // When the user clicks anywhere outside of the modal, close it
    window.onclick = function (event) {
        if (event.target == modal) {
            modal.style.display = "none";
        }
    };

</script>
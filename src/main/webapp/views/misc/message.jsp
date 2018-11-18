<%--
 * panic.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 *
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 --%>

<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<br/>
<p>
	<div class="seccion w3-light-grey">
		<strong><spring:message code="${messageText}"/></strong>
	</div>
</p>

<p>
	<acme:button url="${goBackUrl}" text="label.back" css="formButton toLeft" />
</p>
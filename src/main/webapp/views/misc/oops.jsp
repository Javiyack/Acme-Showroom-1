
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<div>
	<h3>
		<spring:message code="${message}" />
	</h3>
</div>
<br />
<acme:cancel url="/" code="label.back" css="formButton toLeft" />

<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>



<div>
	<h3>
		<spring:message code="wrong.operation" />
	</h3>
	<img src="images/hacking.jpeg" class="hackingImg" alt="HACKING" />
</div>
<br />
<acme:cancel url="/" code="label.back" css="formButton toLeft" />
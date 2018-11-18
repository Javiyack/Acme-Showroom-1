
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<p>
	<spring:message code="panic.text" />
	<code>${name}</code>
	.
</p>

<h2>
	<spring:message code="panic.message" />
</h2>

<p style="font-family: 'Courier New'">${exception}</p>

<h2>
	<spring:message code="panic.stack.trace" />
</h2>

<p style="font-family: 'Courier New'">${stackTrace}</p>
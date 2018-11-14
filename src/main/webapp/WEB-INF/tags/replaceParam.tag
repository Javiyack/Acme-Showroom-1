<%@ tag pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ attribute name="name" required="true" type="java.lang.String"%>
<%@ attribute name="value" required="true" type="java.lang.String"%>

<jstl:url value="">

	<%-- 
      replaces or adds a param to a URL
      if $name in query then replace its value with $value. 
      copies existing 
    --%>

	<jstl:forEach items="${paramValues}" var="p">
		<jstl:choose>
			<jstl:when test="${p.key == name}">
				<jstl:param name="${name}" value="${value}" />
			</jstl:when>
			<jstl:otherwise>
				<jstl:forEach items="${p.value}" var="val">
					<jstl:param name="${p.key}" value="${val}" />
				</jstl:forEach>
			</jstl:otherwise>
		</jstl:choose>
	</jstl:forEach>

	<%-- if $name not in query, then add --%>
	<jstl:if test="${empty param[name] }">
		<jstl:param name="${name}" value="${value}" />
	</jstl:if>

</jstl:url>
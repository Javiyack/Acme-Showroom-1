<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.Map" %>
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

<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<jsp:useBean id="datos" class="java.util.HashMap"/>
<jsp:useBean id="beanClass" class="utilities.BeanClass" scope="request"/>
<div class="seccion w3-light-grey">
    <legend>
        <i class="fa fa-dashboard fa-fw w3-xxxlarge w3-padding w3-margin-right"></i>
        <spring:message code="dashboard.administrator"/>
    </legend>

    <%
        datos = new HashMap <String, String>();
    %>
    <jstl:forEach items="${dashboard}" var="board">
        <div class="w3-row-padding w3-margin-bottom">
            <h5 class="w3-padding">
                <label><spring:message code="dashboard.${board.key}"/></label>
            </h5>
            <jstl:forEach items="${board.value}" var="chart">
                <jstl:set var="chartData" value="["/>
                <jstl:forEach items="${chart.value}" var="data">
                    <jstl:if test="${chart.key != 'chirpsPerTopic'}">
                        <spring:message code="label.${data.key}" var="label"/>
                        <jstl:set var="chartData" value="${chartData}{y: ${data.value}, label: '${label}'},"/>
                    </jstl:if>
                    <jstl:if test="${chart.key eq 'chirpsPerTopic'}">
                        <jstl:set var="chartData" value="${chartData}{y: ${data.value}, label: '${data.key}'},"/>
                    </jstl:if>
                </jstl:forEach>
                <jstl:set var="chartData" value="${chartData}]"/>
                <jstl:set target="${beanClass}" property="value" value="${chartData}"/>
                <jstl:set target="${beanClass}" property="key" value="${chart.key}"/>
                <%
                    datos.put(beanClass.getKey(), beanClass.getValue());
                %>
                <div class="w3-container w3-quarter w3-margin-bottom">
                    <div class="w3-card-4" style="width:100%">
                        <div id="${chart.key}" style="height: 160px; width: 100%;"></div>
                        <div class="w3-container w3-center w3-padding">
                            <label><spring:message code="dashboard.${chart.key}"/></label>
                        </div>
                    </div>
                </div>
            </jstl:forEach>
        </div>
    </jstl:forEach>
</div>
<script>
    window.onload = function () {
        <%
        Iterator<Map.Entry<String, String>> entries = datos.entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry<String, String> entry = entries.next();
            %>
        newChart(<%=entry.getKey()%>, <%=entry.getValue()%>, "", "light2", "column");
        <%
       }%>

        function newChart(chartKey, dataModel, title, theme, type) {
            var chart = new CanvasJS.Chart(chartKey, {
                animationEnabled: true,
                theme: theme, // "light1", "light2", "dark1", "dark2"
                title: {
                    text: title
                },
                data: [{
                    type: type,// "column", "doughnut", "pie"
                    startAngle: 270,
                    yValueFormatString: "##0.00\"\"",
                    indexLabel: "{label} {y}",
                    dataPoints: dataModel
                }]
            });
            chart.render();
        }
    }


</script>


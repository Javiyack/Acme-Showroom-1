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

<jsp:useBean id="datos" class="java.util.HashMap"/>
<div class="seccion w3-light-grey">


    <form action="">
        <input type=checkbox name="myField" value="1" checked>
        <input type=checkbox name="myField" value="2" checked>
        <input type=checkbox name="myField" value="3" checked>
        <input type="submit" value="submit">
    </form>
    <legend>
        <i class="fa fa-dashboard fa-fw w3-xxxlarge w3-padding w3-margin-right"></i>
        <spring:message code="dashboard.administrator"/>

    </legend>

    <%
         out.println(request.getAttribute("dataList"));

    %>
    <div class="w3-row-padding w3-margin-bottom">
        <jstl:forEach items="${dashboard}" var="chart">
            <h3><jstl:out value="${chart.key}"/></h3>
            <jstl:forEach items="${chart.value}" var="data">
                <label><jstl:out value="${data.key}"/>: <jstl:out value="${data.value}"/></label><br>
            </jstl:forEach>
        </jstl:forEach>
        <jstl:set var="i" value="${1}"/>
        i: <jstl:out value="${i}"/>
        <jstl:set var="data1" value="valor"/>
        data1: <jstl:out value="${data1}"/>
    </div>
    <div class="w3-row-padding w3-margin-bottom">
        <jstl:forEach items="${dashboard}" var="chart">
            <jstl:set var="i" value="${1}"/>
            <jstl:set var="chartData" value="["/>
            <jstl:forEach items="${chart.value}" var="data">
                <spring:message code="label.${data.key}" var="label"/>
                <jstl:set var="datosChart" value="${chartData}{y: ${data.value}, label: '${label}'},"/>
                 </jstl:forEach>
            <jstl:set var="datosChart" value="${chartData}]"/>
            <% out.print("DatosChart: "+ request.getAttribute("datosChart"));%>
            <jstl:set var="chartKey" value="${chart.key}]"/>
            <input type="checkbox" name="habits" value="${chart.key}">
            <jstl:set var="i" value="${i + 1}"/>
            <div class="w3-container w3-half">
                <div class="w3-card-4" style="width:100%">
                    <div id="${chart.key}" style="height: 250px; width: 100%;"></div>
                    <div class="w3-container w3-center w3-padding">
                        <label><spring:message code="dashboard.${chart.key}"/></label>
                    </div>
                </div>
            </div>
        </jstl:forEach>
        <%
            String select[] = request.getParameterValues("habits");
            if (select != null && select.length != 0) {
                out.println("You have selected: ");
                for (int i = 0; i < select.length; i++) {
                    out.println(select[i]);
                }
            }
        %>
    </div>

</div>

<script>
    window.onload = function () {
        var jsArray = new Array(20);
        <%
        String keys[] = request.getParameterValues("chartK");
        String values[] = request.getParameterValues("chartD");
        if (values != null && values.length != 0
        && keys != null && keys.length != 0
        && keys.length==values.length) {
            System.out.println("You have selected: ");
            for (int i = 0; i < values.length; i++) {
                System.out.println(values[i]);
                %>
        newChart(<%=keys[i]%>, <%=values[i]%>, "", "light1", "column");
        <%
    }
}%>

        function newChart(chartKey, dataModel, title, theme, type) {
            var chart = new CanvasJS.Chart(chartKey, {
                animationEnabled: true,
                theme: theme, // "light1", "light2", "dark1", "dark2"
                title: {
                    text: title
                },
                data: [{
                    type: type,// "dognut"
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


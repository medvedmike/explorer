<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%--
    Created by Michael on 04.07.2014.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title><spring:message code="label.serviceName"/></title>
    <link rel="stylesheet" href="<c:url value="/resources/css/foundation.min.css"/>">
    <link rel="stylesheet" href="<c:url value="/resources/css/normalize.css"/>">
    <link rel="stylesheet" href="<c:url value="/resources/css/utils.css"/>">
</head>
<body>
    <div class="header row text-center" id="header">
        <tiles:insertAttribute name="header"/>
    </div>
    <div class="content row" id="content">
        <tiles:insertAttribute name="content"/>
    </div>
    <hr/>
    <div class="footer row" id="footer">
        <tiles:insertAttribute name="footer"/>
    </div>
    <script type="text/javascript" src="<c:url value="/resources/js/jquery-1.11.1.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/resources/js/foundation.min.js"/>"></script>
    <script type="text/javascript">
        $(document).foundation();
    </script>
</body>
</html>

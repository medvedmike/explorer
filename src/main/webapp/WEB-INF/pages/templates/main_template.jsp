<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<%--
    Created by Michael on 04.07.2014.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title><spring:message code="label.serviceName"/></title>
</head>
<body>
    <tiles:insertAttribute name="header"/>
    <tiles:insertAttribute name="content"/>
    <tiles:insertAttribute name="footer"/>
</body>
</html>

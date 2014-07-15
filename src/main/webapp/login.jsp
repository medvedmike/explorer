<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%--
    Created by Michael on 09.07.2014.
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
<div class="row">
    <div style="margin-top: 20px;" class="content panel columns large-centered large-8" id="content">
        <div class="row collapse">
            <%--не используется тег spring/tags/form по причине невозможности привязать проверку валидности данных из css фреймворка--%>

        </div>
    </div>
</div>
<script type="text/javascript" src="<c:url value="/resources/js/jquery-1.11.1.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/resources/js/foundation.min.js"/>"></script>
<script type="text/javascript">
    $(document).foundation();
</script>
</body>
</html>

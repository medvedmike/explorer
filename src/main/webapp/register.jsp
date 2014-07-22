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
    <div style="margin-top: 20px;" class="columns large-centered large-8" id="content">
        <div class="row collapse">
            <c:if test="${!empty errorCode}">
                <div data-alert class="alert-box alert"><spring:message code="${errorCode}"/></div>
            </c:if>
            <c:if test="${!empty errors}">
                <c:forEach items="${errors}" var="error">
                    <div data-alert class="alert-box alert"><spring:message code="${error.defaultMessage}"/></div>
                </c:forEach>
            </c:if>
        </div>
        <div class="row collapse">
            <%--не используется тег spring/tags/form по причине невозможности привязать проверку валидности данных из css фреймворка--%>
            <form data-abide method="post" name="user" action="<c:url value="/signup"/>">
            <fieldset class="">
                <legend><spring:message code="label.registerLegend"/></legend>
                <label for="username"><spring:message code="label.login"/> <small><spring:message code="label.required"/></small>
                    <input type="text" name="username" id="username" placeholder="<spring:message code="label.login"/>" required pattern="[a-zA-Z]+"
                    <c:if test="${!empty user}">value="${user.username}"</c:if>/>
                    <small class="error">
                        <spring:message code="inputError.login.length"/><br>
                        <spring:message code="inputError.login.letters"/>
                    </small>
                </label>
                <label for="password"><spring:message code="label.password"/> <small><spring:message code="label.required"/></small>
                    <input type="password" name="password" id="password" placeholder="<spring:message code="label.password"/>" required/>
                    <small class="error">
                        <spring:message code="inputError.password.length"/>
                    </small>
                </label>
                <label for="confirmPassword"><spring:message code="label.password.confirm"/> <small><spring:message code="label.required"/></small>
                    <input type="password" id="confirmPassword" placeholder="<spring:message code="label.password.confirm"/>" name="confirmPassword" required data-equalto="password">
                    <small class="error"><spring:message code="inputError.password.match"/></small>
                </label>
                <button type="submit"><spring:message code="label.signUp"/></button>
            </fieldset>
            </form>
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

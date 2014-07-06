<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
    Created by Michael on 06.07.2014.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="row">
    <h2 class="text-center"><spring:message code="error.title"/></h2>
</div>
<div class="row">
    <c:out value="${message}"/>
</div>

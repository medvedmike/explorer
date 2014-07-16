<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%--
    Created by Michael on 04.07.2014.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--<h1><spring:message code="label.serviceName"/></h1>--%>
<div class="fixed">
    <nav class="top-bar" data-topbar>
        <ul class="title-area">
            <li class="name">
                <h1><a href="<c:url value="/home"/>"><spring:message code="label.serviceName"/></a></h1>
            </li>
            <li class="toggle-topbar menu-icon"><a href="#"><span>Menu</span></a></li>
        </ul>

        <section class="top-bar-section">
            <ul class="left">
                <sec:authorize access="hasRole('ROLE_ADMIN')">
                    <li><a href="<c:url value="/server"/>">Server</a></li>
                </sec:authorize>
                <sec:authorize access="hasRole('ROLE_USER')">
                    <li><a href="<c:url value="/shared"/>">Shared</a></li>
                </sec:authorize>
            </ul>

            <ul class="right">
                <sec:authorize access="!isAuthenticated()">
                    <li class="has-form"><a class="button alert" href="<c:url value="/signup"/>"><spring:message code="label.signUp"/></a></li>
                </sec:authorize>
                <sec:authorize access="isAuthenticated()">
                    <li class="has-dropdown">
                        <a href="#">Settings</a>
                        <ul class="dropdown">
                            <li><a href="<c:url value="/logout"/>">Sign out</a></li>
                        </ul>
                    </li>
                </sec:authorize>
            </ul>
        </section>
    </nav>

</div>
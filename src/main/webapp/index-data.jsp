<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%--
    Created by Michael on 10.07.2014.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="row">
    <sec:authorize access="!isAuthenticated()">
        <div class="columns large-3">
            <form data-abide method="post" action="<c:url value="/j_spring_security_check"/>">
            <fieldset class="">
                <legend>Sign in</legend>
                <c:if test="${!empty param.error}">
                    <div class="alert-box alert">
                        Login or password are incorrect
                    </div>
                </c:if>
                <label for="username"><spring:message code="label.login"/>
                    <input type="text" name="j_username" id="username" placeholder="<spring:message code="label.login"/>" required/>
                </label>
                <label for="password"><spring:message code="label.password"/>
                    <input type="password" name="j_password" id="password" placeholder="<spring:message code="label.password"/>" required/>
                    <small class="error">
                        <spring:message code="inputError.password.length"/>
                    </small>
                </label>
                <input type="checkbox" name="_spring_security_remember_me" id="remember-me" value="Remember me">
                <label for="remember-me">Remember me</label>
                <button type="submit"><spring:message code="label.signIn"/></button>
            </fieldset>
            </form>
        </div>
    </sec:authorize>
    <div class="columns large-9">
        <p>
            Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin feugiat nec arcu id interdum. Nulla volutpat lectus ac dolor rutrum posuere. Praesent in est eros. Proin porttitor nunc leo, sed interdum enim vehicula a. Suspendisse potenti. Cras vel tortor nec orci sagittis tempus ut ac lectus. Proin a nisi eu ligula interdum porttitor nec nec erat. Vivamus convallis semper mollis. Duis lobortis viverra libero quis malesuada.
        </p>
        <p>
            Donec rutrum at orci scelerisque blandit. Sed rhoncus ultricies est, quis posuere nulla dictum vitae. Morbi orci enim, hendrerit ac vulputate in, congue ac sapien. Morbi fermentum, justo vitae dignissim convallis, mauris sem dapibus metus, vulputate molestie metus metus eu dui. Nulla orci sapien, tempus sit amet varius eget, sodales at leo. Mauris euismod risus vitae lorem placerat luctus. Donec vel felis nec ipsum pharetra lacinia id ac lorem.
        </p>
    </div>
</div>

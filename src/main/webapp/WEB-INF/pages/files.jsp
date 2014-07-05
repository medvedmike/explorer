<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
    Created by Michael on 04.07.2014.
--%>
<div class="breadcrumb" id="breadcrumb">
    ${directory.name}
</div>
<div class="directory-content row collapse" id="directory-content">

    <c:if test="${!directory.root}">
        <div class="element row collapse">
            <a class="large-10 columns" href="<c:url value="/files"><c:if test="${directory.parent != null}"><c:param name="directory" value="${directory.parent}"/></c:if></c:url>">
                ../
            </a>
        </div>
    </c:if>

    <c:forEach items="${content}" var="element">
        <c:choose>
            <c:when test="${element.directory}">
                <div class="element row collapse">
                    <a class="large-10 columns" href="<c:url value="/files"><c:param name="directory" value="${element.path}"/></c:url>">
                        <c:out value="${directory.root? element.path : element.name}"/>
                    </a>
                </div>
            </c:when>
            <c:otherwise>
                <div class="element row collapse">
                    <a class="large-10 columns" href="<c:url value="/file"><c:param name="name" value="${element.path}"/></c:url>">
                        <c:out value="${directory.root ? element.path : element.name}"/>
                    </a>
                    <span class="large-2 columns right-align">
                        <c:set var="size" value="${element.length()}"/>
                        <c:choose>
                            <c:when test="${size < 1000}">
                                <c:out value="${size}"/> B
                            </c:when>
                            <c:when test="${size < 1000000}">
                                <spring:eval expression="T(java.lang.String).format('%.3f', size / 1000.0)"/> KB
                            </c:when>
                            <c:when test="${size < 1000000000}">
                                <spring:eval expression="T(java.lang.String).format('%.3f', size / 1000000.0)"/> MB
                            </c:when>
                            <c:otherwise>
                                <spring:eval expression="T(java.lang.String).format('%.3f', size / 1000000000.0)"/> GB
                            </c:otherwise>
                        </c:choose>
                    </span>
                </div>
            </c:otherwise>
        </c:choose>
    </c:forEach>
</div>

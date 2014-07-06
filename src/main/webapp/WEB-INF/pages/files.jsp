<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
    Created by Michael on 04.07.2014.
--%>

<link rel="stylesheet" href="<c:url value="/resources/css/files.css"/>">

<c:if test="${!directory.root}">

    <nav class="breadcrumbs" id="breadcrumbs">
        <a class="current"><c:out value="${directory.name}"/></a>
        <c:if test="${directory.canWrite}"><a data-dropdown="add-dropdown">+</a></c:if>
    </nav>

    <div class="hor-separator-15"></div>

    <ul id="add-dropdown" class="f-dropdown" data-dropdown-content>
        <li><a data-reveal-id="upload-form">Upload file</a></li>
        <li><a data-reveal-id="mkdir-form">Create directory</a></li>
    </ul>

    <div data-reveal class="reveal-modal tiny" id="mkdir-form">
        <a class="close-reveal-modal">&#215;</a>
        <div class="hor-separator-20"></div>
        <div class="row collapse">
            <form data-abide>
                <div class="columns small-9">
                    <label>Directory name
                        <input type="text" required pattern="[a-zA-Z0-9а-яА-Я]+">
                    </label>
                    <small class="error">Folder name must contain only letters and numbers.</small>
                </div>
                <div class="columns small-3">
                    <input class="button" type="submit" value="Create">
                </div>
            </form>
        </div>
    </div>

    <div data-reveal class="reveal-modal tiny" id="upload-form">
        <a class="close-reveal-modal">&#215;</a>
        <div class="hor-separator-20"></div>
        <div class="row collapse">
            <form data-abide action="<c:url value="/file"/>" method="post" enctype="multipart/form-data">
                <div class="columns small-9">
                    <input style="display: none;" type="text" name="directory" value="${directory.path}">
                    <input type="file" name="file" required>
                    <small class="error">You must select a file.</small>
                </div>
                <div class="columns small-3">
                    <input class="button" type="submit" value="Upload">
                </div>
            </form>
        </div>
    </div>
</c:if>

<div class="directory-content <%--panel--%> row collapse" id="directory-content">

    <c:if test="${!directory.root}">
        <div class="element directory row collapse">
            <a class="large-10 columns" href="<c:url value="/files"><c:if test="${directory.parent != null}"><c:param name="directory" value="${directory.parent}"/></c:if></c:url>">
                ../
            </a>
        </div>
    </c:if>

    <c:forEach items="${content}" var="element">
        <c:choose>
            <c:when test="${element.directory}">
                <div class="element directory row collapse">
                    <a class="large-10 columns open" href="<c:url value="/files"><c:param name="directory" value="${element.path}"/></c:url>">
                        <c:out value="${directory.root? element.path : element.name}"/>
                    </a>
                </div>
            </c:when>
            <c:otherwise>
                <c:if test="${!directory.root}">
                    <div class="element file row collapse">
                        <a class="large-10 columns" href="<c:url value="/file"><c:param name="name" value="${element.path}"/></c:url>">
                            <c:out value="${directory.root ? element.path : element.name}"/>
                        </a>
                        <span class="large-2 columns right-align size">
                            <c:set var="size" value="${element.length()}"/>
                            <c:choose>
                                <c:when test="${size < 1000}">
                                    <c:out value="${size}"/> b
                                </c:when>
                                <c:when test="${size < 1000000}">
                                    <spring:eval expression="T(java.lang.String).format('%.3f', size / 1000.0)"/> Kb
                                </c:when>
                                <c:when test="${size < 1000000000}">
                                    <spring:eval expression="T(java.lang.String).format('%.3f', size / 1000000.0)"/> Mb
                                </c:when>
                                <c:otherwise>
                                    <spring:eval expression="T(java.lang.String).format('%.3f', size / 1000000000.0)"/> Gb
                                </c:otherwise>
                            </c:choose>
                        </span>
                    </div>
                </c:if>
            </c:otherwise>
        </c:choose>
    </c:forEach>
</div>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
    Created by Michael on 04.07.2014.
--%>

<c:if test="${!directory.root}">

    <nav class="breadcrumbs" id="breadcrumbs">
        <a class="current"><c:out value="${directory.name}"/></a>
        <a data-reveal-id="new-element">+</a>
    </nav>

    <div data-reveal class="reveal-modal tiny" id="new-element">
        <a class="close-reveal-modal">&#215;</a>
        <ul class="tabs" data-tab>
            <li class="tab-title active"><a href="#panel2-1">Upload file</a></li>
            <li class="tab-title"><a href="#panel2-2">Create directory</a></li>
        </ul>
        <div class="tabs-content">
            <div class="content" id="panel2-2">
                <form data-abide>
                    <div>
                        <label>Directory name
                            <input type="text" required pattern="[a-zA-Z0-9а-яА-Я]+">
                        </label>
                        <small class="error">Folder name must contain only letters and numbers.</small>
                    </div>
                    <input class="button" type="submit" value="Create">
                </form>
            </div>
            <div class="content active" id="panel2-1">
                <form data-abide action="<c:url value="/file"/>" method="post" enctype="multipart/form-data">
                    <div>
                        <input style="display: none;" type="text" name="directory" value="${directory.path}">
                        <input type="file" name="file" required>
                        <small class="error">You must select a file.</small>
                    </div>
                    <input class="button" type="submit" value="Upload">
                </form>
            </div>
        </div>
    </div>
</c:if>

<div class="directory-content panel row collapse" id="directory-content">

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
                <c:if test="${!directory.root}">
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
                </c:if>
            </c:otherwise>
        </c:choose>
    </c:forEach>
</div>

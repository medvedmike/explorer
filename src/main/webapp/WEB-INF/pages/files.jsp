<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
    Created by Michael on 04.07.2014.
--%>

<link rel="stylesheet" href="<c:url value="/resources/css/files.css"/>">

<div class="errors" id="errors">
    <c:if test="${!empty param.message}">
        <div data-alert class="alert-box medium success">
            <spring:message code="${param.message}"/>
            <a href="#" class="close">&times;</a>
        </div>
    </c:if>
    <c:if test="${!empty param.error}">
        <div data-alert class="alert-box medium alert">
            <spring:message code="${param.error}"/>
            <a href="#" class="close">&times;</a>
        </div>
    </c:if>
</div>

<c:if test="${!directory.root}">

    <div class="control-panel row">
        <div class="columns large-4 clearfix">
            <span class="header left"><c:out value="${directory.name}"/></span>
        </div>
        <div class="columns large-8 clearfix">
            <c:if test="${directory.writable}">
                <a class="button dropdown tiny right" data-dropdown="upload-dropdown"><spring:message code="label.upload"/></a>
                <a class="button dropdown tiny right" data-dropdown="mkdir-dropdown"><spring:message code="label.mkdir"/></a>
            </c:if>
            <a href="#" class="button dropdown tiny right" data-dropdown="share-dropdown"><spring:message code="label.share"/></a>
        </div>
    </div>

    <c:if test="${!empty directory.breadcrumbs}">
        <div class="row">
            <nav class="breadcrumbs columns large-12" id="breadcrumbs">
                <c:forEach items="${directory.breadcrumbs}" var="crumb">
                    <a href="<c:url value="/${url}"><c:param name="path" value="${crumb.path}"/></c:url>"><c:out value="${crumb.name}"/></a>
                </c:forEach>
            </nav>
        </div>
    </c:if>

    <div class="hor-separator-15"></div>

    <div class="f-dropdown content" data-dropdown-content id="share-dropdown">
        <form method="post" action="<c:url value="${url}/share"/> ">
            <input type="text" placeholder="username" name="username">
            <input type="text" name="path" value="<c:out value="${directory.path}"/>" style="display: none;">
            <input class="button" type="submit" value="Share">
        </form>
    </div>

    <div class="f-dropdown content" data-dropdown-content id="mkdir-dropdown">
        <form data-abide action="<c:url value="${url}/directory"/>" method="post">
            <input style="display: none;" type="text" name="path" value="${directory.path}">
            <input type="text" name="name" required placeholder="folder name">
            <small class="error"><spring:message code="inputError.folderName"/></small>
            <input class="button" type="submit" value="Create">
        </form>
    </div>

    <div class="f-dropdown content small" data-dropdown-content id="upload-dropdown">
        <form data-abide action="<c:url value="${url}/file"/>" method="post" enctype="multipart/form-data">
            <input style="display: none;" type="text" name="path" value="${directory.path}">
            <div class="row collapse">
                <div class="columns small-10">
                    <input type="text" id="filename" readonly required placeholder="file">
                    <%--<small class="error"><spring:message code="inputError.selectFile"/></small>--%>
                </div>
                <div class="columns small-2">
                    <a href="#" id="openfile" class="button postfix" onclick="openFile(this)">Open</a>
                </div>
            </div>
            <input type="file" name="file" id="file" required style="display: none;">
            <input class="button" type="submit" value="Upload">
        </form>
    </div>
</c:if>

<div class="directory-content row collapse" id="directory-content">

    <c:if test="${!directory.root}">
        <div class="element directory row collapse">
            <a class="large-10 columns" href="<c:url value="/${url}"><c:if test="${directory.parent != null}"><c:param name="path" value="${directory.parent}"/></c:if></c:url>">
                ../
            </a>
        </div>
    </c:if>

    <c:forEach items="${directory.children}" var="element">
        <c:choose>
            <c:when test="${!element.file}">
                <div class="element directory row collapse">
                    <a class="large-11 columns open" href="<c:url value="/${url}"><c:param name="path" value="${element.path}"/></c:url>">
                        <c:out value="${element.name}"/>
                    </a>
                    <c:if test="${element.writable and not directory.root}">
                        <a class="large-1 columns delete text-right" href="
                                <c:url value="/${url}/delete">
                                    <c:param name="path" value="${element.path}"/>
                                    <c:param name="current" value="${directory.path}"/>
                                </c:url>
                            ">
                            <spring:message code="label.delete"/>
                        </a>
                    </c:if>
                </div>
            </c:when>
            <c:otherwise>
                <c:if test="${!directory.root}">
                    <div class="element file row collapse">
                        <a class="large-9 columns" href="<c:url value="${url}/file"><c:param name="name" value="${element.path}"/></c:url>">
                            <c:out value="${element.name}"/>
                        </a>
                        <span class="large-2 columns text-right size">
                            <c:set var="size" value="${element.size}"/>
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
                        <c:if test="${element.writable and not directory.root}">
                            <a class="large-1 columns delete text-right" href="
                                <c:url value="/${url}/delete">
                                    <c:param name="path" value="${element.path}"/>
                                    <c:param name="current" value="${directory.path}"/>
                                </c:url>
                            ">
                                <spring:message code="label.delete"/>
                            </a>
                        </c:if>
                    </div>
                </c:if>
            </c:otherwise>
        </c:choose>
    </c:forEach>
</div>

<script type="text/javascript" src="/resources/js/files.js"></script>
<script type="text/javascript">
    var alertContainer = document.getElementById("errors");
    var alerts = alertContainer.getElementsByTagName("div");
    var ind = 0;
    for (ind; ind < alerts.length; ind++) {
        setTimeout(function(arg) {
            alertContainer.removeChild(arg);
        }, 7000, alerts[ind]);
    }

    var isOpenFileOpen = false;

    function openFile(btn) {
        if (!isOpenFileOpen) {
            isOpenFileOpen = true;
            setTimeout(function() { isOpenFileOpen = false; }, 100);
            var evt = document.createEvent("MouseEvents");
            evt.initEvent("click", true, false);
            var file = document.getElementById("file");
            file.onchange = function () {
                document.getElementById("filename").value = file.value;
            };
            file.dispatchEvent(evt);
        }
    }
</script>

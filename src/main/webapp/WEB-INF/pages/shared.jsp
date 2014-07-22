<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%--
    Created by Michael on 21.07.2014.
--%>
<div class="row">
    <table>
        <thead>
            <tr>
                <th width="70%"><spring:message code="label.path"/></th>
                <th width="20%"><spring:message code="label.username"/></th>
                <th width="10%"><spring:message code="label.settings"/></th>
            </tr>
        </thead>
        <tbody>
            <c:if test="${!empty paths}">
                <c:forEach items="${paths}" var="path">
                    <tr>
                        <td>
                            <c:out value="${path.path}"/>
                        </td>
                        <td>
                            <c:out value="${path.targetUser.username}"/>
                        </td>
                        <td>
                            <a class="link" href="<c:url value="/shared/my/${path.id}?del"/>"><spring:message code="label.delete"/></a>
                        </td>
                    </tr>
                </c:forEach>
            </c:if>
        </tbody>
    </table>
</div>
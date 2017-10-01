<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <jsp:include page="../includes/head.jsp">
        <jsp:param name="tittle" value="Add Group"/>
    </jsp:include>
</head>
<body>
<jsp:include page="../includes/headers/userHeader.jsp">
    <jsp:param name="pageName" value="remote"/>
</jsp:include>
<div class="col-xs-1"></div>
<div class="col-xs-8">
    <div class="container" style="margin-bottom: 30px; width:60%; max-width: 600px;">
        <div class="col-md-2"></div>
        <div class="col-md-8">
            <div class="row">
                <h1>Name: ${name}</h1>
            </div>
            <div class="row">
                <table class="table table-hover table-bordered">
                    <thead>
                    <th>Status</th>
                    <th>Change</th>
                    <th>Time</th>
                    </thead>
                    <tbody>
                    <c:forEach items="${revisions}" var="revision">
                        <tr <c:choose>
                            <c:when test="${revision.status.name == 'doc_deleted'}">
                                class="warning"
                            </c:when>
                            <c:when test="${revision.status.name == 'doc_added'}">
                                class="info"
                            </c:when>
                            <c:when test="${revision.status.name == 'deleted'}">
                                class="danger"
                            </c:when>
                            <c:when test="${revision.status.name == 'added'}">
                                class="success"
                            </c:when>
                        </c:choose>>

                            <td>
                                    ${revision.status}
                            </td>
                            <td>
                                    ${revision.change}
                            </td>
                            <td>
                                <fmt:formatDate value="${revision.dateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
<jsp:include page="../includes/footer.jsp"/>
<script type="text/javascript" src="http://www.google.com/jsapi"></script>
<script type="text/javascript" src="<c:url value="/resources/js/patternInputText.js"/>"></script>
</body>
</html>
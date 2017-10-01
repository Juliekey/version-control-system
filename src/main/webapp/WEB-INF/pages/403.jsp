<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <jsp:include page="includes/head.jsp">
        <jsp:param name="tittle" value="Error"/>
    </jsp:include>
</head>
<body>
<jsp:include page="includes/headers/emptyHeader.jsp"/>
<br>
<body>
<h1>HTTP Status 403 - Access is denied</h1>

<c:choose>
    <c:when test="${empty username}">
        <h2>You do not have permission to access this page!</h2>
    </c:when>
    <c:otherwise>
        <h2>Username : ${username} <br/>
            You do not have permission to access this page!</h2>
    </c:otherwise>
</c:choose>

</body>
</html>
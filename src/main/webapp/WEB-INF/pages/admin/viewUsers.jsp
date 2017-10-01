<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <jsp:include page="../includes/head.jsp">
        <jsp:param name="tittle" value="View Users"/>
    </jsp:include>
</head>
<body>
<jsp:include page="../includes/headers/adminHeader.jsp">
    <jsp:param name="pageName" value="viewUsers"/>
</jsp:include>
<br>
<div class="col-md-2"></div>
<div class="col-md-8">
    <div class="container" style="margin-bottom: 30px; width:60%; max-width: 600px;">
        <br>
        <table class="table table-hover table-striped">
            <thead>
            <th>Username</th>
            <th>Group Name</th>
            <th>Action</th>
            </thead>
            <tbody>
            <c:forEach items="${users}" var="user">
                <tr>
                    <td>"${user.name}"</td>
                    <td>"${user.group}"</td>
                    <c:if test="${user.name != currentUsername}">
                        <td><form  method="POST"  action="${pageContext.request.contextPath}/admin/deleteUser">
                            <input type="hidden" value="${user.id}" name="id"/>
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                            <button type="submit" class="btn btn-danger btn-block">Delete</button>
                        </form></td>
                    </c:if>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
<jsp:include page="../includes/footer.jsp"/>
<script>
    window.onload = function () {
        var result = "${result}";
        if (result != '') {
            sweetAlert(result);
        }
    };
</script>
</body>
</html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
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
<div class="col-xs-3"></div>
<div class="col-xs-6">
    <div class="container" style="margin-bottom: 30px; width:60%; max-width: 600px;">
        <div class="col-md-2"></div>
        <div class="col-md-8"></div>
        <div class="row">
            <form method="POST" action="${pageContext.request.contextPath}/remote/clone">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <br>
                <button type="submit" class="btn btn-primary btn-middle">Clone repository</button>
            </form>
            <h4>Warning: it can wipe off all changes in your current local repository.</h4>
        </div>
        <div class="col-md-2"></div>
        <div class="row">
            <h1>Main repository</h1>
        </div>
        <div class="row">
            <table class="table table-hover table-striped">
                <thead>
                <th>File Name</th>
                <th>Content</th>
                </thead>
                <tbody>
                <c:forEach items="${documents}" var="document">
                    <tr>
                        <td>
                            <a href="${pageContext.request.contextPath}/remote/viewHistory?docId=${document.docId}">${document.name}</a>
                        </td>
                        <td>
                                ${document.content}
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>
<script>
    window.onload = function () {
        var result = "${result}";
        if (result != '') {
            sweetAlert(result);
        }
    };
</script>
<jsp:include page="../includes/footer.jsp"/>
<script type="text/javascript" src="http://www.google.com/jsapi"></script>
<script type="text/javascript" src="<c:url value="/resources/js/patternInputText.js"/>"></script>
</body>
</html>
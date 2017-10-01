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
    <jsp:param name="pageName" value="local"/>
</jsp:include>
<div class="col-xs-2"></div>
<div class="col-xs-10">
    <div class="container" style="margin-bottom: 30px; width:60%; max-width: 600px;">
        <br>
        <c:choose>
            <c:when test="${errorMsg != null}">
                <div class="row">
                    <h1> ${errorMsg}</h1>
                </div>
            </c:when>
            <c:otherwise>
                <div class="row">
                    <form method="POST" action="${pageContext.request.contextPath}/local/add">
                        <br>
                        <input type="text" class="form-control" placeholder="File Name"
                               name="name" maxlength="25">
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        <br>
                        <button type="submit" class="btn btn-primary btn-middle">Add file</button>
                    </form>
                </div>
                <div class="row">
                    <form method="POST" action="${pageContext.request.contextPath}/local/commit">
                        <br>
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        <br>
                        <button type="submit" class="btn btn-success btn-middle">Commit</button>
                    </form>
                    <form method="POST" action="${pageContext.request.contextPath}/local/update">
                        <br>
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        <br>
                        <button type="submit" class="btn btn-warning btn-middle">Update</button>
                    </form>
                </div>
                <div class="col-md-2"></div>
                <div class="row">
                    <h1>My local repository</h1>
                </div>
                <div class="row">
                    <table class="table table-hover table-striped table-bordered">
                        <thead>
                        <th>Document Name</th>
                        <th>Content</th>
                        <th>Action</th>
                        </thead>
                        <tbody>
                        <c:forEach items="${documents}" var="document">
                            <tr>
                                <td>
                                    <a href="${pageContext.request.contextPath}/local/edit?docId=${document.docId}">${document.name}</a>
                                </td>
                                <td>
                                        ${document.content}
                                </td>
                                <td>
                                    <form method="POST" action="${pageContext.request.contextPath}/local/delete">
                                        <input type="hidden" class="form-control" value="${document.docId}"
                                               name="docId">
                                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                        <br>
                                        <button type="submit" class="btn btn-danger btn-small">Delete</button>
                                    </form>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </c:otherwise>
        </c:choose>
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

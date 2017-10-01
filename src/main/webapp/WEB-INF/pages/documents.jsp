<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <jsp:include page="includes/head.jsp">
        <jsp:param name="tittle" value="Add Group"/>
    </jsp:include>
</head>
<body>
<jsp:include page="includes/headers/userHeader.jsp">
    <jsp:param name="pageName" value="documents"/>
</jsp:include>
<div class="col-xs-3"></div>
<div class="col-xs-6">
    <div class="container" style="margin-bottom: 30px; width:60%; max-width: 600px;">
        <div class="col-md-2"></div>
        <div class="col-md-8"></div>
        <div class="row">
            <form method="POST" action="${pageContext.request.contextPath}/documents/createFolder">
                <input type="hidden" value="${currentPath}" name="path"/>
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <br>
                <input type="text" class="form-control" placeholder="Name"
                       name="dirName" maxlength="25" required>
                <button type="submit" class="btn btn-primary btn-middle">Create folder</button>
            </form>
        </div>
        <div class="row">
            <form method="POST" enctype="multipart/form-data"
                  action="${pageContext.request.contextPath}/documents/uploadDocument">
                <input type="hidden" value="${currentPath}" name="path"/>
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <input type="file" name="file"/><br/>
                <button type="submit" class="btn btn-primary btn-middle">Upload Document</button>
            </form>
        </div>
        <div class="col-md-2"></div>
        <div class="row">
            <h1>Current path: "${currentPath}"</h1>
        </div>
        <div class="row">
            <table class="table table-hover table-striped">
                <thead>
                <th>File Name</th>
                <th>Type</th>
                <c:if test="${role = \"MANAGER\"}">
                <th>Action</th>
                </c:if>
                </thead>
                <tbody>
                <c:forEach items="${files}" var="file">
                    <tr>
                        <td>
                            <c:choose>
                                <c:when test="${file.fileType eq \"Directory\"}">
                                    <a href="${pageContext.request.contextPath}/documents?path=${file.path}">${file.name}</a>
                                </c:when>
                                <c:when test="${file.fileType eq \"Txt\"}">
                                    <a href="${pageContext.request.contextPath}/documents/edit?path=${file.path}">${file.name}</a>
                                </c:when>
                                <c:otherwise>
                                    ${file.name}
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td>"${file.fileType}"</td>
                        <c:if test="${role = \"MANAGER\"}">
                        <td>
                            <form method="POST" action="${pageContext.request.contextPath}/documents/delete">
                                <input type="hidden" value="${file.path}" name="path"/>
                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                <button type="submit" class="btn btn-danger btn-block">Delete</button>
                            </form>
                        </td>
                        </c:if>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
        <div class="row" allign="center">
            <input type="button" class="btn btn-primary" value="Back" onclick="window.history.back()"/>

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
    <jsp:include page="includes/footer.jsp"/>
    <script type="text/javascript" src="http://www.google.com/jsapi"></script>
    <script type="text/javascript" src="<c:url value="/resources/js/patternInputText.js"/>"></script>
</body>
</html>
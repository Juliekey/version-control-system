<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <jsp:include page="../includes/head.jsp">
        <jsp:param name="tittle" value="Add Group"/>
    </jsp:include>
</head>
<body>
<jsp:include page="../includes/headers/adminHeader.jsp">
    <jsp:param name="pageName" value="addGroup"/>
</jsp:include>
<div class="col-xs-3"></div>
<div class="col-xs-6">
    <div class="container" style="margin-bottom: 30px; width:60%; max-width: 600px;">
        <form method="POST" modelAttribute="group" action="${pageContext.request.contextPath}/admin/saveGroup">
            <div class="login-form">
                <h1 style="text-align: center">Add Group</h1>

                <c:if test="${not empty error}">
                    <h2 style="text-align: center">${error}</h2>
                </c:if>
                <div class="form-group row">
                    <label class="col-sm-4 control-label">New group name</label>
                    <div class="col-sm-8">
                        <input type="text" class="form-control" placeholder="New Group"
                               name="name" id="new-group-name"
                               title="-   Must be only characters A-z 0-9" maxlength="25">
                        <i class="fa fa-user"></i>
                    </div>
                </div>
                <div class="form-group row">
                    <label class="col-sm-4 control-label">New group role</label>
                    <div class="col-sm-8">
                        <select name="role" class="form-control">
                            <c:forEach var="role" items="${roles}">
                                <option value="${role}">${role.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <div class="row">
                    <div class="col-sm-4 col-xs-0"></div>
                    <button type="submit" class="btn btn-primary col-sm-4 col-xs-12">Save</button>
                    <div class="col-sm-4 col-xs-0"></div>
                </div>

            </div>
        </form>
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
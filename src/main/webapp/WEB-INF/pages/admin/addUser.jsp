<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <jsp:include page="../includes/head.jsp">
        <jsp:param name="tittle" value="Add User"/>
    </jsp:include>
</head>
<body>
<jsp:include page="../includes/headers/adminHeader.jsp">
    <jsp:param name="pageName" value="addUser"/>
</jsp:include>
<div class="col-xs-3"></div>
<div class="col-xs-6">
    <div class="container" style="margin-bottom: 30px; width:60%; max-width: 600px;">
        <form method="POST" modelAttribute="user" action="${pageContext.request.contextPath}/admin/saveUser">
            <div class="login-form">
                <h1 style="text-align: center">Add User</h1>

                <c:if test="${not empty error}">
                    <h2 style="text-align: center">${error}</h2>
                </c:if>

                        <div class="form-group row">
                            <label class="col-sm-4 control-label">Name</label>
                            <div class="col-sm-8">
                                <input type="text" class="form-control" placeholder="Name"
                                       id="name" name="name"
                                       title="-   Must be only characters A-z 0-9" maxlength="25"
                                       required>
                                <i class="fa fa-user"></i>
                            </div>
                        </div>
                        <div class="form-group row">
                            <label class="col-sm-4 control-label">Password</label>
                            <div class="col-sm-8">
                                <input type="password" class="form-control" placeholder="Password "
                                       id="password" name="password"  maxlength="25"
                                       required>
                                <i class="fa fa-lock"></i>
                            </div>
                        </div>
                <div class="form-group-row" id="groups">
                    <label class="col-sm-4 control-label">Group </label>
                    <div class="col-sm-8">
                    <select name="groupId" class="form-control">
                        <c:forEach var="group" items="${groups}">
                            <option value="${group.id}">${group.name}</option>
                        </c:forEach>
                    </select>
                    </div>
                </div>
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <br>
                <div class="form-group-row">
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
<script type="text/javascript" src="<c:url value="/resources/js/patternInputText.js"/>"></script>
</body>
</html>
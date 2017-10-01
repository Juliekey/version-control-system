<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="includes/head.jsp">
        <jsp:param name="tittle" value="Login "/>
    </jsp:include>
</head>
<body>
<jsp:include page="includes/headers/emptyHeader.jsp"/>
<br>
<section class="container">
    <div class="col-md-4"></div>
    <div class="col-md-4">
        <div class="col-md-1"></div>
        <div class="col-md-10">
            <form name='loginForm'
                  action="<c:url value='/login' />" method='POST'>
                <div class="login-form">
                    <h1 style="text-align: center">Login</h1>
                    <br>
                    <div class="form-group ">
                        <input type="text" class="form-control" placeholder="Username " docId="UserName" name="name">
                        <i class="fa fa-user"></i>
                    </div>
                    <div class="form-group log-status">
                        <input type="password" class="form-control" placeholder="Password" docId="Password"
                               name="password">
                        <i class="fa fa-lock"></i>
                    </div>
                    <c:if test="${not empty error}">
                        <h4 style="text-align: center; color: #ce001b;">${error}</h4>
                    </c:if>
                    <br>
                    <div style="text-align: center">
                        <button type="submit" class="btn btn-primary">Login</button>
                    </div>
                    <input type="hidden" name="${_csrf.parameterName}"
                           value="${_csrf.token}" />
                </div>
            </form>
        </div>
        <div class="col-md-1"></div>
    </div>
    <div class="col-md-4"></div>
</section>
<jsp:include page="includes/footer.jsp"/>

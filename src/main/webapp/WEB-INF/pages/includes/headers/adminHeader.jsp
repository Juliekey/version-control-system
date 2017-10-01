<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<header class="navbar navbar-inverse navbar-fixed-top wet-asphalt" role="banner">
    <div class="container">
        <c:url value="/logout" var="logoutUrl" />
        <form id="logoutForm" action="${logoutUrl}" method="post" >
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
        </form>
        <script>
            function formSubmit() {
                document.getElementById("logoutForm").submit();
            }
        </script>
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
        </div>
        <div class="collapse navbar-collapse ">
            <ul class="nav navbar-nav ">
                <c:choose>
                    <c:when test="${param.pageName == 'addUser'}">
                        <li class="active"><a href="<%=request.getContextPath()%>/admin/addUser">Add User</a></li>
                    </c:when>
                    <c:otherwise>
                        <li><a href="<%=request.getContextPath()%>/admin/addUser">Add User</a></li>
                    </c:otherwise>
                </c:choose>
                <c:choose>
                    <c:when test="${param.pageName == 'addGroup'}">
                        <li class="active"><a href="<%=request.getContextPath()%>/admin/addGroup">Add Group</a></li>
                    </c:when>
                    <c:otherwise>
                        <li><a href="<%=request.getContextPath()%>/admin/addGroup">Add Group</a></li>
                    </c:otherwise>
                </c:choose>
                <c:choose>
                    <c:when test="${param.pageName == 'viewUsers'}">
                        <li class="active"><a
                                href="<%=request.getContextPath()%>/admin/viewUsers">View Users</a></li>
                    </c:when>
                    <c:otherwise>
                        <li><a href="<%=request.getContextPath()%>/admin/viewUsers">View Users</a></li>
                    </c:otherwise>
                </c:choose>
                <c:choose>
                    <c:when test="${param.pageName == 'viewGroups'}">
                        <li class="active"><a
                                href="<%=request.getContextPath()%>/admin/viewGroups">View Groups</a></li>
                    </c:when>
                    <c:otherwise>
                        <li><a href="<%=request.getContextPath()%>/admin/viewGroups">View Groups</a></li>
                    </c:otherwise>
                </c:choose>
                <li><a href="javascript:formSubmit()"> Logout</a></li>
            </ul>
        </div>
    </div>
</header>
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
    <jsp:param name="pageName" value="edit"/>
</jsp:include>
<div class="col-xs-3"></div>
<div class="col-xs-6">
    <div class="container" style="margin-bottom: 30px; width:60%; max-width: 600px;">
        <div class="col-md-2"></div>
        <div class="col-md-8"></div>
        <div class="row">
            <h1>Name: ${document.name}</h1>
        </div>
            <div class="row">
                <textarea type="textarea" form="editForm" cols="80" rows="25" name="newContent">${document.content}</textarea>
            </div>
            <br>
            <div class="row col-md-6"><input type="button" class="btn btn-primary btn-block" value="Cancel"
                                             onclick="window.history.back() "/>
            </div>
        <form method="POST" id="editForm" action="${pageContext.request.contextPath}/local/saveEdit">
        <div class="row col-md-6">
                <input type="hidden" value="${document.docId}" name="docId"/>
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <button type="submit" class="btn btn-success btn-block">Save</button>
            </div>
        </form>
    </div>

    <div class="row" align="center">
        <input type="button" class="btn btn-primary" value="Back" onclick="window.history.back() "/>
    </div>
</div>
</div>
<div class="col-xs-3"></div>
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

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="container" style="margin-bottom: 30px;">
    <div class="col-md-2 col-xs-1"></div>
    <div class="col-md-8 col-xs-10">
        <h1 style="text-align: center">Write to support</h1>
        <br>
        <div class="col-md-2 col-sm-1 col-xs-1"></div>
        <div class="col-md-7 col-sm-10 col-xs-10">
            <div class="row">
                <div class="col-md-6 col-sm-6 col-xs-7"><h4>Choose product:</h4></div>
                <div class="col-md-6 col-sm-6 col-xs-5">
                    <select docId="products" name="products" class="form-control" aria-required="true" style="float: right">
                        <c:forEach var="product" items="${productList}">
                            <option value="${product.docId}">${product.name}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <br>
            <textarea docId="description" name="description" cols="60" rows="15"></textarea>
            <br>
            <br>
            <div class="row">
                <div class="col-md-5 col-xs-5"></div>
                <div class="col-md-2 col-xs-2"><button type="submit" class="btn btn-primary" onclick="saveComplaint()">  Send  </button></div>
                <div class="col-md-5 col-xs-5"></div>
            </div>
        </div>
        <div class="col-md-2 col-sm-1 col-xs-1"></div>
    </div>
</div>



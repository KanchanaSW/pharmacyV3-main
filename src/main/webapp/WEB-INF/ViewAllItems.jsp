<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html xmlns:form="http://www.w3.org/1999/xhtml">
<head>
    <title>View All Items</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</head>
<body>
<div class="container">
    <h2>All Items</h2>

    <!--
    //////////////////////////////////////////////////////Search function
    <div class="searchFunction" style="align-items: center">
        <form:form action="${pageContext.request.contextPath}/SearchUserName" method="GET">
            <div class="searchBar">
                <input type="text" name ="searchName" placeholder="Search..">
            </div>
            <button class="searchButton" type="submit">Search</button>
        </form:form>
    </div>
    -->
    <table>
        <thead Class = "table-header">
        <tr>
            <th><span>ItemID</span></th>
            <th><span>ItemName</span></th>
            <th><span>Description</span></th>
            <th><span>Price</span></th>
            <th><span>Quantity</span></th>
            <th><span>Category name</span></th>
            <th><span>Update</span></th>
            <th><span>Add-to-cart</span></th>
            <th><span>Delete</span></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="info" items="${info}" varStatus="item">
            <tr class="lalign">
                <td Class="col col-1" style="width: 25%">${info.itemId}</td>
                <td Class="col col-2" style="width: 25%">${info.itemName}</td>
                <td Class="col col-3" style="width: 25%">${info.des}</td>
                <td Class="col col-1" style="width: 25%">${info.price}</td>
                <td Class="col col-1" style="width: 25%">${info.quantity}</td>
                <td Class="col col-2" style="width: 25%">${info.categoryName}</td>

                <td Class="col col-2" style="background-color: #ffffff"><button class="btn btn-outline-info" type="button" ><a href="${pageContext.request.contextPath}/UpdateItemPage/${info.itemId}">Edit</a></button></td>
                <td class="col col-2" style="color: white"><button class="btn btn-outline-primary" type="button"><a href="${pageContext.request.contextPath}/Add2CartViewItem/${info.itemId}">Add</a></button> </td>
                <td Class="col col-2" style="color: white"><button class="btn btn-outline-danger" type="button" onclick="myFunction()"><a href="${pageContext.request.contextPath}/DeleteItem/${info.itemId}">Delete</a></button></td>


            </tr>
        </c:forEach>

        </tbody>
    </table>
    <div class="Message">
        <div>${success}${error}</div>
    </div>
</div>
</body>
<script>
    function myFunction() {
        confirm("Are you sure you want to delete this user?");
    }
</script>
</html>
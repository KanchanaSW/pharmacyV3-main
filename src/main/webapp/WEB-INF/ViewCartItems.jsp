<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html xmlns:form="http://www.w3.org/1999/xhtml">
<head>
    <title>View All Cart Items</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

    <style>

    </style>
</head>
<body>
<div class="container">
    <h2>All Cart Items</h2>

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
            <th Class="col col-1"><span>CartId</span></th>
            <th Class="col col-2"><span>Is-Purchased</span></th>
            <th Class="col col-1"><span>Quantity</span></th>
            <th Class="col col-1"><span>Total</span></th>
            <th Class="col col-1"><span>Item</span></th>
            <th Class="col col-1"><span>User</span></th>
            <th Class="col col-1"><span>Update</span></th>
            <th Class="col col-1"><span>Delete</span></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="info" items="${info}">
            <tr class="align">
                <td Class="col col-1" style="width: 25%">${info.cartId}</td>
               <td Class="col col-2" style="width: 25%">${info.purchased}</td>
                <td Class="col col-1" style="width: 25%">${info.quantity}</td>
                <td Class="col col-1" style="width: 25%">${info.total}</td>
                <td Class="col col-1" style="width: 25%">${info.item.itemName}</td>
                <td Class="col col-1" style="width: 25%">${info.user.username}</td>

                <td Class="col col-1"><button class="btn btn-outline-info" type="button" ><a href="${pageContext.request.contextPath}/UpdateCartPage/${info.cartId}">Edit</a></button></td>
                <td Class="col col-1"><button class="btn btn-outline-danger" type="button" onclick="myFunction()" ><a href="${pageContext.request.contextPath}/DeleteCart/${info.cartId}">Delete</a></button></td>


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
        confirm("Are you sure you want to delete this cart item?");
    }
</script>
</html>
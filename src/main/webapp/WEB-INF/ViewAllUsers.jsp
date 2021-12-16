<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html xmlns:form="http://www.w3.org/1999/xhtml">
<head>
    <title>View All Users</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</head>
<body>
<div class="container">
    <h2>All Users</h2>

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
            <th><span>UserID</span></th>
            <th><span>UserName</span></th>
            <th><span>Email</span></th>
            <th><span>Contact Number</span></th>
            <th><span>Status</span></th>
            <th><span>Update status</span></th>
            <th><span>Delete</span></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="users" items="${users}" varStatus="item">
            <tr class="lalign">
                <td Class="col col-1" style="width: 25%">${users.userId}</td>
                <td Class="col col-2" style="width: 25%">${users.username}</td>
                <td Class="col col-2" style="width: 25%">${users.email}</td>
                <td Class="col col-2" style="width: 25%">${users.phone}</td>
                <td class="col col-2" style="width: 25%">${users.status}</td>

                <td Class="col col-2" style="width: 25%"><button type="button" class="btn btn-outline-primary" onclick="myFunction2()"><a href="${pageContext.request.contextPath}/UpdateStatus/${users.userId}">Update</a></button></td>

                <td Class="col col-2" style="width: 25%"><button type="button" class="btn btn-outline-danger" onclick="myFunction()"><a href="${pageContext.request.contextPath}/DeleteUser/${users.userId}">Delete</a></button></td>
            </tr>
        </c:forEach>

        </tbody>
    </table>

</div>
</body>
<script>
    function myFunction() {
        confirm("Are you sure you want to delete this user?");
    }
    function myFunction2() {
        confirm("Are you sure you want to update this user status to -verified-?");
    }
</script>
</html>
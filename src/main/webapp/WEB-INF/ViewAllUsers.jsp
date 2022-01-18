<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
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
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
    <!--Admin Navigation Bar-->
    <jsp:include page="Utills/Navbar.jsp" >
        <jsp:param name="page" value="home" />
    </jsp:include>

</head>
<body>
<div class="container">
    <h3 class="text-center p-2">USER-DETAILS</h3>

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
    <table class="table table-bordered" style="background: white">
        <thead Class="table-header">
        <tr class="table  bg-primary" style="color: white">
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
            <tr class="align">
                <td Class="col col-1" style="width: 25%">${users.userId}</td>
                <td Class="col col-2" style="width: 25%">${users.username}</td>
                <td Class="col col-2" style="width: 25%">${users.email}</td>
                <td Class="col col-2" style="width: 25%">${users.phone}</td>
                <td class="col col-2" style="width: 25%">${users.status}</td>
                <c:if test="${users.username == 'admin'}">
                    <td Class="col col-2" style="width: 25%"><button disabled type="button" class="btn btn-outline-primary">Update</button></td>
                    <td Class="col col-2" style="width: 25%"><button disabled type="button" class="btn btn-outline-danger" >BlackList</button></td>

                </c:if>
                <c:if test="${users.username != 'admin'}">
                    <td Class="col col-2" style="width: 25%"><button type="button" class="btn btn-outline-primary"
                        onclick="myFunction2(${users.userId})"><a>Update</a></button></td>

                    <td Class="col col-1" style="color: white">
                        <button class="btn btn-outline-danger" type="button" onclick="myFunction(${users.userId})"><a
                        >BlackList</a></button>
                    </td>
                </c:if>
            </tr>
        </c:forEach>

        </tbody>
    </table>

</div>
</body>
<script>
    function myFunction(id) {
        //confirm("Are you sure you want to delete this user?");
        Swal.fire({
            title: 'Are you sure?',
            text: "You won't be able to revert this!",
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: 'Yes, BlackList user!'
        }).then((result) => {
            if (result.isConfirmed) {
                location.href = "/DeleteUser/" + id;
                //  window.location = "ViewAllItems";
            }
        })
    }
    function myFunction2(id) {
       // confirm("Are you sure you want to update this user status to -verified-?");
        Swal.fire({
            title: 'Are you sure?',
            text: "Are you sure you want to update this user status to -verified-?",
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: 'Yes, update user!'
        }).then((result) => {
            if (result.isConfirmed) {
                location.href = "${pageContext.request.contextPath}/UpdateStatus/" + id;
                //  window.location = "ViewAllItems";
            }
        })
    }
</script>
</html>
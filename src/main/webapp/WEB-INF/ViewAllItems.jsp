<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
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
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
    <!--Admin Navigation Bar-->
    <jsp:include page="Utills/Navbar.jsp">
        <jsp:param name="page" value="home"/>
    </jsp:include>
</head>
<body>
<div class="container">

    <div class="card-header main-search">
        <form:form action="${pageContext.request.contextPath}/SearchItem" method="GET">
            <div class="row">
                <div class="col-10">
                    <input type="text" placeholder="Type Item Name.." class="form-control" name="searchItem">
                </div>
                <div class="col">
                    <button class="btn btn-primary search-btn" type="submit">🔍 Search</button>
                    <button class="btn btn-dark" href="/ViewAllItems">List</button>
                </div>
            </div>
        </form:form>
    </div>


    <table class="table table-bordered" style="background: white">
        <thead Class="table-header">
        <tr class="table bg-primary" style="color: white;">
            <th><span>ItemID</span></th>
            <th><span>ItemName</span></th>
            <th><span>Description</span></th>
            <th><span>Price</span></th>
            <th><span>Quantity</span></th>
            <th><span>Category name</span></th>
            <sec:authorize access="hasRole('ADMIN')">
                <th><span>Update</span></th>
                <th><span>Delete</span></th>
            </sec:authorize>

        </tr>
        </thead>
        <tbody>
        <c:forEach var="info" items="${info}" varStatus="item">
            <tr class="align">
                <td Class="col col-1" style="width: 25%">${info.itemId}</td>
                <td Class="col col-2" style="width: 25%">${info.itemName}</td>
                <td Class="col col-3" style="width: 25%">${info.des}</td>
                <td Class="col col-1" style="width: 25%">${info.price}</td>
                <td Class="col col-1" style="width: 25%">${info.quantity}</td>
                <td Class="col col-2" style="width: 25%">${info.categoryName}</td>
                <sec:authorize access="hasRole('ADMIN')">
                    <td Class="col col-1" style="background-color: #ffffff">
                        <button class="btn btn-outline-info" type="button"><a
                                href="${pageContext.request.contextPath}/UpdateItemPage/${info.itemId}">Edit</a>
                        </button>
                    </td>
                    <td Class="col col-1" style="color: white">
                        <button class="btn btn-outline-danger" type="button" onclick="myFunction(${info.itemId})"><a
                        >❌</a></button>
                    </td>
                </sec:authorize>


            </tr>
        </c:forEach>

        </tbody>
    </table>
    <%--    <div class="alert alert-success" role="alert">
            <strong>${success}${error}</strong> <a href="ViewAllItems" class="alert-close">x</a>
        </div>--%>


</div>
</body>
<script>
    function myFunction(id) {
        //  confirm("Are you sure you want to delete this user?");
        Swal.fire({
            title: 'Are you sure?',
            text: "You won't be able to revert this!",
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: 'Yes, delete it!'
        }).then((result) => {
            if (result.isConfirmed) {
                location.href = "/DeleteItem/" + id;
                //  window.location = "ViewAllItems";
            }
        })
    }


</script>
</html>
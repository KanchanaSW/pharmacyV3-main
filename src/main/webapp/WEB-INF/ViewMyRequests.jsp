<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html xmlns:form="http://www.w3.org/1999/xhtml">

<head>
    <title>My-Requests</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
    <!--nav Bar-->
    <jsp:include page="Utills/Navbar.jsp">
        <jsp:param name="page" value="home"/>
    </jsp:include>

</head>

<body>
<div class="container">
    <h3 class="text-center p-2" style="background: white">MY-REQUESTS</h3>

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
        <tr class="table-primary">
            <th><span>RequestID</span></th>
            <th><span>Item Name</span></th>
            <th><span>Note</span></th>
            <th><span>Status</span></th>
            <th><span>Delete</span></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="requests" items="${requests}" varStatus="item">
            <tr class="align">
                <td Class="col col-1" >${requests.itemRequestsId}</td>
                <td Class="col col-2" >${requests.newItemName}</td>
                <td Class="col col-3" >${requests.note}</td>
                <td Class="col col-1" >${requests.status}</td>

                <td Class="col col-1">
                    <button class="btn btn-outline-danger"
                            type="button" onclick="myFunction(${requests.itemRequestsId})"><a
                    >X</a>
                    </button>
                </td>


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
    function myFunction(id) {
        // confirm("Are you sure you want to delete this item Request?");
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
                location.href = "/DeleteMyRequest/" + id;
                //  window.location = "ViewAllItems";
            }
        })
    }
</script>

</html>
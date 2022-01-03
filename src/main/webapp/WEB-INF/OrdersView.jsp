<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html xmlns:form="http://www.w3.org/1999/xhtml">
<head>
    <title>View All Orders</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
    <!--nav Bar-->
    <jsp:include page="Utills/Navbar.jsp" >
        <jsp:param name="page" value="home" />
    </jsp:include>
    <style type="text/css">
        .container {
            width: auto;
            justify-content: center;
            align-items: center;
            padding: 1%;
        }
    </style>
</head>
<body>
<div class="container">
    <h2 class="text-center p-2" style="background: white">ALL-USER-ORDERS</h2>
    <table class="table table-bordered" style="background: white">
        <thead Class="table-header">
        <tr class="table-primary">
            <th Class="col col-1"><span>OrderId</span></th>
            <th Class="col col-1"><span>CustomerName</span></th>
            <th Class="col col-1"><span>City</span></th>
            <th Class="col col-2"><span>Address</span></th>
            <th Class="col col-1"><span>Date</span></th>
            <th Class="col col-1"><span>Total</span></th>
            <th Class="col col-1"><span>Status</span></th>
            <th Class="col col-1"><span>Pharmacist</span></th>
            <th Class="col col-1"><span>View</span></th>
            <th class="col col-1"><span>Pay</span></th>
            <th Class="col col-1"><span>Delete</span></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="info" items="${info}" varStatus="order">
            <tr class="align">
                <td Class="col col-1" style="width: 25%">${info.ordersDTOId}</td>
                <td Class="col col-1" style="width: 25%">${info.cusName}</td>
                <td Class="col col-1" style="width: 25%">${info.city}</td>
                <td Class="col col-2" style="text-overflow:ellipsis;overflow:hidden;">${info.address}</td>
                <td Class="col col-1" style="width: 25%">${info.date}</td>
                <td Class="col col-1" style="width: 25%">${info.total}</td>
                <td Class="col col-1" style="width: 25%">${info.status}</td>
                <td Class="col col-1" style="width: 25%">${info.user.username}</td>

                <td class="col col-1" style="color: white">
                    <button class="btn btn-outline-secondary" type="button"><a
                            href="${pageContext.request.contextPath}/ViewOrderedItems/${info.ordersDTOId}">View</a>
                    </button>
                </td>
                <td Class="col col-1" style="color: white">
                    <button class="btn btn-outline-primary" type="button" onclick="myFunction1(${info.ordersDTOId})"><a
                           >Pay</a>
                    </button>
                </td>
                <td Class="col col-1" style="color: white">
                    <button class="btn btn-outline-danger" type="button" onclick="myFunction(${info.ordersDTOId})"><a
                          ">❌</a>
                    </button>
                </td>


            </tr>
        </c:forEach>

        </tbody>
    </table>
</div>
</body>
<script>
    function myFunction() {
        //confirm("Are you sure you want to delete this order?");
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
                location.href = "/DeleteOrder/" + id;
                //  window.location = "ViewAllItems";
            }
        })
    }
    function myFunction1(orderId) {

        //confirm("Are you sure you want to pay this order?");
        Swal.fire({
            title: 'Are you sure?',
            text: "To proceed with payment!",
            icon: 'info',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: 'Yes, Proceed!'
        }).then((result) => {
            if (result.isConfirmed) {
                location.href = "/PayOrder/" + id;
                //  window.location = "ViewAllItems";
            }
        })
    }
</script>
</html>
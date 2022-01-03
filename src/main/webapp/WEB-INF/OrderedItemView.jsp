<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html xmlns:form="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    <title>Ordered Items</title>

    <!--nav Bar-->
    <jsp:include page="Utills/Navbar.jsp" >
        <jsp:param name="page" value="home" />
    </jsp:include>

    <style type="text/css">
        .container {
            width: auto;
            justify-content: center;
            align-items: center;
            margin-top: 20px;
            padding: 1%;
        }
        .row {
            padding-bottom: 5px;
        }
        .col1 {
            width: 35%;
            border: black 1px solid;
            padding: 2%;
        }
        .col2 {
            margin-left: 30px;
            border: black 1px solid;
            padding: 2%;
        }

    </style>
</head>
<body>
<div class="container">
    <h2 style="background: white">Ordered Items</h2>
    <table class="table table-bordered" style="background: white">
        <thead Class="table-header">
        <tr class="table-primary">
            <th Class="col col-1"><span>Id</span></th>
            <th Class="col col-1"><span>Date</span></th>
            <th Class="col col-1"><span>ItemName</span></th>
            <th Class="col col-1"><span>Quantity</span></th>
            <th Class="col col-1"><span>Total</span></th>

        </tr>
        </thead>
        <tbody>
        <c:forEach var="oItem" items="${oItem}">
            <tr class="align">
                <td Class="col col-1" style="width: 25%">${oItem.orderedItemDTOId}</td>
                <td Class="col col-1" style="width: 25%">${oItem.date}</td>
                <td Class="col col-1" style="width: 25%">${oItem.itemName}</td>
                <td Class="col col-1" style="width: 25%">${oItem.quantity}</td>
                <td Class="col col-1" style="width: 25%">${oItem.total}</td>

            </tr>
        </c:forEach>

        </tbody>
    </table>
</div>
</body>
</html>
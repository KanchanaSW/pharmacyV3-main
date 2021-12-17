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
    <title>Place Order</title>

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
<h2>Cart list</h2>
<table>
    <thead Class="table-header">
    <tr>
        <th Class="col col-1"><span>Item</span></th>
        <th Class="col col-1"><span>CartId</span></th>
         <th Class="col col-2"><span>Is-Purchased</span></th>
        <th Class="col col-1"><span>Quantity</span></th>
        <th Class="col col-1"><span>Total</span></th>
         <th Class="col col-1"><span>User</span></th>

    </tr>
    </thead>
    <tbody>
    <c:forEach var="cItem" items="${cItem}">
        <tr class="align">
            <td Class="col col-1" style="width: 25%">${cItem.item.itemName}</td>
                  <td Class="col col-1" style="width: 25%">${cItem.cartId}</td>
                   <td Class="col col-2" style="width: 25%">${cItem.purchased}</td>
            <td Class="col col-1" style="width: 25%">${cItem.quantity}</td>
            <td Class="col col-1" style="width: 25%">${cItem.total}</td>

            <td Class="col col-1" style="width: 25%">${cItem.user.username}</td>




        </tr>
    </c:forEach>

    </tbody>
</table>
</body>
</html>
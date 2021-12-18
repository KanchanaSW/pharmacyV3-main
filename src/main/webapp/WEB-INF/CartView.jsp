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
    <title>Cart View</title>
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
    <div class="row">
        <div class="col" style=" border: black 1px solid; margin-right: 10px; padding: 1%;">
            <h2>All Items</h2>
            <table>
                <thead Class="table-header">
                <tr>
                    <%-- <th><span>ItemID</span></th>--%>
                    <th class="col col-1"><span>ItemName</span></th>
                    <%--  <th><span>Description</span></th>--%>
                    <th class="col col-1"><span>Price</span></th>
                    <%-- <th><span>Quantity</span></th>--%>
                    <%--  <th><span>Category name</span></th>--%>
                    <th class="col col-1"><span>Cart</span></th>

                </tr>
                </thead>
                <tbody>
                <c:forEach var="info" items="${info}" varStatus="item">
                    <tr class="align">
                            <%--  <td Class="col col-1" style="width: 25%">${info.itemId}</td>--%>
                        <td Class="col col-1" style="width: 25%">${info.itemName}</td>
                            <%-- <td Class="col col-3" style="width: 25%">${info.des}</td>--%>
                        <td Class="col col-1" style="width: 25%">${info.price}</td>
                            <%--            <td Class="col col-1" style="width: 25%">${info.quantity}</td>
                                        <td Class="col col-2" style="width: 25%">${info.categoryName}</td>--%>

                        <td class="col col-1" style="color: white">
                            <button class="btn btn-outline-primary" type="button"><a
                                    href="${pageContext.request.contextPath}/Add2CartViewItem/${info.itemId}">Add</a>
                            </button>
                        </td>


                    </tr>
                </c:forEach>

                </tbody>
            </table>

        </div>
        <div class="col" style=" border: black 1px solid; margin-right: 10px; padding: 1%;">

            <h2>Cart list</h2>
            <table>
                <thead Class="table-header">
                <tr>
                    <th class="col col-1"><span></span></th>
                    <th Class="col col-1"><span>Item</span></th>
                    <%-- <th Class="col col-1"><span>CartId</span></th>
                     <th Class="col col-2"><span>Is-Purchased</span></th>--%>
                    <th Class="col col-1"><span>Quantity</span></th>
                    <th Class="col col-1"><span>Total</span></th>
                    <%-- <th Class="col col-1"><span>User</span></th>--%>
                    <th Class="col col-1"><span>Update</span></th>
                    <th Class="col col-1"><span>Delete</span></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="cItem" items="${cItem}">
                    <tr class="align">
                        <td class="col col-1"><input type="checkbox"
                                                     onclick="handleSelect(${cItem.cartId},${cItem.total})"></td>
                        <td Class="col col-1" style="width: 25%">${cItem.item.itemName}</td>
                        <td Class="col col-1" hidden style="width: 25%">${cItem.cartId}</td>
                            <%--      <td Class="col col-2" style="width: 25%">${cItem.purchased}</td>--%>
                        <td Class="col col-1" style="width: 25%">${cItem.quantity}</td>
                        <td Class="col col-1" style="width: 25%">${cItem.total}</td>
                            <%-- <td Class="col col-1" style="width: 25%">${cItem.user.username}</td>--%>
                        <td Class="col col-1">
                            <button class="btn btn-outline-info" type="button"><a
                                    href="${pageContext.request.contextPath}/UpdateCartPage/${cItem.cartId}">Edit</a>
                            </button>
                        </td>
                        <td Class="col col-1">
                            <button class="btn btn-outline-danger" type="button" onclick="myFunction()"><a
                                    href="${pageContext.request.contextPath}/DeleteCart/${cItem.cartId}">Delete</a>
                            </button>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
        <div class="col" style=" border: black 1px solid; padding: 1%;">
            <form:form action="${pageContext.request.contextPath}/PlaceNewOrder" method="POST"
                       modelAttribute="newOrder">
                <label>Customer Name:</label>
                <input type="text" onclick="calc();" class="form-control" id="cusName" name="cusName" value=""
                       c:required>
                <label>Customer Address:</label>
                <input type="text" class="form-control" id="address" name="address" value=""
                       c:required>
                <label>City:</label>
                <input type="text" class="form-control" id="city" name="city" value=""
                       c:required>
                <input type="text" class="form-control" id="status" name="status" value="pending"
                       hidden>
                <label>Total Amount: Rs.</label>
                <input type="number" step="any" class="form-control" id="total" name="total" value=""
                       c:required>
                <input type="hidden" name="list" id="list" value="">
                <br>
                <button type="submit" style="margin-left: 58%;" class="btn btn-primary">Place-Order</button>
            </form:form>
        </div>
    </div>

</div>
<script>
    const arr = [];
    const sumArr = [];

    function handleSelect(newId, amount) {
        if (!arr.includes(newId)) {          //checking weather array contain the id
            arr.push(newId);               //adding to array because value doesnt exists
            sumArr.push(amount);
        } else {
            arr.splice(arr.indexOf(newId), 1);  //deleting
            sumArr.splice(sumArr.indexOf(amount), 1);  //deleting
        }
        console.log(arr);
        copy();
    }

    function calc() {
        let result = 0;
        sumArr.forEach(number => {
            result += number;
        })
        document.getElementById("total").value = result;
    }


    function myFunction() {
        confirm("Are you sure you want to delete this cart item?");
    }

    function calcTotal(amount) {

    }

    function copy() {
        return document.getElementById("list").value = arr;
    }

    function copy1() {
        console.log(document.getElementById("list").value)
    }


</script>
</body>
</html>
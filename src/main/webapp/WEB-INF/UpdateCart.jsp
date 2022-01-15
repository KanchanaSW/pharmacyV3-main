<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html xmlns:form="http://www.w3.org/1999/xhtml" xmlns:c="">

<head>
    <meta charset="utf-8">
    <title>Update cart Info</title>

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
            border: 1px black solid;
            width: auto;
            justify-content: center;
            align-items: center;
            margin-top: 3%;
            padding: 1%;
            background: white;
        }

        .fp {
            margin-left: 45%;
        }

        .row {
            padding-bottom: 5px;
        }

        select {
            border: 1px solid silver;
            padding: 6px;
            border-radius: 4px;
        }
    </style>
</head>

<body>

<div class="container" style="width: 400px;">

    <button style="color: red;" type="button" class="close" aria-label="Close">
        <span aria-hidden="true">&times;</span>
    </button>

    <h4 class="card-title">Item Details</h4>
    <input id="itemId2" name="itemId2" value="${itemInfo.itemId}" type="hidden"/>
    <div class="row">
        <div class="col-sm">
            <label for="itemName">Item Name:</label>
            <input type="text" class="form-control" id="itemName" value="${itemInfo.itemName}"
                   readonly="true">
        </div>
    </div>

    <div class="row">
        <div class="col-sm">
            <label>Description:</label>
            <input type="text" readonly="true" class="form-control" c:name="des" value="${itemInfo.des}"
                   readonly="true">
        </div>
    </div>

    <div class="row">
        <div class="col-sm">
            <label>Quantity:</label>
            <input type="number" class="form-control" id="quantity2" value="${itemInfo.quantity}"
                   readonly="true">
        </div>

        <div class="col-sm">
            <label>Category:</label><br>
            <input type="text" readonly="true" class="form-control" id="categoryName" value="${itemInfo.categoryName}"
                   readonly="true">

        </div>

        <div class="col-sm">
            <label>Price: Rs.</label>
            <input type="number" step="any" class="form-control" id="price2" value="${itemInfo.price}"
                   readonly="true">
        </div>

    </div>
    <br>
    <h4 class="card-title">Add to cart</h4>
    <form:form action="/UpdateCart" method="POST" modelAttribute="updateInfo">
        <input id="itemId" name="itemId" value="${itemInfo.itemId}" type="hidden"/>
        <input id="cartId" name="cartId" value="${cartInfo.cartId}" type="hidden"/>
        <div class="row">
            <div class="col-sm">
                <label for>Quantity:</label>
                <input type="number" min=0 class="form-control" id="quantity" name="quantity" onchange="calc()"
                       value="${cartInfo.quantity}"
                       c:required>
            </div>
            <div class="col-sm">
                <label for="total">Price: Rs.</label>
                <input type="number" step="any" class="form-control" id="total" name="total" value="${cartInfo.total}"
                       readonly="true"
                       c:required>
            </div>

            <div class="col-sm">
                <button style="margin-top: 35px" type="submit" class="btn btn-primary btn-sm">Update cart
                </button>
            </div>
        </div>
        <div class="msg" id="success">
            ${success}${error}
        </div>
    </form:form>

</div>

</body>
<script type="text/javascript">
    console.log(${success});
    function calc() {
        //let q;let p2;let cal;let q2;
        let q2 = document.getElementById("quantity2").value;
        let q = document.getElementById("quantity").value;
        let qPrice = document.getElementById("price2").value;
        if (q === q2) {
           return alert("Inventory ran out.");
        } else {
           return document.getElementById("total").value = (q * qPrice);
        }

        //console.log(q * qPrice);
        var x = document.getElementById("success");
        if (x.innerText === "added success") {
            swal.fire({
                title: "Added Success!",
                text: "Check the Cart List!",
                type: "success",
                icon: 'success',
            }).then(function () {
                window.location = "/CartListAndItems";
            });
        } else if (x.innerText === "inventory") {
            swal.fire({
                title: "Inventory ran out!",
                text: "Added Failed! select less quantity",
                type: "error",
                icon: 'warning',
            }).then(function () {
                window.location = "/CartListAndItems";
            });
        }else if (x.innerText === "added failed"){
            swal.fire({
                title: "Error!",
                text: "Try Again ",
                type: "error",
                icon: 'warning',
            }).then(function () {
                window.location = "/CartListAndItems";
            });
        }

    }
</script>
</html>

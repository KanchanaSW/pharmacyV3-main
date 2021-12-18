<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html xmlns:form="http://www.w3.org/1999/xhtml" xmlns:c="">

<head>
    <meta charset="utf-8">
    <title>Add Item to Cart</title>

    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
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


    <input id="itemId" name="itemId" value="${itemInfo.itemId}" type="hidden"/>
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
            <label >Quantity:</label>
            <input type="number" class="form-control" id="quantity2" value="${itemInfo.quantity}"
                   readonly="true">
        </div>

        <div class="col-sm">
            <label>Category:</label><br>
            <input type="text" readonly="true" class="form-control" id="categoryName" value="${itemInfo.categoryName}"
                    readonly="true">

        </div>

        <div class="col-sm">
            <label >Price: Rs.</label>
            <input type="number" step="any" class="form-control" id="price2" value="${itemInfo.price}"
                   readonly="true">
        </div>

    </div>
    <br>
    <h4 class="card-title">Add to cart</h4>
    <form:form action="/Add2CartViewItem/${itemInfo.itemId}" method="POST" modelAttribute="cartItem">

        <div class="row">
            <div class="col-sm">
                <label for>Quantity:</label>
                <input type="number" c:min=0 class="form-control" id="quantity" name="quantity" onchange="calc()" value=""
                       c:required>
            </div>
            <div class="col-sm">
                <label for="total">Price: Rs.</label>
                <input type="number" step="any" class="form-control" id="total" name="total" value=""
                       c:required>
            </div>

            <div class="col-sm">
                <button style="margin-top: 35px"  type="submit" class="btn btn-primary btn-sm">Add to cart
                </button>
            </div>
        </div>
        <p class="Message">${success}${error}</p>
    </form:form>

</div>

</body>
<script type="text/javascript">


    function calc() {
        let q;let p2;let cal;let q2;
        q2=document.getElementById("quantity2").value;
        q= document.getElementById("quantity").value;
        p2=document.getElementById("price2").value;
        if (q === q2){
            alert("Inventory ran out.");
        }else{
            cal=q*p2;
            document.getElementById("total").value=cal;
        }

       // console.log(cal);
    }
</script>
</html>

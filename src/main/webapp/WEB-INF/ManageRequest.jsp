<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html xmlns:form="http://www.w3.org/1999/xhtml" xmlns:c="">

<head>
    <meta charset="utf-8">
    <title>Add Item Item</title>

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

    <button style="color: red;" type="button" class="close" aria-label="Close" onclick="funClose();">
        <span aria-hidden="true">&times;</span>
    </button>

    <h4 class="card-title">Add Item</h4>
    <form:form action="/ManageRequest" method="POST" modelAttribute="ManageRequest">
        <input id="itemId" name="itemId" value="${ir.itemRequestsId}" type="hidden"/>
        <div class="row">
            <div class="col-sm">
                <label for="itemName">Item Name:</label>
                <input type="text" class="form-control" id="itemName" value="${ir.newItemName}" name="itemName">
                <div class="itemNameError">
                    <div>${nError}</div>
                </div>
            </div>
        </div>

        <div class="row">
            <div class="col-sm">
                <label for="des">Description:</label>
                <textarea class="form-control z-depth-1" rows="3" cols="47" name="des" id="des">${ir.note}</textarea>
            </div>
        </div>

        <div class="row">
            <div class="col-sm">
                <label for="quantity">Quantity:</label>
                <input type="number" class="form-control" id="quantity" placeholder="500" value="" name="quantity"
                       required>
                <div class="quantityError">
                    <div>${qError}</div>
                </div>
            </div>

            <div class="col-sm">
                <label>Category:</label><br>
                <!-- <input type="text" readonly="true" class="form-control" id="categoryName" value="${itemInfo.categoryName}" name="categoryName">-->

                <select name="categoryName" id="categoryName">
                    <c:forEach var="cate" items="${cate}" varStatus="item">
                        <option value="${cate.category}">
                            ${cate.category}
                        </option>
                    </c:forEach>
                </select>
            </div>

            <div class="col-sm">
                <label for="price">Price:</label>
                <input type="number" step="any" placeholder="35.00" class="form-control" id="price" value=""
                       name="price"
                       required>
                <div class="priceError">
                    <div>${pError}</div>
                </div>
            </div>

        </div>

        <br>
        <div class="row">
            <div class="col-sm">
                <button style="width: 370px;" type="submit" value="ManageRequest" class="btn btn-primary">Add item
                </button>
            </div>
        </div>
        <div class="msg" id="status">
            ${success}${error}
        </div>
    </form:form>

</div>
<script>
    function funClose() {
        //  window.location.href = "/ViewAllItems";
        Swal.fire({
            title: 'Are you sure?',
            text: "You won't be able to revert this!",
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: 'Yes, cancel it!'
        }).then((result) => {
            if (result.isConfirmed) {
                location.href = "/AdminHome";
                //  window.location = "ViewAllItems";
            }
        })
    }

    //return feedback
    var x = document.getElementById("status");
    if (x.innerText === "Successfully Added") {
        swal.fire({
            title: "Added Success!",
            text: "Check request List!",
            type: "success",
            icon: 'success',
        }).then(function () {
            window.location = "AllRequests";
        });
    } else if (x.innerText === "item already found") {
        swal.fire({
            title: "Item Already found!",
            text: "Added Failed!",
            type: "error",
            icon: 'warning',
        }).then(function () {
            window.location = "AllRequests";
        });
    }else if (x.innerText === "Failed add"){
        swal.fire({
            title: "Error!",
            text: "Try Again ",
            type: "error",
            icon: 'warning',
        }).then(function () {
            window.location = "AllRequests";
        });
    }
</script>
</body>

</html>

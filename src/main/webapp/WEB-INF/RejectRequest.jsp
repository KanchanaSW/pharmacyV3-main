<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html xmlns:form="http://www.w3.org/1999/xhtml">

<head>
    <meta charset="utf-8">
    <title>Reject Requested Item </title>

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

    <button style="color: red;" type="button" class="close" aria-label="Close" onclick="funClose();">
        <span aria-hidden="true">&times;</span>
    </button>

    <h4 class="card-title">Reject Requested Item</h4>
    <form:form action="${pageContext.request.contextPath}/RejectRequest" method="POST" modelAttribute="RejectRequest">
        <input id="itemRequestsId" name="itemRequestsId" value="${ir.itemRequestsId}" type="hidden"/>
        <div class="row">
            <div class="col-sm">
                <label for="newItemName">Requested Item Name:</label>
                <input type="text" class="form-control" id="newItemName"  name="newItemName" value="${ir.newItemName}"
                       disabled>
                </div>

        </div>

        <div class="row">
            <div class="col-sm">
                <label for="note">Brief Note:</label>
                <textarea class="form-control z-depth-1" rows="4" cols="47" name="note" id="note"
                          placeholder="Enter brief note for rejecting..."></textarea>
            </div>

        </div>


        <br>
        <div class="row">
            <div class="col-sm">
                <button style="width: 370px;" type="submit" value="RejectRequest" class="btn btn-primary">Confirm</button>
            </div>
        </div>
        <div class="msg" id="status">
                ${success}${error}
        </div>
    </form:form>

</div>
<script>
    function funClose() {
        //   window.location.href = "/ViewAllItems";
        Swal.fire({
            title: 'Are you sure?',
            text: "You won't be able to update this request!",
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: 'Yes, cancel it!'
        }).then((result) => {
            if (result.isConfirmed) {
                location.href = "/AllRequests" ;
                //  window.location = "ViewAllItems";
            }
        })
    }
    //return feedback data
    var x = document.getElementById("status");
    if (x.innerText === "ItemRequest Was Successfully rejected") {
        swal.fire({
            title: "Rejected Success!",
            text: "Check your request List!",
            type: "success",
            icon: 'success',
        }).then(function () {
            window.location = "AllRequests";
        });
    } else if (x.innerText === "Failed To reject Item request") {
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
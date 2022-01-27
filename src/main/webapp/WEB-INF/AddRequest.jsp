<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html xmlns:form="http://www.w3.org/1999/xhtml">

<head>
    <meta charset="utf-8">
    <title>Request Item</title>

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

    <h4 class="card-title">Request New Item</h4>
    <form:form action="${pageContext.request.contextPath}/AddRequest" method="POST" modelAttribute="AddRequest">

        <div class="row">
            <div class="col-sm">
                <label for="newItemName">Requested Item Name:</label>
                <input type="text" class="form-control" id="newItemName" placeholder="Amoxicillin" name="newItemName"
                       required min=3 maxlength=13>
                <div class="newItemNameError">
                    <div>${nIError}</div>
                </div>

            </div>

        </div>

        <div class="row">
            <div class="col-sm">
                <label for="note">Brief Note:</label>
                <textarea class="form-control z-depth-1" rows="4" cols="47" name="note" id="note"
                          maxlength = "180"
                          placeholder="Enter brief note about the item here..."></textarea>
            </div>

        </div>


        <br>
        <div class="row">
            <div class="col-sm">
                <button style="width: 370px;" type="submit" value="RequestNewItem" class="btn btn-primary">Confirm</button>
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
                location.href = "/UserHome" ;
                //  window.location = "ViewAllItems";
            }
        })
    }
    //return feedback data
    var x = document.getElementById("status");
    if (x.innerText === "Successfully Added") {
        swal.fire({
            title: "Added Success!",
            text: "Check your request List!",
            type: "success",
            icon: 'success',
        }).then(function () {
            window.location = "MyRequests";
        });
    } else if (x.innerText === "item request already found") {
        swal.fire({
            title: "Request Already found!",
            text: "Added Failed!",
            type: "error",
            icon: 'warning',
        }).then(function () {
            window.location = "MyRequests";
        });
    }else if (x.innerText === "Failed add request"){
        swal.fire({
            title: "Error!",
            text: "Try Again ",
            type: "error",
            icon: 'warning',
        }).then(function () {
            window.location = "MyRequests";
        });
    }
</script>
</body>

</html>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html xmlns:form="http://www.w3.org/1999/xhtml" xmlns:c="">

<head>
    <meta charset="utf-8">
    <title>Add Inquiry</title>

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


    <h4 class="card-title">Add Inquiry</h4>
    <form:form action="${pageContext.request.contextPath}/AddInquiry" method="POST" modelAttribute="AddInquiry">

        <div class="row">
            <div class="col-sm">
                <label>Item Name:</label>
                <select name="itemId" id="itemId" class="form-control">
                    <c:forEach var="list" items="${list}" >
                        <option value="${list.itemId}">
                            ${list.itemName}
                        </option>
                    </c:forEach>
                </select>

            </div>
        </div>

        <div class="row">
            <div class="col-sm">
                <label>Question:</label>
                <textarea class="form-control z-depth-1" rows="3" cols="47" name="question" id="question"
                         c:maxlength="120" c:minlength="10" required  placeholder="Enter the inquiry here..."></textarea>
            </div>
        </div>


        <br>
        <div class="row">
            <div class="col-sm">
                <button style="width: 370px;" type="submit" value="AddInquiry" class="btn btn-primary">Add
                    Inquiry
                </button>
            </div>
        </div>
        <%-- <div class="alert alert-success alert-dismissible">
             <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
             <span> ${success}${error}</span>
         </div>--%>
        <div class="msg" id="success">
                ${success}${error}
        </div>
    </form:form>

</div>
<script>
    var x = document.getElementById("success");
    if (x.innerText === "inquiry added") {
        swal.fire({
            title: "Added Success!",
            text: "Check the Product List!",
            type: "success",
            icon: 'success',
        }).then(function () {
            window.location = "ViewMyInquiry";
        });

    }else if (x.innerText === "error occured"){
        swal.fire({
            title: "Error!",
            text: "Try Again ",
            type: "error",
            icon: 'warning',
        }).then(function () {
            window.location = "ViewMyInquiry";
        });
    }
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
                location.href = "/ViewMyInquiry" ;
                //  window.location = "ViewAllItems";
            }
        })
    }

</script>
</body>

</html>
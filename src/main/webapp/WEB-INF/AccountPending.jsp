<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html xmlns:form="http://www.w3.org/1999/xhtml">

<head>
    <title>Account peending</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>

    <style type="text/css">
        .container {
            width: auto;
            justify-content: center;
            align-items: center;
            padding: 1%;
        }
    </style>
</head>

<nav class="navbar navbar-expand-lg bg-primary navbar-dark justify-content-between">
    <a class="navbar-brand" href="#"><strong>PMS</strong></a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNavAltMarkup"
            aria-controls="navbarNavAltMarkup" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarNavAltMarkup">

    </div>
    <a class="nav-item" style="color: white;" href="/logout">Logout</a>
</nav>

<body>
<h3 class="text-center p-2">Account pending</h3>
<div class="container">
    <div class="row">
        <div class="col">
            <h2>Contact Admin</h2>
            <p>Please contact admin about the delay of the account verification via any contact mean available
            below, Or simply mail admin about the inconvenience.</p>
            <h4>+941182991882</h4>
            <h4>skw22456@gmail.com</h4>
        </div>

        <div class="col">
            <form:form action="${pageContext.request.contextPath}/contactAdmin" method="POST" modelAttribute="message">
            <div class="form-group">
                <label>Name:</label>
                <input type="text" class="form-control" placeholder="userName"
                       required>
            </div>
            <div class="form-group">
                <label >Email Address:</label>
                <input type="email" class="form-control" placeholder="abcde@gamil.com"
                       required>
            </div>
            <div class="form-group">
                <label>Message:</label>
                <textarea class="form-control z-depth-1" rows="3" cols="47" name="message" id="message"
                          required  placeholder="Enter the message in brief here..."></textarea>
            </div>
                <div class="text-center p-2">
                <button style="width: 230px;" type="submit" value="cAdmin" class="btn btn-primary">Send</button>
                </div>
                <div class="Message" id="success">
                    <div>${msg}</div>
                </div>
            </form:form>
        </div>
    </div>

</div>
<script>
    var x = document.getElementById("success");
    if (x.innerText === "success") {
        swal.fire({
            title: "Email send Success!",
            text: "Your account will be verrified soon!",
            type: "success",
            icon: 'success',
        }).then(function () {
            window.location = "UserHomePending";
        });
    } else  if (x.innerText === "error"){
        swal.fire({
            title: "Email send fail!",
            text: "Failed!",
            type: "error",
            icon: 'warning',
        }).then(function () {
           window.location = "UserHomePending";
        });
    }
</script>
</body>
</html>
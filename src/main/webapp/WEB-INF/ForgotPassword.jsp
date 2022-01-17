<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html xmlns:form="http://www.w3.org/1999/xhtml">
<head>
    <title>Forgot Password</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
    <style type="text/css">
        body {
            background-image: url("../Pic/D2.png");
            background-repeat: repeat;
            background-attachment: fixed;
            background-size: 70%;
        }

        .container {
            display: flex;
            justify-content: center;
            align-items: center;
            margin-top: 10%;
        }

        .fp {
            margin-left: 45%;
        }

    </style>
</head>
<body>
<nav class="navbar navbar-expand-lg bg-primary navbar-dark justify-content-between">
    <a class="navbar-brand" href="#"><strong>PMS</strong></a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNavAltMarkup"
            aria-controls="navbarNavAltMarkup" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    <a class="nav-item" style="color: white;" href="/Home">HOME</a>
</nav>
<div class="container">

    <div class="card" style="width:400px;background: white;border-color: mediumblue">
        <div class="card-body">
            <h4 class="card-title">Forgot Password</h4>
            <form:form action="${pageContext.request.contextPath}/SendOTPEmail" method="POST" modelAttribute="email">
                <div class="form-group">
                    <label for="email">Email Address:</label>
                    <input type="email" class="form-control" id="email" placeholder="abcde@gamil.com" name="email"
                           required>
                </div>
                <button type="submit" value="SendOTP" class="btn btn-primary">SendOTP</button>
                <div class="Message" id="success">
                    <div>${msg}</div>
                </div>
            </form:form>

        </div>
    </div>
</div>
<script>
    var x = document.getElementById("success");
    if (x.innerText === "ok") {
        swal.fire({
            title: "Email send Success!",
            text: "Check your email!",
            type: "success",
            icon: 'success',
        }).then(function () {
            window.location = "ValidateOTP";
        });
    } else if (x.innerText === "fail") {
        swal.fire({
            title: "OTP send faild!",
            text: "Failed!",
            type: "error",
            icon: 'warning',
        }).then(function () {
            window.location = "Home";
        });
    }
</script>
</body>
</html>
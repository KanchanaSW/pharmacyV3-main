<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html xmlns:form="http://www.w3.org/1999/xhtml">
<head>
    <title>Reset Password</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
    <style>
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
            <h4 class="card-title">Reset Password</h4>
            <form:form action="${pageContext.request.contextPath}/ResetPassword" method="POST" modelAttribute="details">

                <div class="form-group">
                    <label for="password">New Password:</label>
                    <input type="password" class="form-control" id="password" placeholder="skynet123(*)"
                           name="password" required>
                    <span><small id="dm"></small></span>
                </div>
                <input type="number" class="form-control" id="otpNumber" value=${otp} name="otpNumber" hidden>
                <div class="form-group">
                    <label for="re-password">Re-Enter New Password </label>
                    <input type="password" name="re-password" class="form-control" id="re-password"
                           placeholder="skynet123(*)" required>
                    <span><small id="dm2"></small></span>
                </div>
                <button type="submit" value="ResetPassword" class="btn btn-primary">ResetPassword</button>
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
            title: "otp is valid!",
            text: "Change your password!",
            type: "success",
            icon: 'success',
        }).then(function () {
            // window.location;

        });
    } else if (x.innerText === "fail") {
        swal.fire({
            title: "OTP not valid !",
            text: "Failed!",
            type: "error",
            icon: 'warning',
        }).then(function () {
             window.location = "Home";
        });
    }

    //password similarity check
    var pattern = /^(?=.*\d)(?=.*[a-z]).{3,10}$/;

    var password = document.getElementById("password");
    var confirm_password = document.getElementById("re-password");
    var valid = document.getElementById("dm");
    var dontMatch2 = document.getElementById("dm2");

    function validatePassword() {
        if (password.value.match(pattern)) {
            valid.innerText = "";//valid.style.color = "#00ff00";
        } else {
            valid.innerText = "Invalid: Provide at least 3-letter and no.";
            valid.style.color = "#ff0000";
        }
        if (password.value !== confirm_password.value) {
            dontMatch2.innerText = "Dont match";
            dontMatch2.style.color = "#ff0000";
            confirm_password.style.borderColor = "#ff0000";
            console.log("dont-match")
        } else {
            dontMatch2.innerText = "";
            confirm_password.style.borderColor = "";
            console.log("match")
        }
    }
    password.onkeyup = validatePassword;
    confirm_password.onkeyup = validatePassword;
</script>
</body>
</html>
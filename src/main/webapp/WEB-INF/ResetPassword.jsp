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
            background-image: url("../Pic/bwBg2.jpg");
            background-repeat: repeat;
            background-attachment: fixed;
            background-size: 30%;
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
<div class="container">

    <div class="card" style="width:400px;background: white;border-color: mediumblue">
        <div class="card-body">
            <h4 class="card-title">Reset Password</h4>
            <form:form action="${pageContext.request.contextPath}/ResetPassword" method="POST" modelAttribute="details">

                <div class="form-group">
                    <label for="password">New Password:</label>
                    <input type="password" class="form-control" id="password" placeholder="skynet123(*)"
                           name="password">

                </div>
                <input type="number" class="form-control" id="otpNumber" value=${otp} name="otpNumber" hidden>
                <div class="form-group">
                    <label for="newpassword1">Re-Enter New Password </label>
                    <input type="password" name="newpassword1" class="form-control" id="newpassword1"
                           placeholder="skynet123(*)" name="newpassword1">

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
</script>
</body>
</html>
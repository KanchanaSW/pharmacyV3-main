<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html xmlns:form="http://www.w3.org/1999/xhtml">

<head>
    <title>Home Page</title>
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
            margin-top: 6%;
        }
        .rg{
            justify-content: center;
            align-items: center;
             display: flex;
        }
        .fp {
           margin-left: 12px;
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
</nav>

<div class="container">

    <div class="card" style="border-color: silver">
        <div class="card-body">
            <h4 class="card-title">Login Page</h4>

            <form class="box" action="${pageContext.request.contextPath}/authenticate" method="post">

                <div class="form-group">
                    <label>Username:</label>
                    <input type="text" class="form-control" name="username" placeholder="username" required>
                </div>
                <div class="form-group">
                    <label>Password </label>
                    <input type="password" class="form-control" name="password" placeholder="Password" required>
                </div>

                <button style="width: 350px;" type="submit" value="Login" class="btn btn-primary">Login</button>


            </form>
            Did you forgot your password?
            <a class="fp" href="${pageContext.request.contextPath}/ForgotPasswordPage"> Reset Password</a>
            <div class="msg" id="error">
                ${error}
            </div>

            <a class="rg" href="${pageContext.request.contextPath}/RegisterPage">Create Account</a>
        </div>
    </div>
</div>
<script>
    var x = document.getElementById("error");
    if (x.innerText === "bad") {
        swal.fire({
            title: "Bad Credentials!",
            text: "Check username and password!",
            type: "error",
            icon: 'warning',
        }).then(function () {
            window.location = "Home";
        });
    }else if (x.innerText === "passwordResetOk"){
        swal.fire({
            title: "password reset success!",
            text: "You can log in now!",
            type: "success",
            icon: 'success',
        }).then(function () {
            window.location = "Home";
        });
    }else if (x.innerText === "fail"){
        swal.fire({
            title: "password reset fail!",
            text: "try reseting again!",
            type: "error",
            icon: 'warning',
        }).then(function () {
            window.location = "Home";
        });
    }
</script>
</body>

</html>

<!--
<!DOCTYPE html>
<html>

<head>
    <title>Home Page</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

    <style type="text/css">
        .container {
            display: flex;
            justify-content: center;
            align-items: center;
            margin-top: 10%;
        }

        .fp {
            margin-left: 60%;
        }
    </style>
</head>

<body>
    <div class="container">

        <div class="card" style="width:400px">
            <div class="card-body">
                <h4 class="card-title">Login Page</h4>

                <form class="box" action="${pageContext.request.contextPath}/authenticate" method="post">

                    <div class="form-group">
                        <label for="username">Username:</label>
                        <input type="text" class="form-control"  name="username" placeholder="username" required>
                    </div>
                    <div class="form-group">
                        <label for="password">Password </label>
                        <input type="password" class="form-control" name="password" placeholder="Password" required>
                    </div>

                    <input type="submit" name="" value="Login">
                </form>
                <a class="fp"href="${pageContext.request.contextPath}/ForgotPasswordPage">Forgot Password</a>
                <div class="Message">
                    <div>${error}</div>
                </div>

                <a class="rg"href="${pageContext.request.contextPath}/RegisterPage">Register.?</a>
            </div>
        </div>
    </div>
</body>

</html>

-->
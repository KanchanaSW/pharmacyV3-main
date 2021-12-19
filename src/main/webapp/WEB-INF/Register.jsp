<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html xmlns:form="http://www.w3.org/1999/xhtml">

<head>
    <meta charset="utf-8">
    <title>Register Page</title>

    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
    <style type="text/css">
                .container {
                    border: 1px black solid;
                    width: auto;
                    justify-content: center;
                    align-items: center;
                    margin-top: 10%;
                    padding: 1%;
                }

                .fp {
                    margin-left: 45%;
                }
            </style>
</head>

<body>

<div class="container" style="width: 650px;">
    <button style="color: red;" type="button" class="close" aria-label="Close" onclick="funClose();">
        <span aria-hidden="true">&times;</span>
    </button>

    <h4 class="card-title">Pharmacist Register Page</h4>
    <form:form action="${pageContext.request.contextPath}/Register" method="POST"
               modelAttribute="Registers">

        <div class="row">
            <div class="col-sm">
                <h6>Personal Information</h6>
                <label for="username">Username:</label>
                <input type="username" class="form-control" id="username" placeholder="SamanKumara"
                       name="username" required>
                <div id="usernameError">
                    <div>${uError}</div>
                </div>

            </div>
            <div class="col-sm">
                <h6>Contact Information</h6>
                <label for="email">Email:</label>
                <input type="email" class="form-control" id="email" placeholder="abcde@gamil.com"
                       name="email" required>
                <div id="emailError">
                    <div>${eError}</div>
                </div>


            </div>
        </div>

        <div class="row">
            <div class="col-sm">
                <label for="password">Password: </label>
                <input type="password" name="password" class="form-control" id="password" value=""
                       placeholder="saman123&*%" required>
            </div>
            <div class="col-sm">
                <label for="phone">Phone No:</label>
                <input type="phone" class="form-control" id="phone" placeholder="0773556712" name="phone"
                       required>
            </div>
        </div>

        <div class="row">
            <div class="col-sm">

                <label for="re-password">Repeat Password: </label>
                <input type="password" onkeypress="validatePassword();" name="re-password" class="form-control" id="re-password" value=""
                       placeholder="saman123&*%" required>
            </div>
            <div class="col-sm">

            </div>
        </div>

        <br>
        <div class="row">
            <div class="col-sm">
                <button type="submit" value="Create" class="btn btn-primary">Register</button>
                    </div>
        </div>
        <div class="msg" id="status">
            ${success}${error}
        </div>
    </form:form>

    <br>
    <div class="row">
        <div class="col-sm">
            <a class="rg" href="${pageContext.request.contextPath}/Home">Login</a>
        </div>
    </div>


</div>
<script>
    //password similarity check
    var password = document.getElementById("password")
        , confirm_password = document.getElementById("re-password");

    function validatePassword(){
        if(password.value !== confirm_password.value) {
            //confirm_password.setCustomValidity("Passwords Don't Match");
            confirm_password.innerText="dont match";
        } else {
            //confirm_password.setCustomValidity('');
            confirm_password.innerText="";
        }
    }

    //password.onchange = validatePassword;
    //confirm_password.onkeyup = validatePassword;

    //close btn
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
                location.href = "/Home";
                //  window.location = "ViewAllItems";
            }
        })
    }

    //return feedback data
    var x = document.getElementById("status");
    if (x.innerText === "User Added Successfully") {
        swal.fire({
            title: "Account Created Success!",
            text: "Login Now!",
            type: "success",
            icon: 'success',
        }).then(function () {
            window.location = "Home";
        });
    } else if (x.innerText === "Failed To Add User") {
        swal.fire({
            title: "Error!",
            text: "Try Again ",
            type: "error",
            icon: 'warning',
        }).then(function () {
            window.location = "Register";
        });
    }
    var uEr=document.getElementById("usernameError");
    if (x.innerText === "Username already taken!") {
        swal.fire({
            title: "UserName already taken!",
            text: "Use a different username ",
            type: "error",
            icon: 'warning',
        }).then(function () {
            window.location = "Register";
        });
    }
    var eEr=document.getElementById("emailError")
    if (x.innerText === "Email already taken!") {
        swal.fire({
            title: "Account found under this email!",
            text: "Use a different email address ",
            type: "error",
            icon: 'warning',
        }).then(function () {
            window.location = "Register";
        });
    }
</script>
</body>

</html>



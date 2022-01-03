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
    <style>
        body {
            background-image: url("../Pic/bwBg2.jpg");
            background-repeat: repeat;
            background-attachment: fixed;
            background-size: 30%;
        }
        .container {
            border: 1px mediumblue solid;
            width: auto;
            justify-content: center;
            align-items: center;
            margin-top: 10%;
            padding: 1%;
            background: white;
        }
        .msg {
            font-size: small;
            color: #d33;
            font-style: italic;
        }
    </style>
</head>

<body>

<div class="container" style="width: 650px;">
    <button style="color: red;" type="button" class="close" aria-label="Close" onclick="funClose();">
        <span aria-hidden="true">&times;</span>
    </button>

    <h4 class="card-title">Register Page</h4>
    <form:form action="${pageContext.request.contextPath}/Register" method="POST"
               modelAttribute="Registers">

        <div class="row">
            <div class="col-sm">
                <h6>Personal Information</h6>
                <label for="username">Username:</label>
                <c:if test="${uName == null}">
                    <input type="text" class="form-control" id="username" onkeydown="validateU();"
                           placeholder="SamanKumara"
                           name="username" required>
                </c:if>
                <c:if test="${uName != null}">
                    <input type="text" class="form-control" id="username" onkeydown="validateU();"
                           value="${uName}"
                           name="username" required>
                </c:if>
                <span><small id="textU"></small></span>
                <div class="msg" id="usernameError">
                    <div>${uError}</div>
                </div>

            </div>
            <div class="col-sm">
                <h6>Contact Information</h6>
                <label for="email">Email:</label>
                <c:if test="${rEmail == null}">
                    <input type="email" class="form-control" id="email" placeholder="abcde@gamil.com"
                           onkeydown="checkEmail();"
                           name="email" required>
                </c:if>
                <c:if test="${rEmail != null}">
                    <input type="email" class="form-control" id="email" value="${rEmail}" onkeydown="checkEmail();"
                           name="email" required>
                </c:if>
                <div class="msg" id="emailError">
                    <div>${eError}</div>
                </div>


            </div>
        </div>

        <div class="row">
            <div class="col-sm">
                <label for="password">Password: </label>
                <input type="password" name="password" class="form-control" id="password" value=""
                       placeholder="saman123&*%" required>
                <span><small id="dm"></small></span>
            </div>
            <div class="col-sm">
                <label for="phone">Phone No:</label>
                <c:if test="${rPhone == null}">
                    <input type="phone" class="form-control" id="phone" placeholder="0773556712" name="phone"
                           required>
                </c:if>
                <c:if test="${rPhone != null}">
                    <input type="phone" class="form-control" id="phone" value="${rPhone}" name="phone"
                           required>
                </c:if>
            </div>
        </div>

        <div class="row">
            <div class="col-sm">

                <label for="re-password">Repeat Password: </label>
                <input type="password" name="re-password" class="form-control" id="re-password" value=""
                       placeholder="saman123&*%" required>
                <span><small id="dm2"></small></span>
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
    function validateU() {
        var patternU = /^[a-z0-9]{5,15}$/;
        var username = document.getElementById('username').value;
        var uEE = document.getElementById("usernameError");
        var textU = document.getElementById('textU');
        if (username.match(patternU)) {

            textU.innerHTML = "";
            textU.style.color = "";
        } else {
            uEE.innerHTML = "",
                textU.innerHTML = "please enter valid username";
            textU.style.color = "#ff0000";
        }
        if (username === "") {
            uEE.innerHTML = "",
                textU.innerHTML = "";
            textU.style.color = "";
        }
    }

    function checkEmail() {
        var eEE = document.getElementById("emailError");
        var email = document.getElementById('email').value;
        if (email === null) {
            eEE.innerText = ""
        } else {
            eEE.innerText = ""
        }
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
                //window.location.preventDefault();
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
            window.location.preventDefault();
            // window.location = "Register";
        });
    }
    var uEr = document.getElementById("usernameError");
    var uNam = document.getElementById("username");
    if (uEr.innerText === "Username already taken!") {
        swal.fire({
            title: "UserName already taken!",
            text: "Use a different username ",
            type: "error",
            icon: 'warning',
        }).then(function () {
            //window.history.back();
            window.location.preventDefault();
        });


    }
    var eEr = document.getElementById("emailError")
    if (eEr.innerText === "Email already taken!") {
        swal.fire({
            title: "Account found under this email!",
            text: "Use a different email address ",
            type: "error",
            icon: 'warning',
        }).then(function () {
            window.location.preventDefault();
            // window.location = "Register";
        });
    }
</script>
</body>

</html>



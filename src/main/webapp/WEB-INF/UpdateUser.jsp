<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html xmlns:form="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="utf-8">
    <title>Update User</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
    <!-- Navigation Bar-->
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
        }

        .fp {
            margin-left: 45%;
        }

    </style>
</head>
<body>
<div class="container" style="width: 400px;">

    <button style="color: red;" type="button" class="close" aria-label="Close" onclick="funBack();">
        <span aria-hidden="true">&times;</span>
    </button>


    <h4 class="card-title">Update account</h4>

    <form:form action="/UpdateMyAcc" method="POST" modelAttribute="UpdateUser">
        <input id="userId" name="userId" value="${UserDetails.userId}" type="hidden">
        <div class="row">
            <div class="col-sm">
                <label>Email Address:</label>
                <input id="email" name="email" class="form-control" value="${UserDetails.email}" type="text" readonly>
            </div>
        </div>

        <div class="row">
            <div class="col-sm">
                <label>Username:</label>
                <input id="username" name="username" class="form-control" value="${UserDetails.username}" readonly>
            </div>
        </div>

        <div class="row">
            <div class="col-sm">
                <label>Phone No:</label>
                <input id="phone" name="phone" class="form-control" value="${UserDetails.phone}" type="text" minlength="10"
                       maxlength="10"
                       pattern="[0-9]+">
            </div>
        </div>

        <div class="row">
            <div class="col-sm">
                <label for="password">Password: </label>
                <input type="password" name="password" class="form-control" id="password">
                <span><small id="dm"></small></span>
            </div>
        </div>

        <div class="row">
            <div class="col-sm">
                <label for="confirm_password">Repeat Password: </label>
                <input class="form-control" value="" type="password" id="confirm_password"
                       required>
                <span><small id="dm2"></small></span>
            </div>
        </div>


        <br>
        <div class="row">
            <div class="col-sm">
                <button type="submit" value="Update" class="btn btn-primary">Update user</button>
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
<%--<div class="container" style="width: 400px;">

    <button style="color: red;" type="button" class="close" aria-label="Close" onclick="funClose();">
        <span aria-hidden="true">&times;</span>
    </button>

    <h4 class="card-title">Update User</h4>
    <form:form action="/UpdateMyAcc" method="POST" modelAttribute="UpdateUser">

        <form:input path="userId" value="${UserDetails.userId}" type="hidden"/>

        <div class="row">
            <div class="col-sm">
                <label>Email:</label>
                <input id="email" class="form-control" value="${UserDetails.email}" type="text" readonly>
            </div>
        </div>
        <div class="row">
            <div class="col-sm">
                <label>Username:</label>
                <input id="username" class="form-control" value="${UserDetails.username}" readonly>
            </div>
        </div>

        <div class="row">
            <div class="col-sm">
                <label>Phone No:</label>
                <input id="phone" class="form-control" value="${UserDetails.phone}" type="text" minlength="10"
                            maxlength="10"
                            pattern="[0-9]+">
            </div>
        </div>

        <div class="row">
            <div class="col-sm">
                <label for="password">Password: </label>
                <input type="password" class="form-control" id="password">
            </div>
        </div>

        <div class="row">
            <div class="col-sm">
                <label for="confirm_password">Repeat Password: </label>
                <input path="password" class="form-control" value="" type="password" id="confirm_password"
                            required>
            </div>
        </div>
        <br>
        <div class="row">
            <div class="col-sm">
                <button type="submit" value="Update" class="btn btn-primary">Update user</button>
            </div>
        </div>

        <div class="Message">
            <div>${success}${error}</div>
        </div>

    </form:form>

</div>--%>
</body>
<script>
    function funBack() {
        window.history.back();
    }

    var x = document.getElementById("success");
    if (x.innerText === "Successfully Updated The User") {
        swal.fire({
            title: "Updated Success!",
            text: "Account details changed!",
            type: "success",
            icon: 'success',
        }).then(function () {
            window.location = "AdminHome";
        });
    } else if (x.innerText === "Failed To Update The User") {
        swal.fire({
            title: "Error !",
            text: "Updated Failed!",
            type: "error",
            icon: 'warning',
        }).then(function () {
            window.history.back();
        });
    }
    //password similarity check
    var pattern = /^(?=.*\d)(?=.*[a-z]).{3,10}$/;

    var password = document.getElementById("password");
    var confirm_password = document.getElementById("confirm_password");
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
</html>
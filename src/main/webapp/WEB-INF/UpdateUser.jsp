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

    <!--Admin Navigation Bar-->
    <jsp:include page="Utills/Navbar.jsp" >
        <jsp:param name="page" value="home" />
    </jsp:include>

    <style type="text/css">
  .container{
   display: flex;
  justify-content: center;
  align-items: center;
  margin-top: 3%;
  }
  .fp{
    margin-left: 45%;
  }

  </style>
</head>
<body>
<div class="container">

    <div class="card" style="width:400px">
        <div class="card-body">
            <h4 class="card-title">Update User</h4>
            <form:form action="/UpdateMyAcc" method="POST" modelAttribute="UpdateUser">

                <form:input path="userId" value="${UserDetails.userId}" type = "hidden" />

                    <div class="form-group">
                    <label>Email:</label>
                    <form:input path="email" value="${UserDetails.email}" type = "text" readonly="true"/>
                    </div>

                    <div class="form-group">
                        <label>Username:</label>
                        <form:input path="username" value="${UserDetails.username}" readonly="true"/>
                    </div>

                    <div class="form-group">
                        <label>Phone No:</label>
                        <form:input path="phone" value="${UserDetails.phone}" type = "text" minlength="10" maxlength="10" pattern="[0-9]+" />
                    </div>

                    <div class="form-group">
                        <label for="password">Password: </label>
                        <input type = "password" id="password" />
                    </div>

                    <div class="form-group">
                        <label for="confirm_password">Repeat Password: </label>
                        <form:input path="password" value="" type = "password" id="confirm_password" required="true"/>
                    </div>

                    <div class="Message">
                        <div>${success}${error}</div>
                    </div>
                <button type="submit" value="Update" class="btn btn-primary">Update user</button>
            </form:form>

        </div>
    </div>
</div>
</body>
<script type="text/javascript">
    var password = document.getElementById("password")
        , confirm_password = document.getElementById("confirm_password");

    function validatePassword(){
        if(password.value != confirm_password.value) {
            confirm_password.setCustomValidity("Passwords Don't Match");
        } else {
            confirm_password.setCustomValidity('');
        }
    }

    password.onchange = validatePassword;
    confirm_password.onkeyup = validatePassword;
</script>
</html>
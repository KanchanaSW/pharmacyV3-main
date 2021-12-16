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
    <h4 class="card-title">Pharmacist Register Page</h4>
    <form:form action="${pageContext.request.contextPath}/Register" method="POST"
               modelAttribute="Registers">

        <div class="row">
            <div class="col-sm">
                <h6>Personal Information</h6>
                <label for="username">Username:</label>
                <input type="username" class="form-control" id="username" placeholder="SamanKumara"
                       name="username" required>
                <div class="usernameError">
                    <div>${uError}</div>
                </div>

            </div>
            <div class="col-sm">
                <h6>Contact Information</h6>
                <label for="email">Email:</label>
                <input type="email" class="form-control" id="email" placeholder="abcde@gamil.com"
                       name="email" required>
                <div class="emailError">
                    <div>${eError}</div>
                </div>


            </div>
        </div>

        <div class="row">
            <div class="col-sm">
                <label for="password">Password: </label>
                <input type="password" name="password" class="form-control" id="password"
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
                <input type="password" name="re-password" class="form-control" id="re-password"
                       placeholder="saman123&*%" required>
            </div>
            <div class="col-sm">

            </div>
        </div>

        <br>
        <div class="row">
            <div class="col-sm">
                <button type="submit" value="Create" class="btn btn-primary">Register</button>
                <div class="Message">
                    <div>${success}${error}</div>
                </div>
            </div>
        </div>
    </form:form>

    <br>
    <div class="row">
        <div class="col-sm">
            <a class="rg" href="${pageContext.request.contextPath}/Home">Login</a>
        </div>
    </div>


</div>

</body>

</html>

<!--

<html xmlns:form="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="utf-8">
    <title>Register Page</title>

    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

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
            <h4 class="card-title">Register Page</h4>
            <form:form action="${pageContext.request.contextPath}/Register" method="POST" modelAttribute="Registers">
                <div class="form-group">
                    <label for="email">Email:</label>
                    <input type="email" class="form-control" id="email" placeholder="abcde@gamil.com" name="email" required>
                    <div class="emailError"> <div>${eError}</div>
                </div>
                <div class="form-group">
                    <label for="username">Username:</label>
                    <input type="username" class="form-control" id="username" placeholder="SamanKumara" name="username" required>
                    <div class="usernameError"> <div>${uError}</div>
                    </div>
                </div>
                <div class="form-group">
                    <label for="phone">Phone No:</label>
                    <input type="phone" class="form-control" id="phone" placeholder="0773556712" name="phone" required>

                </div>
                <div class="form-group">
                    <label for="password">Password: </label>
                    <input type="password" name="password" class="form-control" id="password" placeholder="saman123&*%" required>

                </div>
                <div class="form-group">
                    <label for="re-password">Repeat Password: </label>
                    <input type="password" name="re-password" class="form-control" id="re-password" placeholder="saman123&*%" required>

                </div>

                <button type="submit" value="Create" class="btn btn-primary">Register</button>
                <div class="Message">
                    <div>${success}${error}</div>
                </div></div>
            </form:form>

            <a class="rg"href="${pageContext.request.contextPath}/Home">Login</a>
        </div>
    </div>
</div>
</body>
</html>

-->

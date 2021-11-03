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

    <style type="text/css">
  .container{
   display: flex;
  justify-content: center;
  align-items: center;
  margin-top: 10%;
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
            <h4 class="card-title">Forgot Password</h4>
            <form:form action="${pageContext.request.contextPath}/SendOTPEmail" method="POST" modelAttribute="email">
                <div class="form-group">
                    <label for="email">Email Address:</label>
                    <input type="email" class="form-control" id="email" placeholder="abcde@gamil.com" name="email" required>
                </div>
                <button type="submit" value="SendOTP" class="btn btn-primary">SendOTP</button>
                <div class="Message">
                    <div>${msg}</div>
                </div>
            </form:form>

        </div>
    </div>
</div>
</body>
</html>
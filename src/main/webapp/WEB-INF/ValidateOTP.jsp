<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html xmlns:form="http://www.w3.org/1999/xhtml">
<head>
    <title>Validate OTP</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

    <style type="text/css">
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
            <h4 class="card-title">Validate OTP</h4>
            <form:form action="${pageContext.request.contextPath}/ValidateOTP" method="POST" modelAttribute="otp">
                <div class="form-group">
                    <label for="otp">OTP No:</label>
                    <input type="number" class="form-control" id="otp" placeholder="1234567" name="otp">

                </div>
                <button type="submit" value="ValidateOTP" class="btn btn-primary">ValidateOTP</button>
                <div class="Message">
                    <div>${msg}</div>
                </div>
            </form:form>
        </div>
    </div>
</div>
</body>
</html>
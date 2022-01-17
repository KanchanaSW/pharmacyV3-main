<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html xmlns:form="http://www.w3.org/1999/xhtml">
<head>
    <title>Pharmacist-Home</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

    <!--nav Bar-->
    <jsp:include page="Utills/Navbar.jsp">
        <jsp:param name="page" value="home"/>
    </jsp:include>

    <style>
        .container {
            margin-top: 70px;
        }

        .card {
            margin: 0 auto;

        }
        .card-title{
            margin-left: 30px;
            margin-top: 10px;
            font-size: 35px;
        }

        select {
            border: 1px solid silver;
            padding: 6px;
            border-radius: 4px;
        }
    </style>
</head>
<body>
<div class="container">

    <div class="card">
        <div class="card-title">Pharmacy Management System</div>
        <div class="card-body">
            <p>
                Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam vestibulum orci a sapien imperdiet
                maximus. Suspendisse vel ex eget mauris efficitur finibus eu a enim. Aenean tellus neque, consectetur
                sit amet lacus a, hendrerit ornare urna. Fusce venenatis lorem vitae augue suscipit, eu tempor massa
                vulputate. Praesent iaculis velit libero, vel faucibus sem pulvinar vel. Curabitur viverra nisl dui, a
                venenatis felis pellentesque id. Donec non lacus diam.
            </p>
            <h3>Hi!&ensp; ${name} &emsp; ${email}</h3>
        </div>
    </div>
</div>

</body>
</html>
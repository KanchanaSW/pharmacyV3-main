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
                Pharmacy management system User dashboard is here. User functionalities are,
            </p>
            <ul>
                <li>View all items</li>
                <li>Manage user account</li>
                <li>Add drug items</li>
                <li>Inquiry about pharmacy item</li>
                <li>Place orders</li>
                <li>Add requests</li>
            </ul>
            <h3>Hi!&ensp; ${name} &emsp; ${email}</h3>
        </div>
    </div>
</div>

</body>
</html>
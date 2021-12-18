<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    <title>Admin Homepage</title>

    <!--Admin Navigation Bar-->
    <jsp:include page="Utills/Navbar.jsp" >
        <jsp:param name="page" value="home" />
    </jsp:include>


    <style type="text/css">
                .container {
                    width: auto;
                    justify-content: center;
                    align-items: center;
                    margin-top: 3%;
                    padding: 1%;
                }
                .card{
                width : 400px;
                justify-content: center;

                }
                .fp {
                    margin-left: 45%;
                }
                .row {
                    padding-bottom: 5px;
                }
                select {
                    border: 1px solid silver;
                    padding: 6px;
                    border-radius: 4px;
                }
            </style>
</head>
<body>
    <h1>Admin Home</h1>

</body>
</html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    <title>Admin Homepage</title>

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
    <div class="container">
        <div class="card">
        <div class="Row">
            <div class="col-sm">
                <a href="ViewAllUsers" class="btn btn-link btn-block">View All Users</a>
            </div>
            <div class="col-sm">
                <a href="UpdateAUser" class="btn btn-link btn-block">Update my account</a>
            </div>
        </div>
        </div>
        <br><br>
        <div class="card">
        <div class="Row">
            <div class="col-sm">
                <a href="NewItemPage" class="btn btn-link btn-block">Add New Item</a>
            </div>
            <div class="col-sm">
                <a href="ViewAllItems" class="btn btn-link btn-block">View All Items</a>
            </div>
        </div>
        </div>
        <br><br>
        <div class="card">
            <div class="Row">
                <div class="col-sm">
                    <a href="AddRequestPage" class="btn btn-link btn-block">Add Item Request : UserF</a>
                </div>
                <div class="col-sm">
                    <a href="MyRequests" class="btn btn-link btn-block">View My Requests : UserF</a>
                </div>
                <div class="col-sm">
                    <a href="AllRequests" class="btn btn-link btn-block">View All Requests</a>
                </div>
            </div>
        </div>
        <br><br>
        <div class="card">
            <div class="Row">
                <div class="col-sm">
                    <a href="CartListAndItems" class="btn btn-link btn-block">Cart View</a>
                </div>

            </div>
        </div>

    </div>
</body>
</html>
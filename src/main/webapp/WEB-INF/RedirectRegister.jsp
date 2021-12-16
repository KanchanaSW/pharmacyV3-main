<!DOCTYPE html>
<html>

<head>
    <title>Redirect Register</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

    <style type="text/css">
        .container {
            display: flex;
            justify-content: center;
            align-items: center;
            margin-top: 10%;
        }
        .rg{
            justify-content: center;
            align-items: center;
            display: flex;
        }
        .fp {
            margin-left: 12px;
        }
    </style>
</head>

<body>
<div class="container">

    <div class="card" style="width:390px">
        <div class="card-body">
            <h4 class="card-title">Choose User Type</h4>
            <div class="container">
                <div class="row">
                    <div class="col">
                        <a type="button" class="btn btn-primary"  href="${pageContext.request.contextPath}/RegisterPage">
                            Pharmacist</a>
                     </div>
                    <div class="col">
                        <a type="button" class="btn btn-warning"  href="${pageContext.request.contextPath}/RegisterPage/Customer">
                            Customer</a>

                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>

</html>

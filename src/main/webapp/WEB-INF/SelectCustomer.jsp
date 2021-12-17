<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html xmlns:form="http://www.w3.org/1999/xhtml" xmlns:c="">

<head>
    <meta charset="utf-8">
    <title>Select Customer</title>

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
            margin-top: 3%;
            padding: 1%;
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

<label>Customers:</label><br>
<select name="customer" id="customer">
    <c:forEach var="cus" items="${cus}" >
        <option value="${cus.userId}">
                ${cus.username}
        </option>
    </c:forEach>
</select>
<button style="margin-top: 35px"  type="button" c:onclick="cli()" class="btn btn-primary btn-sm">View Cart
</button>
</body>
<script>
    function cli(){
        let custId=document.getElementById("customer").value;

    }
</script>
</html>
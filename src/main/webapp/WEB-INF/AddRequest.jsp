<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html xmlns:form="http://www.w3.org/1999/xhtml">

<head>
    <meta charset="utf-8">
    <title>Request Item</title>

    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    <!--nav Bar-->
    <jsp:include page="Utills/Navbar.jsp" >
        <jsp:param name="page" value="home" />
    </jsp:include>
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

<div class="container" style="width: 400px;">

    <button style="color: red;" type="button" class="close" aria-label="Close">
        <span aria-hidden="true">&times;</span>
    </button>

    <h4 class="card-title">Request New Item</h4>
    <form:form action="${pageContext.request.contextPath}/AddRequest" method="POST" modelAttribute="AddRequest">

        <div class="row">
            <div class="col-sm">
                <label for="newItemName">Requested Item Name:</label>
                <input type="text" class="form-control" id="newItemName" placeholder="Amoxicillin" name="newItemName"
                       required>
                <div class="newItemNameError">
                    <div>${nIError}</div>
                </div>

            </div>

        </div>

        <div class="row">
            <div class="col-sm">
                <label for="note">Brief Note:</label>
                <textarea class="form-control z-depth-1" rows="4" cols="47" name="note" id="note"
                          placeholder="Enter brief note about the item here..."></textarea>
            </div>

        </div>


        <br>
        <div class="row">
            <div class="col-sm">
                <button style="width: 370px;" type="submit" value="RequestNewItem" class="btn btn-primary">Confirm</button>
                <div class="Message">
                    <div>${success}${error}</div>
                </div>
            </div>
        </div>
    </form:form>

</div>

</body>

</html>
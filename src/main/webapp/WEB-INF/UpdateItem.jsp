<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html xmlns:form="http://www.w3.org/1999/xhtml" xmlns:c="">

<head>
    <meta charset="utf-8">
    <title>Edit Item</title>

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

<div class="container" style="width: 400px;">

    <button style="color: red;" type="button" class="close" aria-label="Close">
        <span aria-hidden="true">&times;</span>
    </button>

    <h4 class="card-title">Edit Item</h4>
    <form:form action="/UpdateItem" method="POST" modelAttribute="updateInfo">

        <input id="itemId" name="itemId" value="${itemInfo.itemId}" type = "hidden" />
        <div class="row">
            <div class="col-sm">
                <label for="itemName">Item Name:</label>
                <input type="text" class="form-control" id="itemName" value="${itemInfo.itemName}" name="itemName"readonly="true" >
                    <div class="itemNameError">
                    <div>${nError}</div>
                </div>
            </div>
        </div>

        <div class="row">
            <div class="col-sm">
                <label for="des">Description:</label>
                <textarea  class="form-control z-depth-1" rows="3" cols="47" name="des" id="des">${itemInfo.des}</textarea>
            </div>
        </div>

        <div class="row">
            <div class="col-sm">
                <label for="quantity">Quantity:</label>
                <input type="number" class="form-control" id="quantity" value="${itemInfo.quantity}" name="quantity"
                       required>
                <div class="quantityError">
                    <div>${qError}</div>
                </div>
            </div>

            <div class="col-sm">
                <label>Category:</label><br>
               <!-- <input type="text" readonly="true" class="form-control" id="categoryName" value="${itemInfo.categoryName}" name="categoryName">-->

                <select name="categoryName" id="categoryName" >
                    <c:forEach var="cate" items="${cate}" varStatus="item">
                        <option value="${cate.category}" >
                            ${cate.category}
                        </option>
                    </c:forEach>
                </select>
            </div>

            <div class="col-sm">
                <label for="price">Price:</label>
                <input type="number" step="any" class="form-control" id="price" value="${itemInfo.price}" name="price"
                       required>
                <div class="priceError">
                    <div>${pError}</div>
                </div>
            </div>

        </div>

        <br>
        <div class="row">
            <div class="col-sm"><button style="width: 370px;" type="submit" value="AddItem" class="btn btn-primary">Update</button>
                <div class="Message">
                    <div>${success}${error}</div>
                </div>
            </div>
        </div>
    </form:form>

</div>

</body>

</html>

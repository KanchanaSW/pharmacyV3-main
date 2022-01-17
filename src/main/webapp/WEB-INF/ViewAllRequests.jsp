<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html xmlns:form="http://www.w3.org/1999/xhtml">

<head>
    <title>Requests</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
    <!--nav Bar-->
    <jsp:include page="Utills/Navbar.jsp">
        <jsp:param name="page" value="home"/>
    </jsp:include>

</head>

<body>
<div class="container">
    <h3 class="text-center p-2">PRODUCT-REQUESTS</h3>

    <!--
//////////////////////////////////////////////////////Search function
<div class="searchFunction" style="align-items: center">
<form:form action="${pageContext.request.contextPath}/SearchUserName" method="GET">
<div class="searchBar">
<input type="text" name ="searchName" placeholder="Search..">
</div>
<button class="searchButton" type="submit">Search</button>
</form:form>
</div>
-->
    <table class="table table-bordered" style="background: white">
        <thead Class="table-header">
        <tr class="table bg-primary" style="color: white">
            <th Class="col col-1"><span>RequestID</span></th>
            <th Class="col col-2"><span>Item Name</span></th>
            <th Class="col col-3"><span>Note</span></th>
            <th Class="col col-1"><span>UserId</span></th>
            <th Class="col col-1"><span>Status</span></th>
            <%-- <th Class="col col-1"><span>User Name</span></th>--%>
            <th Class="col col-1"><span>Manage</span></th>
            <th Class="col col-1"><span>Reject</span></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="requests" items="${requests}">
            <tr class="align">
                <td Class="col col-1" style="width: 25%">${requests.itemRequestsId}</td>
                <td Class="col col-2" style="width: 25%">${requests.newItemName}</td>
                <td Class="col col-3" style="width: 25%">${requests.note}</td>
                <td Class="col col-1" style="width: 25%">${requests.userId}</td>
                <td Class="col col-1" style="width: 25%">${requests.status}</td>
                    <%--         <td Class="col col-1" style="width: 25%">${requests.username}</td>--%>

                <td Class="col col-1">
                    <c:if test="${requests.status == 'Completed'}">
                        <button class="btn btn-outline-secondary btn-sm"
                                     type="button" disabled>
                            <a>Manage</a>
                        </button>
                    </c:if>
                    <c:if test="${requests.status == 'Rejected'}">
                        <button class="btn btn-outline-primary btn-sm"
                                type="button" disabled>
                            <a>Reject</a>
                        </button>
                    </c:if>
                    <c:if test="${requests.status == 'pending'}">
                        <button class="btn btn-outline-primary btn-sm"
                                type="button">
                            <a href="ManageRequestPage/${requests.itemRequestsId}">Manage</a>
                        </button>
                    </c:if>
                </td>
                <td class="col col-1">
                    <c:if test="${requests.status == 'Completed'}">
                        <button class="btn btn-outline-secondary btn-sm"
                                type="button" disabled>
                            <a>Reject</a>
                        </button>
                    </c:if>
                    <c:if test="${requests.status == 'Rejected'}">
                        <button class="btn btn-outline-primary btn-sm"
                                type="button" disabled>
                            <a>Reject</a>
                        </button>
                    </c:if>
                    <c:if test="${requests.status == 'pending'}">
                        <button class="btn btn-outline-danger btn-sm"
                                type="button">
                            <a  href="${pageContext.request.contextPath}/RejectRequestPage/${requests.itemRequestsId}">Reject</a>
                        </button>
                    </c:if>

                </td>
                    <%--
                         <button class="btn btn-outline-primary"
                            type="button"><a
                            href="${pageContext.request.contextPath}/ManageRequestPage/${requests.itemRequestsId}">Manage</a>
                    </button>

                     <td Class="col col-1" style="width: 25%">
                                <button class="btn btn-outline-danger"
                                        type="button" onclick="myFunction(${requests.itemRequestsId})"><a
                                >❌</a></button>
                            </td>--%>


            </tr>
        </c:forEach>

        </tbody>
    </table>

</div>
</body>
<script>
/*       function reject(id) {
           // confirm("Are you sure you want to delete this item Request?");
           <%-- href="RejectRequest/${requests.itemRequestsId}" --%>
            Swal.fire({
                title: 'Are you sure reject request?',
                text: "You won't be able to revert this!",
                icon: 'warning',
                showCancelButton: true,
                confirmButtonColor: '#3085d6',
                cancelButtonColor: '#d33',
                confirmButtonText: 'Yes, reject it!'
            }).then((result) => {
                if (result.isConfirmed) {
                    location.href = "/RejectRequest/" + id;
                    //  window.location = "ViewAllItems";
                }
            })
        }*/
</script>

</html>
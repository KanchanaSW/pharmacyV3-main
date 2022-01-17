<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<style>
    body {
    <sec:authorize access="hasRole('USER')" var="user">
        background-image: url("../../Pic/D2.png");
        background-size: 70%;
    </sec:authorize>
    <sec:authorize access="hasRole('ADMIN')" var="admin">
        background-image: url("../../Pic/D2.png");
        background-size: 70%;
    </sec:authorize>
        background-repeat: repeat;
        background-attachment: fixed;
        font-family: "Arial Narrow";
    }
</style>
<nav class="navbar navbar-expand-lg bg-primary navbar-dark justify-content-between">
    <a class="navbar-brand" href="#"><strong>PMS</strong></a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNavAltMarkup"
            aria-controls="navbarNavAltMarkup" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarNavAltMarkup">
        <div class="navbar-nav">
            <sec:authorize access="hasRole('USER')">
                <a class="nav-item nav-link active" href="/UserHome">Home <span class="sr-only">(current)</span></a>
            </sec:authorize>
            <sec:authorize access="hasRole('ADMIN')">
                <a class="nav-item nav-link active" href="/AdminHome">Home <span class="sr-only">(current)</span></a>
                <a class="nav-item nav-link" href="/ViewAllUsers">Users</a>
            </sec:authorize>

            <a class="nav-item nav-link" href="/UpdateAUser">Account</a>


            <ul class="navbar-nav">
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="#" id="navbarDropdownMenuLink" data-toggle="dropdown"
                       aria-haspopup="true" aria-expanded="false">
                        Products
                    </a>
                    <div class="dropdown-menu" aria-labelledby="navbarDropdownMenuLink">
                        <sec:authorize access="hasRole('ADMIN')">
                            <a class="dropdown-item" href="/NewItemPage">Add-Item</a>
                        </sec:authorize>
                        <a class="dropdown-item" href="/ViewAllItems">Item-List</a>
                    </div>
                </li>
            </ul>
            <ul class="navbar-nav">
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="#" id="navbarOrder" data-toggle="dropdown"
                       aria-haspopup="true" aria-expanded="false">
                        Orders
                    </a>
                    <div class="dropdown-menu" aria-labelledby="navbarDropdownMenuLink">
                        <a class="dropdown-item" href="/OrdersView">Orders</a>
                        <a class="dropdown-item" href="/CartListAndItems">Place-Order</a>
                    </div>
                </li>
            </ul>
            <ul class="navbar-nav">
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="#" id="navbarDDMLR" data-toggle="dropdown"
                       aria-haspopup="true" aria-expanded="false">
                        Requests
                    </a>
                    <div class="dropdown-menu" aria-labelledby="navbarDropdownMenuLink">
                        <sec:authorize access="hasRole('USER')">
                            <a class="dropdown-item" href="/AddRequestPage">Add-Request</a>
                            <a class="dropdown-item" href="/MyRequests">View-My-Requests</a>
                        </sec:authorize>
                        <sec:authorize access="hasRole('ADMIN')">
                            <a class="dropdown-item" href="/AllRequests">All-Requests</a>
                        </sec:authorize>
                    </div>
                </li>
            </ul>

            <ul class="navbar-nav">
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="#" id="navbarInquiry" data-toggle="dropdown"
                       aria-haspopup="true" aria-expanded="false">
                        Inquires
                    </a>
                    <div class="dropdown-menu" aria-labelledby="navbarDropdownMenuLink">
                        <sec:authorize access="hasRole('USER')">
                            <a class="dropdown-item" href="/AddInquiryPage">Add-Inquiry</a>
                            <a class="dropdown-item" href="/ViewMyInquiry">View-My-Inquiry</a>
                        </sec:authorize>
                        <sec:authorize access="hasRole('ADMIN')">
                            <a class="dropdown-item" href="/ViewAllInquires">All-Inquiry</a>
                        </sec:authorize>
                    </div>
                </li>
            </ul>

        </div>
    </div>
    <a class="nav-item" style="color: white;" href="/logout">Logout</a>
</nav>
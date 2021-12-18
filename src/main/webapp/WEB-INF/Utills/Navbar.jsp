<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <a class="navbar-brand" href="#">Navbar</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNavAltMarkup"
            aria-controls="navbarNavAltMarkup" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarNavAltMarkup">
        <div class="navbar-nav">
            <sec:authorize access="hasRole('USER')">
                <a class="nav-item nav-link active" href="UserHome">Home <span class="sr-only">(current)</span></a>
            </sec:authorize>
            <sec:authorize access="hasRole('ADMIN')">
                <a class="nav-item nav-link active" href="AdminHome">Home <span class="sr-only">(current)</span></a>
                <a class="nav-item nav-link" href="ViewAllUsers">Users</a>
            </sec:authorize>

            <a class="nav-item nav-link" href="UpdateAUser">Account</a>



            <ul class="navbar-nav">
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="#" id="navbarDropdownMenuLink" data-toggle="dropdown"
                       aria-haspopup="true" aria-expanded="false">
                        Products
                    </a>
                    <div class="dropdown-menu" aria-labelledby="navbarDropdownMenuLink">
                        <sec:authorize access="hasRole('ADMIN')">
                            <a class="dropdown-item" href="NewItemPage">Add-Item</a>
                        </sec:authorize>
                        <a class="dropdown-item" href="ViewAllItems">Item-List</a>
                        <a class="dropdown-item" href="OrdersView">Orders</a>
                        <a class="dropdown-item" href="CartListAndItems">Place-Order</a>
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
                        <a class="dropdown-item" href="AddRequestPage">Add-Request</a>
                        <a class="dropdown-item" href="MyRequests">View-My-Requests</a>
                        </sec:authorize>
                        <sec:authorize access="hasRole('ADMIN')">
                            <a class="dropdown-item" href="AllRequests">All-Requests</a>
                        </sec:authorize>
                    </div>
                </li>
            </ul>

        </div>
    </div>
</nav>
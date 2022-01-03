<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Error404</title>
    <link rel="stylesheet" href="../CSS/error404.css">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    <!--nav Bar-->
    <jsp:include page="Utills/Navbar.jsp" >
        <jsp:param name="page" value="home" />
    </jsp:include>
</head>
<body>
<div id="error-page">
    <div class="content">
        <h2 class="header" data-text="404">
            404
        </h2>
        <h4 data-text="Opps! Page not found">
            Opps! Page not found
        </h4>
        <p>
            Sorry, the page you're looking for doesn't exist. If you think something is broken, report a problem.
        </p>
        <div class="btns">
            <sec:authorize access="hasRole('USER')">
                <a href="/UserHome">return home</a>
            </sec:authorize>
            <sec:authorize access="hasRole('ADMIN')">
                <a href="/AdminHome">return home</a>
            </sec:authorize>

            <a href="#">report problem</a>
        </div>
    </div>
</div>
</body>
</html>
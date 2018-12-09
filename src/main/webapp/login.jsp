<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8">
    <title>Booking System - TicketIT</title>
    <meta name="description" content="A TicketIT Booking System, book your tickets here.">
    <link rel="stylesheet" type="text/css" href="style.css" />
</head>

<body>
<div class="pageHeader">
    <img class="websiteLogo" src="images/logo.png" alt="TicketIT Logo">
    <div class="headerUserAccount">
        <c:if test="${!cookie.containsKey('memberId')}">
            <form action="login" method="GET">
                <table><tr><th><button type="submit" style="float: right;">Login</button></th></tr></table>
            </form>
        </c:if>
        <c:if test="${cookie.containsKey('memberId')}">
            <form action="logout" method="POST">
                <table><tr><th><button type="submit" style="float: right;">Logout</button></th></tr></table>
            </form>
            <form action="dashboard" method="GET">
                <table><tr><th><button type="submit" style="float: right;">Dashboard</button></th></tr></table>
            </form>
        </c:if>
    </div>
</div>

<div class="pageContent">
    <br><br>
    <h1>Login</h1>

    <!-- Login form -->
    <form class="detailsForm" action="login" method="POST">
        <table class="detailsTable">
            <tr>
                <td><input type="email" name="userEmail" placeholder="Email" required></td>
            </tr>
            <tr>
                <td><input type="password" name="userPassword" placeholder="Password" required></td>
            </tr>
            <tr>
                <td><button type="submit" style="text-align: center">Login</button></td>
            </tr>
        </table>
    </form>
    <br>

    <!-- Register section -->
    <h2>Don't have an account?</h2>
    <form class="detailsForm" action="register" method="GET">
        <table class="detailsTable">
            <tr>
                <td><button type="submit" style="text-align: center">Register</button></td>
            </tr>
        </table>
    </form>
</div>

<div class="pageFooter">
    <span>Copyright 2018</span>
</div>
</body>
</html>

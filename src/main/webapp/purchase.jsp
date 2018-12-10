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
                <form action="login" method="GET" style="display:inline-block">
                    <table><tr><th><button type="submit" style="float: right;">Login</button></th></tr></table>
                </form>
            </c:if>
            <c:if test="${cookie.containsKey('memberId')}">
                <form action="logout" method="POST" style="display:inline-block">
                    <table><tr><th><button type="submit" style="float: right;">Logout</button></th></tr></table>
                </form>
                <form action="dashboard" method="GET" style="display:inline-block">
                    <table><tr><th><button type="submit" style="float: right;">Dashboard</button></th></tr></table>
                </form>
            </c:if>
        </div>
    </div>

        <div class="pageContent">

            <!-- Display event summary -->
            <div class="eventSummary" style="background-image: url(${chosenEvent.getNameAsImagePath()})"/>
                <div id="titleAndDescription" style="width: 100%">
                    <p><b>${chosenEvent.getTitle()}</b><br>${chosenEvent.getDescription()}</p>
                </div>
            </div>
        <br>

        <h1>Thank You</h1>
        <h2>Your booking has been successful. You will get an email confirmation shortly.</h2>

        </div>

        <div class="pageFooter">
            <span>Copyright 2018</span>
        </div>
    </body>
</html>

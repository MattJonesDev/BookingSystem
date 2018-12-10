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
                    <form action="admin" method="GET" style="display:inline-block">
                        <table><tr><th><button type="submit" style="float: right;">Dashboard</button></th></tr></table>
                    </form>
                </c:if>
            </div>
        </div>

        <div class="pageContent">
            <h1>Create a new event</h1><br>

            <!-- Form to create a new event. -->
            <form class="detailsForm" action="adminCreate" method="POST" enctype="multipart/form-data">
                <table class="detailsTable">
                    <tr>
                        <td><h2>Ticket</h2></td>
                        <td><h2>Default Ticket</h2></td>
                    </tr>
                    <tr>
                        <td><input type="text" name="eventTitle" placeholder="Event Title" required></td>
                        <td><input type="text" name="ticketName" placeholder="Ticket Name" required></td>
                    </tr>
                    <tr>
                        <td><input type="text" name="eventDesc" placeholder="Event Description" required></td>
                        <td><input type="text" name="ticketPrice" placeholder="Ticket Price" required></td>
                    </tr>
                    <tr>
                        <td><input type="file" name="eventImage" placeholder="Event Image" accept="image/jpeg"></td>
                        <td><input type="text" name="ticketAvailable" placeholder="Number Available" required></td>
                    </tr>
                    <tr>
                        <td><input type="date" name="eventDate" placeholder="Event Date" required></td>
                    </tr>
                    <tr>
                        <td><input type="time" name="eventTime" placeholder="Event Time" required></td>
                    </tr>
                    <tr>
                        <td> </td>
                        <td><button type="submit" style="text-align: center">Create</button></td>
                    </tr>
                </table>
            </form>
        </div>

        <div class="pageFooter">
            <span>Copyright 2018</span>
        </div>
    </body>
</html>


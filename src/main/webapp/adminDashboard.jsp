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

    <div>
        <span>Create event</span>
        <form action="" method="post">
            <input type="hidden" name="action" value="createEvent">
            <table>
                <tr>
                    <th>Title</th><th>Description</th><th>Image</th> <!-- Setup event -->
                </tr>
                <tr>
                    <th>Ticket Name</th><th>Ticket Price</th> <!-- Default Ticket -->
                </tr>
                <tr>
                    <th><button id="createEventButton" type="submit">Create</button></th> <!-- Create the event -->
                </tr>
            </table>
        </form>
        <br><br>
        <span>Create ticket for existing event</span>
        <form action="" method="post">
            <input type="hidden" name="action" value="createTicket">
            <table>
                <tr>
                    <th>Event Dropdown</th><th>Ticket Name</th><th>Ticket Price</th>
                    <th><button id="createTicketButton" type="submit">Create</button></th>
                </tr>
            </table>
        </form>
    </div>

    <span>Delete an existing event</span>
    <c:forEach items="${eventList}" var="event">
        <div>
            <form action="" method="post">
                <input type="hidden" name="action" value="deleteEvent">
                <table>
                    <tr>
                        <th><span>${event.getTitle()}</span></th>
                        <th><button id="deleteEventButton" type="submit">Delete</button></th>
                    </tr>
                </table>
            </form>
        </div>
    </c:forEach>

</div>

<div class="pageFooter">
    <span>Copyright 2018</span>
</div>
</body>
</html>


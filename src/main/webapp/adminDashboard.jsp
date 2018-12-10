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
            <span>Events</span>
            <!-- Option to create an event. -->
            <form action="adminCreate" method="GET">
                <button id="createEventButton" type="submit">Create</button>
            </form>

            <!-- List events and have option to edit. -->
            <c:forEach items="${eventList}" var="event">
                <div>
                    <form action="adminEdit" method="GET">
                        <input type="hidden" name="eventId" value="${event.getId()}">
                        <table>
                            <tr>
                                <th><span>${event.getTitle()}</span></th>
                                <th><button id="editEventButton" type="submit">Edit</button></th>
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


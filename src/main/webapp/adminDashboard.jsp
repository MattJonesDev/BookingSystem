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
            <!-- Option to create an event. -->
            <table>
                <tr>
                    <th><span>Events</span></th>

                    <!-- Option to create a new event -->
                    <th>
                        <form action="adminCreate" method="GET">
                            <button id="createEventButton" type="submit">Create</button>
                        </form>
                    </th>
                </tr>
            </table>

            <!-- List events and have option to edit. -->
            <c:forEach items="${eventList}" var="event">
                <div>
                    <table>
                        <tr>
                            <!-- List the event name -->
                            <th><span>${event.getTitle()}</Span></th>

                            <!-- Option to edit the event -->
                            <th>
                                <form action="adminEdit" method="GET">
                                    <input type="hidden" name="eventId" value="${event.getId()}">
                                    <button id="editEventButton" type="submit">Edit</button>
                                </form>
                            </th>

                            <!-- Option to delete the event -->
                            <th>
                                <form action="admin" method="POST">
                                    <input type="hidden" name="eventId" value="${event.getId()}">
                                    <input type="hidden" name="action" value="deleteEvent">
                                    <button id="deleteEventButton" type="submit">Delete</button>
                                </form>
                            </th>
                        </tr>
                    </table>
                </div>
            </c:forEach>
        </div>

        <div class="pageFooter">
            <span>Copyright 2018</span>
        </div>
    </body>
</html>


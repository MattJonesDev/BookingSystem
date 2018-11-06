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
            <img class="websiteLogo" src="Resources/logo.png" alt="TicketIT Logo">
            <div class="headerUserAccount">
                <!-- Todo - Implement User login / account -->
                <form>
                    <table><tr><th><button type="submit" style="float: right;">Login</button></th></tr></table>
                </form>
            </div>
        </div>

        <div class="pageContent">
            <!-- Display event summary -->
            <div class="eventSummary"/>
                <div id="titleAndDescription" style="width: 100%">
                    <p><b>${chosenEvent.getTitle()}</b><br>${chosenEvent.getDescription()}</p>
                </div>
            </div>
            <br>

            <!-- Display tickets available for event and allow user selection. -->
            <form class="ticketTypesForm" action="checkout" method="post">
                <table class="ticketTypesTable">
                    <tr>
                        <th>Name</th>
                        <th>Price</th>
                        <th>Number Available</th>
                        <th>Amount To Buy</th>
                    </tr>
                    <c:forEach items="${eventTicketsList}" var="ticket">
                        <tr>
                            <td>${ticket.getName()}</td>
                            <td>${ticket.getPrice()}</td>
                            <td>${ticket.getNumberAvailable()}</td>
                            <td><input type="number" name="${ticket.getId()}" value="0"></td>
                        </tr>
                    </c:forEach>
                </table>
                <input type="hidden" name="eventId" value="${chosenEvent.getId()}">
                <br><button type="submit" style="float: right;">Checkout</button>
            </form>
        </div>

        <div class="pageFooter">
            <span>Copyright 2018</span>
        </div>
    </body>
</html>


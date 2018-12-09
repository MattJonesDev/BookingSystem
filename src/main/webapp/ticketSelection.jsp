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
            <!-- Display event summary -->
            <div class="eventSummary" style="background-image: url(${chosenEvent.getNameAsImagePath()})"/>
                <div id="titleAndDescription" style="width: 100%">
                    <p><b>${chosenEvent.getTitle()}</b><br>${chosenEvent.getDescription()}</p>
                </div>
            </div>
            <br>

            <!-- Display tickets available for event and allow user selection. -->
            <h1>Available Tickets</h1>
            <form class="infoSelectForm" action="checkout" method="GET">
                <table class="infoSelectTable">
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
                            <td><input type="number" name="${ticket.getId()}" value="0" min="0" max="${ticket.getNumberAvailable()}"></td>
                        </tr>
                    </c:forEach>
                </table>

                <!-- Add hidden ids for use on the checkout page. -->
                <input type="hidden" name="eventId" value="${chosenEvent.getId()}">
                <c:if test="${!cookie.containsKey('memberId')}">
                    <input type="hidden" name="memberId" value="0">
                </c:if>
                <c:if test="${cookie.containsKey('memberId')}">
                    <input type="hidden" name="memberId" value="${cookie['memberId'].getValue()}">
                </c:if>

                <br><button type="submit" style="float: right;">Checkout</button>
            </form><br>

            <!-- Cancel  button -->
            <form class="detailsForm" action="home" method="post">
                <table class="detailsTable">
                    <tr><td><button type="submit" style="text-align: center">Cancel</button></td></tr>
                </table>
            </form>
        </div>

        <div class="pageFooter">
            <span>Copyright 2018</span>
        </div>
    </body>
</html>


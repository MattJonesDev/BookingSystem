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
            <!-- Dynamically add summary div for each event available -->
            <c:forEach items="${eventList}" var="event">
                <div class="eventSummary"/>
                    <div id="titleAndDescription">
                        <p><b>${event.getTitle()}</b><br>${event.getDescription()}</p>
                    </div>

                    <div id="priceAndBuy">
                        <form action="ticketSelection" method="post">
                            <input type="hidden" name="eventId" value="${event.getId()}">
                            <button id="purchaseButton" type="submit">Purchase</button>
                        </form>
                    </div>
                </div>
            </c:forEach>
        </div>

        <div class="pageFooter">
            <span>Copyright 2018</span>
        </div>
    </body>
</html>


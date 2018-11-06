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

            <!-- Payment Form -->
            <form class="checkoutForm" action="purchase" method="post">
                <input type="hidden" name="bookingId" value="${booking.getId()}">
                <table class="checkoutTable">
                    <tr><td><input type="text" name="name" placeholder="Full Name"></td><td><input type="text" name="cardType" placeholder="Card Type"></td></tr>
                    <tr><td><input type="text" name="telephone" placeholder="Telephone"></td><td><input type="text" name="cardHolder" placeholder="Card Holder"></td></tr>
                    <tr><td><input type="text" name="addressLine1" placeholder="Address Line 1"></td><td><input type="text" name="cardNumber" placeholder="Card Number"></td></tr>
                    <tr><td><input type="text" name="addressLine2" placeholder="Address Line 2"></td><td><input type="text" name="cardSecurityCode" placeholder="Card Security Code"></td></tr>
                    <tr><td><input type="text" name="addressLine3" placeholder="Address Line 3"></td><td> </td></tr>
                    <tr><td><input type="text" name="addressCity" placeholder="City"></td><td><input type="checkbox" name="sendTickets">Send tickets to address<br></td></tr>
                    <tr><td><input type="text" name="addressCountry" placeholder="Country"></td><td>Due: ${invoice.getAmount()}</td></tr>
                    <tr><td><input type="text" name="addressPostcode" placeholder="Postcode"></td><td><button type="submit" style="text-align: center">Finish & Pay</button></td></tr>
                </table>
            </form>
            <form class="checkoutForm" action="cancel" method="post">
                <table class="checkoutTable">
                    <tr><td><input type="hidden" name="bookingId" value="${booking.getId()}"></td><td><button type="submit" style="text-align: center">Cancel</button></td></tr>
                </table>
            </form>

        </div>

        <div class="pageFooter">
            <span>Copyright 2018</span>
        </div>
    </body>
</html>

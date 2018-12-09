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

        <div class="pageContent">

            <!-- Display event summary -->
            <div class="eventSummary" style="background-image: url(${chosenEvent.getNameAsImagePath()})"/>
                <div id="titleAndDescription" style="width: 100%">
                    <p><b>${chosenEvent.getTitle()}</b><br>${chosenEvent.getDescription()}</p>
                </div>
            </div>
            <br>

            <!-- Payment Form -->
            <!-- If the customer is not logged in then display un-filled form -->
            <c:if test="${!cookie.containsKey('memberId')}">
                <form class="detailsForm" action="checkout" method="POST">
                    <input type="hidden" name="bookingId" value="${booking.getId()}">
                    <input type="hidden" name="eventId" value="${chosenEvent.getId()}">
                    <table class="detailsTable">
                        <tr>
                            <td><input type="text" name="name" placeholder="Full Name" required></td>
                            <td><input type="email" name="email" placeholder="Email" required></td>
                        </tr>
                        <tr>
                            <td><input type="text" name="telephone" placeholder="Telephone"></td>
                            <td>
                                <select name="cardType">
                                    <option value="visaDebit">VISA Debit</option>
                                    <option value="visaCredit">VISA Credit</option>
                                    <option value="mastercard">Mastercard</option>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td><input type="text" name="addressLine1" placeholder="Address Line 1" required></td>
                            <td><input type="text" name="cardHolder" placeholder="Card Holder" required></td>
                        </tr>
                        <tr>
                            <td><input type="text" name="addressLine2" placeholder="Address Line 2" required></td>
                            <td><input type="text" name="cardNumber" placeholder="Card Number" required></td>
                        </tr>
                        <tr>
                            <td><input type="text" name="addressLine3" placeholder="Address Line 3" required></td>
                            <td><input type="text" name="cardSecurityCode" placeholder="Card Security Code" required></td>
                        </tr>
                        <tr>
                            <td><input type="text" name="addressCity" placeholder="City" required></td>
                            <td><input type="checkbox" name="sendTickets">Send tickets to address<br></td>
                        </tr>
                        <tr>
                            <td><input type="text" name="addressCountry" placeholder="Country" required></td>
                            <td>Due: ${invoice.getAmount()}</td>
                        </tr>
                        <tr>
                            <td><input type="text" name="addressPostcode" placeholder="Postcode" required></td>
                            <td><button type="submit" style="text-align: center">Finish & Pay</button></td>
                        </tr>
                    </table>
                </form>
            </c:if>
            <!-- If the customer is logged in then display filled form -->
            <c:if test="${cookie.containsKey('memberId')}">
                <form class="detailsForm" action="checkout" method="POST">
                    <input type="hidden" name="bookingId" value="${booking.getId()}">
                    <input type="hidden" name="eventId" value="${chosenEvent.getId()}">
                    <table class="detailsTable">
                        <tr>
                            <td><input type="text" name="name" placeholder="Full Name" value="${member.getName()}" required></td>
                            <td><input type="email" name="email" placeholder="Email" value="${member.getEmail()}" required></td>
                        </tr>
                        <tr>
                            <td><input type="text" name="telephone" placeholder="Telephone" value="${member.getTelephone()}"></td>
                            <td>
                                <select name="cardType">
                                    <option value="visaDebit">VISA Debit</option>
                                    <option value="visaCredit">VISA Credit</option>
                                    <option value="mastercard">Mastercard</option>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td><input type="text" name="addressLine1" placeholder="Address Line 1" value="${member.getAddress()[0]}" required></td>
                            <td><input type="text" name="cardHolder" placeholder="Card Holder" value="${memberCard.getHolder()}" required></td>
                        </tr>
                        <tr>
                            <td><input type="text" name="addressLine2" placeholder="Address Line 2" value="${member.getAddress()[1]}" required></td>
                            <td><input type="text" name="cardNumber" placeholder="Card Number" value="${memberCard.getNumber()}" required></td>
                        </tr>
                        <tr>
                            <td><input type="text" name="addressLine3" placeholder="Address Line 3" value="${member.getAddress()[2]}" required></td>
                            <td><input type="text" name="cardSecurityCode" placeholder="Card Security Code" required></td>
                        </tr>
                        <tr>
                            <td><input type="text" name="addressCity" placeholder="City" value="${member.getAddress()[3]}" required></td>
                            <td><input type="checkbox" name="sendTickets">Send tickets to address<br></td>
                        </tr>
                        <tr>
                            <td><input type="text" name="addressCountry" placeholder="Country" value="${member.getAddress()[4]}" required></td>
                            <td>Due: ${invoice.getAmount()}</td>
                        </tr>
                        <tr>
                            <td><input type="text" name="addressPostcode" placeholder="Postcode" value="${member.getAddress()[5]}" required></td>
                            <td><button type="submit" style="text-align: center">Finish & Pay</button></td>
                        </tr>
                    </table>
                </form>
            </c:if>

            <!-- Cancel checkout button -->
            <form class="detailsForm" action="home" method="post">
                <table class="detailsTable">
                    <tr><td><input type="hidden" name="bookingId" value="${booking.getId()}"></td><td><button type="submit" style="text-align: center">Cancel</button></td></tr>
                </table>
            </form>

        </div>

        <div class="pageFooter">
            <span>Copyright 2018</span>
        </div>
    </body>
</html>

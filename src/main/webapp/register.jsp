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
            <br><br>
            <h1>Registration Form</h1>

            <!-- Register form -->
            <form class="detailsForm" action="register" method="POST">
                <table class="detailsTable">
                    <tr>
                        <td><input type="text" name="name" placeholder="Full Name" required></td>
                        <td><input type="email" name="email" placeholder="Email" required></td>
                    </tr>
                    <tr>
                        <td><input type="text" name="telephone" placeholder="Telephone"></td>
                        <td><input type="password" name="password" placeholder="Password" required></td>
                    </tr>
                    <tr>
                        <td><input type="text" name="addressLine1" placeholder="Address Line 1" required></td>
                        <td>
                            <select name="cardType">
                                <option value="visaDebit">VISA Debit</option>
                                <option value="visaCredit">VISA Credit</option>
                                <option value="mastercard">Mastercard</option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td><input type="text" name="addressLine2" placeholder="Address Line 2" required></td>
                        <td><input type="text" name="cardHolder" placeholder="Card Holder" required></td>
                    </tr>
                    <tr>
                        <td><input type="text" name="addressLine3" placeholder="Address Line 3" required></td>
                        <td><input type="text" name="cardNumber" placeholder="Card Number" required></td>
                    </tr>
                    <tr>
                        <td><input type="text" name="addressCity" placeholder="City" required></td>
                        <td><input type="text" name="cardSecurityCode" placeholder="Card Security Code" required></td>
                    </tr>
                    <tr>
                        <td><input type="text" name="addressCountry" placeholder="Country" required></td>
                    </tr>
                    <tr>
                        <td><input type="text" name="addressPostcode" placeholder="Postcode" required></td>
                        <td><button type="submit" style="text-align: center">Register</button></td>
                    </tr>
                </table>
            </form>
        </div>

        <div class="pageFooter">
            <span>Copyright 2018</span>
        </div>
    </body>
</html>

package com.TicketIT.Servlets;

import com.TicketIT.DataAccessObject.*;
import com.TicketIT.Model.Booking;
import com.TicketIT.Model.Card;
import com.TicketIT.Model.Customer;
import com.TicketIT.Model.Invoice;
import com.mongodb.MongoClient;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "PurchaseServlet")
public class PurchaseServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // Get required DAOs
        MongoClient mongo = (MongoClient) request.getServletContext().getAttribute("MONGO_CLIENT");
        MongoDBEventDAO eventDAO = new MongoDBEventDAO(mongo);
        MongoDBBookingDAO bookingDAO = new MongoDBBookingDAO(mongo);
        MongoDBInvoiceDAO invoiceDAO = new MongoDBInvoiceDAO(mongo);
        MongoDBCustomerDAO customerDAO = new MongoDBCustomerDAO(mongo);
        MongoDBCardDAO cardDAO = new MongoDBCardDAO(mongo);

        Booking booking = bookingDAO.GetBookingById(request.getParameter("bookingId"));
        Invoice invoice = invoiceDAO.GetInvoiceById(booking.getInvoiceId());

        // Populate Customer
        Customer customer = customerDAO.CreateCustomer(new Customer());
        customer.setName(request.getParameter("name"));
        customer.setTelephone(request.getParameter("telephone"));
        customer.setAddress(request.getParameter("addressLine1") + "," +
                request.getParameter("addressLine2") + "," +
                request.getParameter("addressLine3") + "," +
                request.getParameter("addressCity") + "," +
                request.getParameter("addressCountry") + "," +
                request.getParameter("addressPostcode") + ",");


        // Populate Card
        Card card = cardDAO.CreateCard(new Card());
        card.setType(request.getParameter("cardType"));
        card.setHolder(request.getParameter("cardHolder"));
        card.setNumber(request.getParameter("cardNumber"));
        card.setSecurityCode(request.getParameter("cardSecurityCode"));

        // Update Booking and Invoice
        booking.setCustomerId(customer.getId());
        booking.setSendTickets(Boolean.parseBoolean(request.getParameter("sendTickets")));
        invoice.setPaid(true);
        invoice.setCardId(card.getId());

        cardDAO.UpdateCard(card);
        customerDAO.UpdateCustomer(customer);
        bookingDAO.UpdateBooking(booking);
        invoiceDAO.UpdateInvoice(invoice);

        request.setAttribute("chosenEvent", eventDAO.GetEventById(request.getParameter("eventId")));
        request.getRequestDispatcher("/purchase.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}
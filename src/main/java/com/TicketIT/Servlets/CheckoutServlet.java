package com.TicketIT.Servlets;

import com.TicketIT.DataAccessObject.MongoDBBookingDAO;
import com.TicketIT.DataAccessObject.MongoDBEventDAO;
import com.TicketIT.DataAccessObject.MongoDBInvoiceDAO;
import com.TicketIT.DataAccessObject.MongoDBTicketDAO;
import com.TicketIT.Model.Booking;
import com.TicketIT.Model.Event;
import com.TicketIT.Model.Invoice;
import com.TicketIT.Model.Ticket;
import com.mongodb.MongoClient;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@WebServlet(name = "CheckoutServlet")
public class CheckoutServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // Get required DAOs
        MongoClient mongo = (MongoClient) request.getServletContext().getAttribute("MONGO_CLIENT");
        MongoDBEventDAO eventDAO = new MongoDBEventDAO(mongo);
        MongoDBTicketDAO ticketDAO = new MongoDBTicketDAO(mongo);
        MongoDBBookingDAO bookingDAO = new MongoDBBookingDAO(mongo);
        MongoDBInvoiceDAO invoiceDAO = new MongoDBInvoiceDAO(mongo);

        // Create new Booking & Invoice and store in database.
        Booking booking = bookingDAO.CreateBooking(new Booking());
        Invoice invoice = invoiceDAO.CreateInvoice(new Invoice());
        invoice.setAmount(0.0); // Set default amount.

        // Adds the selected ticket ids to a list.
        ArrayList<String> chosenTicketIds = new ArrayList<>();
        for(Ticket ticket : ticketDAO.GetAllTickets()){
            if(!ticket.getEventId().equals(request.getParameter("eventId")))
                continue;

            int amountToBuy = Integer.parseInt(request.getParameter(ticket.getId()));
            if(amountToBuy > 0){
                for(int i = 0; i < amountToBuy; i++){
                    chosenTicketIds.add(ticket.getId());
                    ticket.setNumberAvailable(ticket.getNumberAvailable() - 1);
                    invoice.setAmount(invoice.getAmount() + ticket.getPrice());
                }
                ticketDAO.UpdateTicket(ticket);
            }
        }

        // Set tickets and invoice id for booking.
        booking.setTickets(chosenTicketIds);
        booking.setInvoiceId(invoice.getId());

        // Update Booking and Invoice in database.
        invoiceDAO.UpdateInvoice(invoice);
        bookingDAO.UpdateBooking(booking);

        request.setAttribute("booking", bookingDAO.GetBooking(booking));
        request.setAttribute("invoice", invoiceDAO.GetInvoice(invoice));
        request.setAttribute("chosenEvent", eventDAO.GetEventById(request.getParameter("eventId")));
        request.getRequestDispatcher("/checkout.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}

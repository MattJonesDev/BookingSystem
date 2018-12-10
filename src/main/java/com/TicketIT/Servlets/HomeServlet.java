package com.TicketIT.Servlets;

import com.TicketIT.DataAccessObject.MongoDBBookingDAO;
import com.TicketIT.DataAccessObject.MongoDBEventDAO;
import com.TicketIT.DataAccessObject.MongoDBInvoiceDAO;
import com.TicketIT.DataAccessObject.MongoDBTicketDAO;
import com.TicketIT.Model.Booking;
import com.TicketIT.Model.Event;
import com.TicketIT.Model.Ticket;
import com.mongodb.MongoClient;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "HomeServlet")
public class HomeServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // Get all events from database.
        MongoClient mongo = (MongoClient) request.getServletContext().getAttribute("MONGO_CLIENT");
        MongoDBEventDAO eventDAO = new MongoDBEventDAO(mongo);
        List<Event> events = eventDAO.GetAllEvents();

        // If a bookingId is provided with POST request, cancel it.
        if(request.getParameter("bookingId") != null){
            // Get required DAOs
            MongoDBBookingDAO bookingDAO = new MongoDBBookingDAO(mongo);
            MongoDBTicketDAO ticketDAO = new MongoDBTicketDAO(mongo);
            MongoDBInvoiceDAO invoiceDAO = new MongoDBInvoiceDAO(mongo);

            // Make the tickets within the booking available.
            Booking booking = bookingDAO.GetBookingById(request.getParameter("bookingId"));
            for(String ticketId : booking.getTickets()){
                Ticket ticket = ticketDAO.GetTicketById(ticketId);
                ticket.setNumberAvailable(ticket.getNumberAvailable() + 1);
                ticketDAO.UpdateTicket(ticket);
            }

            // Delete the booking & invoice.
            invoiceDAO.DeleteInvoice(invoiceDAO.GetInvoiceById(bookingDAO.GetBookingById(request.getParameter("bookingId")).getInvoiceId()));
            bookingDAO.DeleteBooking(bookingDAO.GetBookingById(request.getParameter("bookingId")));
        }

        request.setAttribute("eventList", events);
        request.getRequestDispatcher("/home.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // Get all events from database.
        MongoClient mongo = (MongoClient) request.getServletContext().getAttribute("MONGO_CLIENT");
        MongoDBEventDAO eventDAO = new MongoDBEventDAO(mongo);
        List<Event> events = eventDAO.GetAllEvents();

        request.setAttribute("eventList", events);
        request.getRequestDispatcher("/home.jsp").forward(request, response);
    }
}

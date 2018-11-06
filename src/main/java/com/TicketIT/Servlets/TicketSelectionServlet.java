package com.TicketIT.Servlets;

import com.TicketIT.DataAccessObject.MongoDBEventDAO;
import com.TicketIT.DataAccessObject.MongoDBTicketDAO;
import com.TicketIT.Model.Event;
import com.TicketIT.Model.Ticket;
import com.mongodb.MongoClient;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "TicketSelectionServlet")
public class TicketSelectionServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // Get all events from database.
        MongoClient mongo = (MongoClient) request.getServletContext().getAttribute("MONGO_CLIENT");
        MongoDBTicketDAO ticketDAO = new MongoDBTicketDAO(mongo);
        MongoDBEventDAO eventDAO = new MongoDBEventDAO(mongo);
        List<Ticket> tickets = ticketDAO.GetAllTickets();

        // ToDo: Move logic somewhere else? Simplify?
        ArrayList<Ticket> eventTickets = new ArrayList<Ticket>();
        for(Ticket ticket : tickets){
            if(ticket.getEventId().equals(request.getParameter("eventId")))
                eventTickets.add(ticket);
        }

        // Get event
        Event chosenEvent = new Event();
        chosenEvent.setId(request.getParameter("eventId"));

        request.setAttribute("chosenEvent", eventDAO.GetEvent(chosenEvent));
        request.setAttribute("eventTicketsList", eventTickets);
        request.getRequestDispatcher("/ticketSelection.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}

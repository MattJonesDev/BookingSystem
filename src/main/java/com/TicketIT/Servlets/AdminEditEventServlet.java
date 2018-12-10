package com.TicketIT.Servlets;

import com.TicketIT.DataAccessObject.MongoDBEventDAO;
import com.TicketIT.DataAccessObject.MongoDBMemberDAO;
import com.TicketIT.DataAccessObject.MongoDBTicketDAO;
import com.TicketIT.Model.Event;
import com.TicketIT.Model.Ticket;
import com.TicketIT.Utils.AdminUtils;
import com.mongodb.MongoClient;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 1024 * 1024 * 5, maxRequestSize = 1024 * 1024 * 5 * 5)
@WebServlet(name = "AdminEditEventServlet")
public class AdminEditEventServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        MongoClient mongo = (MongoClient) request.getServletContext().getAttribute("MONGO_CLIENT");
        MongoDBMemberDAO memberDAO = new MongoDBMemberDAO(mongo);

        // Validate that the user is allowed to be here.
        if(!AdminUtils.IsMemberAllowedAccess(memberDAO, request.getCookies()))
            response.sendRedirect(request.getContextPath());

        // An eventId must be supplied in order to edit it.
        if(!request.getParameterMap().containsKey("eventId"))
            response.sendRedirect(request.getContextPath() + "/admin");

        MongoDBTicketDAO ticketDAO = new MongoDBTicketDAO(mongo);
        if (request.getParameterMap().containsKey("action")) {
            if (request.getParameter("action").equals("editEvent")) {
                // Update the event in the database.
                MongoDBEventDAO eventDAO = new MongoDBEventDAO(mongo);
                Event event = eventDAO.GetEventById(request.getParameter("eventId"));
                event.setTitle(request.getParameter("eventTitle"));
                event.setDescription(request.getParameter("eventDesc"));
                event.setDate(request.getParameter("eventDate"));
                event.setTime(request.getParameter("eventTime"));
                eventDAO.UpdateEvent(event);

                // Upload new event image.
                String uploadPath = getServletContext().getRealPath("") + File.separator + "images";
                for (Part part : request.getParts()) {
                    part.write(uploadPath + File.separator +
                        (event.getTitle().toLowerCase().replaceAll("\\s+","")));
                }
            } else if (request.getParameter("action").equals("createTicket")) {
                // Create a new ticket in database.
                Ticket ticket = ticketDAO.CreateTicket(new Ticket());
                ticket.setName(request.getParameter("ticketName"));
                ticket.setPrice(Double.parseDouble(request.getParameter("ticketPrice")));
                ticket.setNumberAvailable(Integer.parseInt(request.getParameter("ticketAvailable")));
                ticketDAO.UpdateTicket(ticket);
            } else if (request.getParameter("action").equals("deleteTicket")) {
                // Delete the chosen ticket from database.
                Ticket ticket = ticketDAO.GetTicketById(request.getParameter("ticketId"));
                ticketDAO.DeleteTicket(ticket);
            }
        }

        // After the action has been handled, return to edit page.
        request.setAttribute("eventId", request.getParameter("eventId"));
        request.getRequestDispatcher("/adminDashboardEdit.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        MongoClient mongo = (MongoClient) request.getServletContext().getAttribute("MONGO_CLIENT");
        MongoDBMemberDAO memberDAO = new MongoDBMemberDAO(mongo);

        // Validate that the user is allowed to be here.
        if(!AdminUtils.IsMemberAllowedAccess(memberDAO, request.getCookies()))
            response.sendRedirect(request.getContextPath());

        // If an eventId has not been supplied, it cannot be edited.
        if(!request.getParameterMap().containsKey("eventId"))
            response.sendRedirect(request.getContextPath());

        // Retrieve a list of tickets for the event.
        MongoDBTicketDAO ticketDAO = new MongoDBTicketDAO(mongo);
        List<Ticket> eventTickets = new ArrayList<>();
        for(Ticket ticket : ticketDAO.GetAllTickets()){
            if(ticket.getEventId().equals(request.getParameter("eventId")))
                eventTickets.add(ticket);
        }

        request.setAttribute("eventId", request.getParameter("eventId"));
        request.setAttribute("eventTickets", eventTickets);
        request.getRequestDispatcher("/adminDashboardEdit.jsp").forward(request, response);
    }
}

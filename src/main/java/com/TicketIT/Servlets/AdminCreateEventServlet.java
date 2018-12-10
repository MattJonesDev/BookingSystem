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

@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 1024 * 1024 * 5, maxRequestSize = 1024 * 1024 * 5 * 5)
@WebServlet(name = "AdminCreateEventServlet")
public class AdminCreateEventServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        MongoClient mongo = (MongoClient) request.getServletContext().getAttribute("MONGO_CLIENT");
        MongoDBMemberDAO memberDAO = new MongoDBMemberDAO(mongo);

        // Validate that the user is allowed to be here.
        if(!AdminUtils.IsMemberAllowedAccess(memberDAO, request.getCookies()))
            response.sendRedirect(request.getContextPath());

        // Create new Event Object
        MongoDBEventDAO eventDAO = new MongoDBEventDAO(mongo);
        Event event = eventDAO.CreateEvent(new Event());
        event.setTitle(request.getParameter("eventTitle"));
        event.setDescription(request.getParameter("eventDesc"));
        event.setDate(request.getParameter("eventDate"));
        event.setTime(request.getParameter("eventTime"));

        // Create new Ticket Object.
        MongoDBTicketDAO ticketDAO = new MongoDBTicketDAO(mongo);
        Ticket ticket = ticketDAO.CreateTicket(new Ticket());
        ticket.setName(request.getParameter("ticketName"));
        ticket.setPrice(Double.parseDouble(request.getParameter("ticketPrice")));
        ticket.setNumberAvailable(Integer.parseInt(request.getParameter("ticketAvailable")));
        ticket.setEventId(event.getId());

        // Update Event & Ticket objects in database.
        eventDAO.UpdateEvent(event);
        ticketDAO.UpdateTicket(ticket);

        // Upload image on to the local disk.
        String uploadPath = getServletContext().getRealPath("") + File.separator + "images";
        for (Part part : request.getParts()) {
            part.write(uploadPath + File.separator +
                    (event.getTitle().toLowerCase().replaceAll("\\s+","")));
        }

        // Redirect back to admin dashboard.
        request.getRequestDispatcher("/adminDashboard.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        MongoClient mongo = (MongoClient) request.getServletContext().getAttribute("MONGO_CLIENT");
        MongoDBMemberDAO memberDAO = new MongoDBMemberDAO(mongo);

        // Validate that the user is allowed to be here.
        if(!AdminUtils.IsMemberAllowedAccess(memberDAO, request.getCookies()))
            response.sendRedirect(request.getContextPath());

        request.getRequestDispatcher("/adminDashboardCreate.jsp").forward(request, response);
    }
}

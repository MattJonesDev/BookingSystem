package com.TicketIT.Servlets;

import com.TicketIT.DataAccessObject.MongoDBEventDAO;
import com.TicketIT.DataAccessObject.MongoDBMemberDAO;
import com.TicketIT.Model.Event;
import com.TicketIT.Utils.AdminUtils;
import com.mongodb.MongoClient;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "AdminDashboardServlet")
public class AdminDashboardServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        MongoClient mongo = (MongoClient) request.getServletContext().getAttribute("MONGO_CLIENT");
        MongoDBMemberDAO memberDAO = new MongoDBMemberDAO(mongo);

        // Validate that the user is allowed to be here.
        if(!AdminUtils.IsMemberAllowedAccess(memberDAO, request.getCookies()))
            response.sendRedirect(request.getContextPath());

        // If there's a action request, process it.
        if(request.getParameterMap().containsKey("action")){
            if(request.getParameter("action").equals("createEvent")){
                request.getRequestDispatcher("/adminDashboardCreate.jsp").forward(request, response);
            } else if(request.getParameter("action").equals("editEvent")){
                request.setAttribute("eventId", request.getParameter("eventId"));
                request.getRequestDispatcher("/adminDashboardEdit.jsp").forward(request, response);
            }
        }

        request.getRequestDispatcher("/adminDashboard.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        MongoClient mongo = (MongoClient) request.getServletContext().getAttribute("MONGO_CLIENT");
        MongoDBMemberDAO memberDAO = new MongoDBMemberDAO(mongo);

        // Validate that the user is allowed to be here.
        if(!AdminUtils.IsMemberAllowedAccess(memberDAO, request.getCookies()))
            response.sendRedirect(request.getContextPath());

        // Get all events from database.
        MongoDBEventDAO eventDAO = new MongoDBEventDAO(mongo);
        List<Event> events = eventDAO.GetAllEvents();

        request.setAttribute("eventList", events);
        request.getRequestDispatcher("/home.jsp").forward(request, response);
    }
}

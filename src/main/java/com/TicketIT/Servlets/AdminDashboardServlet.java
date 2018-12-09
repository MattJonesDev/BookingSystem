package com.TicketIT.Servlets;

import com.TicketIT.DataAccessObject.MongoDBEventDAO;
import com.TicketIT.DataAccessObject.MongoDBMemberDAO;
import com.TicketIT.Model.Event;
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

        // If they are not allowed here, redirect them to the homepage.
        if(!IsMemberAllowedAccess(memberDAO, request.getCookies()))
            response.sendRedirect(request.getContextPath());


        // ToDo: Admin Dash - Handle all the different actions of the dashboard.
        if(request.getParameter("action").equals("createEvent")){
            CreateEvent();
        } else if(request.getParameter("action").equals("createTicket")){
            CreateTicket();
        } else if(request.getParameter("action").equals("deleteEvent")){
            DeleteEvent("");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        MongoClient mongo = (MongoClient) request.getServletContext().getAttribute("MONGO_CLIENT");
        MongoDBMemberDAO memberDAO = new MongoDBMemberDAO(mongo);

        // If they are not allowed here, redirect them to the homepage.
        if(!IsMemberAllowedAccess(memberDAO, request.getCookies()))
            response.sendRedirect(request.getContextPath());

        // Get all events from database.
        MongoDBEventDAO eventDAO = new MongoDBEventDAO(mongo);
        List<Event> events = eventDAO.GetAllEvents();

        request.setAttribute("eventList", events);
        request.getRequestDispatcher("/home.jsp").forward(request, response);
    }

    public void CreateEvent(){
        // ToDo: Admin Dash - Create Event Function.
        // Create a new event object.
        // Upload event image to local disk.
        // Update Mongo through DAO.
    }

    public void CreateTicket(){
        // ToDo: Admin Dash - Create Ticket Function.
        // Create a new ticket object for event.
        // Update Mongo through DAO.
    }

    public void DeleteEvent(String eventId){
        // ToDo: Admin Dash - Delete Event Function.
        // Delete event from Mongo
        // Delete event image from local disk.
    }

    private Boolean IsMemberAllowedAccess(MongoDBMemberDAO memberDAO, Cookie[] cookies){

        try{
            // Find the memberId cookie
            String cookieMemberId = "none";
            for(Cookie cookie : cookies){
                if(cookie.getName().equals("memberId"))
                    cookieMemberId = cookie.getValue();
            }

            // If the memberId cookie doesn't exist, they are not allowed.
            if(cookieMemberId.equals("none"))
                return false;

            // If the member is not an admin, they are not allowed here.
            if(!memberDAO.GetMemberById(cookieMemberId).getIsAdmin())
                return false;

            // If the member is an admin, they are allowed.
            if(memberDAO.GetMemberById(cookieMemberId).getIsAdmin())
                return true;
        }catch(Exception ex){
            // If there is any error, they are not allowed here.
            return false;
        }

        return false;
    }
}

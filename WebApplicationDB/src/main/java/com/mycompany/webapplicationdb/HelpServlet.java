package com.mycompany.webapplicationdb;

import java.io.IOException;
import java.sql.Connection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "HelpServlet", urlPatterns = {"/help"})
public class HelpServlet extends HttpServlet {

    private JDBC jdbc;

    @Override
    public void init() throws ServletException {
        jdbc = new JDBC("3306", "social_media", "root", "admin");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  {
            RequestDispatcher dispatcher = request.getRequestDispatcher("/views/help.jsp");
            dispatcher.forward(request, response);

    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String message = request.getParameter("post_content");

        System.out.println(message);
        // Get username
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username"); // Ensure user is logged in
        
        // Send to admin_messages
        boolean successfulInsert = jdbc.insertAdminMessage(username, message);
        
        RequestDispatcher dispatcher = request.getRequestDispatcher("/views/help.jsp");
        if(successfulInsert) {
            dispatcher.forward(request, response);
        }
    }
}

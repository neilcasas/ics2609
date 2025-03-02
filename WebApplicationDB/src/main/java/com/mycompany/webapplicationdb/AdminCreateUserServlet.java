package com.mycompany.webapplicationdb;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "AdminCreateUserServlet", urlPatterns = {"/adminCreateUser"})
public class AdminCreateUserServlet extends HttpServlet {

    private JDBC jdbc;

    @Override
    public void init() throws ServletException {
        jdbc = new JDBC("3306", "social_media", "root", "admin");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        RequestDispatcher dispatcher = request.getRequestDispatcher("/views/admin/create.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String userRole = "user"; // Default role

        try {
            // 🔴 Check if the number of users exceeds the limit
            int userCount = jdbc.getUserCount();
            if (userCount >= 5) {
                request.setAttribute("error", "Maximum user limit (5) reached. Cannot create a new account.");
                RequestDispatcher dispatcher = request.getRequestDispatcher("/views/admin/create.jsp");
                dispatcher.forward(request, response);
                return;
            }

            // ✅ Proceed with user creation
            jdbc.createUser(username, password, userRole);
            response.sendRedirect("admin");  // Redirect to admin page after successful signup
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "Error creating account: " + e.getMessage());
            RequestDispatcher dispatcher = request.getRequestDispatcher("/views/admin/create.jsp");
            dispatcher.forward(request, response);
        }
    }
}
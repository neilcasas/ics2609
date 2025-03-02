package com.mycompany.webapplicationdb;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "AdminEditUserServlet", urlPatterns = {"/adminEditUser"})
public class AdminEditUserServlet extends HttpServlet {

    private JDBC jdbc;

    @Override
    public void init() throws ServletException {
        jdbc = new JDBC("3306", "social_media", "root", "admin");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        request.setAttribute("username", username);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/views/admin/edit.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String originalUsername = request.getParameter("original_username");
        String newUsername = request.getParameter("username");
        String newPassword = request.getParameter("password");

        try {
            jdbc.updateUser(originalUsername, newUsername, newPassword);
            response.sendRedirect(request.getContextPath() + "/adminSuccess");
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "Error updating user: " + e.getMessage());
            RequestDispatcher dispatcher = request.getRequestDispatcher("/views/admin/edit.jsp");
            dispatcher.forward(request, response);
        }
    }
}
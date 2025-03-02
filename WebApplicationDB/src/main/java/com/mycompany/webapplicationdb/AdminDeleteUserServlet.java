package com.mycompany.webapplicationdb;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "AdminDeleteUserServlet", urlPatterns = {"/adminDeleteUser"})
public class AdminDeleteUserServlet extends HttpServlet {

    private JDBC jdbc;

    @Override
    public void init() throws ServletException {
        jdbc = new JDBC("3306", "social_media", "root", "admin");
    }

    // GET: Display the delete page with all users having role "user"
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<String> usernames = new ArrayList<>();
        try {
            ResultSet rs = jdbc.getUsersByRole("user");
            while (rs.next()) {
                String user = rs.getString("user_name"); // use "user_name" since that's the column name
                usernames.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "Error fetching users: " + e.getMessage());
        }
        request.setAttribute("usernames", usernames);
        
        RequestDispatcher dispatcher = request.getRequestDispatcher("/views/admin/delete.jsp");
        dispatcher.forward(request, response);
    }

    // POST: Delete the selected user and refresh the page
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Use the same parameter name as in delete.jsp: "delete_user"
        String usernameToDelete = request.getParameter("delete_user");
        try {
            jdbc.deleteUser(usernameToDelete);
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "Error deleting user: " + e.getMessage());
        }
        // Refresh the page by redirecting to the GET endpoint
        response.sendRedirect(request.getContextPath() + "/adminDeleteUser");
    }
}
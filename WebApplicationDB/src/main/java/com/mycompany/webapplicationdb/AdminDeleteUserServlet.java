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

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<User> users = new ArrayList<>();
        try {
            ResultSet rs = jdbc.getUsersByRole("user");

            while (rs.next()) {
                String username = rs.getString("user_name");
                String password = rs.getString("password");
                String role = rs.getString("user_role");

                users.add(new User(username, password, role));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "Error fetching users: " + e.getMessage());
        }
        request.setAttribute("users", users);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/views/admin/delete.jsp");
        dispatcher.forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String[] usersToDelete = request.getParameterValues("delete_users");

        List<User> deletedUsers = new ArrayList<>();

        if (usersToDelete != null) {
            for (String username : usersToDelete) {
                try {
                    // Fetch user details before deletion
                    ResultSet rs = jdbc.getUser(username); // Use the existing `jdbc` instance
                    if (rs.next()) {
                        String password = rs.getString("password");
                        String role = rs.getString("user_role");

                        // Store user details before deleting
                        deletedUsers.add(new User(username, password, role));

                        // Now delete the user
                        jdbc.deleteUser(username);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    request.setAttribute("error", "Error deleting user: " + e.getMessage());
                }
            }
        }

        request.setAttribute("deletedUsers", deletedUsers);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/views/admin/deleteResult.jsp");
        dispatcher.forward(request, response);
    }

}
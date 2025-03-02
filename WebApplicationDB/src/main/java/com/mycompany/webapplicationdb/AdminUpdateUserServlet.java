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

@WebServlet(name = "AdminUpdateUserServlet", urlPatterns = {"/adminUpdateUser"})
public class AdminUpdateUserServlet extends HttpServlet {

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

        // Forward to update.jsp
        RequestDispatcher dispatcher = request.getRequestDispatcher("/views/admin/update.jsp");
        dispatcher.forward(request, response);
    }

    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String selectedUser = request.getParameter("selectedUser");
        String newUsername = request.getParameter("newUsername");
        String newPassword = request.getParameter("newPassword");
        String newRole = request.getParameter("newRole");

        if (selectedUser == null || selectedUser.isEmpty()) {
            request.setAttribute("error", "No user selected for update.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/views/admin/update.jsp");
            dispatcher.forward(request, response);
            return;
        }

        try {
            // Check if at least one field is provided for update
            if ((newUsername == null || newUsername.isEmpty()) &&
                (newPassword == null || newPassword.isEmpty()) &&
                (newRole == null || newRole.isEmpty())) {
                request.setAttribute("error", "No new values provided for update.");
                RequestDispatcher dispatcher = request.getRequestDispatcher("/views/admin/update.jsp");
                dispatcher.forward(request, response);
                return;
            }

            // Check if the new username already exists to prevent duplicates
            if (newUsername != null && !newUsername.isEmpty() && jdbc.usernameExists(newUsername)) {
                request.setAttribute("error", "Username already exists!");
                RequestDispatcher dispatcher = request.getRequestDispatcher("/views/admin/update.jsp");
                dispatcher.forward(request, response);
                return;
            }

            // Retrieve current user details from the database
            ResultSet rs = jdbc.getUser(selectedUser);
            String currentUsername = null, currentPassword = null, currentRole = null;

            if (rs.next()) {
                currentUsername = rs.getString("user_name");
                currentPassword = rs.getString("password");
                currentRole = rs.getString("user_role");
            } else {
                request.setAttribute("error", "User not found.");
                RequestDispatcher dispatcher = request.getRequestDispatcher("/views/admin/update.jsp");
                dispatcher.forward(request, response);
                return;
            }

            // Set default values if fieldset is empty
            if (newUsername == null || newUsername.isEmpty()) {
                newUsername = currentUsername;
            }
            if (newPassword == null || newPassword.isEmpty()) {
                newPassword = currentPassword;
            }
            if (newRole == null || newRole.isEmpty()) {
                newRole = currentRole;
            }

            // If no changes are made, inform
            if (newUsername.equals(currentUsername) &&
                newPassword.equals(currentPassword) &&
                newRole.equals(currentRole)) {
                request.setAttribute("error", "No changes detected.");
                RequestDispatcher dispatcher = request.getRequestDispatcher("/views/admin/update.jsp");
                dispatcher.forward(request, response);
                return;
            }

            // Update user in the database
            jdbc.updateUser(selectedUser, newUsername, newPassword, newRole);

            // Prepare updated user list with only the modified user
            List<User> updatedUsers = new ArrayList<>();
            updatedUsers.add(new User(newUsername, newPassword, newRole));
            request.setAttribute("updatedUsers", updatedUsers);

            // Redirect to result page
            RequestDispatcher dispatcher = request.getRequestDispatcher("/views/admin/updateResult.jsp");
            dispatcher.forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "Error updating user: " + e.getMessage());
            RequestDispatcher dispatcher = request.getRequestDispatcher("/views/admin/update.jsp");
            dispatcher.forward(request, response);
        }
    }
}
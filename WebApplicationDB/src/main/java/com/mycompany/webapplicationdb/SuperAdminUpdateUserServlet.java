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

@WebServlet(name = "SuperAdminUpdateUserServlet", urlPatterns = {"/superAdminUpdateUser"})
public class SuperAdminUpdateUserServlet extends HttpServlet {

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
            ResultSet rsUsers = jdbc.getUsersByRole("user");
            ResultSet rsAdmins = jdbc.getUsersByRole("admin");

            while (rsUsers.next()) {
                users.add(new User(rsUsers.getString("user_name"), rsUsers.getString("password"), "user"));
            }
            while (rsAdmins.next()) {
                users.add(new User(rsAdmins.getString("user_name"), rsAdmins.getString("password"), "admin"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "Error fetching users: " + e.getMessage());
        }

        request.setAttribute("users", users);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/views/super_admin/update.jsp");
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
            request.getRequestDispatcher("/views/super_admin/update.jsp").forward(request, response);
            return;
        }

        try {
            if ((newUsername == null || newUsername.isEmpty()) &&
                (newPassword == null || newPassword.isEmpty()) &&
                (newRole == null || newRole.isEmpty())) {
                request.setAttribute("error", "No new values provided for update.");
                request.getRequestDispatcher("/views/super_admin/update.jsp").forward(request, response);
                return;
            }

            // Validate new role input (should be either "user" or "admin")
            if (newRole != null && !newRole.isEmpty() &&
                !newRole.equalsIgnoreCase("user") && !newRole.equalsIgnoreCase("admin")) {
                request.setAttribute("error", "Invalid role. Must be 'user' or 'admin'.");
                request.getRequestDispatcher("/views/super_admin/update.jsp").forward(request, response);
                return;
            }

            // Check if the new username exists
            if (newUsername != null && !newUsername.isEmpty() && jdbc.usernameExists(newUsername)) {
                request.setAttribute("error", "Username already exists!");
                request.getRequestDispatcher("/views/super_admin/update.jsp").forward(request, response);
                return;
            }

            // Fetch current user details
            ResultSet rs = jdbc.getUser(selectedUser);
            String currentUsername = null, currentPassword = null, currentRole = null;

            if (rs.next()) {
                currentUsername = rs.getString("user_name");
                currentPassword = rs.getString("password");
                currentRole = rs.getString("user_role");
            } else {
                request.setAttribute("error", "User not found.");
                request.getRequestDispatcher("/views/super_admin/update.jsp").forward(request, response);
                return;
            }

            // Assign current values if fields are empty
            if (newUsername == null || newUsername.isEmpty()) newUsername = currentUsername;
            if (newPassword == null || newPassword.isEmpty()) newPassword = currentPassword;
            if (newRole == null || newRole.isEmpty()) newRole = currentRole;

            // If no changes detected, return
            if (newUsername.equals(currentUsername) &&
                newPassword.equals(currentPassword) &&
                newRole.equals(currentRole)) {
                request.setAttribute("error", "No changes detected.");
                request.getRequestDispatcher("/views/super_admin/update.jsp").forward(request, response);
                return;
            }

            // Update the user in the database
            jdbc.updateUser(selectedUser, newUsername, newPassword, newRole);

            // Prepare updated user list
            List<User> updatedUsers = new ArrayList<>();
            updatedUsers.add(new User(newUsername, newPassword, newRole));
            request.setAttribute("updatedUsers", updatedUsers);

            // Redirect to result page
            request.getRequestDispatcher("/views/super_admin/updateResult.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "Error updating user: " + e.getMessage());
            request.getRequestDispatcher("/views/super_admin/update.jsp").forward(request, response);
        }
    }
}

package com.mycompany.webapplicationdb;

import java.io.BufferedReader;
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

        BufferedReader reader = request.getReader();
        StringBuilder requestBody = new StringBuilder();
        String line;

        while ((line = reader.readLine()) != null) {
            requestBody.append(line);
        }

        // Convert to string
        String payload = requestBody.toString();

        String[] userPairs = payload.split(",");
        List<String> updated = new ArrayList<>();

        List<String> usernameUpdates = new ArrayList<>();

        for (String pair : userPairs) {
            String[] userPair = pair.split(":");
            if (userPair.length == 2) {
                String field = userPair[0].split("-")[0];
                String user = userPair[0].split("-")[1];
                String value = userPair[1].trim();

                System.out.println("User: " + user + " Field: " + field + " Value: " + value);
                updated.add("User: " + user + " Field: " + field + " Value: " + value);

                try {
                    if ("user_name".equals(field)) {
                        // Store username updates separately to execute them later
                        usernameUpdates.add(pair);
                    } else {
                        // Execute all other updates immediately
                        switch (field) {
                            case "password":
                                jdbc.updatePassword(user, value);
                                break;
                            case "user_role":
                                if ("user".equals(value) || "admin".equals(value)) {
                                    jdbc.updateRole(user, value);
                                }
                                break;
                            default:
                                System.out.println("Error");
                                break;
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        for (String pair : usernameUpdates) {
            String[] userPair = pair.split(":");
            String user = userPair[0].split("-")[1];
            String value = userPair[1].trim();

            try {
                jdbc.updateUsername(user, value);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        response.setStatus(HttpServletResponse.SC_OK);
        request.setAttribute("updated", updated);
        System.out.println("updated list: " + updated);
        request.getRequestDispatcher("/views/admin/updateResult.jsp").forward(request, response);
    }
}

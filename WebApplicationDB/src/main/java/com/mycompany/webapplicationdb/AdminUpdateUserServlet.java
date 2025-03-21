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
        System.out.println("Received Payload: " + payload); // Debugging

        // Split by commas
        String[] keyValuePairs = payload.split(",");

        // Process key-value pairs
        for (String pair : keyValuePairs) {
            String[] entry = pair.split(":");
            if (entry.length == 2) {
                String key = entry[0].trim();
                String value = entry[1].trim();
                System.out.println("Key: " + key + ", Value: " + value);
            }
        }

        // Send a response
        response.setContentType("text/plain");
        response.getWriter().write("Received successfully!");
    }
}

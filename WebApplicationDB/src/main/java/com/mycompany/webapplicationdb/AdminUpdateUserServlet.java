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
        List<String> usernames = new ArrayList<>();
        try {
            ResultSet rs = jdbc.getUsersByRole("user");
            while (rs.next()) {
                String user = rs.getString("user_name");
                usernames.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "Error fetching users: " + e.getMessage());
        }
        request.setAttribute("usernames", usernames);

        // Forward to the update.jsp page which lists all users
        RequestDispatcher dispatcher = request.getRequestDispatcher("/views/admin/update.jsp");
        dispatcher.forward(request, response);
    }
}

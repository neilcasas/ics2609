package com.mycompany.webapplicationdb;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.RequestDispatcher;

@WebServlet(name = "AdminServlet", urlPatterns = {"/admin"})
public class AdminServlet extends HttpServlet {

    private JDBC jdbc;

    @Override
    public void init() throws ServletException {
        jdbc = new JDBC("3306", "social_media", "root", "admin");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");

        if (username == null) {
            response.sendRedirect("login.jsp"); // Redirect to login page if not logged in
            return;
        }

        List<String> usernames = new ArrayList<>();

        try {
            ResultSet rs = jdbc.getUsersByRole("user"); 

            while (rs.next()) {
                String user = rs.getString("user_name");
                usernames.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        request.setAttribute("usernames", usernames);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/views/admin/admin.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Landing page displaying only users.";
    }
}
package com.mycompany.webapplicationdb;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "LoginServlet", urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {

    private JDBC jdbc;

    @Override
    public void init() throws ServletException {
        jdbc = new JDBC("3306", "social_media", "root", "admin");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try {
            ResultSet rs = jdbc.getUser(username, password);

            if (rs.next()) {
                HttpSession session = request.getSession();
                session.setAttribute("username", username);

                String userRole = rs.getString("user_role");
                session.setAttribute("user_role", userRole);
                System.out.println(userRole);

                switch (userRole) {
                    case "admin":
                        response.sendRedirect("admin");
                        break;
                    case "super_admin":
                        response.sendRedirect("super_admin");
                        break;
                    default:
                        response.sendRedirect("home");
                        break;
                }

            } else {
                request.setAttribute("error", "Invalid username or password");
                request.getRequestDispatcher("/views/error.jsp").forward(request, response);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "Database error: " + e.getMessage());
            request.getRequestDispatcher("/views/error.jsp").forward(request, response);
        }
    }
}

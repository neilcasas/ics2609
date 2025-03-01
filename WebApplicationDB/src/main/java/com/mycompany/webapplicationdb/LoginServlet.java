/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.mycompany.webapplicationdb;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
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
            if (rs.next()) { // User found
                HttpSession session = request.getSession();
                session.setAttribute("username", username);
                System.out.println("Username: " + username);
                response.sendRedirect("home");
            } else {
                response.sendRedirect("login.jsp?error=invalid"); // Redirect back with an error
            }
        } catch (SQLException e) {
            throw new ServletException("Database error", e);
        }
    }
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}

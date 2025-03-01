/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.mycompany.webapplicationdb;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author neila
 */
@WebServlet(name = "SignUpServlet", urlPatterns = {"/signup"})
public class SignUpServlet extends HttpServlet {

    private JDBC jdbc;

    @Override
    public void init() throws ServletException {
        jdbc = new JDBC("3306", "social_media", "root", "admin");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Get parameters
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String user_role = "user";

        // Save user
        boolean successfulCreate = jdbc.createUser(username, password, user_role);
        
        if(successfulCreate) {
                HttpSession session = request.getSession();
                session.setAttribute("username", username);
                System.out.println("Username: " + username);
                response.sendRedirect("home");
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}

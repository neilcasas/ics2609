package com.mycompany.webapplicationdb;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "SignoutServlet", urlPatterns = {"/signout"})
public class SignoutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Invalidate the session
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        
        // Redirect to homepage
        response.sendRedirect(request.getContextPath() + "/");
    }

    @Override
    public String getServletInfo() {
        return "Handles user sign-out by invalidating the session and redirecting to the home page";
    }
}

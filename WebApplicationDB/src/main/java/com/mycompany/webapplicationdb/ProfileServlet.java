package com.mycompany.webapplicationdb;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpSession;

@WebServlet(name = "ProfileServlet", urlPatterns = {"/profile"})
public class ProfileServlet extends HttpServlet {

    private JDBC jdbc;

    @Override
    public void init() throws ServletException {
        jdbc = new JDBC("3306", "social_media", "root", "admin");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        ArrayList<String> posts = new ArrayList<>();

        if (username != null && !username.trim().isEmpty()) {
            try {
                ResultSet rs = jdbc.getPosts(username);
                while (rs.next()) {
                    for (int i = 1; i <= 5; i++) { // Assuming 5 posts per user
                        String post = rs.getString("post" + i);
                        if (post != null && !post.trim().isEmpty()) {
                            posts.add(post);
                        }
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        request.setAttribute("username", username);
        request.setAttribute("posts", posts);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/views/profile.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get action and current user
        String action = request.getParameter("action"); // Use getParameter instead of getAttribute
        HttpSession session = request.getSession();
        String currentUser = (String) session.getAttribute("username");

        if ("create".equals(action)) {
            System.out.println(action); // Debugging
            // Get post content
            String postContent = request.getParameter("post_content");

            if (postContent != null && !postContent.trim().isEmpty()) {
                // Save post
                jdbc.createPost(currentUser, postContent);
            }

            // Redirect
            response.sendRedirect(request.getContextPath() + "/profile?username=" + currentUser);

        } else if ("delete".equals(action)) {
            System.out.println(action); // Debugging
            // Get post's index
            String indexStr = request.getParameter("post_index");
            if (indexStr != null && !indexStr.isEmpty()) {
                try {
                    int index = Integer.parseInt(indexStr);
                    // Delete post
                    jdbc.deletePost(currentUser, index);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }

            // Redirect
            response.sendRedirect(request.getContextPath() + "/profile?username=" + currentUser);
        }
    }

    @Override
    public String getServletInfo() {
        return "Handles user profile and posts retrieval";
    }

}

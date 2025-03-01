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
import javax.servlet.http.HttpSession;

@WebServlet(name = "UsersServlet", urlPatterns = {"/users"})
public class UsersServlet extends HttpServlet {

    private JDBC jdbc;

    @Override
    public void init() throws ServletException {
        jdbc = new JDBC("3306", "social_media", "root", "admin");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username"); // Get logged-in user

        ArrayList<String> followedUsers = new ArrayList<>();

        if (username != null) {
            try {
                ResultSet rs = jdbc.getFollowed(username);
                while (rs.next()) {
                    for (int i = 1; i <= 3; i++) { // Assuming there are 3 follow slots
                        String user = rs.getString("follow" + i);
                        if (user != null && !user.isEmpty()) {
                            followedUsers.add(user);
                        }
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        request.setAttribute("followedUsers", followedUsers);
        request.getRequestDispatcher("/views/users.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");

        // Check if the request is for unfollowing
        String unfollowedUser = request.getParameter("unfollow_user");

        if (unfollowedUser != null && !unfollowedUser.isEmpty()) {
            try {
                jdbc.unfollowUser(username, unfollowedUser);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            response.sendRedirect("users");
            return;
        }

        // Check if the request is for following
        String followUser = request.getParameter("follow_user");

        if (followUser != null && !followUser.isEmpty()) {
            try {
                boolean success = jdbc.followUser(username, followUser);
                if (!success) {
                    request.setAttribute("errorMessage", "You can only follow up to 3 users.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        response.sendRedirect("users");
    }

}

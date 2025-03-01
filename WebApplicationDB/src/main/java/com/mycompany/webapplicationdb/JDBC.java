package com.mycompany.webapplicationdb;

import java.sql.*;

public class JDBC {

    private Connection conn;

    public JDBC(String port, String databaseName, String userName, String password) {
        String jdbcUrl = "jdbc:mysql://localhost:" + port + "/" + databaseName
                + "?user=" + userName + "&password=" + password + "&useSSL=false";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            conn = DriverManager.getConnection(jdbcUrl);
            System.err.println("✅ Connection successful! Connected to database: " + databaseName);

        } catch (ClassNotFoundException e) {
            System.err.println("MySQL JDBC Driver not found!");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Failed to connect to MySQL database!");
            e.printStackTrace();
        }
    }

    public ResultSet getUser(String username, String password) throws SQLException {
        String sqlStr = "SELECT * FROM account WHERE user_name = ? AND password = ?";
        PreparedStatement pstmt = conn.prepareStatement(sqlStr);
        pstmt.setString(1, username);
        pstmt.setString(2, password);
        return pstmt.executeQuery();
    }

    public ResultSet getAllUsers() throws SQLException {
        System.out.println("hello");
        Statement stmt = conn.createStatement();
        return stmt.executeQuery("SELECT * FROM account");
    }

    public ResultSet getFollowedPosts(String username) throws SQLException {
        String sqlStr = "SELECT p.user_name, p.post1, p.post2, p.post3, p.post4, p.post5 "
                + "FROM posts p "
                + "JOIN follows f ON p.user_name IN (f.follow1, f.follow2, f.follow3) "
                + "WHERE f.user_name = ?";

        PreparedStatement stmt = conn.prepareStatement(sqlStr);
        stmt.setString(1, username);

        return stmt.executeQuery();
    }

    public ResultSet getPosts(String username) throws SQLException {
        String sqlStr = "SELECT post1, post2, post3, post4, post5 FROM posts WHERE user_name = ?";
        PreparedStatement stmt = conn.prepareStatement(sqlStr);
        stmt.setString(1, username);

        return stmt.executeQuery();
    }

    // Add a new post
    public void createPost(String username, String content) {
        try {
            // Check if user already has posts
            String selectQuery = "SELECT post1, post2, post3, post4, post5 FROM posts WHERE user_name = ?";
            PreparedStatement selectStmt = conn.prepareStatement(selectQuery);
            selectStmt.setString(1, username);
            ResultSet rs = selectStmt.executeQuery();

            if (rs.next()) {
                String[] posts = {
                    rs.getString("post1"), rs.getString("post2"), rs.getString("post3"),
                    rs.getString("post4"), rs.getString("post5")
                };

                // If there are empty slots, place the new post in the first available one
                for (int i = 0; i < 5; i++) {
                    if (posts[i] == null) {
                        String updateQuery = "UPDATE posts SET post" + (i + 1) + " = ? WHERE user_name = ?";
                        PreparedStatement updateStmt = conn.prepareStatement(updateQuery);
                        updateStmt.setString(1, content);
                        updateStmt.setString(2, username);
                        updateStmt.executeUpdate();
                        return;
                    }
                }

                // If all 5 slots are full, shift posts and insert new post in post1
                String updateQuery = "UPDATE posts SET post5 = post4, post4 = post3, post3 = post2, post2 = post1, post1 = ? WHERE user_name = ?";
                PreparedStatement updateStmt = conn.prepareStatement(updateQuery);
                updateStmt.setString(1, content);
                updateStmt.setString(2, username);
                updateStmt.executeUpdate();
            } else {
                // If user doesn't have a row in posts, create one with the first post
                String insertQuery = "INSERT INTO posts (user_name, post1) VALUES (?, ?)";
                PreparedStatement insertStmt = conn.prepareStatement(insertQuery);
                insertStmt.setString(1, username);
                insertStmt.setString(2, content);
                insertStmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Delete a post by shifting others up
    public void deletePost(String username, int postIndex) {
        if (postIndex < 1 || postIndex > 5) {
            System.out.println("Invalid post index. Must be between 1 and 5.");
            return;
        }

        try {
            // Get current posts
            String selectQuery = "SELECT post1, post2, post3, post4, post5 FROM posts WHERE user_name = ?";
            PreparedStatement selectStmt = conn.prepareStatement(selectQuery);
            selectStmt.setString(1, username);
            ResultSet rs = selectStmt.executeQuery();

            if (rs.next()) {
                String[] posts = {
                    rs.getString("post1"), rs.getString("post2"), rs.getString("post3"),
                    rs.getString("post4"), rs.getString("post5")
                };

                // Ensure the requested post exists before shifting
                if (posts[postIndex - 1] == null) {
                    System.out.println("Post does not exist.");
                    return;
                }

                // Shift posts up
                for (int i = postIndex - 1; i < 4; i++) {
                    posts[i] = posts[i + 1]; // Move next post up
                }
                posts[4] = null; // Last post is now empty

                // Update the database
                String updateQuery = "UPDATE posts SET post1 = ?, post2 = ?, post3 = ?, post4 = ?, post5 = ? WHERE user_name = ?";
                PreparedStatement updateStmt = conn.prepareStatement(updateQuery);
                for (int i = 0; i < 5; i++) {
                    updateStmt.setString(i + 1, posts[i]); // Set updated post values
                }
                updateStmt.setString(6, username);
                updateStmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Get followed users
    public ResultSet getFollowed(String username) throws SQLException {
        String sqlStr = "SELECT follow1, follow2, follow3 FROM follows WHERE user_name = ?";
        PreparedStatement stmt = conn.prepareStatement(sqlStr);
        stmt.setString(1, username);

        return stmt.executeQuery();
    }

    public void unfollowUser(String username, String unfollowed) throws SQLException {
        String sqlStr = "UPDATE follows SET "
                + "follow1 = CASE WHEN follow1 = ? THEN NULL ELSE follow1 END, "
                + "follow2 = CASE WHEN follow2 = ? THEN NULL ELSE follow2 END, "
                + "follow3 = CASE WHEN follow3 = ? THEN NULL ELSE follow3 END "
                + "WHERE user_name = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sqlStr)) {
            stmt.setString(1, unfollowed);
            stmt.setString(2, unfollowed);
            stmt.setString(3, unfollowed);
            stmt.setString(4, username);
            stmt.executeUpdate();
        }
    }

    public boolean followUser(String username, String followUser) throws SQLException {
        String checkQuery = "SELECT follow1, follow2, follow3 FROM follows WHERE user_name = ?";
        String updateQuery = "UPDATE follows SET %s = ? WHERE user_name = ?";

        try (PreparedStatement checkStmt = conn.prepareStatement(checkQuery)) {
            checkStmt.setString(1, username);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                // Check if the user is already followed
                for (int i = 1; i <= 3; i++) {
                    String followedUser = rs.getString("follow" + i);
                    if (followedUser != null && followedUser.equals(followUser)) {
                        return false; // User is already being followed
                    }
                }

                // Find an empty slot to follow the new user
                for (int i = 1; i <= 3; i++) {
                    String column = "follow" + i;
                    if (rs.getString(column) == null) {
                        try (PreparedStatement updateStmt = conn.prepareStatement(
                                String.format(updateQuery, column))) {
                            updateStmt.setString(1, followUser);
                            updateStmt.setString(2, username);
                            updateStmt.executeUpdate();
                            return true; 
                        }
                    }
                }
            }
        }
        return false;
    }

}

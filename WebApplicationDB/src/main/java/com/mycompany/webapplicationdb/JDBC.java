package com.mycompany.webapplicationdb;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;

public class JDBC {

    private Connection conn;

    public JDBC(String port, String databaseName, String userName, String password) {
        String jdbcUrl = "jdbc:mysql://localhost:" + port + "/" + databaseName
                + "?user=" + userName + "&password=" + password + "&useSSL=false&allowPublicKeyRetrieval=true";

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

    public ResultSet getUser(String username) throws SQLException {
        String sqlStr = "SELECT * FROM account WHERE user_name = ?";
        PreparedStatement pstmt = conn.prepareStatement(sqlStr);
        pstmt.setString(1, username);
        return pstmt.executeQuery();
    }

    public ResultSet getUsersByRole(String role) throws SQLException {
        String sqlStr = "SELECT * FROM account WHERE user_role = ?";
        System.out.println("Fetching users with role: " + role);
        PreparedStatement stmt = conn.prepareStatement(sqlStr);
        stmt.setString(1, role);
        return stmt.executeQuery();
    }

    public void updateUser(String originalUsername, String newUsername, String newPassword, String newRole) throws SQLException {
        String sql = "UPDATE account SET user_name = ?, password = ?, user_role = ? WHERE user_name = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newUsername);
            pstmt.setString(2, newPassword);
            pstmt.setString(3, newRole);
            pstmt.setString(4, originalUsername);
            pstmt.executeUpdate();
        }
    }

    public boolean usernameExists(String username) throws SQLException {
        String query = "SELECT COUNT(*) FROM account WHERE user_name = ?";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, username);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            return rs.getInt(1) > 0;
        }
        return false;
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
        String selectQuery = "SELECT post1, post2, post3, post4, post5 FROM posts WHERE user_name = ?";
        String shiftQuery = "UPDATE posts SET post5 = post4, post4 = post3, post3 = post2, post2 = post1, post1 = ? WHERE user_name = ?";
        String insertQuery = "INSERT INTO posts (user_name, post1) VALUES (?, ?)";

        try (PreparedStatement selectStmt = conn.prepareStatement(selectQuery)) {
            selectStmt.setString(1, username);
            ResultSet rs = selectStmt.executeQuery();

            if (rs.next()) {
                // User exists, shift posts and insert new post1
                try (PreparedStatement shiftStmt = conn.prepareStatement(shiftQuery)) {
                    shiftStmt.setString(1, content);
                    shiftStmt.setString(2, username);
                    shiftStmt.executeUpdate();
                }
            } else {
                // User doesn't exist, create a new row
                try (PreparedStatement insertStmt = conn.prepareStatement(insertQuery)) {
                    insertStmt.setString(1, username);
                    insertStmt.setString(2, content);
                    insertStmt.executeUpdate();
                }
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

    // Get admin messages
    public ResultSet getMessages() throws SQLException {
        String sqlStr = "SELECT sender, content FROM admin_messages";
        PreparedStatement stmt = conn.prepareStatement(sqlStr);
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

    // Method to insert a message into admin_messages
    public boolean insertAdminMessage(String username, String message) {
        String sql = "INSERT INTO admin_messages (sender, content) VALUES (?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, message);
            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean createUser(String username, String password, String user_role) {
        String sql = "INSERT INTO account VALUES (?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.setString(3, user_role);
            int rowsInserted = stmt.executeUpdate();

            if (rowsInserted > 0 && "user".equals(user_role)) {
                String followSQL = "INSERT INTO follows (user_name) VALUES (?)";

                try (PreparedStatement followStmt = conn.prepareStatement(followSQL)) {
                    followStmt.setString(1, username);
                    followStmt.executeUpdate();
                }
            }
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public int getUserCount() throws SQLException {
        String sql = "SELECT COUNT(*) FROM account";
        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery(sql);
        if (rs.next()) {
            return rs.getInt(1);
        }
        return 0;
    }

    public void deleteUser(String username) throws SQLException {
        String sql = "DELETE FROM account WHERE user_name = '" + username + "'";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.executeUpdate(sql);
    }

    public void updateUser(String originalUsername, String newUsername, String newPassword) throws SQLException {
        String sql = "UPDATE account SET user_name = '" + newUsername + "', password = '" + newPassword
                + "' WHERE user_name = '" + originalUsername + "'";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.executeUpdate();
    }

    public void updateUsername(String oldUsername, String newUsername) throws SQLException {
        String sql = "UPDATE account SET user_name = ? WHERE user_name = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newUsername);
            pstmt.setString(2, oldUsername);
            pstmt.executeUpdate();
        }
    }

    public void updatePassword(String username, String newPassword) throws SQLException {
        String sql = "UPDATE account SET password = ? WHERE user_name = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newPassword);
            pstmt.setString(2, username);
            pstmt.executeUpdate();
        }
    }

    public void updateRole(String username, String newRole) throws SQLException {
        String sql = "UPDATE account SET user_role = ? WHERE user_name = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newRole);
            pstmt.setString(2, username);
            pstmt.executeUpdate();
        }
    }

}

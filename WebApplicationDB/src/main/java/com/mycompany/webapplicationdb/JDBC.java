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

}

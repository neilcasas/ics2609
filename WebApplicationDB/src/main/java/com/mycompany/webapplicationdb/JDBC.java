package com.mycompany.webapplicationdb;
import java.sql.*;

public class JDBC {

    private Connection conn;

    public JDBC(String port, String databaseName, String userName, String password) {
        String jdbcUrl = "jdbc:mysql://localhost:" + port + "/" + databaseName + "?user=" + userName + "&password=" + password;
        try {
            conn = DriverManager.getConnection(jdbcUrl);
        } catch (SQLException e) {
            System.err.println("Failed to create connection");
            System.err.println(e.toString());
        }
    }

    public ResultSet getUser(String username, String password) throws SQLException {
        String sqlStr = "SELECT * FROM Users WHERE username = ? AND password = ?";
        PreparedStatement pstmt = conn.prepareStatement(sqlStr);
        pstmt.setString(1, username);
        pstmt.setString(2, password);
        return pstmt.executeQuery();
    }

    public ResultSet getAllUsers() throws SQLException {
        Statement stmt = conn.createStatement();
        return stmt.executeQuery("SELECT * FROM Users");
    }
}

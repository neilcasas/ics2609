package com.mycompany.group1;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.*;

public class JDBCStartup {

    private class User {

        String username;
        String password;
        String accessRole;

        public User(String username, String password, String accessRole) {
            this.username = username;
            this.password = password;
            this.accessRole = accessRole;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getAccessRole() {
            return accessRole;
        }

        public void setAccessRole(String accessRole) {
            this.accessRole = accessRole;
        }
    }

    private Connection conn;

    public JDBCStartup(String username, String password, String database) {
        String connStr = "jdbc:sqlite:" + database;
        System.out.println(connStr);
        try {
            conn = DriverManager.getConnection(connStr);
        } catch (SQLException e) {
            System.err.println("Failed to create connection");
            System.err.println(e.toString());
        }
    }

    public ResultSet getAll() throws SQLException {
        Statement stmt = conn.createStatement();
        return stmt.executeQuery("select * from Users");
    }

    public ResultSet getUser(String username, String password) throws SQLException {
        String sqlStr = "SELECT * FROM Users WHERE username = ? AND password = ?";
        PreparedStatement pstmt = conn.prepareStatement(sqlStr);
        pstmt.setString(1, username);
        pstmt.setString(2, password);
        return pstmt.executeQuery();
    }

    public static void main(String[] args) {
        String directPathToDb = "C:\\Users\\neila\\Documents\\NetBeansProjects\\SimpleDBApp\\AccountsDB";
        JDBCStartup jdbc = new JDBCStartup("root", "root", directPathToDb);

        try {
            ResultSet rs = jdbc.getAll();
            while (rs.next()) {
                System.out.println(rs.getString("username") + " " + rs.getString("password"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Database connection error: " + e.getMessage());
            return;
        }

        // Create the login frame
        JFrame frame = new JFrame("Login");
        frame.setLayout(new GridLayout(4, 2, 10, 10));

        JLabel userLabel = new JLabel("Username:");
        frame.add(userLabel);
        JTextField userField = new JTextField(20);
        frame.add(userField);

        JLabel passwordLabel = new JLabel("Password:");
        frame.add(passwordLabel);
        JPasswordField passwordField = new JPasswordField(20);
        frame.add(passwordField);
        
        
        // Store failed login ctr in hidden field
        JTextField failedLoginCtr = new JTextField(20);
        failedLoginCtr.setText("0");
        failedLoginCtr.setVisible(false);
        frame.add(failedLoginCtr);

        frame.add(new JLabel());
        JButton loginBtn = new JButton("Login");
        frame.add(loginBtn);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        

        loginBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = userField.getText();
                String password = new String(passwordField.getPassword());
                try {
                    ResultSet rs = jdbc.getUser(username, password);
                    if (rs.next()) {
                        JOptionPane.showMessageDialog(frame, "Login successful for user: " + rs.getString("username"),
                                "Success", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(frame, "Invalid username or password.", "Error",
                                JOptionPane.ERROR_MESSAGE);
                        int failedCtr = Integer.parseInt(failedLoginCtr.getText());
                        failedCtr++;
                        
                        if(failedCtr >= 3) {
                            System.exit(0);
                        }
                        
                        failedLoginCtr.setText(failedCtr + "");
                        
                    }
                } catch (SQLException err) {
                    JOptionPane.showMessageDialog(frame, "An error occurred while processing your login.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    err.printStackTrace();
                }
            }
        });
    }

}

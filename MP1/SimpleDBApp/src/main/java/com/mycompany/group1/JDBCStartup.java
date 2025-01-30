package com.mycompany.group1;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.*;

public class JDBCStartup {

    private Connection conn;

    public JDBCStartup(String database) {
        String connStr = "jdbc:sqlite:" + database;
        try {
            conn = DriverManager.getConnection(connStr);
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

    public static void main(String[] args) {
        String directPathToDb = "C:\\Users\\neila\\Documents\\dev\\ics2609\\MP1\\SimpleDBApp\\AccountsDB";
        JDBCStartup jdbc = new JDBCStartup(directPathToDb);

        JFrame frame = new JFrame("Login");
        frame.setLayout(new GridLayout(4, 2, 10, 10));

        JLabel userLabel = new JLabel("Username:");
        JTextField userField = new JTextField(20);
        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField(20);
        JButton loginBtn = new JButton("Login");
        frame.add(userLabel);
        frame.add(userField);
        frame.add(passwordLabel);
        frame.add(passwordField);
        frame.add(new JLabel());
        frame.add(loginBtn);

        // Store failed login ctr in hidden field
        JTextField failedLoginCtr = new JTextField(20);
        failedLoginCtr.setText("0");
        failedLoginCtr.setVisible(false);
        frame.add(failedLoginCtr);

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
                        failedLoginCtr.setText("0"); // reset when its a successful login
                        String role = rs.getString("user_role");
                        frame.dispose();
                        if ("admin".equalsIgnoreCase(role)) {
                            showAdminPanel(jdbc);
                        } else {
                            showUserPanel(username);
                        }
                    } else {
                        JOptionPane.showMessageDialog(frame, "Invalid username or password.", "Error", JOptionPane.ERROR_MESSAGE);
                        int failedCtr = Integer.parseInt(failedLoginCtr.getText());
                        failedCtr++;

                        if (failedCtr >= 3) {
                            System.exit(0);
                        }

                        failedLoginCtr.setText(failedCtr + "");
                    }
                } catch (SQLException err) {
                    JOptionPane.showMessageDialog(frame, "An error occurred while processing your login.", "Error", JOptionPane.ERROR_MESSAGE);
                    err.printStackTrace();
                }
            }
        });
    }

    private static void showUserPanel(String username) {
        JFrame userFrame = new JFrame("Welcome");
        userFrame.setLayout(new FlowLayout());
        userFrame.add(new JLabel("Welcome, " + username + "!"));
        JButton logoutBtn = new JButton("Logout");
        userFrame.add(logoutBtn);
        userFrame.setSize(300, 150);
        userFrame.setLocationRelativeTo(null);
        userFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        userFrame.setVisible(true);

        logoutBtn.addActionListener(e -> {
            userFrame.dispose();
            main(null);
        });
    }

    private static void showAdminPanel(JDBCStartup jdbc) {
        JFrame adminFrame = new JFrame("Admin Panel");
        adminFrame.setLayout(new BorderLayout());

        JTextArea userList = new JTextArea(10, 30);
        userList.setEditable(false);

        try {
            ResultSet rs = jdbc.getAllUsers();
            while (rs.next()) {
                userList.append(rs.getString("username") + " - " + rs.getString("user_role") + "\n");
            }
        } catch (SQLException e) {
            userList.setText("Error fetching users.");
        }

        adminFrame.add(new JScrollPane(userList), BorderLayout.CENTER);
        JButton logoutBtn = new JButton("Logout");
        adminFrame.add(logoutBtn, BorderLayout.SOUTH);

        adminFrame.setSize(400, 300);
        adminFrame.setLocationRelativeTo(null);
        adminFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        adminFrame.setVisible(true);

        logoutBtn.addActionListener(e -> {
            adminFrame.dispose();
            main(null);
        });
    }
}

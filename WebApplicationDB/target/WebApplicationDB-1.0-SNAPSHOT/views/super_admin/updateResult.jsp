<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ page import="com.mycompany.webapplicationdb.User" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Update User</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <style>
            body {
                background-color: #f5f8fa;
            }
            .container {
                max-width: 600px;
                margin-top: 20px;
            }
            .tweet-box {
                background: white;
                padding: 15px;
                border-radius: 10px;
                box-shadow: 0 2px 5px rgba(0,0,0,0.1);
                margin-bottom: 10px;
            }
            .tweet-username {
                font-weight: bold;
                color: #333;
            }
            .tweet-content {
                margin-top: 5px;
            }
            .navbar-brand{
                margin-left: 100px;
            }
        </style>
    </head>
    <body>
        <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
            <a class="navbar-brand fs-3" href="super_admin">Welcome, <%= session.getAttribute("username")%></a>
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarText" aria-controls="navbarText" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarText">
                <ul class="navbar-nav mr-auto">
                    <li class="nav-item active">
                        <a class="nav-link fs-4" href="super_admin">Home</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link fs-4" href="superAdminCreateUser">Create</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link fs-4" href="superAdminDeleteUser">Delete</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link fs-4" href="superAdminUpdateUser">Update  <span class="sr-only">(current)</span></a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link fs-4" href="signout">Sign Out</a>
                    </li>
                </ul>
            </div>
        </nav>
        <div class="container mt-3">
            <h2 class="text-center">Recently Updated Users</h2>
            <table class="table table-bordered">
                <thead>
                    <tr>
                        <th>Username</th>
                        <th>New Password</th>
                        <th>Role</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        List<User> updatedUsers = (List<User>) request.getAttribute("updatedUsers");
                        if (updatedUsers != null && !updatedUsers.isEmpty()) {
                            for (User user : updatedUsers) {
                    %>
                    <tr>
                        <td><%= user.getUsername()%></td>
                        <td><%= user.getPassword()%></td>
                        <td><%= user.getRole()%></td>
                    </tr>
                    <%
                        }
                    } else {
                    %>
                    <tr>
                        <td colspan="3" class="text-center">No recently updated users.</td>
                    </tr>
                    <% }%>
                </tbody>
            </table>
            <div class="text-center">
                <a href="<%= request.getContextPath()%>/superAdminUpdateUser" class="btn btn-primary">Back to Update Page</a>
            </div>
        </div>

    </body>
</html>

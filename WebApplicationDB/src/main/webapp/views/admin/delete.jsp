<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Delete User</title>
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
        <a class="navbar-brand fs-3" href="admin">Welcome, <%= session.getAttribute("username") %></a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarText" aria-controls="navbarText" aria-expanded="false" aria-label="Toggle navigation">
          <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarText">
          <ul class="navbar-nav mr-auto">
            <li class="nav-item active">
              <a class="nav-link fs-4" href="admin">Home</a>
            </li>
            <li class="nav-item">
              <a class="nav-link fs-4" href="adminCreateUser">Create</a>
            </li>
            <li class="nav-item">
              <a class="nav-link fs-4" href="adminDeleteUser">Delete  <span class="sr-only">(current)</span></a>
            </li>
            <li class="nav-item">
              <a class="nav-link fs-4" href="adminUpdateUser">Update</a>
            </li>
          </ul>
        </div>
    </nav>
    <div class="limited-container">
        <div class="container mt-3" style="max-width: 900px; margin: 0 auto;">
            <h2>List of Users</h2>

            <ul class="ps-3 list-group">
                <% 
                    List<String> usernames = (List<String>) request.getAttribute("usernames");
                    if (usernames != null && !usernames.isEmpty()) {
                        for (int i = 0; i < usernames.size(); i++) {
                %>
                <li class="list-group-item d-flex align-items-center justify-content-between">
                    <span><%= usernames.get(i) %></span>
                    <form action="<%= request.getContextPath() %>/adminDeleteUser" method="POST">
                        <input type="hidden" name="delete_user" value="<%= usernames.get(i) %>">
                        <input type="submit" class="btn btn-danger" value="Delete">
                    </form>
                </li>
                <% 
                        }
                    } else { 
                %>
                <li class="list-group-item text-center">No users available.</li>
                <% } %>
            </ul>
        </div>
    </div>

</body>
</html>
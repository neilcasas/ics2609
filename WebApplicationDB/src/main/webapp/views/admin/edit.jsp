<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    // Retrieve the username passed as a request parameter.
    String username = request.getParameter("username");
    if(username == null) {
        username = "";
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Edit User</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
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
              <a class="nav-link fs-4" href="adminDeleteUser">Delete</a>
            </li>
            <li class="nav-item">
              <a class="nav-link fs-4" href="adminUpdateUser">Update  <span class="sr-only">(current)</span></a>
            </li>
          </ul>
        </div>
    </nav>
<div class="container mt-3 card p-4 shadow-lg" style="max-width: 600px; margin: 0 auto;">
    <h2>Edit User</h2>
    <form action="<%= request.getContextPath() %>/adminEditUser" method="POST">
        <!-- Hidden field to retain the original username -->
        <input type="hidden" name="original_username" value="<%= request.getAttribute("username") %>">
        <div class="mb-3">
            <label class="form-label">Username</label>
            <input type="text" class="form-control" name="username" value="<%= request.getAttribute("username") %>" required>
        </div>
        <div class="mb-3">
            <label class="form-label">Password</label>
            <input type="password" class="form-control" name="password" required>
        </div>
        <button type="submit" class="btn btn-success">Save Changes</button>
    </form>
</div>
</body>
</html>
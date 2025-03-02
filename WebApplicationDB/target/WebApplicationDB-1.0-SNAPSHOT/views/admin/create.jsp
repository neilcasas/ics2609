<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Create User</title>
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
        h2, .form-group{
            margin-bottom: 20px;
        }
        .navbar-brand{
            margin-left: 100px;
        }
        .limited-container {
            display: flex;
            justify-content: center;
            align-items: center;
            height: calc(100vh - 56px); 
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
              <a class="nav-link fs-4" href="adminCreateUser">Create  <span class="sr-only">(current)</span></a>
            </li>
            <li class="nav-item">
              <a class="nav-link fs-4" href="adminDeleteUser">Delete</a>
            </li>
            <li class="nav-item">
              <a class="nav-link fs-4" href="adminUpdateUser">Update</a>
            </li>
          </ul>
        </div>
    </nav>

    <div class="limited-container">
        <div class="container mt-3 card p-4 shadow-lg" style="max-width: 400px;">
            <%
                String error = (String) request.getAttribute("error");
                
                 if(error != null) {
            %>
            <div> <%= error %> </div>
            <% } %>
            <h2 class="text-center">Create a new account</h2>
            <form action="adminCreateUser" method="POST">
                <div class="form-group">
                    <label for="username">Username</label>
                    <input type="text" class="form-control" id="username" name="username" placeholder="Enter username" required>
                </div>
                <div class="form-group">
                    <label for="password">Password</label>
                    <input type="password" class="form-control" id="password" name="password" placeholder="Password" required>
                </div>
                <div class="d-flex justify-content-center mt-3">
                    <button type="submit" class="btn btn-primary">Create</button>
                </div>
            </form>
        </div>
    </div>
</body>
</html>
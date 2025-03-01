<%@ page import="java.sql.*" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
    .limited-form {
        max-width: 400px; 
    }

    h1 {
        margin-bottom: 80px; 
    }

    h2 {
        margin-top: 50px; 
    }
    
    .navbar-brand{
        margin-left: 100px;
    }
    

    </style>
</head>
<body>
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
        <a class="navbar-brand fs-3" href="/WebApplicationDB/views/profile.jsp">Welcome, Kaiser</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarText" aria-controls="navbarText" aria-expanded="false" aria-label="Toggle navigation">
          <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarText">
          <ul class="navbar-nav mr-auto">
            <li class="nav-item active">
              <a class="nav-link fs-4" href="/WebApplicationDB/views/landing.jsp">Home</a>
            </li>
            <li class="nav-item">
              <a class="nav-link fs-4" href="/WebApplicationDB/views/profile.jsp">Profile</a>
            </li>
            <li class="nav-item">
              <a class="nav-link fs-4" href="/WebApplicationDB/views/users.jsp">Users <span class="sr-only">(current)</span> </a>
            </li>
            <li class="nav-item">
              <a class="nav-link fs-4" href="/WebApplicationDB/views/help.jsp">Help</a>
            </li>
          </ul>
        </div>
    </nav>
    <div class="limited-container">
        <div class="container mt-3" style="max-width: 1745px; margin: 0 auto;">
            <h2>Followed Users</h2>
            <ul class="ps-3">  <!-- Add padding to maintain indentation -->
                <li class="d-flex align-items-center gap-2">
                    <form method="POST">
                        <input type="hidden" name="unfollow_user" value="Isagi Yoichi">
                        <input type="submit" class="btn btn-danger" value="Unfollow">
                    </form>
                    <span>Isagi Yoichi</span>
                </li>
            </ul>
            
            
            <hr>

            <h2>Follow a new user</h2>
            <form method="POST" class="limited-form">
                <div class="input-group mb-3">
                    <span class="input-group-text">@</span>
                    <div class="form-floating flex-grow-1">
                        <input type="text" class="form-control small-input" id="floatingInputGroup1" name="follow_user" placeholder="Username" required>
                        <label for="floatingInputGroup1">Username</label>
                    </div>
                    <input type="submit" class="btn btn-primary" value="Follow">
                </div>
            </form>
        </div>
    </div>
</body>
</html>

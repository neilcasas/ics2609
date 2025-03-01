<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
     .limited-container {
        margin-left: 100px; 
    }

    .limited-form {
        max-width: 400px; 
    }

    h1 {
        margin-bottom: 80px; 
    }

    h2 {
        margin-top: 80px; 
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
              <a class="nav-link fs-4" href="/WebApplicationDB/views/landing.jsp">Home <span class="sr-only">(current)</span></a>
            </li>
            <li class="nav-item">
              <a class="nav-link fs-4" href="/WebApplicationDB/views/profile.jsp">Profile</a>
            </li>
            <li class="nav-item">
              <a class="nav-link fs-4" href="/WebApplicationDB/views/users.jsp">Users</a>
            </li>
            <li class="nav-item">
              <a class="nav-link fs-4" href="/WebApplicationDB/views/help.jsp">Help</a>
            </li>
          </ul>
        </div>
    </nav>
    <div class="limited-container">
        <h1>Hello</h1>
    </div>
</body>
</html>
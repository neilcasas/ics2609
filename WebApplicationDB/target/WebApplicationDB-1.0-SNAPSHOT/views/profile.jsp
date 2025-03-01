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

    h2 {
        margin-top: 50px; 
    }
    
    .navbar-brand{
        margin-left: 100px;
    }
    
    .mb-3{
        max-width: 900px;
    }
    
    .list-group{
        max-width: 900px;
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
              <a class="nav-link fs-4" href="/WebApplicationDB/views/profile.jsp">Profile  <span class="sr-only">(current)</span> </a>
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
        <div class="container mt-3" style="max-width: 1745px; margin: 0 auto;">
        <h2>Posts</h2>

        <h2>Create a New Post</h2>
        <form action="profile.jsp" method="POST">
            <div class="mb-3">
                <label for="postContent" class="form-label">Your Post:</label>
                <textarea class="form-control" id="postContent" name="post_content" rows="3" required></textarea>
            </div>
            <button type="submit" class="btn btn-primary">Post</button>
        </form>

        <hr>

        <h2>Your Posts</h2>
        <ul class="list-group">
            <li class="list-group-item d-flex justify-content-between align-items-center">
                <form method="POST" style="display:inline;">
                    <input type="hidden" name="delete_post" value="">
                    <button type="submit" class="btn btn-danger btn-sm">Delete</button>
                </form>
            </li>
        </ul>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </div>
</body>
</html>
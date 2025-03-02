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

            .mb-3{
                max-width: 900px;
            }
        </style>
    </head>
    <body>
        <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
            <a class="navbar-brand fs-3" href="/WebApplicationDB/home">Welcome, <%= session.getAttribute("username")%></a>
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarText" aria-controls="navbarText" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarText">
                <ul class="navbar-nav mr-auto">
                    <li class="nav-item active">
                        <a class="nav-link fs-4" href="/WebApplicationDB/home">Home</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link fs-4" href="/WebApplicationDB/profile?username=<%= session.getAttribute("username")%>">Profile</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link fs-4" href="/WebApplicationDB/users">Users</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link fs-4" href="/WebApplicationDB/help">Help</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link fs-4" href="signout">Sign Out</a>
                    </li>
                </ul>
            </div>
        </nav>
        <div class="limited-container">
            <div class="container mt-3" style="max-width: 1745px; margin: 0 auto;">
                <form action="/WebApplicationDB/help" method="POST">
                    <h2>Message Form to Admin</h2>
                    <div class="mb-3">
                        <label for="postContent" class="form-label">Your message:</label>
                        <textarea class="form-control" id="postContent" name="post_content" rows="3" required></textarea>
                    </div>
                    <button type="submit" class="btn btn-primary">Send</button>
                </form>
            </div>
        </div>
    </body>
</html>
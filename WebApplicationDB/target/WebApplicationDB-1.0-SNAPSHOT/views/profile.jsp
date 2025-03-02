<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList" %>
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
                <h2>Create a New Post</h2>
                <form action="/WebApplicationDB/profile" method="POST">
                    <div class="mb-3">
                        <label for="postContent" class="form-label">Your Post:</label>
                        <textarea class="form-control" id="postContent" name="post_content" rows="3" required></textarea>
                        <input type="hidden" name="action" value="create" />
                    </div>
                    <button type="submit" class="btn btn-primary">Post</button>
                </form>
                <hr>
                <%
                    ArrayList<String> posts = (ArrayList<String>) request.getAttribute("posts");
                    String profileUsername = (String) request.getAttribute("username");

                %>
                <h2>@<%= profileUsername%>'s Posts</h2>
                <ul class="list-group">
                    <% if (posts != null && !posts.isEmpty()) { %>
                    <% for (int i = 0; i < posts.size(); i++) {%>
                    <li class="list-group-item">
                        <p><b>@<%= profileUsername%></b></p>
                        <p><%= posts.get(i)%></p>
                        <% if (profileUsername.equals(session.getAttribute("username"))) {%>
                        <form action="/WebApplicationDB/profile" method="POST">
                            <button class="btn btn-danger">Delete</button>
                            <input type="hidden" name="action" value="delete" />
                            <input type="hidden" name="post_index" value=<%= i + 1%> />
                        </form>
                        <% } %>
                    </li>
                    <% } %>
                    <% } else { %>
                    <p>No posts available.</p>
                    <% }%>
                </ul>
            </div>

            <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
        </div>
    </body>
</html>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Home</title>
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
        </style>
    </head>
    <body>
         <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
            <a class="navbar-brand fs-3" href="/WebApplicationDB/views/profile.jsp">Welcome, <%= session.getAttribute("username")%></a>
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarText" aria-controls="navbarText" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarText">
                <ul class="navbar-nav mr-auto">
                    <li class="nav-item active">
                        <a class="nav-link fs-4" href="/WebApplicationDB/home">Home</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link fs-4" href="/WebApplicationDB/profile?username=<%= session.getAttribute("username") %>">Profile</a>
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

        <div class="container">


            <%
                List<String> usernames = (List<String>) request.getAttribute("usernames");
                List<String> posts = (List<String>) request.getAttribute("posts");

                if (usernames != null && posts != null) {
                    for (int i = 0; i < usernames.size(); i++) {
            %>
            <div class="tweet-box">
                <div class="tweet-username">
                    <a href="/WebApplicationDB/profile?username=<%=usernames.get(i)%>">@<%= usernames.get(i)%></a>
                </div>
                <div class="tweet-content"><%= posts.get(i)%></div>
            </div>
            <%
                }
            } else {
            %>
            <p>No posts to display.</p>
            <%
                }
            %>
        </div>
    </body>
</html>

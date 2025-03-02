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
            .navbar-brand {
                margin-left: 100px;
            }
            h2{
                text-align: center;
                margin-top: 30px;
            }
        </style>
    </head>
    <body>
        <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
            <a class="navbar-brand fs-3" href="super_admin">
                Welcome, <%= session.getAttribute("username")%>
            </a>
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarText" 
                    aria-controls="navbarText" aria-expanded="false" aria-label="Toggle navigation">
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
                        <a class="nav-link fs-4" href="superAdminUpdateUser">Update</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link fs-4" href="signout">Sign Out</a>
                    </li>
                </ul>
            </div>
        </nav>

        <h2>List of Users and Admins</h2>
        <div class="container">
            <%
                List<String> usernames = (List<String>) request.getAttribute("usernames");
                if (usernames != null) {
                    for (int i = 0; i < usernames.size(); i++) {
            %>
            <div class="tweet-box">
                <div class="tweet-username">@<%= usernames.get(i)%></div>
            </div>
            <%
                }
            } else {
            %>
            <%
                }
            %>
        </div>
    </body>
</html>

<%@ page import="java.util.List" %>
<%@ page import="com.mycompany.webapplicationdb.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Update User</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <style>
            body {
                background-color: #f5f8fa;
            }
            .navbar-brand{
                margin-left: 100px;
            }

            /* Form styling */
            .form-group {
                margin-bottom: 15px;
            }

            .form-group label {
                display: block;
                margin-bottom: 5px;
            }

            .form-group input {
                width: 50%;
                padding: 8px;
            }

            .form-group select{
                width: 50%;
                padding: 8px;
            }
            button[type="submit"] {
                width: 50%;
                padding: 10px;
                background-color: #007bff;
                color: white;
                border: none;
                cursor: pointer;
            }

            h2 {
                text-align: center;
                margin-top: 20px;
                margin-bottom: 40px;
            }

            h3 {
                text-align: left;
            }
        </style>

    </head>
    <body>
        <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
            <a class="navbar-brand fs-3" href="admin">Welcome, <%= session.getAttribute("username")%></a>
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
                        <a class="nav-link fs-4" href="superAdminUpdateUser">Update  <span class="sr-only">(current)</span></a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link fs-4" href="signout">Sign Out</a>
                    </li>
                </ul>
            </div>
        </nav>
        <div class="container">
            <h2 class="text-center">Update Users</h2>

            <% String error = (String) request.getAttribute("error");
                if (error != null) {%>
            <div class="alert alert-danger"><%= error%></div>
            <% }%>

                <div class="row container-fluid">
                    <div class="col">
                        <h4>Select a User</h4>
                        <ul class="list-group">
                            <li class="list-group-item">
                                <div class="row">
                                    <div class="col-2 fw-bold">Username</div>
                                    <div class="col-2 fw-bold">Password</div>
                                    <div class="col-2 fw-bold">Role</div>
                                    <div class="col-2 fw-bold">New Username</div>
                                    <div class="col-2 fw-bold">New Password</div>
                                    <div class="col-2 fw-bold">New Role</div>
                                </div>

                            </li>

                            <% List<User> users = (List<User>) request.getAttribute("users");
                                if (users != null && !users.isEmpty()) {
                                    for (User user : users) {%>
                            <li class="list-group-item">
                                <div class="row align-items-center">
                                    <div class="col-2 text-truncate"><%= user.getUsername()%></div>
                                    <div class="col-2 text-truncate"><%= user.getPassword()%></div>
                                    <div class="col-2"><%= user.getRole()%></div>
                                    <div class="col-2">
                                        <input type="text" name="user_name-<%=user.getUsername()%>" class="form-control">
                                    </div>
                                    <div class="col-2">
                                        <input type="password" name="password-<%=user.getUsername()%>" class="form-control">
                                    </div>
                                    <div class="col-2">
                                        <input type="text" name="user_role-<%=user.getUsername()%>" class="form-control">
                                    </div>
                                </div>
                            </li>
                            <% }
                            } else { %>
                            <li class="list-group-item text-center">No users available.</li>
                                <% }%>
                        </ul>
                        <button id="updateUsers" class="btn btn-primary mt-4">Update Users</button>

                    </div>
                </div>
        </div>        
        <script>
            const forms = document.querySelectorAll("input");
            const updateBtn = document.querySelector("#updateUsers");

            updateBtn.addEventListener("click", (e) => {
                e.preventDefault();
                const responses = [];

                forms.forEach((form) => {
                    if (form.value.trim() !== "" && form.name.trim() !== "") {
                        responses.push(form.name + ":" + form.value);
                    }
                })

                // Send to servlet
                fetch("/WebApplicationDB/adminUpdateUser", {
                    method: "POST",
                    body: responses
                })
            });
        </script>
    </body>
</html>
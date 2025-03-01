<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Users</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
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
                        <a class="nav-link fs-4" href="/WebApplicationDB/views/help.jsp">Help</a>
                    </li>
                </ul>
            </div>
        </nav>

        <div class="container mt-3">
            <h2>Followed Users</h2>
            <ul class="ps-3">
                <c:choose>
                    <c:when test="${not empty followedUsers}">
                        <c:forEach var="user" items="${followedUsers}">
                            <li class="d-flex align-items-center gap-2 m-2">
                                <form method="POST">
                                    <input type="hidden" name="unfollow_user" value="${user}">
                                    <input type="submit" class="btn btn-danger" value="Unfollow">
                                </form>
                                <span>${user}</span>
                            </li>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <li class="text-muted">No followed users</li>
                        </c:otherwise>
                    </c:choose>

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
    </body>
</html>

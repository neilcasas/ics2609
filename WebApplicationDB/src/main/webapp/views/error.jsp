<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Error</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f2f2f2;
            color: #333;
            display: flex;
            justify-content: center; /* Center horizontally */
            align-items: center; /* Center vertically */
            height: 100vh; /* Full viewport height */
            margin: 0; /* Remove default margin */
        }

        .container {
            text-align: center;
            padding: 20px;
            background-color: white; /* White background for the container */
            border-radius: 8px; /* Rounded corners */
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1); /* Subtle shadow */
        }

        h1 {
            color: #e74c3c; /* Red color for the error message */
        }

        p {
            font-size: 18px;
            margin: 20px 0;
        }

        button {
            background-color: #3498db; /* Blue button */
            color: white;
            border: none;
            padding: 10px 20px;
            font-size: 16px;
            cursor: pointer;
            border-radius: 5px;
            transition: background-color 0.3s;
        }

        button:hover {
            background-color: #2980b9; /* Darker blue on hover */
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>Error</h1>
        <p>${error}</p>
        <form action="<%= request.getHeader("referer") %>" method="get">
            <button type="submit">Return</button>
        </form>
    </div>
</body>
</html>
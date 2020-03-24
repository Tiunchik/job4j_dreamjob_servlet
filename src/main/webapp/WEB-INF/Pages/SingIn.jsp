<%--
  Created by IntelliJ IDEA.
  User: Tiunchik
  Date: 24.03.2020
  Time: 0:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Sing In</title>
    <style>
        body {
            font-size: 120%;
            font-family: "Times New Roman", sans-serif;
            color: white;
            background-color: #333333;
            margin-left: 30%;
            margin-right: 30%;
            border: dotted lightblue;
        }
        div {
            width: 400px;
            height: 200px;
            background: darkblue;
            position: absolute;
            top: 0;
            bottom: 0;
            left: 0;
            right: 0;
            margin: auto;
            border: double antiquewhite;
        }
    </style>
</head>
<body>
<form class="password" action="${pageContext.request.contextPath}/singin" method="post">
    <div>
        <label for="login">Login</label>
        <input type="text" name="login" id="login" required>
        <br>
        <label for="password">Password</label>
        <input type="password" name="password" id="password">
        <br>
        <input type="submit" value="Authorization">
    </div>
</form>
</body>
</html>

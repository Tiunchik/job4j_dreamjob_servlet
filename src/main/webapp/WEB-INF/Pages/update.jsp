<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="servlets.user.User" %>
<%@ page import="servlets.user.ValidateService" %><%--
  Created by IntelliJ IDEA.
  User: Tiunchik
  Date: 21.03.2020
  Time: 21:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Update user</title>
</head>
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
</style>
<body>

<form action="${pageContext.request.contextPath}/edit?" method="post">
    ID: <input type="number" name="id" value="<c:out value="${user.id}"></c:out>" readonly>
    <br>
    Name: <input type="text" name="name" value="<c:out value="${user.name}"></c:out>">
    <br>
    Login: <input type="text" name="login" value="<c:out value="${user.login}"></c:out>">
    <br>
    Email: <input type="text" name="email" value="<c:out value="${user.email}"></c:out>">
    <br>
    <input type="submit" value="Change">
</form>
</body>
</html>

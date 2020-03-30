<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="C" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="servlets.mainprogramm.User" %>
<%@ page import="servlets.mainprogramm.ValidateService" %><%--
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
<jsp:useBean id="user" scope="request" type="servlets.mainprogramm.User"/>

<jsp:useBean id="userrole" scope="request" type="servlets.mainprogramm.Role"/>

<jsp:useBean id="role" scope="request" type="servlets.mainprogramm.Role"/>

<form action="${pageContext.request.contextPath}/edit?" method="post">
    <p>ID: <input type="number" name="id" value="${user.id}" readonly></p>
    <p>Name: <input type="text" name="name" value="${user.name}"></p>
    <p>Login: <input type="text" name="login" value="${user.login}"></p>
    <p>Email: <input type="text" name="email" value="${user.email}"></p>
    <c:if test="${role.role.equals('admin')}">
        <p>Role:<select size="1" name="role" id="role" required>
            <option value="admin">Administrator</option>
            <option value="user">User</option>
        </select></p>
    </c:if>
    <p>Password: <input type="text" name="password" value="${userrole.password}"></p>
    <br>
    <input type="submit" value="Change">
</form>
</body>
</html>

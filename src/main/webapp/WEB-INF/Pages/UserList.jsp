<%--
  Created by IntelliJ IDEA.
  User: Tiunchik
  Date: 21.03.2020
  Time: 18:55
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>List of users</title>
    <style>
        body {
            font-size: 120%;
            font-family: "Times New Roman", sans-serif;
            color: white;
            background-color: #333333;
            margin-left: 27%;
            margin-right: 30%;
            border: dotted lightblue;
        }

        table {
            border: 1px solid black;
            width: 800px;
        }

        tr {
            border: 1px dashed grey;
        }

        th {
            border: 1px dashed grey;
        }

        td {
            border: 1px dashed grey;
        }
    </style>
</head>
<body>
<table>
    <tr>
        <th>ID number</th>
        <th>Name</th>
        <th>login</th>
        <th>E-mail</th>
        <th>Creation Date</th>
        <th>Photo</th>
        <th>Control buttons</th>
    </tr>
    <tr>
        <jsp:useBean id="users" scope="request" type="java.util.List"/>
        <c:forEach items="${users}" var="user">
        <td>${user.id}</td>
        <td>${user.name}</td>
        <td>${user.login}</td>
        <td>${user.email}</td>
        <td>${user.createDate}</td>
        <td><img src="${pageContext.servletContext.contextPath}/download?name=bin/images/${user.id}.jpg" width="100px"
                 height="100px" alt=""/></td>
        <td>
            <jsp:useBean id="role" scope="request" type="servlets.mainprogramm.Role"/>
            <c:if test="${role.role.equals('user') && user.equals(role.user)}">
                <form action="${pageContext.request.contextPath}/edit?" method="get">
                    <input type="hidden" name="id" value="${user.id}">
                    <input type="submit" value="Change">
                </form>
            </c:if>
            <c:if test="${role.role.equals('admin')}">
                <form action="${pageContext.request.contextPath}/edit?" method="get">
                    <input type="hidden" name="id" value="${user.id}">
                    <input type="submit" value="Change">
                </form>
                <form action="${pageContext.request.contextPath}/" method="post">
                    <input type="hidden" name="action" value="delete">
                    <input type="hidden" name="id" value="${user.id}">
                    <input type="submit" value="Delete">
                </form>
            </c:if>
           </td>
    </tr>
    </c:forEach>
</table>
<br>
<form action="${pageContext.request.contextPath}/create" method="get">
    <input type="submit" value="Add user">
</form>
<br>
<form action="${pageContext.request.contextPath}/logout" method="post">
    <input type="submit" value="Logout">
</form>

</body>

</html>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Tiunchik
  Date: 23.03.2020
  Time: 10:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add Images</title>
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
        <th>Control buttons</th>
    </tr>
    <tr>
        <c:forEach items="${users}" var="user">
        <td><c:out value="${user.id}"></c:out></td>
        <td><c:out value="${user.name}"></c:out></td>
        <td><c:out value="${user.login}"></c:out></td>
        <td><c:out value="${user.email}"></c:out></td>
        <td><c:out value="${user.createDate}"></c:out></td>
        <td>
            <form action="${pageContext.request.contextPath}/edit?" method="get">
                <input type="hidden" name="id" value="<c:out value="${user.id}"></c:out>">
                <input type="submit" value="Change">
            </form>
        </td>
    </tr>
    </c:forEach>
</table>
<br>
<form action="${pageContext.request.contextPath}/create" method="get">
    <input type="submit" value="Add user">
</form>
</body>
</html>

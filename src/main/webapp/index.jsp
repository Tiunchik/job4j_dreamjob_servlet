<%@ page import="servlets.user.User" %>
<%@ page import="servlets.user.ValidateService" %>
<%--
  Created by IntelliJ IDEA.
  User: Tiunchik
  Date: 21.03.2020
  Time: 18:55
  To change this template use File | Settings | File Templates.
--%>
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
        <th>Control buttons</th>
    </tr>
    <tr>
        <% for (User user : ValidateService.LOGIC.findALL()) { %>
        <td><%=user.getId()%>
        </td>
        <td><%=user.getName()%>
        </td>
        <td><%=user.getLogin()%>
        </td>
        <td><%=user.getEmail()%>
        </td>
        <td><%=user.getCreateDate()%>
        </td>
        <td>
            <form action="<%=request.getContextPath()%>/update.jsp?" method="get">
                <input type="hidden" name="id" value="<%=user.getId()%>">
                <input type="submit" value="Change">
            </form>
            <form action="<%=request.getContextPath()%>/list" method="post">
                <input type="hidden" name="action" value="delete">
                <input type="hidden" name="id" value="<%=user.getId()%>">
                <input type="submit" value="Delete">
            </form>
        </td>
    </tr>
    <% } %>
    <form action="<%=request.getContextPath()%>/create.jsp" method="get">
        <input type="submit" value="Add user">
    </form>
</table>
</body>
</html>

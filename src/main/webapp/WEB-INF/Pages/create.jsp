<%--
  Created by IntelliJ IDEA.
  User: Tiunchik
  Date: 21.03.2020
  Time: 20:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Create user</title>
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
</head>
<body>

<form action="${pageContext.request.contextPath}/create" method="post">
    <br>
    <label for="name">User name</label>
    <input type="text" name="name" id="name" value="" required>
    <br>
    <label for="id">User ID</label>
    <input type="number" name="id" id="id" value="" required>
    <br>
    <input type="hidden" name="image" id="image" value="${pageContext.servletContext.contextPath}/download?name=${image}">
    <input type="submit" value="Create">
</form>

<form action="${pageContext.request.contextPath}/load"  method="post" enctype="multipart/form-data">
    <label for="file">Photo</label>
    <br>
    <img src="${pageContext.servletContext.contextPath}/download?name=${image}" width="200px" height="200px" alt=""/>
    <br>
    <input type="file" name="file" id="file" value="">
    <br>
    <input type="submit" value="Add photo">
</form>

</body>
</html>

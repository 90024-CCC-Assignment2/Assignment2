<%--
  Created by IntelliJ IDEA.
  User: liusiyu
  Date: 2022/4/24
  Time: 01:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<table width="800px" cellpadding="0" cellspacing="0" align="center" border="1px">
    <tr>
        <td>Name</td>
        <td>Age</td>
    </tr>
    <c:forEach items="${result}" var="person">
    <tr>
        <td>${person.name}</td>
        <td>${person.age}</td>
    </tr>
    </c:forEach>
</body>
</html>

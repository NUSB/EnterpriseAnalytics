<%-- 
    Document   : admin
    Created on : 14.11.2017, 9:00:54
    Author     : Panas
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>admin Page</title>
    </head>
    <body>
        <h1>Hello admin!</h1>
        <c:url value="admin\userList" var="linkUserList"/>
        <a href="${linkUserList}">Пользователи</a>
    </body>
</html>

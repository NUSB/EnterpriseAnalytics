<%-- 
    Document   : admin
    Created on : 14.11.2017, 9:00:54
    Author     : Panas
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <%@include file="jspf/head.jspf" %>
    <body>
        
        <%@include file="jspf/header.jspf" %>
        панель администратора 
        <c:url value="admin\userList" var="linkUserList"/>
        <a href="${linkUserList}">Пользователи</a>
    </body>
</html>

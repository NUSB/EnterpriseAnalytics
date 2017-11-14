<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<c:url value="login" var="loginURL"/>
<c:url value="admin" var="adminURL"/>
<c:url value="user" var="userURL"/>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Analitic</title>
    </head>
    <body>
        <h1>Этот сайт создан для анализа бизнесса!</h1>
        <a href="${loginURL}">Войти</a>
        <a href="${adminURL}">админ</a>
        <a href="${userURL}">моя страница</a>
        <br>
        <a href="<c:url value="j_spring_security_logout" />" > Logout</a>
    </body>
</html>

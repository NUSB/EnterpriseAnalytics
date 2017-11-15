<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<c:url value="login" var="loginURL"/>
<c:url value="admin" var="adminURL"/>
<c:url value="user" var="userURL"/>
<html>
    <%@include file="jspf/head.jspf" %>
    <body>
        <h1>Этот сайт создан для анализа бизнесса!</h1>
        <a href="${loginURL}">Войти</a>
        <a href="${adminURL}">админ</a>
        <a href="${userURL}">моя страница</a>
    </body>
</html>

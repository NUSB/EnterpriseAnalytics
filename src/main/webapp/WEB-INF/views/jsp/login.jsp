<%-- 
    Document   : login
    Created on : 14.11.2017, 9:03:48
    Author     : Panas
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:url value="/j_spring_security_check" var="loginUrl" />

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>login page</title>
    </head>
    <body>
    
    <form method="post" action="${loginUrl}">
        <input type="text" name="username" placeholder="Username" required="required" class="input-txt" />
        <input type="password" name="password" placeholder="Password" required="required" class="input-txt" />
        <div class="login-footer">
            <a href="#" class="lnk"><span class="icon icon--min">ಠ╭╮ಠ</span> I've forgotten something</a>
            <button type="submit" class="btn btn--right">Sign in  </button>

        </div>
    </form>
</body>
</html>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:url value="/j_spring_security_check" var="loginUrl" />

<!DOCTYPE html>
<html >
    <%@include file="jspf/head.jspf" %>
    <body style="background-color: #5D92B0">
        <div class="container">
            <div class="login">
                <h1 class="login-heading">
                    <strong>Welcome.</strong> Please login.</h1>
                <form method="post" action="${loginUrl}">
                    <input type="text" name="username" placeholder="Username" required="required" class="input-txt" />
                    <input type="password" name="password" placeholder="Password" required="required" class="input-txt" />
                    <div class="login-footer">
                        <a href="#" class="lnk">
                            <span class="icon icon--min">ಠ╭╮ಠ</span> 
                            I've forgotten something
                        </a>
                        <button type="submit" class="btn btn--right">Sign in  </button>

                    </div>
                </form>
            </div>
        </div>

    </body>
</html>


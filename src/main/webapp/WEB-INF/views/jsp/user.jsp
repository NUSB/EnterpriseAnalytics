<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>user Page</title>
    </head>
    <body>
        <h1>Hello user!</h1>
        <sec:authorize access="hasRole('ROLE_ADMIN')">
            <a href="<c:url value="admin"/>">админка</a>
        </sec:authorize>

    </body>
</html>

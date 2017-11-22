<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <%@include file="../../jspf/head.jspf"%>
    <body>
        <%@include  file="../../jspf/header.jspf" %>
        <%@include file="../../jspf/adminMenu.jspf" %>
        <h1>Список пользователей системы</h1>
        <table>
            <c:forEach items="${users}" var="user">
                <tr>
                    <td>${user.name}</td>
                    <td>_${user.password}</td>
                    <td>_${user.enabled}</td>  
                </tr>
            </c:forEach>
        </table>

    </body>
</html>

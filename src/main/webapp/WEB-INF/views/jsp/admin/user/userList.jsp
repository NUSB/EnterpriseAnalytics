<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <%@include file="../../jspf/head.jspf" %>
    <body>
        <%@include file="../../jspf/header.jspf" %>
        <div class="grid-container">
            <%@include file="../../jspf/adminMenu.jspf" %>
            <div class="table-section">
                <h1>Список пользователей системы</h1>
                <button class="button_add"  onClick='location.href = "${pageContext.request.contextPath}/admin/user/add"'>Добавить</button>
                <table>
                    <tr>
                        <th>Логин</th>
                        <th>Пароль</th>
                        <th>Активный</th>
                        <th>Действие</th>
                    </tr>
                    <c:forEach items="${users}" var="user">
                        <tr> 
                            <td>${user.name}</td>
                            <td>${user.password}</td>
                            <td>${user.enabled}</td>
                            <td>
                                <button class="button_add"  onClick='location.href = "${pageContext.request.contextPath}/admin/user/${user.name}"'>Просмотр</button>
                                <button class="button_add"  onClick='location.href = "${pageContext.request.contextPath}/admin/user/delete/${user.name}"'>Удалить</button>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
        </div>
        <%@include file="../../jspf/footer.jspf" %>
    </body>
</html>

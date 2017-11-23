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
                <h1 class="page_title">Список пользователей системы</h1>
                <a class="icons_table" href="${pageContext.request.contextPath}/admin/user/add"><i class="demo-icon icon-doc-add"></i></a>
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
                                <a class="icons_table" href="${pageContext.request.contextPath}/admin/user/${user.name}"><i class="demo-icon icon-edit"></i></a>
                                <a class="icons_table" href="${pageContext.request.contextPath}/admin/user/delete/${user.name}"><i class="demo-icon icon-trash-empty"></i></a>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
        </div>
        <%@include file="../../jspf/footer.jspf" %>
    </body>
</html>

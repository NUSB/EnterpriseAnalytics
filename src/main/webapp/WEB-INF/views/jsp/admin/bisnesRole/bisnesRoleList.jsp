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
                <button class="button_add"  onClick='location.href = "${pageContext.request.contextPath}/admin/bisnesRole/add"'>Добавить</button>
                <table>
                    <tr>
                        <th>№</th>
                        <th>Наименование</th>
                        <th>Подчиненн</th>
                        <th>Действие</th>
                    </tr>
                    <c:forEach items="${bisnesRoles}" var="bisnesRole">
                        <tr accountNumber="${bisnesRole.id}"> 
                            <td>${bisnesRole.id}</td>
                            <td>${bisnesRole.name}</td>
                            <td>
                                <a href="#" accountLink="${bisnesRole.parent.id}">${bisnesRole.parent.id}</a> 
                            </td>
                            <td>
                                <button class="button_add"  onClick='location.href = "${pageContext.request.contextPath}/admin/bisnesRole/${bisnesRole.id}"'>Просмотр</button>
                                <button class="button_add"  onClick='location.href = "${pageContext.request.contextPath}/admin/bisnesRole/delete/${bisnesRole.id}"'>Удалить</button>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </div>

        </div>

        <%@include file="../../jspf/footer.jspf" %>
    </body>
</html>

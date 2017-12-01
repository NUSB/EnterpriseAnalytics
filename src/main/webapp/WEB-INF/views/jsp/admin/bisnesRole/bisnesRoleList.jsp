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
                <%@include file="../../jspf/messages.jspf" %>
                <h1 class="page_title">Роли учасников бизнес процессов</h1>
                <a class="icons_table" href="${pageContext.request.contextPath}/admin/bisnesRole/add"><i class="demo-icon icon-doc-add"></i></a>
                <table>
                    <tr>
                        <th>№</th>
                        <th>Наименование</th>
                        <th>Подчинен</th>
                        <th>Действие</th>
                    </tr>
                    <c:forEach items="${bisnesRoles}" var="bisnesRole">
                        <tr accountNumber="${bisnesRole.id}"> 
                            <td>${bisnesRole.id}</td>
                            <td>${bisnesRole.name}</td>
                            <td>
                                <a href="#" accountLink="${bisnesRole.parent.id}">${bisnesRole.parent.name}</a> 
                            </td>
                            <td>
                                <a class="icons_table" href="${pageContext.request.contextPath}/admin/bisnesRole/${bisnesRole.id}"><i class="demo-icon icon-edit"></i></a>
                                <a class="icons_table" href="${pageContext.request.contextPath}/admin/bisnesRole/delete/${bisnesRole.id}"><i class="demo-icon icon-trash-empty"></i></a>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </div>

        </div>

        <%@include file="../../jspf/footer.jspf" %>
    </body>
</html>

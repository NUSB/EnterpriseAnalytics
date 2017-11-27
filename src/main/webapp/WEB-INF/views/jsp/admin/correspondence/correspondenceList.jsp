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
                <h1 class="page_title">Корреспонденция счетов</h1>
                <a class="icons_table" href="${pageContext.request.contextPath}/admin/bisnesRole/add"><i class="demo-icon icon-doc-add"></i></a>
                <table>
                    <tr>
                        <th>№</th>
                        <th>Дт </th>
                        <th>Кт</th>
                        <th>Документ</th>
                        <th>Действие</th>
                    </tr>
                    <c:forEach items="${correspondences}" var="correspondence">
                        <tr> 
                            <td>${correspondence.id}</td>
                            <td>${correspondence.debet.code} (<a href="${pageContext.request.contextPath}/admin/acount/${correspondence.debet.id}">${correspondence.debet.name}</a>)</td>
                            <td>${correspondence.credit.code} (<a href="${pageContext.request.contextPath}/admin/acount/${correspondence.credit.id}">${correspondence.credit.name}</a>)</td>
                            <td><a href="#">${correspondence.document.name}</a></td>
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

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
                <h1 class="page_title">Список документов</h1>
                <a class="icons_table" href="${pageContext.request.contextPath}/admin/document/add"><i class="demo-icon icon-doc-add"></i></a>
                <table>
                    <tr>
                        <th>Название</th>
                        <th>Описание</th>
                        <th>Действие</th>
                    </tr>
                    <c:forEach items="${documents}" var="document">
                        <tr> 
                            <td>${document.name}</td>
                            <td>${document.annotation}</td>
                            <td>
                                <a class="icons_table" href="${pageContext.request.contextPath}/admin/document/${document.id}"><i class="demo-icon icon-edit"></i></a>
                                <a class="icons_table" href="${pageContext.request.contextPath}/admin/document/delete/${document.id}"><i class="demo-icon icon-trash-empty"></i></a>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
        </div>
        <%@include file="../../jspf/footer.jspf" %>
    </body>
</html>

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
                <button class="button_add"  onClick='location.href="${pageContext.request.contextPath}/admin/acount/add"'>Добавить</button>
                <table>
                    <tr>
                        <th>№</th>
                        <th>Наименование</th>
                        <th>А/П</th>
                        <th>Родитель</th>
                        <th>Группа</th>
                        <th>Действие</th>
                    </tr>

                    <c:forEach items="${acounts}" var="acount">

                        <tr  > <!--accountNumber="${acount.id}"-->
                            <td>${acount.code}</td>
                            <td>${acount.name}</td>
                            <td>${acount.type}</td>
                            <td><a href="#" >${acount.parent}</a></td> <!--accountLink="${acount.parent.id}"-->
                            <td>${acount.group}</td>
                            <td>
                                <button>Просмотр</button>
                                <button>Удалить</button>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </div>

        </div>

        <%@include file="../../jspf/footer.jspf" %>
    </body>
</html>

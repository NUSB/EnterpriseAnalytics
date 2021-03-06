<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
    <%@include file="../../jspf/head.jspf" %>
    <body>
        <%@include file="../../jspf/header.jspf" %>
        <div class="grid-container-full">
            <div class="grid-container">
                <%@include file="../../jspf/adminMenu.jspf" %>
                <div class="grid-container-full">
                    <%@include file="../../jspf/messages.jspf" %>

                    <div class="form-design">

                        <div class="grid-container-full">
                            <form:form method="POST" action="${sendTo}" class="data-form" modelAttribute="document" >

                                <div class="form-head">
                                    <h1>${title}</h1>
                                </div>

                                <div class="grid-container">
                                    <div class="form-name">
                                        <p class="vertical-alignment">Название: </p>
                                    </div>
                                    <div class="form-input">
                                        <form:input path="name" required="required"/>
                                    </div>

                                    <div class="form-name">
                                        <p>Описание:</p>
                                    </div>

                                    <div class="form-input">
                                        <form:textarea path="annotation" cols="30" rows="10" required="required" />
                                        <input class="save_button right-alignment" type="submit" value="Сохранить">
                                    </div>

                                </div>

                            </form:form>
                            <c:if test="${!isNewDocument}">
                                <form:form action="${pageContext.request.contextPath}/admin/document/${document.id}/responsibilityForDocuments/add" 
                                           id="form_roles" modelAttribute="responsibilityForDocuments">
                                    <div class="form-head-embeded">
                                        <h1>Роли</h1>
                                    </div>
                                    <div class="roles_document grid-container-cols-3">
                                        <div class="form-input">
                                            <p>Роли:</p>
                                            <input list="roles_document1" id="document-role"  name="role_d">
                                            <datalist id="roles_document1" aria-required="true">
                                                <c:forEach items="${allbisnesRoles}" var="bisnesRole">
                                                    <option value="${bisnesRole.name}"></option>
                                                </c:forEach>
                                            </datalist>
                                            <input type="text" name="role" disabled required="" id="editDocumentRole" class="invisible" />
                                        </div>
                                        <div class="form-input">
                                            <p>Описание:</p>
                                            <form:textarea path="annotation" id="description_role" cols="50" rows="5" required="required"/>
                                        </div>
                                        <div class="button">
                                            <input class="save_button" type="submit" id="roleAddButton" value="Добавить">
                                            <input class="save_button invisible" type="submit" id="roleEditButton" value="Сохранить">
                                            <input class="save_button invisible" type="button" id="roleCancelButton" value="Отмена">
                                        </div>
                                    </div>
                                </form:form>

                                <div class="table-section">
                                    <table>
                                        <tr>
                                            <th>Роль</th>
                                            <th>Описание</th>
                                            <th>Действие</th>
                                        </tr>
                                        <c:forEach items="${document.responsibilityForDocumentses}" var="item">
                                            <tr>
                                                <td class="document-role">${item.bisnesRole.name}</td>
                                                <td class="document-description">${item.annotation}</td>
                                                <td>
                                                    <a class="icons_table" href="${pageContext.request.contextPath}/admin/document/${document.id}/responsibilityForDocuments/delete/${item.id}"><i class="demo-icon icon-trash-empty"></i></a>
                                                    <a class="icons_table"  pathUpdateCurrentRole="${pageContext.request.contextPath}/admin/document/${document.id}/responsibilityForDocuments/update/${item.id}"><i class="demo-icon icon-edit"></i></a>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </table>

                                </div>
                            </c:if>
                        </div>
                    </div>
                </div>
            </div>


            <%@include file="../../jspf/footer.jspf" %>
        </div>
    </body>
</html>

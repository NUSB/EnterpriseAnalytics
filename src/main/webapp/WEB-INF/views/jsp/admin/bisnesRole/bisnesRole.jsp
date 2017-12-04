<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
    <%@include file="../../jspf/head.jspf" %>
    <body>
        <%@include file="../../jspf/header.jspf" %>
        <div class="grid-container">
            <%@include file="../../jspf/adminMenu.jspf" %>
            <div class="grid-container-full">
                <div class="form-design">
                    <div class="grid-container-full">
                        <form:form method="POST" action="${sendTo}" class="data-form" modelAttribute="bisnesRole" >
                            <div class="form-head">
                                <h1>${title}</h1>
                            </div>
                            <div class="grid-container">
                                <div class="form-name">
                                    <p class="vertical-alignment">Номер:</p>
                                </div>
                                <div class="form-input">
                                    <input type="text" value="${bisnesRole.id}" disabled="disabled"/>
                                </div>

                                <div class="form-name">
                                    <p class="vertical-alignment">Название: </p>
                                </div>
                                <div class="form-input">
                                    <form:input path="name" required="required"/>
                                </div>


                                <div class="form-name">
                                    <p>Подчинен</p>
                                </div>
                                <div class="form-input">
                                    <input list="parents1" name="subordination1" value="${bisnesRole.parent.name}" autocomplete="off"/>
                                    <datalist id="parents1" aria-required="true">
                                        <c:forEach items="${allbisnesRoles}" var="current_parrent">
                                            <option value="${current_parrent.name}">${current_parrent.id}</option>
                                        </c:forEach>
                                    </datalist>
                                </div>

                                <div class="form-name">
                                    <p>Описание:</p>
                                </div>
                                <div class="form-input">
                                    <form:textarea path="annotation" cols="30" rows="10" required="required" />
                                    <input class="save_button" type="submit" value="Сохранить">
                                </div>
                            </div>
                        </form:form>
                    </div>
                </div>
            </div>
        </div>
        <%@include file="../../jspf/footer.jspf" %>
    </body>
</html>

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
            <form:form method="POST" action="add" class="data-form" modelAttribute="acount" >
                <div class="form-head">
                    <h1>${title}</h1>
                </div>
                <div class="grid-container">
                    <div class="form-name">
                        <p class="vertical-alignment">Номер:</p>
                    </div>
                    <div class="form-input">
                        <form:input path="code" required="required"/>
                    </div>
                    <div class="form-name">
                        <p class="vertical-alignment">Название: </p>
                    </div>
                    <div class="form-input">
                        <form:input path="name" required="required"/>
                    </div>
                    <div class="form-name">
                        <p>Тип счета:</p>
                    </div>
                    <div class="form-input">
                        <p><form:radiobutton path="type" value="А"/> Активный</p>
                        <p><form:radiobutton path="type" value="П"/> Пассивный</p>
                        <p><form:radiobutton path="type" value="П/А" checked="checked"/> Активно-пассивный</p>
                    </div>
                    <div class="form-name">
                        <p>Родитель:</p>
                    </div>
                    <div class="form-input">
                        <form:input path="parent" list="parents"/>
                        <datalist id="parents" aria-required="true">
                            <c:forEach items="${acounts}" var="current_parrent">
                                <option value="${current_parrent.name}">${current_parrent.code}</option>
                            </c:forEach>
                        </datalist>
                    </div>

                    <div class="form-name">
                        <p>Группа:</p>
                    </div>
                    <div class="form-input">
                        <form:checkbox path="group"/>
                    </div>
                    <div class="form-name">
                        <p>Описание:</p>
                    </div>
                    <div class="form-input">
                        <form:textarea path="anotation" cols="30" rows="10" required="required" />
                        <input class="save_button" type="submit" value="Сохранить">
                    </div>
                </div>
            </form:form>
        </div>
        <%@include file="../../jspf/footer.jspf" %>
    </body>
</html>

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
                    <div class="form-design">

                        <div class="grid-container-full">
                            <form:form method="POST" action="${sendTo}" class="data-form" modelAttribute="user" >
                                <div class="form-head">
                                    <h1>${title}</h1>
                                </div>
                                <div class="grid-container">

                                    <div class="form-name">
                                        <p class="vertical-alignment">Логин:</p>
                                    </div>
                                    <div class="form-input">
                                        <form:input path="name" required="required"/>
                                    </div>
                                    <div class="form-name">
                                        <p class="vertical-alignment">Пароль:</p>
                                    </div>
                                    <div class="form-input">
                                        <form:input path="password" required="required"/>
                                    </div>
                                    <div class="form-name">
                                        <p>Права:</p>
                                    </div>
                                    <div class="form-input">
                                        <!--Checkbox autocopletion should be done in the next coding-->
                                        <p><input type="checkbox" userRestriction="0"/> Администрирование</p>
                                        <p><input type="checkbox" userRestriction="1"/> Бухгалтерия</p>
                                        <p><input type="checkbox" userRestriction="2"/> Документооборот</p>
                                        <p><input type="checkbox" userRestriction="3"/> Подчиненность</p>
                                    </div>
                                    <div class="form-name">
                                        <p>Активный:</p>
                                    </div>
                                    <div class="form-input">
                                        <form:checkbox path="enabled"/>
                                    </div>
                                    <div></div>
                                    <div class="form-input">
                                        <input class="save_button" type="submit" value="Сохранить">
                                    </div>
                                </div>
                            </form:form>
                        </div>
                    </div>
                </div>
            </div>
            <%@include file="../../jspf/footer.jspf" %>
        </div>
    </body>
</html>

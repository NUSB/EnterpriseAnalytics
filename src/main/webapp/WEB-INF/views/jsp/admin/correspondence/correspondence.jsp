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
                        <form:form method="POST" action="${sendTo}" class="data-form" modelAttribute="correspondence">
                            <div class="form-head">
                                <h1>${title}</h1>
                            </div>
                            <div class="grid-container">

                                <div class="form-name">
                                    <p>Дебет:</p>
                                </div>
                                <div class="form-input">
                                    <input list="acounts" name="debet" value="${correspondence.debet.name}"/>
                                    <datalist id="acounts" aria-required="true">
                                        <c:forEach items="${acounts}" var="current">
                                            <option value="${current.name}">${current.code}</option>
                                        </c:forEach>
                                    </datalist>
                                </div>
                                <div class="form-name">
                                    <p>Кредит</p>
                                </div>
                                <div class="form-input">
                                    <input list="acounts" name="credit" value="${correspondence.credit.name}"/>
                                </div>

                                <div class="form-name">
                                    <p>Документ:</p>
                                </div>
                                <div class="form-input">
                                    <input list="documents" name="document" value="${correspondence.document.name}"/>
                                    <datalist id="documents" aria-required="true">
                                        <c:forEach items="${documents}" var="doc">
                                            <option value="${doc.name}"/>
                                        </c:forEach>
                                    </datalist>
                                </div>  
                                <div class="form-name">
                                    <p>Описание:</p>
                                </div>
                                <div class="form-input">
                                    <textarea name="annotation" cols="30" rows="10" required="required">${correspondence.annotation}</textarea>
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

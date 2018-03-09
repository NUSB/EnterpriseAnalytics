<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Analytic</title>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/meyer-reset/2.0/reset.min.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/info-style.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/signin.css">

    </head>
    <body>
        <%@include file="../jspf/header.jspf" %>
        <div class="info-page-container">
            <section>
                <header >
                    <h1>Информация о счёте № ${acount.code} "${acount.name}"</h1>
                    <sec:authorize access="hasRole('ROLE_ADMIN')">
                        <label><a href="${pageContext.request.contextPath}/admin/acount/${acount.id}">Edit</a></label>
                    </sec:authorize>
                </header>
                <article>
                    <h2>Общая информация</h2>
                    <p>${acount.anotation}</p>
                    <c:if test="${acount.parent ne null}">
                        <p>Является субсчётом счёта № <a href="${pageContext.request.contextPath}/info/acount/${acount.parent.id}">${acount.parent.code} "${acount.parent.name}"</a></p>
                    </c:if>
                    <c:if test="${not empty acount.subAcounts}">
                        <p>Имеет следующие субсчета:</p>
                        <ul>
                            <c:forEach var="acc" items="${acount.subAcounts}">
                                <li>
                                    <a href="${pageContext.request.contextPath}/info/acount/${acc.id}">
                                        ${acc.code} 
                                        "${acc.name}"
                                    </a> - 
                                    ${acc.anotation}
                                </li>
                            </c:forEach>
                        </ul>
                    </c:if>    
                </article>

                <c:if test="${ not empty debet}">
                    <article>
                        <h2>Кореспондирует по дебету со следующими cчетами</h2>
                        <c:forEach var="acc" items="${debet}">
                            <p>
                                <a href="${pageContext.request.contextPath}/info/acount/${acc.credit.id}">
                                    ${acc.credit.code} "${acc.credit.name}"
                                </a> на основании документа 
                                <a href="${pageContext.request.contextPath}/info/document/${acc.document.id}"> 
                                    ${acc.document.name}
                                </a>. ${acc.annotation}
                            </p>
                        </c:forEach>
                    </article>
                </c:if>
                <c:if test="${ not empty credit}">
                    <article>
                        <h2>Кореспондирует по кредиту со следующими cчетами</h2>
                        <c:forEach var="acc" items="${credit}">
                            <p>
                                <a href="${pageContext.request.contextPath}/info/acount/${acc.debet.id}">
                                    ${acc.debet.code} "${acc.debet.name}"
                                </a> на основании документа 
                                <a href="${pageContext.request.contextPath}/info/document/${acc.document.id}"> 
                                    ${acc.document.name}
                                </a>. ${acc.annotation}
                            </p>
                        </c:forEach>
                    </article>
                </c:if>
                <article>
                    <a href="${pageContext.request.contextPath}/user" class="btn-back" tabindex="0"><span></span></a>
                </article>
            </section>
        </div>
        <%@include file="../jspf/footer.jspf" %>
    </body>
</html>
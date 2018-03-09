<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
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
                <header>
                    <h1>Информация об счёте № 5</h1>
                </header>
                <article>
                    <h2>Общая информация</h2>
                    <p>предназначен для того-то того-то. и ещё куча описания </p>
                </article>
                <article>
                    <h2>Кореспондирует по дебету со следующими четами</h2>
                    <p>предназначен для того-то того-то. и ещё куча описания </p>
                </article>
                <article>
                    <a onclick="history.back();" class="btn-back" tabindex="0"><span></span></a>
                </article>
            </section>
        </div>
        <%@include file="../jspf/footer.jspf" %>
    </body>
</html>
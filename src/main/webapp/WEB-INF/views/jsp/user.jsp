<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <%@include file="jspf/head.jspf" %>
    <body class="schema-page">
        <%@include file="jspf/header.jspf" %>

        <canvas style="border: 1px solid black; margin: 0px; padding: 0px;" id="graphicFrame"></canvas>
        <button class="button" id="schemaSaveButton">Сохранить</button>
        <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/GraphicFrame.js"></script>
    </body>
</html>

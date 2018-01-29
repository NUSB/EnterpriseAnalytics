<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <%@include file="jspf/head.jspf" %>
    <body class="schema-page">
        <%@include file="jspf/header.jspf" %>

        <canvas style="border: 1px solid black; margin: 0px; padding: 0px;" id="graphicFrame"></canvas>
        <button class="button" id="schemaSaveButton">Сохранить</button>
        <div id="willNotSaveMessage">Внимание ваши изменения схемы не будут сохранены<br>
        так как у вас не достаточно прав для этой операции!</div>
        <div class="scale-schema-block">
            <i id="scaleUp" class="icon-plus-squared"></i>
            <i id="scaleDown" class="icon-minus-squared"></i>
        </div>
        <div class="settings-icon"><i id="setting-icon" class="icon-cog"></i></div>
        <div class="schema-settings" id="schema-settings">
            <div>
            <input type="checkbox" id="setting-1" ><p>Бизнес-роли</p>
            </div>
            <div>
            <input type="checkbox" id="setting-2" ><p>Иерархия бизнес-ролей</p>
            </div>
            <div>
            <input type="checkbox" id="setting-3" ><p>Документы</p>
            </div>
            <div>
            <input type="checkbox" id="setting-4" ><p>Счета</p>
            </div>
            <div>
            <input type="checkbox" id="setting-5" ><p>Иерархия счетов</p>
            </div>
        </div>
        <div id = "info-chart-schema">
        </div>
        <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/GraphicFrame.js"></script>
    </body>
</html>

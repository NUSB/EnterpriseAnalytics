<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <%@include file="../../jspf/head.jspf" %>
    <body>
        
        <%@include file="../../jspf/header.jspf" %>
        <%@include file="../../jspf/adminMenu.jspf" %>
        <c:forEach items="${acounts}" var="ac">
            ${ac.name} <br>
        </c:forEach>
        
    </body>
</html>


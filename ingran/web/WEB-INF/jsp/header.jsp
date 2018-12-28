<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title><%=request.getParameter("pageTitle")%> - INGRAN</title>

        <spring:url value="/resources/css/bootstrap.min.css" var="bootstrapCss" />
        <spring:url value="/resources/css/estilos.css" var="coreCss" />

        <link href="${bootstrapCss}" rel="stylesheet" />
        <link href="${coreCss}" rel="stylesheet" />

        <spring:url value="/resources/js/jquery.min.js" var="coreJs" />

        <script src="${coreJs}"></script>
    </head>
    <body>
        <%
        HttpSession objsesion = request.getSession(false);

        String menu = (String) objsesion.getAttribute("menu");
        %>
        <jsp:include page="menu.jsp">
            <jsp:param name="menu" value="${menu}" />
        </jsp:include>
        
        <div style="margin-top:60px">
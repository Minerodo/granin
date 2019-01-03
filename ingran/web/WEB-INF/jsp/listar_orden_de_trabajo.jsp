<%@ page session="false"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<jsp:include page="header.jsp">
    <jsp:param name="pageTitle" value="Listar Catorcenas" />
</jsp:include>

<spring:url value="/editar_orden_de_trabajo.htm" var="urlEditarODT" />

<div class="col-md-12">
    <div id="tbl">
        <div class="table-responsive table-centered">
            <jsp:include page="filtro_botones.jsp"/>
            <table class="table table-hover" id="filtro">
                <thead>
                    <tr class="">
                        <th>Id</th>
                        <th>Titulo</th>
                        <th>Proyecto</th>
                        <th>Propietario</th>
                        <th>Fecha</th>
                        <th>Monto</th>
                        <th>Avance</th>
                        <th>Acciones</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="odt" items="${odts}">
                        <tr>
                            <td>${odt.id}</td>
                            <td>${odt.titulo}</td>
                            <td>${odt.proyecto.descripcion}</td>
                            <td>${odt.propietario.nombre}</td>
                            <td>${odt.fecha}</td>
                            <td>${odt.monto}</td>
                            <td>${odt.avance}%</td>
                            <td><a href="${urlEditarODT}?id=${odt.id}"><span class='glyphicon glyphicon-pencil'></span></a></td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>

<jsp:include page="filtro.jsp">
    <jsp:param name="columnas" value="" />
</jsp:include>

<jsp:include page="footer.jsp" />
<%@ page session="false"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<jsp:include page="header.jsp">
    <jsp:param name="pageTitle" value="Reporte de Actividad por Persona" />
</jsp:include>

<div class="col-md-12">
    <div id="tbl">
        <div class="table-responsive table-centered">

            <jsp:include page="filtro_botones.jsp"/>

            <h3 align="center"><p class="text-muted">REPORTE DE ACTIVIDAD POR PERSONA</p></h3>

            <label for="catorcenas">Catorcena:</label>
            <select id="catorcenas" onchange="agregar('catorcenas', 'catorcena', true);" class="form-control">
                <c:forEach var="catorcena" items="${catorcenas}">
                    <option value="${catorcena.id}" <c:if test="${catorcena.id == cat}">selected</c:if>>${catorcena.descripcion}</option>
                </c:forEach>
            </select>

            <table class="table table-hover" id="filtro">
                <thead>
                    <tr class="">
                        <th>Nombre</th>
                        <th>Actividad</th>
                            <c:forEach var="fecha" items="${fechas}">
                            <th>${fecha}</th>
                            </c:forEach>
                        <th>Total General</th>
                        <th>Estado</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="actividad_por_persona" items="${actividad_por_personas}">
                        <tr class="<c:if test="${actividad_por_persona.estado == 'APROBADO'}">success</c:if><c:if test="${actividad_por_persona.estado == 'RECHAZADO'}">danger</c:if><c:if test="${actividad_por_persona.estado == 'REGRESADO'}">warning</c:if>">
                            <td>${actividad_por_persona.nombre}</td>
                            <td>${actividad_por_persona.actividad}</td>
                            <c:forEach var="dato" items="${actividad_por_persona.fechas}">
                                <td>
                                    <c:if test="${dato != 0.0}">
                                        ${dato}
                                    </c:if>
                                </td>
                            </c:forEach>
                            <td>${actividad_por_persona.total_general}</td>
                            <td>${actividad_por_persona.estado}</td>
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

<spring:url value="/resources/js/util/planilla_gets_url.js" var="planilla_gets_urlJs"/>
<script src="${planilla_gets_urlJs}" charset="UTF-8"></script>

<jsp:include page="footer.jsp" /> 
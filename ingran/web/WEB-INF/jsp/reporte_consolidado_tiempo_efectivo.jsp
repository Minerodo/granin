<%@ page session="false"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<jsp:include page="header.jsp">
    <jsp:param name="pageTitle" value="Reporte de Horas por Persona" />
</jsp:include>

<div class="col-md-12">
    <div id="tbl">
        <div class="table-responsive table-centered">  

            <form:form name="frrc" id="frrc" method="POST" action="">
                <jsp:include page="filtro_botones.jsp"/>

                <h3 align="center"><p class="text-muted">REPORTE CONSOLIDADO DE TIEMPO EFECTIVO</p></h3>

                <label for="catorcenas">Catorcena:</label>
                <select id="catorcenas" onchange="agregar('catorcenas', 'catorcena', true);" class="form-control">
                    <c:forEach var="catorcena" items="${catorcenas}">
                        <option value="${catorcena.id}" <c:if test="${catorcena.id == cat}">selected</c:if>>${catorcena.descripcion}</option>
                    </c:forEach>
                </select>

                <div class="btn-toolbar" role="toolbar" aria-label="Toolbar with button groups">
                    <div class="btn-group" role="group" aria-label="First Group">
                        <input type="submit" name="at" class="btn btn-secondary" value="Aprobar Todo"/>
                        <input type="submit" name="afs" class="btn btn-secondary" value="Aprobar Filas Seleccionadas"/>
                        <input type="submit" name="scfs" class="btn btn-secondary" value="Solicitar Cambios en Filas Seleccionadas"/>
                        <input type="submit" name="rfs" class="btn btn-secondary" value="Rechazar Filas Seleccionadas"/>
                    </div>
                </div>

                <table class="table table-hover" id="filtro">
                    <thead>
                        <tr class="">
                            <th>Seleccionar</th>
                            <th>Nombre</th>
                            <th>Tipo de Horas</th>
                                <c:forEach var="fecha" items="${fechas}">
                                <th>${fecha}</th>
                                </c:forEach>
                            <th>Total General</th>
                            <th>Estado</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="tiempo_efectivo" items="${tiempo_efectivos}">
                            <tr class="<c:if test="${tiempo_efectivo.actividad == 'APROBADO'}">success</c:if><c:if test="${tiempo_efectivo.actividad == 'RECHAZADO'}">danger</c:if><c:if test="${tiempo_efectivo.actividad == 'REGRESADO'}">warning</c:if>">
                                <td><input type="checkbox" name="registro" value="${tiempo_efectivo.cargo},${tiempo_efectivo.concepto}"/></td>
                                <td>${tiempo_efectivo.nombre}</td>
                                <td>${tiempo_efectivo.tipo_de_hora}</td>
                                <c:forEach var="dato" items="${tiempo_efectivo.fechas}">
                                    <td>
                                        <c:if test="${dato != 0.0}">
                                            ${dato}
                                        </c:if>
                                    </td>
                                </c:forEach>
                                <td>${tiempo_efectivo.total_general}</td>
                                <td>${tiempo_efectivo.actividad}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </form:form>
        </div>
    </div>
</div>

<jsp:include page="filtro.jsp">
    <jsp:param name="columnas" value="col_0: \"none\"," />
</jsp:include>

<spring:url value="/resources/js/util/planilla_gets_url.js" var="planilla_gets_urlJs"/>
<script src="${planilla_gets_urlJs}" charset="UTF-8"></script>

<jsp:include page="footer.jsp" />
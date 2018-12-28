<%@ page session="false"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<jsp:include page="header.jsp">
    <jsp:param name="pageTitle" value="Reporte de Catorcena Cargada" />
</jsp:include>

<spring:url value="/editar_planilla_hora.htm" var="urlEditarPlanillaHora" />

<div class="col-md-12">
    <div id="tbl">
        <div class="table-responsive table-centered">

            <form:form name="frrc" id="frrc" method="POST" action="#">
                <jsp:include page="filtro_botones.jsp"/>

                <h3 align="center"><p class="text-muted">REPORTE CATORCENA CARGADA</p></h3>

                <label for="catorcenas">Catorcena:</label>
                <select id="catorcenas" onchange="agregar('catorcenas', 'catorcena', true);" class="form-control">
                    <c:forEach var="catorcena" items="${catorcenas}">
                        <option value="${catorcena.id}" <c:if test="${catorcena.id == cat}">selected</c:if>>${catorcena.descripcion}</option>
                    </c:forEach>
                </select>

                <c:if test="${not empty msg}">
                    <div class="alert alert-${css} alert-dismissible" role="alert">
                        <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                        <strong>${msg}</strong>
                    </div>
                </c:if>

                <c:if test="${menu == 'Ingeniero'}">
                    <div class="btn-toolbar" role="toolbar" aria-label="Toolbar with button groups">
                        <div class="btn-group" role="group" aria-label="First Group">
                            <input type="submit" name="at" class="btn btn-secondary" value="Aprobar Todo"/>
                            <input type="submit" name="afs" class="btn btn-secondary" value="Aprobar Filas Seleccionadas"/>
                            <input type="submit" name="scfs" class="btn btn-secondary" value="Solicitar Cambios en Filas Seleccionadas"/>
                            <input type="submit" name="rfs" class="btn btn-secondary" value="Rechazar Filas Seleccionadas"/>
                        </div>
                    </div>
                </c:if>

                <table class="table table-hover" id="filtro">
                    <thead>
                        <tr class="">
                            <c:if test="${menu != 'Administrador'}">
                                <th>
                                    <c:if test="${menu == 'Bodeguero'}">
                                        Accion
                                    </c:if>
                                    <c:if test="${menu == 'Ingeniero'}">
                                        Seleccionar
                                    </c:if>
                                </th>
                            </c:if>
                            <th>Fecha</th>
                            <th>DUI</th>
                            <th>Nombre</th>
                            <th>Cargo</th>
                            <th>Cliente</th>
                            <th>Centro de Costo</th>
                            <th>Fase</th>
                            <th>Descripcion de Fase</th>
                            <th>Hora Inicio</th>
                            <th>Hora Final</th>
                            <th>Tipo de Hora</th>
                            <th>Horas</th>
                            <th>Estado</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="catorcena_cargada" items="${catorcena_cargadas}">
                            <tr class="<c:if test="${catorcena_cargada.ingeniero_estado == 'APROBADO'}">success</c:if><c:if test="${catorcena_cargada.ingeniero_estado == 'RECHAZADO'}">danger</c:if><c:if test="${catorcena_cargada.ingeniero_estado == 'REGRESADO'}">warning</c:if>">
                                <c:if test="${menu != 'Administrador'}">    
                                <td>
                                    <c:if test="${menu == 'Bodeguero' && !catorcena_cargada.enviado}">
                                        <a href="${urlEditarPlanillaHora}?id=${catorcena_cargada.id}"><span class='glyphicon glyphicon-pencil'></span></a>
                                        </c:if>
                                        <c:if test="${menu == 'Ingeniero'}">
                                        <input type="checkbox" name="registro" value="${catorcena_cargada.id_detalle}"/>
                                    </c:if>
                                </td>
                                </c:if>
                                <td>${catorcena_cargada.fecha}</td>
                                <td>${catorcena_cargada.dui}</td>
                                <td>${catorcena_cargada.nombre}</td>
                                <td>${catorcena_cargada.cargo}</td>
                                <td>${catorcena_cargada.cliente}</td>
                                <td>${catorcena_cargada.centro_de_costo}</td>
                                <td>${catorcena_cargada.fase}</td>
                                <td>${catorcena_cargada.descripcion_de_fase}</td>
                                <td>${catorcena_cargada.hora_inicio}</td>
                                <td>${catorcena_cargada.hora_fin}</td>
                                <td>${catorcena_cargada.tipo_de_hora}</td>
                                <td>${catorcena_cargada.cantidad_de_horas}</td>
                                <td>${catorcena_cargada.ingeniero_estado}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </form:form>
        </div>
    </div>
</div>

<div class="col-md-12 col-md-push-9">
    <spring:url value="/enviar_catorcena.htm" var="urlEnviarCatorcena" />
    <a href="${urlEnviarCatorcena}" class="btn btn-primary">
        <c:if test="${menu == 'Bodeguero'}">
            Enviar Planilla a Ingeniero de Obra
        </c:if>
        <c:if test="${menu == 'Ingeniero'}">
            Enviar Planilla
        </c:if>
        <c:if test="${menu == 'Administrador'}">
            Generar Cargador
        </c:if>
    </a>
</div>

<c:if test="${menu != 'Administrador'}">
    <jsp:include page="filtro.jsp">
        <jsp:param name="columnas" value="col_0: \"none\", col_9: \"none\", col_10: \"none\", col_11: \"select\", col_12: \"none\", col_13: \"select\"," />
    </jsp:include>
</c:if>
<c:if test="${menu == 'Administrador'}">
    <jsp:include page="filtro.jsp">
        <jsp:param name="columnas" value="col_8: \"none\", col_9: \"none\", col_10: \"select\", col_11: \"none\", col_12: \"select\"," />
    </jsp:include>
</c:if>

<spring:url value="/resources/js/util/planilla_gets_url.js" var="planilla_gets_urlJs"/>
<script src="${planilla_gets_urlJs}" charset="UTF-8"></script>

<jsp:include page="footer.jsp" />
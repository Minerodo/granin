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

                <h3 align="center"><p class="text-muted">CARGADOR CATORCENAL</p></h3>

                <label for="catorcenas">Catorcena:</label>
                <select id="catorcenas" onchange="agregar('catorcenas', 'catorcena', true);" class="form-control">
                    <c:forEach var="catorcena" items="${catorcenas}">
                        <option value="${catorcena.id}" <c:if test="${catorcena.id == cat}">selected</c:if>>${catorcena.descripcion}</option>
                    </c:forEach>
                </select>

                <table class="table table-hover" id="filtro">
                    <thead>
                        <tr class="">
                            <th>Empleado</th>
                            <th>Concepto</th>
                            <th>Centro de costos</th>
                            <th>Nomina</th>
                            <th>Fase</th>
                            <th>Proyecto</th>
                            <th>Cantidad</th>
                            <th>Monto</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="cargador_catorcenal" items="${cargadors}">
                            <tr>
                                <td>${cargador_catorcenal.empleado}</td>
                                <td>${cargador_catorcenal.concepto}</td>
                                <td>${cargador_catorcenal.centro_de_costo}</td>
                                <td>${cargador_catorcenal.nomina}</td>
                                <td>${cargador_catorcenal.fase}</td>
                                <td>${cargador_catorcenal.proyecto}</td>
                                <td>${cargador_catorcenal.cantidad}</td>
                                <td>${cargador_catorcenal.monto}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </form:form>
        </div>
    </div>
</div>

<jsp:include page="filtro.jsp">
    <jsp:param name="columnas" value="" />
</jsp:include>

<spring:url value="/resources/js/util/planilla_gets_url.js" var="planilla_gets_urlJs"/>
<script src="${planilla_gets_urlJs}" charset="UTF-8"></script>

<jsp:include page="footer.jsp" />
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

                <h3 align="center"><p class="text-muted">CARGADOR SEMANAL</p></h3>

                <label for="catorcenas">Catorcena:</label>
                <select id="catorcenas" onchange="agregar('catorcenas', 'catorcena', true);" class="form-control">
                    <c:forEach var="catorcena" items="${catorcenas}">
                        <option value="${catorcena.id}" <c:if test="${catorcena.id == cat}">selected</c:if>>${catorcena.descripcion}</option>
                    </c:forEach>
                </select>
                <h4 align="center"><p class="text-muted">SEMANA 1</p></h4>
                <table class="table table-hover" id="filtro1">
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
                        <c:forEach var="cargador_semanal1" items="${cargadors1}">
                            <tr>
                                <td>${cargador_semanal1.empleado}</td>
                                <td>${cargador_semanal1.concepto}</td>
                                <td>${cargador_semanal1.centro_de_costo}</td>
                                <td>${cargador_semanal1.nomina}</td>
                                <td>${cargador_semanal1.fase}</td>
                                <td>${cargador_semanal1.proyecto}</td>
                                <td>${cargador_semanal1.cantidad}</td>
                                <td>${cargador_semanal1.monto}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
                
                <h4 align="center"><p class="text-muted">SEMANA 2</p></h4>
                <table class="table table-hover" id="filtro2">
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
                        <c:forEach var="cargador_semanal2" items="${cargadors2}">
                            <tr>
                                <td>${cargador_semanal2.empleado}</td>
                                <td>${cargador_semanal2.concepto}</td>
                                <td>${cargador_semanal2.centro_de_costo}</td>
                                <td>${cargador_semanal2.nomina}</td>
                                <td>${cargador_semanal2.fase}</td>
                                <td>${cargador_semanal2.proyecto}</td>
                                <td>${cargador_semanal2.cantidad}</td>
                                <td>${cargador_semanal2.monto}</td>
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

<script language="javascript" type="text/javascript">
    var tfConfig = {
        sort: true,
        filters_row_index: 0,
        //col_number_format: [null, null, 'US', 'US', 'US', 'US', 'US', 'US', 'US'],
        remember_grid_values: false,
        alternate_rows: true,
        rows_counter: true,
        rows_counter_text: "Filas Visualizadas: ",
        btn_reset: true,
        status_bar: false,
        fill_slc_on_demand: false,
        multiple_slc_tooltip: "Presione la tecla Ctrl para seleccionar múltiples valores",
        paging: false,
        paging_length: 10,
        display_all_text: "Mostrar Todos",
        input_watermark: 'Buscar...',
        enable_empty_option: true,
        empty_text: "Mostrar Vacios",
    };
    var tf1 = setFilterGrid("filtro1", tfConfig);
    var tf2 = setFilterGrid("filtro2", tfConfig);
</script>

<jsp:include page="footer.jsp" />
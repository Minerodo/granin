<%@ page session="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<jsp:include page="header.jsp">
    <jsp:param name="pageTitle" value="Crear Planilla Hora" />
</jsp:include>

<c:if test="${not empty urlNuevo}">
    <a class="btn btn-success" href="${urlNuevo}">Nuevo Registro</a>                
</c:if>
<h3 align="center"><p class="text-muted">REGISTRAR PLANILLA AL DIA</p></h3>

<c:if test="${not empty msg}">
    <div class="alert alert-${css} alert-dismissible" role="alert">
        <button type="button" class="close" data-dismiss="alert" aria-label="Close">
            <span aria-hidden="true">&times;</span>
        </button>
        <strong>${msg}</strong>
    </div>
</c:if>

<form:form name="frrc" id="frrc" method="POST" action="" commandName="planilla">
    <div class="col-xs-12 col-sm-12 col-md-6">
        <div class="form-group">
            <div class="col-xs-12 col-sm-12 col-md-12">
                <form:input path="bodeguero.id" readonly="true" hidden="true"/>
                <form:label path="bodeguero.empleado.nombre">Bodeguero:</form:label>
                <form:input path="bodeguero.empleado.nombre" class="form-control" placeholder="Bodeguero" type="text" required="true" readonly="true"/>
            </div>
        </div>
        <div class="form-group">
            <div class="col-xs-12 col-sm-6 col-md-6">
                <form:input path="catorcena.id" hidden="true"/>
                <form:label path="catorcena.descripcion">Catorcena:</form:label>
                <form:input path="catorcena.descripcion" class="form-control" placeholder="Catorcena" type="text" required="true" readonly="true"/>
                <form:errors path="catorcena.descripcion"/>
            </div>
        </div>
        <div class="form-group">
            <div class="col-xs-12 col-sm-6 col-md-6">
                <form:label path="fecha">Fecha:</form:label>
                <form:input path="fecha" onfocusout="agregar('fecha','fh',false);" class="form-control" placeholder="Fecha" min="${planilla.catorcena.fecha_inicio}" max="${planilla.catorcena.fecha_fin}" type="date" required="true"/>
                <form:errors path="fecha"/>
            </div>
        </div>
        <div class="form-group">
            <div class="col-xs-12 col-sm-6 col-md-12">
                <form:label path="empleado.nombre">Empleado:</form:label>
                <form:input path="empleado.nombre" onfocusout="agregar_desde_lista('empleado.nombre', 'emp', 'listEmpleado', true);" class="form-control" list="listEmpleado" placeholder="Empleado" type="text" required="true"/>
                <form:errors path="empleado.nombre"/>
            </div>
        </div>
        <div class="form-group">
            <div class="col-xs-12 col-sm-6 col-md-12">
                <form:input path="empleado.empleado" hidden="true"/>
                <form:label path="empleado.puesto.descripcion">Cargo:</form:label>
                <form:input path="empleado.puesto.descripcion" class="form-control" placeholder="Cargo" type="text" required="true" readonly="true"/>
                <form:errors path="empleado.puesto.descripcion"/>
            </div>
        </div>
    </div>
    <div class="col-xs-12 col-md-6">
        <div class="form-group">
            <div class="col-xs-12 col-sm-6 col-md-12">
                <form:input path="cliente.cliente" hidden="true"/>
                <form:label path="cliente.nombre">Cliente:</form:label>
                <form:input path="cliente.nombre" onfocusout="agregar_desde_lista('cliente.nombre', 'cl', 'listCliente', false);" class="form-control" list="listCliente" placeholder="Cliente" type="text" required="true"/>
                <form:errors path="cliente.nombre"/>
            </div>
        </div>
        <div class="form-group">
            <div class="col-xs-12 col-sm-6 col-md-12">
                <form:input path="centro_de_costo.centro_de_costo" hidden="true"/>
                <form:label path="centro_de_costo.descripcion">Centro de Costo:</form:label>
                <form:input path="centro_de_costo.descripcion" onfocusout="agregar_desde_lista('centro_de_costo.descripcion', 'cdc', 'listCentroDeCosto', true);" class="form-control" list="listCentroDeCosto" placeholder="Centro de Costo" type="text" required="true"/>
                <form:errors path="centro_de_costo.descripcion"/>
            </div>
        </div>
        <div class="form-group">
            <div class="col-xs-12 col-md-2">
                <label class="text-center">Horario:</label>
            </div>
        </div>
        <div class="form-group">
            <div class="col-xs-6 col-md-3 col-md-offset-2">
                <form:label path="hora_entrada">Hora Entrada:</form:label>
                <form:input path="hora_entrada" class="form-control" onchange="calcular();" placeholder="Hora de Entrada" type="time" required="true"/>
                <form:errors path="hora_entrada"/>
            </div>
        </div>
        <div class="form-group">
            <div class="col-xs-6 col-md-3 col-md-offset-2">
                <form:label path="hora_salida">Hora Salida:</form:label>
                <form:input path="hora_salida" class="form-control" onchange="calcular();" placeholder="Hora de Salida" type="time" required="true"/>
                <form:errors path="hora_salida"/>
            </div>
        </div>
        <div class="form-group">
            <div class="col-xs-12 col-md-2">
                <label class="text-center alignCenter">Tiempo de Receso:</label>
            </div>
        </div>
        <div class="form-group">
            <div class="col-xs-3  col-md-3 col-md-offset-2">
                <form:label path="tiempo_receso_horas">Horas:</form:label>
                <form:input path="tiempo_receso_horas" class="form-control" onchange="calcular();" placeholder="Horas" type="number" required="true"/>
                <form:errors path="tiempo_receso_horas"/>
            </div>
        </div>
        <div class="form-group">
            <div class="col-xs-3 col-md-3 col-md-offset-2">
                <form:label path="tiempo_receso_minutos">Minutos:</form:label>
                <form:input path="tiempo_receso_minutos" class="form-control" onchange="calcular();" placeholder="Minutos" type="number" required="true"/>
                <form:errors path="tiempo_receso_minutos"/>
            </div>
        </div>
        <div class="form-group">
            <div class="col-xs-6 col-md-3 col-md-offset-9">
                <form:label path="horas_efectivas">Horas Efectivas:</form:label>
                <form:input path="horas_efectivas" class="form-control" type="text" required="true" readonly="true"/>
                <form:errors path="horas_efectivas"/>
                <span id="total"></span>
            </div>
        </div>
    </div>
    <form:errors path="bodeguero.empleado.nombre"/>
    <div class="form-group">
        <div class="col-xs-12 col-md-12">
            <input type="text" name="lista" id="lista" value="${lista}" hidden="true"/>
            <a href="javascript:void(0);" class="add_button btn btn-success" title="Add field">Agregar Fases</a>
        </div>
    </div>
    <div class="col-xs-12 col-md-12 col-lg-12">
        <div class="col-xs-3 col-md-2 col-lg-2">
            Fase
        </div>
        <div class="hidden-xs col-md-2">
            Descripcion
        </div>
        <div class="col-xs-2 col-md-2">
            Hora Inicio
        </div>
        <div class="col-xs-2 col-md-2">
            Hora Final
        </div>
        <div class="col-xs-2 col-md-2">
            Tipo de Hora
        </div>
        <div class="col-xs-2 col-md-2">
            Cantidad de Horas
        </div>
    </div>
    <div class="field_wrapper">
        <c:forEach items="${planilla.planilla_hora_detalle}" var="phd" varStatus="status">
            <div class="col-xs-12 col-md-12 col-lg-12">
                <div class="col-xs-3 col-md-2 col-lg-2">
                    <input value="${phd.fase.fase}" name="planilla_hora_detalle[${status.index}].fase.fase" onfocusout="seleccionarFase(${status.index});" list="listFase" class="form-control" id="planilla_hora_detalle${status.index}.fase.fase" required="true" type="text" placeholder="Fase">
                </div>
                <div class="hidden-xs col-md-2 col-lg-2">
                    <input value="${phd.fase.nombre}" name="planilla_hora_detalle[${status.index}].fase.nombre" class="form-control" id="planilla_hora_detalle${status.index}.fase.nombre" required="true" type="text" placeholder="Descripcion" readonly="true">
                </div>
                <div class="col-xs-2 col-md-2 col-lg-2">
                    <input value="${phd.hora_inicio}" name="planilla_hora_detalle[${status.index}].hora_inicio" class="form-control" id="planilla_hora_detalle${status.index}.hora_inicio" required="true" onchange="calc(${status.index});" type="time">
                </div>
                <div class="col-xs-2 col-md-2 col-lg-2">
                    <input value="${phd.hora_fin}" name="planilla_hora_detalle[${status.index}].hora_fin" class="form-control" id="planilla_hora_detalle${status.index}.hora_fin" required="true" onchange="calc(${status.index});" type="time">
                </div>
                <div class="col-xs-2 col-md-2 col-lg-2">
                    <input value="${phd.concepto.descripcion}" name="planilla_hora_detalle[${status.index}].concepto.descripcion" list="listConcepto" class="form-control" id="planilla_hora_detalle${status.index}.concepto.descripcion" required="true" type="text" placeholder="Tipo de Hora">
                </div>
                <div class="col-xs-2 col-md-2 col-lg-2">
                    <input value="${phd.cantidad_hora}" name="planilla_hora_detalle[${status.index}].cantidad_hora" class="form-control" id="planilla_hora_detalle${status.index}.cantidad_hora" required="true" type="text" readonly="readonly">
                </div>
            </div>
        </c:forEach>
    </div>
    <div class="col-md-11 col-md-offset-1">
        <div class="col-md-2 col-md-offset-10">
            <form:label path="cantidad_de_horas">Horas Totales:</form:label>
            <form:input path="cantidad_de_horas" class="form-control" type="text" required="true" readonly="true"/>
            <form:errors path="cantidad_de_horas"/>
        </div>
    </div>
    <c:if test="${empty urlNuevo}">
        <div class="col-xs-12">
            <a href="" class="btn btn-danger">Quitar Fases</a>
            <input type="submit" class="btn btn-primary" value="Aceptar">
        </div>
    </c:if>
</form:form>

<spring:url value="/resources/js/util/proyecto_horario.js" var="proyecto_horarioJs" />
<script src="${proyecto_horarioJs}"></script>

<spring:url value="/resources/js/util/planilla_fase.js" var="planilla_faseJs"/>
<script src="${planilla_faseJs}"></script>

<spring:url value="/resources/js/util/planilla_gets_url.js" var="planilla_gets_urlJs"/>
<script src="${planilla_gets_urlJs}" charset="UTF-8"></script>

<jsp:include page="agregar_eliminar_elementos.jsp">
    <jsp:param name="indicex" value="x = ${indice};" />
    <jsp:param name="indicey" value="y = ${indice};" />
</jsp:include>

<spring:url value="/resources/js/util/lista_seleccion.js" var="lista_seleccion_urlJs"/>
<script src="${lista_seleccion_urlJs}" charset="UTF-8"></script>

<datalist id="listCliente">
    <c:forEach var="cliente" items="${clientes}">
        <option value="${cliente.nombre}" data-ejemplo="${cliente.cliente}"/>
    </c:forEach>
</datalist>

<datalist id="listCentroDeCosto">
    <c:forEach var="centro_de_costo" items="${centro_de_costos}">
        <option value="${centro_de_costo.centro_de_costo.descripcion}" data-ejemplo="${centro_de_costo.centro_de_costo.centro_de_costo}"/>
    </c:forEach>
</datalist>

<datalist id="listFase">
    <c:forEach var="fase" items="${fases}">
        <option value="${fase.fase}" label="${fase.nombre}" data-ejemplo="${fase.nombre}"/>
    </c:forEach>
</datalist>

<datalist id="listConcepto">
    <c:forEach var="concepto" items="${conceptos}">
        <option value="${concepto.descripcion}" label="${concepto.concepto}" data-ejemplo="${concepto.concepto}"/>
    </c:forEach>
</datalist>

<datalist id="listEmpleado">
    <c:forEach var="empleado" items="${empleados}">
        <option value="${empleado.nombre}" label="${empleado.empleado}" data-ejemplo="${empleado.empleado}"/>
    </c:forEach>
</datalist>

<jsp:include page="footer.jsp" /> 

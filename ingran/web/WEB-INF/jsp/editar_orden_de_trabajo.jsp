<%@ page session="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<jsp:include page="header.jsp">
    <jsp:param name="pageTitle" value="Crear Orden de Trabajo" />
</jsp:include>

<div class="col-xs-4 col-sm-4 col-md-4 col-lg-4">
    <h3 align="center"><p class="text-muted">REGISTRAR ORDEN DE TRABAJO</p></h3>
    <c:if test="${not empty msg}">
        <div class="alert alert-${css} alert-dismissible" role="alert">
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
            <strong>${msg}</strong>
        </div>
    </c:if>
    <form:form name="frrc" id="frrc" method="POST" action="" commandName="odt">
        <div class="form-group">
            <form:label path="id">Centro de Costo:</form:label>
            <form:input path="id" class="form-control" type="text" required="true" readonly="true"/>
            <form:errors path="id"/>
        </div>
        <div class="form-group">
            <form:label path="proyecto.centro_de_costo">Centro de Costo:</form:label>
            <form:input path="proyecto.centro_de_costo" onfocusout="seleccionarProyecto();" class="form-control" placeholder="Centro de Costo" list="listProyectos" type="text" required="true"/>
            <form:errors path="proyecto.centro_de_costo"/>
        </div>
        <div class="form-group">
            <form:label path="proyecto.descripcion">Proyecto:</form:label>
            <form:input path="proyecto.descripcion" class="form-control" placeholder="Proyecto" type="text" required="true" readonly="true"/>
            <form:errors path="proyecto.descripcion"/>
        </div>
        <div class="form-group">
            <form:label path="propietario.cliente">Cliente</form:label>
            <form:input path="propietario.cliente" onfocusout="seleccionarCliente();" class="form-control" placeholder="Cliente" list="listPropietarios" type="text" required="true"/>
            <form:errors path="propietario.cliente"/>
        </div>
        <div class="form-group">
            <form:label path="propietario.nombre">Propietario:</form:label>
            <form:input path="propietario.nombre" class="form-control" placeholder="Propietario" type="text" required="true" readonly="true"/>
            <form:errors path="propietario.nombre"/>
        </div>
        <div class="form-group">
            <form:label path="titulo">Titulo:</form:label>
            <form:input path="titulo" class="form-control" placeholder="Titulo" type="text" required="true"/>
            <form:errors path="titulo"/>
        </div>
        <div class="form-group">
            <form:label path="fecha">Fecha:</form:label>
            <form:input path="fecha" class="form-control" type="date" required="true"/>
            <form:errors path="fecha"/>
        </div>
        <input type="submit" class="btn btn-primary" value="Guardar">
        <input type="submit" class="btn btn-success" value="Guardar y Cerrar">

        <spring:url value="/listar_orden_de_trabajo.htm" var="urlListarODT" />
        <a href="${urlListarODT}" class="btn btn-danger">Cerrar</a>
    </form:form>
</div>

<div class="col-xs-8 col-sm-8 col-md-8 col-lg-8">
    <h3 align="center"><p class="text-muted">DETALLE DE ORDEN</p></h3>
    <div id="tbl">
        <div class="table-responsive table-centered">
            <jsp:include page="filtro_botones.jsp"/>
            <table class="table table-hover" id="filtro">
                <thead>
                    <tr>
                        <th>Item</th>
                        <th>Descripcion</th>
                        <th>Unidad</th>
                        <th>Cantidad</th>
                        <th>P.U.</th>
                        <th>SubTotal</th>
                        <th>Avance</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="catorcena" items="${catorcenas}">
                        <tr class="<c:choose><c:when test="${catorcena.activo}">success</c:when><c:otherwise>danger</c:otherwise></c:choose>">
                            <td>${catorcena.descripcion}</td>
                            <td>
                                <c:if test="${catorcena.activo}">
                                    Activa
                                </c:if>
                                <c:if test="${!catorcena.activo}">
                                    Desactivada
                                </c:if>
                            </td>
                            <td>
                                <a href="${urlEditarCatorcena}?id=${catorcena.id}"><span class='glyphicon glyphicon-pencil'></span></a>
                                    <c:choose>
                                        <c:when test="${catorcena.activo}">
                                        <a href="${urlDesactivarCatorcena}?id=${catorcena.id}"><span class='glyphicon glyphicon-eye-open'></span></a>
                                        </c:when>
                                        <c:otherwise>
                                        <a href="${urlActivarCatorcena}?id=${catorcena.id}"><span class='glyphicon glyphicon-eye-close'></span></a>
                                        </c:otherwise>
                                    </c:choose>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>

<datalist id="listProyectos">
    <c:forEach var="proyecto" items="${proyectos}">
        <option value="${proyecto.centro_de_costo}" label="${proyecto.descripcion}" data-ejemplo="${proyecto.descripcion}"/>
    </c:forEach>
</datalist>

<datalist id="listPropietarios">
    <c:forEach var="propietario" items="${propietarios}">
        <option value="${propietario.cliente}" label="${propietario.nombre}" data-ejemplo="${propietario.nombre}"/>
    </c:forEach>
</datalist>

<script>
    function seleccionarProyecto() {
        var dato = document.getElementById('proyecto.centro_de_costo').value;
        var value = $('#listProyectos [value="' + dato + '"]').data('ejemplo');
        document.getElementById('proyecto.descripcion').value = value;
    }
    
    function seleccionarCliente() {
        var dato = document.getElementById('propietario.cliente').value;
        var value = $('#listPropietarios [value="' + dato + '"]').data('ejemplo');
        document.getElementById('propietario.nombre').value = value;
    }
</script>

<jsp:include page="filtro.jsp">
    <jsp:param name="columnas" value="" />
</jsp:include>

<jsp:include page="footer.jsp" />
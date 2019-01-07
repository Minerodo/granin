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
            <form:label path="id">Id:</form:label>
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
            <form:label path="propietario.cliente">Cliente:</form:label>
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

    <!-- Orden de Trabajo Detalle Modal -->
    <spring:url value="/resources/css/style.css" var="styleCss" />
    <link href="${styleCss}" rel="stylesheet" />

    <button onclick="document.getElementById('id01').style.display = 'block'" style="width:auto;" class="btn btn-primary">Agregar Actividades</button>

    <div id="id01" class="modal">
        <div class="imgcontainer">
            <span onclick="document.getElementById('id01').style.display = 'none'" class="close" title="Close Modal">&times;</span>
        </div>
        <form:form class="modal-content animate" name="frrc" id="frrc" method="POST" action="" commandName="odtd">
            <div class="container">
                <div class="form-group col-xs-1">
                    <form:label path="laudo.id">Id:</form:label>
                    <form:input path="laudo.id" class="form-control" type="text" required="true" readonly="true" />
                    <form:errors path="laudo.id"/>
                </div>
                <div class="form-group col-xs-4">
                    <form:label path="laudo.descripcion">Actividad:</form:label>
                    <form:input path="laudo.descripcion" onfocusout="seleccionarLaudo();" class="form-control" list="listActividades" type="text" required="true" />
                    <form:errors path="laudo.descripcion"/>
                </div>
                <div class="form-group col-xs-1">
                    <form:label path="laudo.unidad_medida.nombre">Unidad:</form:label>
                    <form:input path="laudo.unidad_medida.nombre" class="form-control" type="text" required="true" readonly="true" />
                    <form:errors path="laudo.unidad_medida.nombre"/>
                </div>
                <div class="form-group col-xs-1">
                    <form:label path="cantidad">Cantidad:</form:label>
                    <form:input path="cantidad" class="form-control" type="text" required="true" />
                    <form:errors path="cantidad"/>
                </div>
                <div class="form-group col-xs-2">
                    <form:label path="laudo.costo">P.U.:</form:label>
                    <form:input path="laudo.costo" class="form-control" type="text" required="true" readonly="true" />
                    <form:errors path="laudo.costo"/>
                </div>
                <div class="form-group col-xs-2">
                    <form:label path="subtotal">Sub-Total:</form:label>
                    <form:input path="subtotal" class="form-control" type="text" required="true" readonly="true" />
                    <form:errors path="subtotal"/>
                </div>
                
            </div>
                <input type="submit" name="agregar" class="btn btn-primary" value="Agregar">
        </form:form>
    </div>

    <script>
        // Get the modal
        var modal = document.getElementById('id01');

        // When the user clicks anywhere outside of the modal, close it
        window.onclick = function (event) {
            if (event.target == modal) {
                modal.style.display = "none";
            }
        }
    </script>
    <!-- Orden de Trabajo Detalle Modal -->
</div>

<div class="col-xs-8 col-sm-8 col-md-8 col-lg-8">
    <h3 align="center"><p class="text-muted">DETALLE DE ORDEN</p></h3>
    <div id="tbl">
        <div class="table-responsive table-centered">

            <table class="table table-hover" id="filtro">
                <thead>
                    <tr>
                        <th></th>
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
                    <c:forEach var="act" items="${acti}">
                        <tr>
                            <td><input type="checkbox" name="registro" value="${act.id}"/></td>
                            <td>${act.laudo.id}</td>
                            <td>${act.laudo.descripcion}</td>
                            <td>${act.laudo.unidad_medida.nombre}</td>
                            <td>${act.cantidad}</td>
                            <td>${act.precio_unitario}</td>
                            <td>${act.subtotal}</td>
                            <td>${act.avance}</td>
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

<datalist id="listActividades">
    <c:forEach var="actividad" items="${actividades}">
        <option value="${actividad.descripcion}" label="${actividad.id}" data-ejemplo="${actividad.id}" data-ejemplo1="${actividad.costo}" data-ejemplo2="${actividad.unidad_medida.nombre}"/>
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

    function seleccionarLaudo() {
        var dato = document.getElementById('laudo.descripcion').value;
        var value = $('#listActividades [value="' + dato + '"]').data('ejemplo');
        document.getElementById('laudo.id').value = value;
        var value1 = $('#listActividades [value="' + dato + '"]').data('ejemplo1');
        document.getElementById('laudo.costo').value = value1;
        var value2 = $('#listActividades [value="' + dato + '"]').data('ejemplo2');
        document.getElementById('laudo.unidad_medida.nombre').value = value2;
    }

    function subtotal() {
        alert('hola');
    }
</script>

<jsp:include page="filtro.jsp">
    <jsp:param name="columnas" value="" />
</jsp:include>

<jsp:include page="footer.jsp" />
<%@ page session="false"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<jsp:include page="header.jsp">
    <jsp:param name="pageTitle" value="Editar Usuario" />
</jsp:include>

<spring:url value="/desasignar_proyecto.htm" var="urlDesasignarProyecto" />

<div class="col-md-4">                    
    <h3 align="center"><p class="text-muted">ASIGNAR PROYECTOS</p></h3>                
    <c:if test="${not empty msg}">        
        <div class="alert alert-${css} alert-dismissible" role="alert">
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
            <strong>${msg}</strong>
        </div>
    </c:if>
    <form:form method="POST" modelAttribute="usuario" >
        <!--
        <div class="form-group">    
        <form:label path="id">Id:</form:label>                            
        <form:input path="id" class="form-control" readonly="true"/>
        <form:errors path="id"/>
        </div>
        -->
        <div class="form-group">
            <form:label path="usuario">Usuario:</form:label>
            <form:input path="usuario" class="form-control" readonly="true"/>
        </div>
        <div class="form-group">
            <form:label path="empleado.nombre">Empleado:</form:label> 
            <form:input path="empleado.nombre" class="form-control" readonly="true"/>
        </div>
        <div class="form-group">
            <form:label path="rol.nombre">Rol:</form:label>
            <form:input path="rol.nombre" class="form-control" readonly="true"/>
        </div>
        <div class="form-group">
            <form:label path="centro_de_costo.centro_de_costo">Centro de Costo:</form:label> 
            <form:input path="centro_de_costo.centro_de_costo" onfocusout="seleccionar();" class="form-control" list="listProyecto" placeholder="Seleccionar..." required="true"/>
            <form:errors path="centro_de_costo.centro_de_costo"/>
        </div>
        <div class="form-group">
            <form:label path="centro_de_costo.descripcion">Descripcion:</form:label> 
            <form:input path="centro_de_costo.descripcion" class="form-control" readonly="true" required="true"/>
            <form:errors path="centro_de_costo.descripcion"/>
        </div>
        <input type="submit" name="asignar" class="btn btn-primary" value="Asignar">
        <input type="submit" name="cerrar" class="btn btn-success" value="Asignar y Cerrar">

        <spring:url value="/listar_usuarios.htm" var="urlListarUsuarios" /> 
        <a href="${urlListarUsuarios}" class="btn btn-danger">Cerrar</a>
    </form:form>
</div>

<datalist id="listProyecto">
    <c:forEach var="proyecto" items="${proyectos}">
        <option value="${proyecto.centro_de_costo}" label="${proyecto.descripcion}" data-ejemplo="${proyecto.descripcion}"/>
    </c:forEach>
</datalist>

<script>
    function seleccionar() {
        var dato = document.getElementById('centro_de_costo.centro_de_costo').value;
        var value = $('#listProyecto [value="' + dato + '"]').data('ejemplo');

        document.getElementById('centro_de_costo.descripcion').value = value;
    }
</script>

<div class="col-md-8">                    
    <h3 align="center"><p class="text-muted">PROYECTOS ASIGNADOS</p></h3>               
    <div class="table-responsive table-centered">
        <!--<jsp:include page="filtro_botones.jsp"/>-->
        <table class="table table-hover" id="filtro">
            <thead>
                <tr>
                    <th>Centro de Costo</th>
                    <th>Descripcion Centro de Costo</th>
                    <th>Accion</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="centro_de_costo" items="${centro_de_costos}">
                    <tr>
                        <td>${centro_de_costo.centro_de_costo.centro_de_costo}</td>
                        <td>${centro_de_costo.centro_de_costo}</td>
                        <td><a href="${urlDesasignarProyecto}?id=${centro_de_costo.id}&usr=${usuario.id}"><span class='glyphicon glyphicon-remove'></span></a></td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</div>

<jsp:include page="filtro.jsp">
    <jsp:param name="columnas" value="col_3: \"none\"," />
</jsp:include>

<jsp:include page="footer.jsp" />

</body>
</html>

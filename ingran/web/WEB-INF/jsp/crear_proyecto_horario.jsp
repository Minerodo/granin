<%@ page session="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<jsp:include page="header.jsp">
    <jsp:param name="pageTitle" value="Crear Horario de Proyecto" />
</jsp:include>

<div class="col-xs-3"></div>
<div class="col-xs-6">
    <h3 align="center"><p class="text-muted">REGISTRAR HORARIO DE PROYECTO</p></h3>                
    <c:if test="${not empty msg}">        
        <div class="alert alert-${css} alert-dismissible" role="alert">
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
            <strong>${msg}</strong>
        </div>
    </c:if>
    <form:form name="frrc" id="frrc" method="POST" action="#" commandName="proyecto">
        <div class="form-group">
            <form:label path="centro_de_costo.centro_de_costo">Centro de Costo:</form:label>
            <form:input path="centro_de_costo.centro_de_costo" class="form-control" onfocusout="seleccionar();" list="listCentroDeCosto" placeholder="Centro de Costo" type="text" required="true"/>
            <form:errors path="centro_de_costo.centro_de_costo"/>
        </div>
        <div class="form-group">
            <form:label path="centro_de_costo.descripcion">Proyecto:</form:label>
            <form:input path="centro_de_costo.descripcion" class="form-control" list="listProyecto" placeholder="Proyecto" type="text" required="true" readonly="true"/>
            <form:errors path="centro_de_costo.descripcion"/>
        </div>
        <div class="col-md-4">
            <div class="form-group">
                <label>Horario:</label>
            </div>
        </div>
        <div class="col-md-4">
            <div class="form-group">
                <form:label path="hora_entrada">Hora Entrada:</form:label>
                <form:input path="hora_entrada" class="form-control" onchange="calcular();" value="00:00" placeholder="Hora de Entrada" type="time" required="true"/>
                <form:errors path="hora_entrada"/>
            </div>
        </div>
        <div class="col-md-4">
            <div class="form-group">
                <form:label path="hora_salida">Hora Salida:</form:label>
                <form:input path="hora_salida" class="form-control" onchange="calcular();" value="00:00" placeholder="Hora de Salida" type="time" required="true"/>
                <form:errors path="hora_salida"/>
            </div>
        </div>
        <div class="col-md-4">
            <div class="form-group">
                <label>Tiempo de Receso:</label>
            </div>
        </div>
        <div class="col-md-4">
            <div class="form-group">
                <form:label path="tiempo_receso_horas">Horas:</form:label>
                <form:input path="tiempo_receso_horas" class="form-control" onchange="calcular();" placeholder="Horas" type="number" required="true"/>
                <form:errors path="tiempo_receso_horas"/>
            </div>
        </div>
        <div class="col-md-4">
            <div class="form-group">
                <form:label path="tiempo_receso_minutos">Minutos:</form:label>
                <form:input path="tiempo_receso_minutos" class="form-control" onchange="calcular();" placeholder="Minutos" type="number" required="true"/>
                <form:errors path="tiempo_receso_minutos"/>
            </div>
        </div>
        <div class="col-md-4"></div>
        <div class="col-md-4"></div>
        <div class="col-md-4">
            <div class="form-group">
                <form:label path="horas_efectivas">Horas Efectivas:</form:label>
                <form:input path="horas_efectivas" class="form-control" onchange="calcular();" value="00:00" placeholder="Horas Efectivas" type="text" required="true" readonly="true"/>
                <form:errors path="horas_efectivas"/>
                <span id="total"></span>
            </div>
        </div>
        <input type="submit" class="btn btn-primary" value="Aceptar">                        
    </form:form>
</div>
<div class="col-xs-3"></div>

<datalist id="listCentroDeCosto">
    <c:forEach var="proyectos" items="${proyectos}">
        <option value="${proyectos.centro_de_costo}" label="${proyectos.descripcion}" data-ejemplo="${proyectos.descripcion}"/>
    </c:forEach>
</datalist>

<spring:url value="/resources/js/util/proyecto_horario.js" var="proyecto_horarioJs" />
<script src="${proyecto_horarioJs}"></script>

<script>
    function seleccionar() {
        var dato = document.getElementById('centro_de_costo.centro_de_costo').value;
        var value = $('#listCentroDeCosto [value="' + dato + '"]').data('ejemplo');

        document.getElementById('centro_de_costo.descripcion').value = value;
    }
</script>

<jsp:include page="footer.jsp" /> 
</body>
</html>

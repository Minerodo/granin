<%@ page session="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<jsp:include page="header.jsp">
    <jsp:param name="pageTitle" value="Crear Orden de Trabajo" />
</jsp:include>

<div class="col-xs-4 col-xs-offset-4">
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
            <form:label path="proyecto.descripcion">Proyecto:</form:label>
            <form:input path="proyecto.descripcion" class="form-control" placeholder="Proyecto" list="listProyectos" type="text" required="true"/>
            <form:errors path="proyecto.descripcion"/>
        </div>
        <div class="form-group">
            <form:label path="propietario.nombre">Propietario:</form:label>
            <form:input path="propietario.nombre" class="form-control" placeholder="Propietario" list="listPropietarios" type="text" required="true"/>
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
        <input type="submit" class="btn btn-primary" value="Registrar">
    </form:form>
</div>

<datalist id="listProyectos">
    <c:forEach var="proyecto" items="${proyectos}">
        <option value="${proyecto.descripcion}" label="${proyecto.centro_de_costo}" data-ejemplo="${proyecto.centro_de_costo}"/>
    </c:forEach>
</datalist>

<datalist id="listPropietarios">
    <c:forEach var="propietario" items="${propietarios}">
        <option value="${propietario.nombre}" label="${propietario.cliente}" data-ejemplo="${propietario.cliente}"/>
    </c:forEach>
</datalist>

<jsp:include page="footer.jsp" />
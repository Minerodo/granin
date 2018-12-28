<%@ page session="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<jsp:include page="header.jsp">
    <jsp:param name="pageTitle" value="Inicio de Sesion" />
</jsp:include>

<h3 align="center"><p class="text-muted">INICIO DE SESION</p></h3>                
<c:if test="${not empty msg}">
    <div class="alert alert-${css} alert-dismissible" role="alert">
        <button type="button" class="close" data-dismiss="alert" aria-label="Close">
            <span aria-hidden="true">&times;</span>
        </button>
        <strong>${msg}</strong>
    </div>
</c:if>

<form:form name="frrc" id="frrc" method="POST" action="" commandName="usuario" cssClass="form-horizontal">
    <div class="form-group">
        <form:label path="usuario" cssClass="col-sm-4 col-sm-offset-4 col-md-4 col-md-offset-4 col-lg-4 col-lg-offset-4">Usuario:</form:label>
        </div>
        <div class="form-group">
            <div class="col-sm-4 col-sm-offset-4 col-md-4 col-md-offset-4 col-lg-4 col-lg-offset-4">
            <form:input path="usuario" cssClass="form-control" placeholder="Nombre de usuario" required="true"/>
        </div>
        <form:errors path="usuario"/>
    </div>
    <div class="form-group">
        <form:label path="contrasenia" cssClass="col-sm-4 col-sm-offset-4 col-md-4 col-md-offset-4 col-lg-4 col-lg-offset-4">Contraseña:</form:label> 
        </div>
        <div class="form-group">
            <div class="col-sm-4 col-sm-offset-4 col-md-4 col-md-offset-4 col-lg-4 col-lg-offset-4">
            <form:password path="contrasenia" class="form-control" placeholder="Contraseña" required="true"/>
        </div>
        <form:errors path="contrasenia"/>
    </div>
    <div class="form-group">
        <input type="submit" class="btn btn-primary col-xs-8 col-xs-offset-2 col-sm-2 col-sm-offset-5 col-md-2 col-md-offset-5 col-lg-2 col-lg-offset-5" value="Iniciar Sesion">
    </div>
</form:form>

<jsp:include page="footer.jsp" /> 
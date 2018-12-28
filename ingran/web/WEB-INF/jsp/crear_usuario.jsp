<%@ page session="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html lang="es">
    <jsp:include page="header.jsp">
        <jsp:param name="pageTitle" value="Crear Usuario" />
    </jsp:include>

    <div class="main">
        <div class="container">            
            <div class="row">
                <div class="col-xs-4"></div>
                <div class="col-xs-4">
                    <h3 align="center"><p class="text-muted">REGISTRAR USUARIO</p></h3>                
                    <c:if test="${not empty msg}">        
                        <div class="alert alert-${css} alert-dismissible" role="alert">
                            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                            <strong>${msg}</strong>
                        </div>
                    </c:if>
                    <form:form name="frrc" id="frrc" method="POST" action="#" commandName="usuario">
                        <!--
                        <div class="form-group">
                        <form:label path="id">ID:</form:label>                            
                        <form:input path="id" class="form-control" placeholder="ID" required="true"/>
                        <form:errors path="id"/>
                    </div>
                        -->
                        <div class="form-group">
                            <form:label path="usuario">Usuario:</form:label>
                            <form:input path="usuario" class="form-control" placeholder="Nombre de usuario" required="true"/>
                            <form:errors path="usuario"/>
                        </div>
                        <div class="form-group">
                            <form:label path="empleado.empleado">Empleado:</form:label> 
                            <form:input path="empleado.empleado" class="form-control" list="listEmpleado" placeholder="Empleado" required="true"/>
                            <datalist id="listEmpleado">
                                <c:forEach var="empleados" items="${empleados}">
                                    <option value="${empleados.empleado}" label="${empleados}"/>
                                </c:forEach>
                            </datalist>
                            <form:errors path="empleado.empleado"/>
                        </div>
                        <div class="form-group">
                            <form:label path="correo">Correo:</form:label> 
                            <form:input path="correo" class="form-control" placeholder="Correo"/>
                            <form:errors path="correo"/>
                        </div>
                        <div class="form-group">
                            <form:label path="telefono">Teléfono:</form:label> 
                            <form:input path="telefono" class="form-control" placeholder="Teléfono"/>
                            <form:errors path="telefono"/>
                        </div>
                        <div class="form-group">
                            <form:label path="rol.id">Rol:</form:label>
                            <form:select path="rol.id" class="form-control" required="true">
                                <form:option value="" label="Seleccionar..."/>
                                <c:forEach var="roles" items="${roles}">
                                    <form:option value="${roles.id}" label="${roles}"/>
                                </c:forEach>
                            </form:select>
                            <form:errors path="rol.id"/>
                        </div>
                        <input type="submit" class="btn btn-primary" value="Registrar">
                    </form:form>
                </div>
                <div class="col-xs-4"></div>
            </div>
        </div>
    </div>
    <jsp:include page="footer.jsp" /> 
</body>
</html>

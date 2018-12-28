<%@ page session="false"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html lang="es">
    <jsp:include page="header.jsp">
        <jsp:param name="pageTitle" value="Editar Usuario" />
    </jsp:include>
    
    <spring:url value="/listar_usuarios.htm" var="urlListarUsuarios" /> 

    <div class="main">
        <div class="container">            
            <div class="row">
                <div class="col-md-12">                    
                    <h3 align="center"><p class="text-muted">EDITAR USUARIO</p></h3>                
                    <c:if test="${not empty msg}">        
                        <div class="alert alert-${css} alert-dismissible" role="alert">
                            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                            <strong>${msg}</strong>
                        </div>
                    </c:if>
                    <form:form method="POST" modelAttribute="usuario" >
                        <form:errors path="*"/>
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
                            <form:errors path="usuario"/>
                        </div>
                        <div class="form-group">
                            <form:label path="empleado.nombre">Empleado:</form:label> 
                            <form:input path="empleado.nombre" class="form-control" readonly="true"/>
                        </div>
                        <div class="form-group">
                            <form:label path="correo">Correo:</form:label> 
                            <form:input path="correo" class="form-control" />
                            <form:errors path="correo"/>
                        </div>
                        <div class="form-group">
                            <form:label path="telefono">Telefono:</form:label> 
                            <form:input path="telefono" class="form-control" required="true"/>
                            <form:errors path="telefono"/>
                        </div>
                        <div class="form-group">
                            <form:label path="rol.id">Rol:</form:label>
                            <form:select path="rol.id" class="form-control" required="true">
                                <c:forEach var="roles" items="${roles}">
                                    <form:option value="${roles.id}" label="${roles}"/>
                                </c:forEach>
                            </form:select>
                            <form:errors path="rol.id"/>
                        </div>
                        <input type="submit" class="btn btn-primary" value="Guardar">
                        <input type="submit" class="btn btn-success" value="Guardar y Cerrar">
                        <a href="${urlListarUsuarios}" class="btn btn-danger">Cerrar</a>
                    </form:form>
                </div>
            </div>
        </div>
    </div>

    <jsp:include page="footer.jsp" />
</body>
<script>
    $(function(){
        $("#rol").val(${rol.id})
    });
</script>
</html>

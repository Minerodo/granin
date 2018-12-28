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

    <div class="main">
        <div class="container">            
            <div class="row">
                <div class="col-md-12">                    
                    <h3 align="center"><p class="text-muted">EDITAR CATORCENA</p></h3>                
                    <c:if test="${not empty msg}">        
                        <div class="alert alert-${css} alert-dismissible" role="alert">
                            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                            <strong>${msg}</strong>
                        </div>
                    </c:if>
                    <form:form method="POST" modelAttribute="catorcena" >
                        <form:errors path="*"/>
                        <!--
                        <div class="form-group">    
                        <form:label path="id">Id:</form:label>                            
                        <form:input path="id" class="form-control" readonly="true"/>
                        <form:errors path="id"/>
                        </div>
                        -->
                        <div class="form-group">
                            <form:label path="fecha_inicio">Fecha de Inicio:</form:label>
                            <form:input path="fecha_inicio" class="form-control" placeholder="Fecha de Inicio" type="date" required="true"/>
                            <form:errors path="fecha_inicio"/>
                        </div>
                        <div class="form-group">
                            <form:label path="fecha_fin">Fecha de Finalizacion:</form:label>
                            <form:input path="fecha_fin" class="form-control" placeholder="Fecha de Finalizacion" type="date" required="true"/>
                            <form:errors path="fecha_fin"/>
                        </div>
                        <input type="submit" class="btn btn-primary" value="Guardar">
                        <input type="submit" class="btn btn-success" value="Guardar y Cerrar">
                        
                        <spring:url value="/listar_catorcenas.htm" var="urlListarCatorcenas" /> 
                        <a href="${urlListarCatorcenas}" class="btn btn-danger">Cerrar</a>
                    </form:form>
                </div>
            </div>
        </div>
    </div>

    <jsp:include page="footer.jsp" />
</body>
<script>
    $(function () {
        $("#rol").val(${rol.id})
    });
</script>
</html>

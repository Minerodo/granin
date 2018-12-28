<%@ page session="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html lang="es">
    <jsp:include page="header.jsp">
        <jsp:param name="pageTitle" value="Crear Catorcena" />
    </jsp:include>

    <div class="main">
        <div class="container">            
            <div class="row">
                <div class="col-xs-4 col-xs-offset-4">
                    <h3 align="center"><p class="text-muted">REGISTRAR CATORCENA</p></h3>
                    <c:if test="${not empty msg}">
                        <div class="alert alert-${css} alert-dismissible" role="alert">
                            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                            <strong>${msg}</strong>
                        </div>
                    </c:if>
                    <form:form name="frrc" id="frrc" method="POST" action="#" commandName="catorcena">
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
                        <input type="submit" class="btn btn-primary" value="Registrar">
                    </form:form>
                </div>
            </div>
        </div>
    </div>
    <jsp:include page="footer.jsp" /> 
</body>
</html>

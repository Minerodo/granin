<%@ page session="false"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<jsp:include page="header.jsp">
    <jsp:param name="pageTitle" value="Listar Clientes" />
</jsp:include>

<spring:url value="/editar_usuario.htm" var="urlEditarUsuario" />
<spring:url value="/activar_usuario.htm" var="urlActivarUsuario" /> 
<spring:url value="/desactivar_usuario.htm" var="urlDesactivarUsuario" />
<spring:url value="/eliminar_usuario.htm" var="urlEliminarUsuario" />
<spring:url value="/asignar_proyecto_usuario.htm" var="urlAsignarProyectoUsuario" />

<div class="col-md-12">
    <div id="tbl">
        <div class="table-responsive table-centered">
            <jsp:include page="filtro_botones.jsp"/>
            <table class="table table-hover" id="filtro">
                <thead>
                    <tr class="">
                        <th>Usuario</th>
                        <th>Rol</th>
                        <th>Apellido, Nombre</th>
                        <th>Correo</th>
                        <th>Acciones</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="usuario" items="${usuarios}">
                        <tr class="<c:choose><c:when test="${usuario.activo}">success</c:when><c:otherwise>danger</c:otherwise></c:choose>">
                            <td>${usuario.usuario}</td>
                            <td>${usuario.rol}</td>
                            <td>${usuario.empleado}</td>
                            <td>${usuario.correo}</td>
                            <td>
                                <c:if test="${usuario.rol.nombre != 'administrador'}">
                                    <a href="${urlAsignarProyectoUsuario}?id=${usuario.id}"><span class='glyphicon glyphicon-plus'></span></a>
                                    </c:if>
                                <a href="${urlEditarUsuario}?id=${usuario.id}"><span class='glyphicon glyphicon-pencil'></span></a>
                                    <c:choose>
                                        <c:when test="${usuario.activo}">
                                        <a href="${urlDesactivarUsuario}?id=${usuario.id}"><span class='glyphicon glyphicon-eye-open'></span></a>
                                        </c:when>
                                        <c:otherwise>
                                        <a href="${urlActivarUsuario}?id=${usuario.id}"><span class='glyphicon glyphicon-eye-close'></span></a>
                                        </c:otherwise>
                                    </c:choose>
                                <a href="${urlEliminarUsuario}?id=${usuario.id}"><span class='glyphicon glyphicon-remove'></span></a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>

<jsp:include page="filtro.jsp">
    <jsp:param name="columnas" value="col_1: \"select\", col_4: \"none\"," />
</jsp:include>

<jsp:include page="footer.jsp" /> 
</body>
</html>

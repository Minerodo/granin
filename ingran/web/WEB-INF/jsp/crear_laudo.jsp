<%@ page session="false"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<jsp:include page="header.jsp">
    <jsp:param name="pageTitle" value="Listar Laudo" />
</jsp:include>

<spring:url value="/editar_laudo.htm" var="urlEditarLaudo" /> 
<spring:url value="/activar_usuario.htm" var="urlActivarUsuario" /> 
<spring:url value="/desactivar_usuario.htm" var="urlDesactivarUsuario" />
<spring:url value="/crear_laudo_ccosto.htm" var="urlCrearLaudoPersonal" />

<div class="col-xs-4 col-md-4 col-lg-4">
    <h3 align="center"><p class="text-muted">REGISTRAR LAUDO</p></h3>
    <c:if test="${not empty msg}">
        <div class="alert alert-${css} alert-dismissible" role="alert">
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
            <strong>${msg}</strong>
        </div>
    </c:if>
    <form:form name="frrc" id="frrc" method="POST" action="#" commandName="laudo">
        <!--
        <div class="form-group">
        <form:label path="id">Id:</form:label> 
        <form:input path="id" class="form-control" placeholder="ID" readonly="true"/>
        <form:errors path="id"/>
    </div>
        -->
        <div class="form-group">
            <form:label path="descripcion">Descripción:</form:label> 
            <form:input path="descripcion" class="form-control" placeholder="Descripcion" required="required"/>
            <form:errors path="descripcion"/>
        </div>
        <div class="form-group">
            <form:label path="costo">Valor:</form:label> 
            <form:input path="costo" class="form-control" placeholder="Costo" required="required"/>
            <form:errors path="costo"/>
        </div>
        <div class="form-group">
            <form:label path="estado">Estado:</form:label>
            <form:select path="estado" class="form-control" required="true">
                <form:option value="" label="Seleccionar..."/>
                <form:option value="A" label="Activo"/>
                <form:option value="I" label="Inactivo"/>
            </form:select>
            <form:errors path="estado"/>
        </div>
        <div class="form-group">
            <form:label path="unidad_medida.id">Unidad de Medida:</form:label>
            <form:select path="unidad_medida.id" class="form-control" required="true">
                <form:option value="" label="Seleccionar..."/>
                <c:forEach var="unidad" items="${unidades}">
                    <form:option value="${unidad.id}" label="${unidad.nombre}"/>
                </c:forEach>
            </form:select>
            <form:errors path="unidad_medida.id"/>
        </div>
        <input type="submit" class="btn btn-primary" value="Registrar">
    </form:form>    
</div>

<div class="col-xs-8 col-md-8 col-lg-8">
    <div id="tbl">
        <div class="table-responsive table-centered">
            <input type="button" value="Agregar Paginado" onclick="if (tf.paging)
                        return;
                    tf.AddPaging(true);">
            <input type="button" value="Quitar Paginado" onclick="if (!tf.paging)
                        return;
                    tf.paging = false;
                    tf.RemovePaging();
                    tf.Filter();">
            <table class="table table-hover" id="filtro">
                <thead>
                    <tr>
                        <!--
                        <th>Id</th>
                        -->
                        <th>Descripcion</th>
                        <th>Costo</th>
                        <th>Unidad de Medida</th>
                        <th>Estado</th>
                        <th>Acciones</th>
                    </tr>                
                </thead>
                <tbody>
                    <c:forEach var="laudo" items="${laudos}">
                        <tr>
                            <!--
                            <td>${laudo.id}</td>
                            -->
                            <td>${laudo.descripcion}</td>
                            <td>${laudo.costo}</td>
                            <td>${laudo.unidad_medida.nombre}</td>
                            <td>${laudo.estado}</td>
                            <td>
                                <c:if test="${usuario.rol.nombre != 'administrador'}">
                                    <a href="${urlCrearLaudoPersonal}?idLaudo=${laudo.id}"><span class='glyphicon glyphicon-plus'></span></a>
                                    </c:if>
                                <a href="${urlEditarLaudo}?id=${laudo.id}"><span class='glyphicon glyphicon-pencil'></span></a>
                                    <c:choose>
                                        <c:when test="${laudo.estado eq 'A'}">
                                        <a href="${urlDesactivarUsuario}?id=${usuario.id}"><span class='glyphicon glyphicon-eye-open'></span></a>
                                        </c:when>
                                        <c:otherwise>
                                        <a href="${urlActivarUsuario}?id=${usuario.id}"><span class='glyphicon glyphicon-eye-close'></span></a>
                                        </c:otherwise>
                                    </c:choose>
                                <!--                                                    
                                                                <a href="${urlEliminarUsuario}?id=${usuario.id}"><span class='glyphicon glyphicon-remove'></span></a>
                                -->
                            </td>

                        </tr>
                    </tbody>
                </c:forEach>
            </table>
        </div>
    </div>
</div>

<jsp:include page="filtro.jsp">
    <jsp:param name="columnas" value="" />
</jsp:include>

<jsp:include page="footer.jsp" /> 
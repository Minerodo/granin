<%@ page session="false"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<jsp:include page="header.jsp">
    <jsp:param name="pageTitle" value="Listar Laudo Especifico" />
</jsp:include>

<spring:url value="/editar_usuario.htm" var="urlEditarUsuario" /> 
<spring:url value="/activar_usuario.htm" var="urlActivarUsuario" /> 
<spring:url value="/desactivar_usuario.htm" var="urlDesactivarUsuario" />
<spring:url value="/eliminar_usuario.htm" var="urlEliminarUsuario" />
<spring:url value="/crear_laudo_personal.htm" var="urlCrearLaudoPersonal" />

<div class="col-xs-4 col-md-4 col-lg-4">
    <form:form name="frrc" id="frrc" method="POST" action="#" commandName="laudoccosto"><!--command name es el nombre del modelo-->
        <div class="form-group">
            <form:label path="id">Id:</form:label> 
            <form:input path="id" class="form-control" readonly="true"/>
            <form:errors path="id"/>
        </div>
        <div class="form-group">
            <form:label path="descripcion">Valor:</form:label> 
            <form:input path="descripcion" class="form-control" placeholder="COSTO" readonly="true"/>
            <form:errors path="descripcion"/>
        </div>
        <div class="form-group">
            <form:label path="costo">Valor:</form:label> 
            <form:input path="costo" class="form-control" placeholder="COSTO" required="required"/>
            <form:errors path="costo"/>
        </div>
        <div class="form-group">
            <form:label path="unidad_medida.nombre">Valor:</form:label> 
            <form:input path="unidad_medida.nombre" class="form-control" placeholder="COSTO" readonly="true"/>
            <form:errors path="unidad_medida.nombre"/>
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
            <form:label path="centro_costo.centro_de_costo">Centro de Costos:</form:label> 
            <form:input path="centro_costo.centro_de_costo" class="form-control" list="listCentroCosto" placeholder="Centro de Costo" required="true"/>
            <datalist id="listCentroCosto">
                <c:forEach var="centrodecosto" items="${centrosdecostos}">
                    <option value="${centrodecosto.centro_de_costo}" label="${centrodecosto.descripcion}"/>
                </c:forEach>
            </datalist>
            <form:errors path="centro_costo.centro_de_costo"/>
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
                        <th>Centro de Costo</th>
                        <th>Descripción</th>
                        <th>Costo</th>
                        <th>Estado</th>
                        <th>Acciones</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="laudosccosto" items="${laudosccosto}">
                        <tr>
                            <!--
                            <td>${laudosccosto.id}</td>
                            -->
                            <td>${laudosccosto.centro_costo.centro_de_costo}</td>
                            <td>${laudosccosto.centro_costo.descripcion}</td>
                            <td>${laudosccosto.costo}</td>
                            <td>${laudosccosto.estado}</td>
                            <td>
                                <c:if test="${usuario.rol.nombre != 'administrador'}">
                                    <a href="${urlCrearLaudoPersonal}?id=${laudosccosto.id}"><span class='glyphicon glyphicon-plus'></span></a>
                                    </c:if>
                                <a href="${urlEditarUsuario}?id=${usuario.id}"><span class='glyphicon glyphicon-pencil'></span></a>
                                    <c:choose>
                                        <c:when test="${laudosccosto.estado eq 'A'}">
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
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>

<jsp:include page="filtro.jsp">
    <jsp:param name="columnas" value="" />
</jsp:include>

<jsp:include page="footer.jsp" /> 
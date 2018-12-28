<%@ page session="false"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<jsp:include page="header.jsp">
    <jsp:param name="pageTitle" value="Listar Catorcenas" />
</jsp:include>

<spring:url value="/editar_catorcena.htm" var="urlEditarCatorcena" />
<spring:url value="/activar_catorcena.htm" var="urlActivarCatorcena" />
<spring:url value="/desactivar_catorcena.htm" var="urlDesactivarCatorcena" />

<div class="col-md-12">
    <div id="tbl">
        <div class="table-responsive table-centered">
            <jsp:include page="filtro_botones.jsp"/>
            <table class="table table-hover" id="filtro">
                <thead>
                    <tr class="">
                        <th>Catorcena</th>
                        <th>Estado</th>
                        <th>Acciones</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="catorcena" items="${catorcenas}">
                        <tr class="<c:choose><c:when test="${catorcena.activo}">success</c:when><c:otherwise>danger</c:otherwise></c:choose>">
                            <td>${catorcena.descripcion}</td>
                            <td>
                                <c:if test="${catorcena.activo}">
                                    Activa
                                </c:if>
                                <c:if test="${!catorcena.activo}">
                                    Desactivada
                                </c:if>
                            </td>
                            <td>
                                <a href="${urlEditarCatorcena}?id=${catorcena.id}"><span class='glyphicon glyphicon-pencil'></span></a>
                                    <c:choose>
                                        <c:when test="${catorcena.activo}">
                                        <a href="${urlDesactivarCatorcena}?id=${catorcena.id}"><span class='glyphicon glyphicon-eye-open'></span></a>
                                        </c:when>
                                        <c:otherwise>
                                        <a href="${urlActivarCatorcena}?id=${catorcena.id}"><span class='glyphicon glyphicon-eye-close'></span></a>
                                        </c:otherwise>
                                    </c:choose>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>

<jsp:include page="filtro.jsp">
    <jsp:param name="columnas" value="col_1: \"select\", col_2: \"none\"," />
</jsp:include>

<jsp:include page="footer.jsp" />
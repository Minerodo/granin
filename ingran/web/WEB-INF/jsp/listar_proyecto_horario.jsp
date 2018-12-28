<%@ page session="false"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<jsp:include page="header.jsp">
    <jsp:param name="pageTitle" value="Listar proyectos" />
</jsp:include>

<spring:url value="/editar_proyecto_horario.htm" var="urlEditarProyectoHorario" />

<div class="col-md-12">
    <div id="tbl">
        <div class="table-responsive table-centered">
            <jsp:include page="filtro_botones.jsp"/>
            <table class="table table-hover" id="filtro">
                <thead>
                    <tr>
                        <th>Centro de Costo</th>
                        <th>Proyecto</th>
                        <th>Hora Entrada</th>
                        <th>Hora Salida</th>
                        <th>Tiempo de Receso</th>
                        <th>Horas Efectivas</th>
                        <th>Acciones</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="proyecto" items="${proyectos}">
                        <tr>
                            <td>${proyecto.centro_de_costo.centro_de_costo}</td>
                            <td>${proyecto.centro_de_costo.descripcion}</td>
                            <td>${proyecto.hora_entrada}</td>
                            <td>${proyecto.hora_salida}</td>
                            <td>${proyecto.tiempo_receso_horas} horas ${proyecto.tiempo_receso_minutos} minutos</td>
                            <td>${proyecto.horas_efectivas}</td>
                            <td><a href="${urlEditarProyectoHorario}?id=${proyecto.id_horario}"><span class='glyphicon glyphicon-pencil'></span></a></td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>

<jsp:include page="filtro.jsp">
    <jsp:param name="columnas" value="col_6: \"none\"," />
</jsp:include>

<jsp:include page="footer.jsp" /> 
</body>
</html>

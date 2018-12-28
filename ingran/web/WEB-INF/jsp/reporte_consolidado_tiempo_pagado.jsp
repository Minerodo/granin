<%@ page session="false"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<jsp:include page="header.jsp">
    <jsp:param name="pageTitle" value="Reporte de Horas por Persona" />
</jsp:include>

<div class="col-md-12">
    <div id="tbl">
        <div class="table-responsive table-centered">  

            <form:form name="frrc" id="frrc" method="POST" action="">
                <jsp:include page="filtro_botones.jsp"/>

                <h3 align="center"><p class="text-muted">REPORTE CONSOLIDADO DE TIEMPO EFECTIVO</p></h3>

                <label for="catorcenas">Catorcena:</label>
                <select id="catorcenas" onchange="agregar('catorcenas', 'catorcena', true);" class="form-control">
                    <c:forEach var="catorcena" items="${catorcenas}">
                        <option value="${catorcena.id}" <c:if test="${catorcena.id == cat}">selected</c:if>>${catorcena.descripcion}</option>
                    </c:forEach>
                </select>

                <table class="table table-hover" id="filtro">
                    <thead>
                        <tr class="">
                            <th>Nombre</th>
                            <th>Cargo</th>
                            <th>Tipo de Horas</th>
                            <th>Horas Efectivas</th>
                            <th>Horas Pagadas</th>
                            <th>Horas $</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="tiempo_pagado" items="${tiempo_pagados}">
                            <tr>
                                <td>${tiempo_pagado.nombre}</td>
                                <td>${tiempo_pagado.cargo}</td>
                                <td>${tiempo_pagado.tipo_de_hora}</td>
                                <td>${tiempo_pagado.horas_efectivas}</td>
                                <td>${tiempo_pagado.horas_pagadas}</td>
                                <td>${tiempo_pagado.total_general}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </form:form>
        </div>
    </div>
</div>

<jsp:include page="filtro.jsp">
    <jsp:param name="columnas" value="col_2: \"select\"," />
</jsp:include>

<spring:url value="/resources/js/util/planilla_gets_url.js" var="planilla_gets_urlJs"/>
<script src="${planilla_gets_urlJs}" charset="UTF-8"></script>

<jsp:include page="footer.jsp" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<table>
    <tr>
        <th>No.</th>
        <th>Name</th>
        <th>Lastname</th>
        <th>Email</th>
        <th>${funciono}</th>
    </tr>
    <c:forEach items="${planilla}" var="contact" varStatus="status">
        <tr>
            <td align="center">${status.count}</td>
            <td><input value="${contact.fase.fase}"/></td>
            <td><input value="${contact.fase.nombre}"/></td>
            <td><input value="${contact.tipo_de_hora.tipo_de_hora}"/></td>
            <td><input value="${contact.horas}"/></td>
        </tr>
    </c:forEach>
</table>

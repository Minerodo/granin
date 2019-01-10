<%@ page session="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<jsp:include page="header.jsp">
    <jsp:param name="pageTitle" value="Inicio" />
</jsp:include>

<div class="col-md-12">                    
    <h3 align="center"><p class="text-muted">BIENVENIDO A PLANILLA DE INGRAN</p></h3>                
    <c:if test="${not empty msg}">        
        <div class="alert alert-${css} alert-dismissible" role="alert">
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
            <strong>${msg}</strong>
        </div>
    </c:if>

    <c:if test="${men.equals('Bodeguero')}">        
        
    </c:if>
</div>

<jsp:include page="footer.jsp" />
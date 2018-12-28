<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
</div>
</body>
<div>
    <hr>
    <footer>
        <p></p>
    </footer>
</div>
</html>

<spring:url value="/resources/js/bootstrap.min.js" var="bootstrapJs" />
<spring:url value="/resources/js/func.js" var="func" />

<script src="${bootstrapJs}"></script>
<script src="${func}"></script>
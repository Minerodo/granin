<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<spring:url value="/resources/tablefilter/tablefilter_all_min.js" var="TableFilterJs" />
<script type="text/javascript" language="javascript" src="${TableFilterJs}"></script>

<spring:url value="resources/tablefilter/" var="base" />

<script language="javascript" type="text/javascript">
    var tfConfig = {
        sort: true,
        filters_row_index: 0,
        //col_number_format: [null, null, 'US', 'US', 'US', 'US', 'US', 'US', 'US'],
        remember_grid_values: false,
        alternate_rows: true,
        rows_counter: true,
        rows_counter_text: "Filas Visualizadas: ",
        btn_reset: true,
        status_bar: false,
        fill_slc_on_demand: false,
        multiple_slc_tooltip: "Presione la tecla Ctrl para seleccionar múltiples valores",
        paging: true,
        paging_length: 10,
        <%=request.getParameter("columnas")%>
        display_all_text: "Mostrar Todos",
        input_watermark: 'Buscar...',
        enable_empty_option: true,
        empty_text: "Mostrar Vacios",
    };
    var tf = setFilterGrid("filtro", tfConfig);
</script>
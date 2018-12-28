<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<nav class="navbar navbar-inverse navbar-fixed-top">
    <!-- Menu Global -->
    <spring:url value="/inicio.htm" var="urlInicio" />
    <spring:url value="/salir.htm" var="urlSalir" />

    <spring:url value="Planilla al día" var="prmPlanillaHora" />
    <spring:url value="/listar_catorcena_cargada.htm" var="urlCatorcenaCargada" />
    <spring:url value="/reporte_catorcena_hora.htm" var="urlCatorcenaHora" />
    <spring:url value="/reporte_horas_por_persona.htm" var="urlCatorcenaHorasPorPersona" />
    <spring:url value="/reporte_actividad_por_persona.htm" var="urlCatorcenaActividadPorPersona" />
    <spring:url value="/reporte_persona_por_actividad.htm" var="urlCatorcenaPersonasEnCadaActividad" />

    <spring:url value="Planilla obra" var="prmPlanillaObra" />

    <%
        if (request.getParameter("menu").equals("Administrador")) {
    %>
    <spring:url value="/crear_proyecto_horario.htm" var="urlCrearProyecto" />
    <spring:url value="/listar_proyecto_horario.htm" var="urlListarProyectos" />

    <spring:url value="/crear_usuario.htm" var="urlCrearUsuario" />
    <spring:url value="/listar_usuarios.htm" var="urlListarUsuarios" />

    <spring:url value="/crear_catorcena.htm" var="urlCrearCatorcena" />
    <spring:url value="/listar_catorcenas.htm" var="urlListarCatorcenas" />

    <spring:url value="/reporte_consolidado_tiempo_efectivo.htm" var="urlConsolidadoTiempoEfectivo" />
    <spring:url value="/reporte_consolidado_tiempo_pagado.htm" var="urlConsolidadoTiempoPagado" />

    <spring:url value="/cargador_catorcenal.htm" var="urlCargadorCatorcenal" />
    <spring:url value="/cargador_semanal.htm" var="urlCargadorSemanal" />

    <spring:url value="/crear_laudo.htm" var="urlCrearLaudo" />
    <spring:url value="/listar_laudo.htm" var="urlListarLaudo" />

    <div class="navbar-header">
        <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-sidebar-navbar-collapse-1">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
        </button>
    </div>

    <div class="collapse navbar-collapse" id="bs-sidebar-navbar-collapse-1">
        <ul class="nav navbar-nav">
            <li><a href="${urlInicio}">Inicio<span class="pull-right hidden-xs showopacity glyphicon glyphicon-home"></span></a></li>

            <li class="dropdown"><!-- Reportes -->
                <a href="#" class="dropdown-toggle" data-toggle="dropdown">Reportes<span class="caret"></span><span class="pull-right hidden-xs showopacity glyphicon glyphicon-stats"></span></a>
                <ul class="dropdown-menu forAnimate" role="menu">
                    <li class="dropdown-header">${prmPlanillaHora}</li>
                    <li><a href="${urlCatorcenaCargada}"> - Catorcena Cargada</a></li>
                    <li><a href="${urlCatorcenaHorasPorPersona}"> - Horas por Persona</a></li>
                    <li><a href="${urlCatorcenaActividadPorPersona}"> - Actividades por Persona</a></li>
                    <li><a href="${urlCatorcenaPersonasEnCadaActividad}"> - Personas en Cada Actividad</a></li>
                    <li class="dropdown-header">Consolidado</li>
                    <li><a href="${urlConsolidadoTiempoEfectivo}"> - Tiempo Efectivo</a></li>
                    <li><a href="${urlConsolidadoTiempoPagado}"> - Tiempo Pagado</a></li>
                    <li class="dropdown-header">Cargador</li>
                    <li><a href="${urlCargadorCatorcenal}"> - Catorcenal</a></li>
                    <li><a href="${urlCargadorSemanal}"> - Semanal</a></li>
                </ul>
            </li>

            <li class="dropdown"><!-- Proyectos -->
                <a href="#" class="dropdown-toggle" data-toggle="dropdown">Proyectos<span class="caret"></span><span class="pull-right hidden-xs showopacity glyphicon glyphicon-book"></span></a>
                <ul class="dropdown-menu forAnimate" role="menu">
                    <li class="dropdown-header">Horarios</li>
                    <li><a href="${urlCrearProyecto}"> - Crear<span class="pull-right hidden-xs showopacity glyphicon glyphicon-plus"></span></a></li>
                    <li class="divider"></li>
                    <li><a href="${urlListarProyectos}"> - Listar<span class="pull-right hidden-xs showopacity glyphicon glyphicon-list"></span></a></li>
                </ul>
            </li>

            <li class="dropdown"><!-- Usuarios -->
                <a href="#" class="dropdown-toggle" data-toggle="dropdown">Usuarios<span class="caret"></span><span class="pull-right hidden-xs showopacity glyphicon glyphicon-user"></span></a>
                <ul class="dropdown-menu forAnimate" role="menu">
                    <li><a href="${urlCrearUsuario}"> - Crear<span class="pull-right hidden-xs showopacity glyphicon glyphicon-plus"></span></a></li>
                    <li class="divider"></li>
                    <li><a href="${urlListarUsuarios}"> - Listar<span class="pull-right hidden-xs showopacity glyphicon glyphicon-list"></span></a></li>
                </ul>
            </li>

            <li class="dropdown"><!-- Catorcenas -->
                <a href="#" class="dropdown-toggle" data-toggle="dropdown">Catorcenas<span class="caret"></span><span class="pull-right hidden-xs showopacity glyphicon glyphicon-calendar"></span></a>
                <ul class="dropdown-menu forAnimate" role="menu">
                    <li><a href="${urlCrearCatorcena}"> - Crear<span class="pull-right hidden-xs showopacity glyphicon glyphicon-plus"></span></a></li>
                    <li class="divider"></li>
                    <li><a href="${urlListarCatorcenas}"> - Listar<span class="pull-right hidden-xs showopacity glyphicon glyphicon-list"></span></a></li>
                </ul>
            </li>

            <li class="dropdown"><!-- Laudo -->
                <a href="#" class="dropdown-toggle" data-toggle="dropdown">Laudo<span class="caret"></span><span class="pull-right hidden-xs showopacity glyphicon glyphicon-calendar"></span></a>
                <ul class="dropdown-menu forAnimate" role="menu">
                    <li><a href="${urlCrearLaudo}"> - Crear<span class="pull-right hidden-xs showopacity glyphicon glyphicon-plus"></span></a></li>
                    <li class="divider"></li>
                    <li><a href="${urlListarLaudo}"> - Listar<span class="pull-right hidden-xs showopacity glyphicon glyphicon-list"></span></a></li>
                </ul>
            </li>

            <li><a href="${urlSalir}">Cerrar Sesion <span class="pull-right hidden-xs showopacity glyphicon glyphicon-off"></span></a></li>

            <li class="dropdown"> <!-- Conf -->
                <a href="#"><span class="pull-right hidden-xs showopacity glyphicon glyphicon-cog"></span></a>
            </li>
        </ul>
    </div>
    <%
        }
        if (request.getParameter("menu").equals("Ingeniero")) {
    %>
    <div class="navbar-header">
        <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-sidebar-navbar-collapse-1">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
        </button>
    </div>
    <div class="collapse navbar-collapse" id="bs-sidebar-navbar-collapse-1">
        <ul class="nav navbar-nav">
            <li><a href="${urlInicio}">Inicio<span class="pull-right hidden-xs showopacity glyphicon glyphicon-home"></span></a></li>

            <li class="dropdown"><!-- Reportes -->
                <a href="#" class="dropdown-toggle" data-toggle="dropdown">Reportes<span class="caret"></span><span class="pull-right hidden-xs showopacity glyphicon glyphicon-stats"></span></a>
                <ul class="dropdown-menu forAnimate" role="menu">
                    <li class="dropdown-header">${prmPlanillaHora}</li>
                    <li><a href="${urlCatorcenaCargada}"> - Catorcena Cargada</a></li>
                    <li><a href="${urlCatorcenaHorasPorPersona}"> - Horas por Persona</a></li>
                    <li><a href="${urlCatorcenaActividadPorPersona}"> - Actividades por Persona</a></li>
                    <li><a href="${urlCatorcenaPersonasEnCadaActividad}"> - Personas en Cada Actividad</a></li>
                </ul>
            </li>                

            <li><a href="${urlSalir}">Cerrar Sesion <span class="pull-right hidden-xs showopacity glyphicon glyphicon-off"></span></a></li>

            <li class="dropdown"> <!-- Conf -->
                <a href="#"><span class="pull-right hidden-xs showopacity glyphicon glyphicon-cog"></span></a>                        
            </li>
        </ul>
    </div>
    <%
        }
        if (request.getParameter("menu").equals("Bodeguero")) {
    %>
    <spring:url value="/crear_planilla_hora.htm" var="urlCrearPlanillaHora" />

    <div class="navbar-header">
        <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-sidebar-navbar-collapse-1">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
        </button>
    </div>
    <div class="collapse navbar-collapse" id="bs-sidebar-navbar-collapse-1">
        <ul class="nav navbar-nav">
            <li><a href="${urlInicio}">Inicio<span class="pull-right hidden-xs showopacity glyphicon glyphicon-home"></span></a></li>

            <li class="dropdown"><!-- Planilla -->
                <a href="#" class="dropdown-toggle" data-toggle="dropdown">Planilla<span class="caret"></span><span class="pull-right hidden-xs showopacity glyphicon glyphicon-list-alt"></span></a>
                <ul class="dropdown-menu forAnimate" role="menu">
                    <li class="dropdown-header">${prmPlanillaHora}</li>
                    <li><a href="${urlCrearPlanillaHora}"> - Crear</a></li>
                    <li class="dropdown-header">${prmPlanillaObra}</li>
                </ul>
            </li>

            <li class="dropdown"><!-- Reportes -->
                <a href="#" class="dropdown-toggle" data-toggle="dropdown">Reportes<span class="caret"></span><span class="pull-right hidden-xs showopacity glyphicon glyphicon-stats"></span></a>
                <ul class="dropdown-menu forAnimate" role="menu">
                    <li class="dropdown-header">${prmPlanillaHora}</li>
                    <li><a href="${urlCatorcenaCargada}"> - Catorcena Cargada</a></li>
                    <li><a href="${urlCatorcenaHorasPorPersona}"> - Horas por Persona</a></li>
                    <li><a href="${urlCatorcenaActividadPorPersona}"> - Actividades por Persona</a></li>
                    <li><a href="${urlCatorcenaPersonasEnCadaActividad}"> - Personas en Cada Actividad</a></li>
                    <li class="dropdown-header">${prmPlanillaObra}</li>
                </ul>
            </li>

            <li><a href="${urlSalir}">Cerrar Sesion <span class="pull-right hidden-xs showopacity glyphicon glyphicon-off"></span></a></li>

            <li class="dropdown"> <!-- Conf -->
                <a href="#"><span class="pull-right hidden-xs showopacity glyphicon glyphicon-cog"></span></a>                        
            </li>
        </ul>
    </div>
    <%
        }
    %>

</nav>
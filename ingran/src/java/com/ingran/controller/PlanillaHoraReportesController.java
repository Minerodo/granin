package com.ingran.controller;

import com.ingran.data.CatorcenaDB;
import com.ingran.data.reportes.CatorcenaCargadaDB;
import com.ingran.data.reportes.FechasDB;
import com.ingran.data.reportes.PlanillaHoraReportesDB;
import com.ingran.model.Catorcena;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.util.WebUtils;

@Controller
public class PlanillaHoraReportesController {

    @RequestMapping(value = "reporte_horas_por_persona", method = RequestMethod.GET)
    public String cargarFormReporteHorasPorPersona(Model model, HttpServletRequest request) {
        try {
            HttpSession objSesion = request.getSession(false);

            String in = (String) objSesion.getAttribute("in");
            if (in.equals("no")) {
                return "redirect:index.htm";
            }

            Catorcena catorcena = new Catorcena();
            //se comprueba si existe un parametro de la catorcena a ver en el url de la pagina
            try {
                if (!request.getParameter("catorcena").isEmpty()) {
                    //si existe el parametro se captura el id de la catorcena
                    Integer cat = Integer.parseInt(request.getParameter("catorcena"));
                    //se asigna el id a la catorcena
                    catorcena.setId(cat);
                }
            } catch (NullPointerException e) {
                //se obtiene la catorcena activa
                CatorcenaDB.obtenerCatorcenaActiva(catorcena);
            }

            FechasDB fechas = new FechasDB();
            PlanillaHoraReportesDB hora_por_personas = new PlanillaHoraReportesDB();

            String menu = (String) objSesion.getAttribute("menu");
            int id = (Integer) objSesion.getAttribute("userid");

            //se crea la instancia de las catorcenas
            CatorcenaDB catorcenas = new CatorcenaDB();

            if (menu.equals("Bodeguero")) {
                fechas.obtenerFechasBodeguero(catorcena, id);
                hora_por_personas.obtenerHorasPorPersonaBodeguero(fechas, catorcena, id);
                //se obtienen las catorcenas que puede revisar el administrador
                catorcenas.obtenerCatorcenasBodeguero();
            }

            if (menu.equals("Ingeniero")) {
                fechas.obtenerFechasIngeniero(catorcena, id);
                hora_por_personas.obtenerHorasPorPersonaIngeniero(fechas, catorcena, id);
                //se obtienen las catorcenas que puede revisar el administrador
                catorcenas.obtenerCatorcenasIngeniero();
            }
            
            if (menu.equals("Administrador")) {
                fechas.obtenerFechasAdministrador(catorcena);
                hora_por_personas.obtenerHorasPorPersonaAdministrador(fechas, catorcena);
                //se obtienen las catorcenas que puede revisar el administrador
                catorcenas.obtenerCatorcenasAdministrador();
            }

            model.addAttribute("fechas", fechas.getFechas());

            model.addAttribute("hora_por_personas", hora_por_personas.getPlanilla_hora_reportes());
            //se envia las catorcenas al select para que el administrador pueda seleccionar una catorcena diferente
            model.addAttribute("catorcenas", catorcenas.getCatorcenas());
            //Aqui envio el id de la catorcena para que se seleccione en el select de la pagina web
            model.addAttribute("cat", catorcena.getId());

            return "reporte_planilla_hora_horas_por_persona";
        } catch (NullPointerException ex) {
            return "redirect:salir.htm";
        }
    }

    @RequestMapping(value = "reporte_actividad_por_persona", method = RequestMethod.GET)
    public String cargarFormReporteActividadPorPersona(Model model, HttpServletRequest request) {
        try {
            HttpSession objSesion = request.getSession(false);

            String in = (String) objSesion.getAttribute("in");
            if (in.equals("no")) {
                return "redirect:index.htm";
            }

            Catorcena catorcena = new Catorcena();
            //se comprueba si existe un parametro de la catorcena a ver en el url de la pagina
            try {
                if (!request.getParameter("catorcena").isEmpty()) {
                    //si existe el parametro se captura el id de la catorcena
                    Integer cat = Integer.parseInt(request.getParameter("catorcena"));
                    //se asigna el id a la catorcena
                    catorcena.setId(cat);
                }
            } catch (NullPointerException e) {
                //se obtiene la catorcena activa
                CatorcenaDB.obtenerCatorcenaActiva(catorcena);
            }

            FechasDB fechas = new FechasDB();
            PlanillaHoraReportesDB hora_por_personas = new PlanillaHoraReportesDB();

            String menu = (String) objSesion.getAttribute("menu");
            int id = (Integer) objSesion.getAttribute("userid");

            //se crea la instancia de las catorcenas
            CatorcenaDB catorcenas = new CatorcenaDB();

            if (menu.equals("Bodeguero")) {
                fechas.obtenerFechasBodeguero(catorcena, id);
                hora_por_personas.obtenerActividadPorPersonaBodeguero(fechas, catorcena, id);
                //se obtienen las catorcenas que puede revisar el administrador
                catorcenas.obtenerCatorcenasBodeguero();
            }

            if (menu.equals("Ingeniero")) {
                fechas.obtenerFechasIngeniero(catorcena, id);
                hora_por_personas.obtenerActividadPorPersonaIngeniero(fechas, catorcena, id);
                //se obtienen las catorcenas que puede revisar el administrador
                catorcenas.obtenerCatorcenasIngeniero();
            }
            
            if (menu.equals("Administrador")) {
                fechas.obtenerFechasAdministrador(catorcena);
                hora_por_personas.obtenerActividadPorPersonaAdministrador(fechas, catorcena);
                //se obtienen las catorcenas que puede revisar el administrador
                catorcenas.obtenerCatorcenasAdministrador();
            }

            model.addAttribute("fechas", fechas.getFechas());

            model.addAttribute("actividad_por_personas", hora_por_personas.getPlanilla_hora_reportes());
            //se envia las catorcenas al select para que el administrador pueda seleccionar una catorcena diferente
            model.addAttribute("catorcenas", catorcenas.getCatorcenas());
            //Aqui envio el id de la catorcena para que se seleccione en el select de la pagina web
            model.addAttribute("cat", catorcena.getId());

            return "reporte_planilla_hora_actividad_por_persona";
        } catch (NullPointerException ex) {
            return "redirect:salir.htm";
        }
    }

    @RequestMapping(value = "reporte_persona_por_actividad", method = RequestMethod.GET)
    public String cargarFormReportePersonaPorActividad(Model model, HttpServletRequest request) {
        try {
            HttpSession objSesion = request.getSession(false);

            String in = (String) objSesion.getAttribute("in");
            if (in.equals("no")) {
                return "redirect:index.htm";
            }

            Catorcena catorcena = new Catorcena();
            //se comprueba si existe un parametro de la catorcena a ver en el url de la pagina
            try {
                if (!request.getParameter("catorcena").isEmpty()) {
                    //si existe el parametro se captura el id de la catorcena
                    Integer cat = Integer.parseInt(request.getParameter("catorcena"));
                    //se asigna el id a la catorcena
                    catorcena.setId(cat);
                }
            } catch (NullPointerException e) {
                //se obtiene la catorcena activa
                CatorcenaDB.obtenerCatorcenaActiva(catorcena);
            }

            FechasDB fechas = new FechasDB();
            PlanillaHoraReportesDB hora_por_personas = new PlanillaHoraReportesDB();

            String menu = (String) objSesion.getAttribute("menu");
            int id = (Integer) objSesion.getAttribute("userid");

            //se crea la instancia de las catorcenas
            CatorcenaDB catorcenas = new CatorcenaDB();

            if (menu.equals("Bodeguero")) {
                fechas.obtenerFechasBodeguero(catorcena, id);
                hora_por_personas.obtenerPersonaPorActividadBodeguero(fechas, catorcena, id);
                //se obtienen las catorcenas que puede revisar el administrador
                catorcenas.obtenerCatorcenasBodeguero();
            }

            if (menu.equals("Ingeniero")) {
                fechas.obtenerFechasIngeniero(catorcena, id);
                hora_por_personas.obtenerPersonaPorActividadIngeniero(fechas, catorcena, id);
                //se obtienen las catorcenas que puede revisar el administrador
                catorcenas.obtenerCatorcenasIngeniero();
            }
            
            if (menu.equals("Administrador")) {
                fechas.obtenerFechasAdministrador(catorcena);
                hora_por_personas.obtenerPersonaPorActividadAdministrador(fechas, catorcena);
                //se obtienen las catorcenas que puede revisar el administrador
                catorcenas.obtenerCatorcenasAdministrador();
            }

            model.addAttribute("fechas", fechas.getFechas());

            model.addAttribute("personas_por_actividad", hora_por_personas.getPlanilla_hora_reportes());
            //se envia las catorcenas al select para que el administrador pueda seleccionar una catorcena diferente
            model.addAttribute("catorcenas", catorcenas.getCatorcenas());
            //Aqui envio el id de la catorcena para que se seleccione en el select de la pagina web
            model.addAttribute("cat", catorcena.getId());

            return "reporte_planilla_hora_persona_por_actividad";
        } catch (NullPointerException ex) {
            return "redirect:salir.htm";
        }
    }

    @RequestMapping(value = "reporte_consolidado_tiempo_efectivo", method = RequestMethod.GET)
    public String cargarFormReporteConsolidadoTiempoEfectivo(Model model, HttpServletRequest request) {
        try {
            //Se obtiene la sesion activa y los datos relacionados a la sesion
            HttpSession objSesion = request.getSession(false);
            //Se obtiene un valor de si esta o no logeado en el sistema
            String in = (String) objSesion.getAttribute("in");
            //Se comprueba si esta logeado si no lo esta se redirecciona a la pagina de inicio de sesion
            if (in.equals("no")) {
                return "redirect:index.htm";
            }
            //si esta logeado se obtiene el tipo de usuario para asi cargar el menu asignado
            String menu = (String) objSesion.getAttribute("menu");
            //si el usuario no es un administrador se redireccionara a la pagina de acceso denegado
            if (!menu.equals("Administrador")) {
                return "redirect:error.htm";
            }
            //se crea la instancia de las catorcenas
            CatorcenaDB catorcenas = new CatorcenaDB();
            //se obtienen las catorcenas que puede revisar el administrador
            catorcenas.obtenerCatorcenasAdministrador();
            //se envia las catorcenas al select para que el administrador pueda seleccionar una catorcena diferente
            model.addAttribute("catorcenas", catorcenas.getCatorcenas());
            //se crea la instancia de las fechas
            FechasDB fechas = new FechasDB();
            //se crea la instancia de las catorcenas
            Catorcena catorcena = new Catorcena();
            //se comprueba si existe un parametro de la catorcena a ver en el url de la pagina
            try {
                if (!request.getParameter("catorcena").isEmpty()) {
                    //si existe el parametro se captura el id de la catorcena
                    Integer cat = Integer.parseInt(request.getParameter("catorcena"));
                    //se asigna el id a la catorcena
                    catorcena.setId(cat);
                    //se obtienen las fechas de la catorcena seleccionada
                    fechas.obtenerFechasAdministrador(catorcena);
                }
            } catch (NullPointerException e) {
                //se obtiene la catorcena activa
                CatorcenaDB.obtenerCatorcenaActiva(catorcena);
                //se obtienen las fechas de la catorcena activa
                fechas.obtenerFechasAdministrador(catorcena);
            }
            //Se crea una instancia de la clase que obtiene los reportes
            PlanillaHoraReportesDB tiempo_efectivos = new PlanillaHoraReportesDB();
            //Se carga el reporte deseado a una lista
            tiempo_efectivos.obtenerConsolidadoTiempoEfectivo(fechas, catorcena);
            //Aqui envio el id de la catorcena para que se seleccione en el select de la pagina web
            model.addAttribute("cat", catorcena.getId());
            //Aqui envio las fechas de la catorcena activa o la catorcena seleccionada para mostrarla en los titulos de la tabla
            model.addAttribute("fechas", fechas.getFechas());
            //Se envia el reporte para ser mostrado en pantalla
            model.addAttribute("tiempo_efectivos", tiempo_efectivos.getPlanilla_hora_reportes());

            return "reporte_consolidado_tiempo_efectivo";
        } catch (NullPointerException ex) {
            return "redirect:salir.htm";
        }
    }

    @RequestMapping(value = "reporte_consolidado_tiempo_efectivo", method = RequestMethod.POST)
    public String cargarFormReporteConsolidadoTiempoEfectivo(HttpServletRequest request) {
        try {
            HttpSession objSesion = request.getSession(false);

            String in = (String) objSesion.getAttribute("in");
            if (in.equals("no")) {
                return "redirect:index.htm";
            }
            //si esta logeado se obtiene el tipo de usuario para asi cargar el menu asignado
            String menu = (String) objSesion.getAttribute("menu");
            //si el usuario no es un administrador se redireccionara a la pagina de acceso denegado
            if (!menu.equals("Administrador")) {
                return "redirect:error.htm";
            }
            Catorcena catorcena = new Catorcena();
            //se comprueba si existe un parametro de la catorcena a ver en el url de la pagina
            try {
                if (!request.getParameter("catorcena").isEmpty()) {
                    //si existe el parametro se captura el id de la catorcena
                    Integer cat = Integer.parseInt(request.getParameter("catorcena"));
                    //se asigna el id a la catorcena
                    catorcena.setId(cat);
                }
            } catch (NullPointerException e) {
                //se obtiene la catorcena activa
                CatorcenaDB.obtenerCatorcenaActiva(catorcena);
            }
            //Se obtiene el id del usuario logeado
            int id = (Integer) objSesion.getAttribute("userid");

            if (WebUtils.hasSubmitParameter(request, "at")) {
                CatorcenaCargadaDB aprobarTodos = new CatorcenaCargadaDB();
                aprobarTodos.aprobarTodosAdministrador(id, catorcena);
            }

            if (WebUtils.hasSubmitParameter(request, "afs")) {
                try {
                    String[] registros = request.getParameterValues("registro");

                    for (String registro : registros) {
                        String[] datos = registro.split(",");
                        CatorcenaCargadaDB aprobarFilasSeleccionadas = new CatorcenaCargadaDB();
                        aprobarFilasSeleccionadas.aprobarFilasSeleccionadasAdministrador(id, datos[0], catorcena, datos[1]);
                    }
                } catch (NullPointerException ex) {
                }
            }

            if (WebUtils.hasSubmitParameter(request, "scfs")) {
                try {
                    String[] registros = request.getParameterValues("registro");

                    for (String registro : registros) {
                        String[] datos = registro.split(",");
                        CatorcenaCargadaDB solicitarCambiosEnFilasSeleccionadas = new CatorcenaCargadaDB();
                        solicitarCambiosEnFilasSeleccionadas.solicitarCambioEnFilasSeleccionadasAdministrador(id, datos[0], catorcena, datos[1]);
                    }
                } catch (NullPointerException ex) {
                }
            }

            if (WebUtils.hasSubmitParameter(request, "rfs")) {
                try {
                    String[] registros = request.getParameterValues("registro");

                    for (String registro : registros) {
                        String[] datos = registro.split(",");
                        CatorcenaCargadaDB rechazarFilasSeleccionadas = new CatorcenaCargadaDB();
                        rechazarFilasSeleccionadas.rechazarFilasSeleccionadasAdministrador(id, datos[0], catorcena, datos[1]);
                    }
                } catch (NullPointerException ex) {
                }
            }

            return "redirect:reporte_consolidado_tiempo_efectivo.htm?catorcena=" + catorcena.getId();
        } catch (NullPointerException ex) {
            return "redirect:salir.htm";
        }
    }
    
    @RequestMapping(value = "reporte_consolidado_tiempo_pagado", method = RequestMethod.GET)
    public String cargarFormReporteConsolidadoTiempoPagado(Model model, HttpServletRequest request) {
        try {
            //Se obtiene la sesion activa y los datos relacionados a la sesion
            HttpSession objSesion = request.getSession(false);
            //Se obtiene un valor de si esta o no logeado en el sistema
            String in = (String) objSesion.getAttribute("in");
            //Se comprueba si esta logeado si no lo esta se redirecciona a la pagina de inicio de sesion
            if (in.equals("no")) {
                return "redirect:index.htm";
            }
            //si esta logeado se obtiene el tipo de usuario para asi cargar el menu asignado
            String menu = (String) objSesion.getAttribute("menu");
            //si el usuario no es un administrador se redireccionara a la pagina de acceso denegado
            if (!menu.equals("Administrador")) {
                return "redirect:error.htm";
            }
            //se crea la instancia de las catorcenas
            CatorcenaDB catorcenas = new CatorcenaDB();
            //se obtienen las catorcenas que puede revisar el administrador
            catorcenas.obtenerCatorcenasAdministrador();
            //se envia las catorcenas al select para que el administrador pueda seleccionar una catorcena diferente
            model.addAttribute("catorcenas", catorcenas.getCatorcenas());
            //se crea la instancia de las catorcenas
            Catorcena catorcena = new Catorcena();
            //se comprueba si existe un parametro de la catorcena a ver en el url de la pagina
            try {
                if (!request.getParameter("catorcena").isEmpty()) {
                    //si existe el parametro se captura el id de la catorcena
                    Integer cat = Integer.parseInt(request.getParameter("catorcena"));
                    //se asigna el id a la catorcena
                    catorcena.setId(cat);
                }
            } catch (NullPointerException e) {
                //se obtiene la catorcena activa
                CatorcenaDB.obtenerCatorcenaActiva(catorcena);
            }
            //Se crea una instancia de la clase que obtiene los reportes
            PlanillaHoraReportesDB tiempo_pagados = new PlanillaHoraReportesDB();
            //Se carga el reporte deseado a una lista
            tiempo_pagados.obtenerConsolidadoTiempoPagado(catorcena);
            //Aqui envio el id de la catorcena para que se seleccione en el select de la pagina web
            model.addAttribute("cat", catorcena.getId());
            //Se envia el reporte para ser mostrado en pantalla
            model.addAttribute("tiempo_pagados", tiempo_pagados.getPlanilla_hora_reportes());

            return "reporte_consolidado_tiempo_pagado";
        } catch (NullPointerException ex) {
            return "redirect:salir.htm";
        }
    }
}

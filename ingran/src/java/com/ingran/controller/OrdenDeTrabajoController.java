package com.ingran.controller;

import com.ingran.data.CentroDeCostoDB;
import com.ingran.data.ClienteDB;
import com.ingran.data.LaudoDB;
import com.ingran.data.OrdenDeTrabajoDB;
import com.ingran.data.OrdenDeTrabajoDetalleDB;
import com.ingran.model.OrdenDeTrabajo;
import com.ingran.model.OrdenDeTrabajoDetalle;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.util.WebUtils;

@Controller
public class OrdenDeTrabajoController {

    @RequestMapping(value = "/crear_orden_de_trabajo", method = RequestMethod.GET)
    public String cargarFormCrear(Model model, HttpServletRequest request) {
        try {
            HttpSession objSesion = request.getSession(false);

            String in = (String) objSesion.getAttribute("in");
            if (in.equals("no")) {
                return "redirect:index.htm";
            }
            String menu = (String) objSesion.getAttribute("menu");
            if (!menu.equals("Administrador")) {
                return "redirect:error.htm";
            }

            CentroDeCostoDB proyectos = new CentroDeCostoDB();
            proyectos.obtenerCentroDeCostos();
            model.addAttribute("proyectos", proyectos.getCentro_de_costos());

            ClienteDB propietarios = new ClienteDB();
            propietarios.obtenerClientes();
            model.addAttribute("propietarios", propietarios.getClientes());

            model.addAttribute("odt", new OrdenDeTrabajo());

            return "crear_orden_de_trabajo";
        } catch (NullPointerException ex) {
            return "redirect:salir.htm";
        }
    }

    @RequestMapping(value = "/crear_orden_de_trabajo", method = RequestMethod.POST)
    public String cargarFormCrear(@ModelAttribute("odt") OrdenDeTrabajo odt, BindingResult result, Model model, HttpServletRequest request) {
        try {
            HttpSession objSesion = request.getSession(false);

            String in = (String) objSesion.getAttribute("in");
            if (in.equals("no")) {
                return "redirect:index.htm";
            }

            String menu = (String) objSesion.getAttribute("menu");
            if (!menu.equals("Administrador")) {
                return "redirect:error.htm";
            }

            if (OrdenDeTrabajoDB.crearOrdenDeTrabajo(odt)) {
                model.addAttribute("css", "success");
                model.addAttribute("msg", "Orden De Trabajo Registrada Correctamente !!!");
            }

            model.addAttribute("odt", odt);

            return "crear_orden_de_trabajo";
        } catch (NullPointerException ex) {
            return "redirect:salir.htm";
        }
    }

    @RequestMapping(value = "/listar_orden_de_trabajo", method = RequestMethod.GET)
    public String cargarFormListar(Model model, HttpServletRequest request) {
        try {
            HttpSession objSesion = request.getSession(false);

            String in = (String) objSesion.getAttribute("in");
            if (in.equals("no")) {
                return "redirect:index.htm";
            }
            String menu = (String) objSesion.getAttribute("menu");
            if (!menu.equals("Administrador")) {
                return "redirect:error.htm";
            }

            OrdenDeTrabajoDB odts = new OrdenDeTrabajoDB();
            odts.obtenerOrdenDeTrabajos();

            model.addAttribute("odts", odts.getOrden_de_trabajos());

            return "listar_orden_de_trabajo";
        } catch (NullPointerException ex) {
            return "redirect:salir.htm";
        }
    }

    @RequestMapping(value = "/editar_orden_de_trabajo", method = RequestMethod.GET)
    public String cargarFormEditar(Model model, HttpServletRequest request) {
        try {
            HttpSession objSesion = request.getSession(false);

            String in = (String) objSesion.getAttribute("in");
            if (in.equals("no")) {
                return "redirect:index.htm";
            }

            String menu = (String) objSesion.getAttribute("menu");
            if (!menu.equals("Administrador")) {
                return "redirect:error.htm";
            }

            OrdenDeTrabajo odt = new OrdenDeTrabajo();
            odt.setId(Integer.parseInt(request.getParameter("id")));
            OrdenDeTrabajoDB.obtenerOrdenDeTrabajo(odt);

            CentroDeCostoDB proyectos = new CentroDeCostoDB();
            proyectos.obtenerCentroDeCostos();
            model.addAttribute("proyectos", proyectos.getCentro_de_costos());

            ClienteDB propietarios = new ClienteDB();
            propietarios.obtenerClientes();
            model.addAttribute("propietarios", propietarios.getClientes());

            model.addAttribute("odt", odt);

            //Orden de Trabajo Detalle
            LaudoDB actividades = new LaudoDB();
            actividades.obtenerLaudos();
            model.addAttribute("actividades", actividades.getLaudos());

            model.addAttribute("odtd", new OrdenDeTrabajoDetalle());
            //Orden de Trabajo Detalle

            return "editar_orden_de_trabajo";
        } catch (NullPointerException ex) {
            return "redirect:salir.htm";
        }
    }

    @RequestMapping(value = "/editar_orden_de_trabajo", method = RequestMethod.POST)
    public String cargarFormEditar(@ModelAttribute("odt") OrdenDeTrabajo odt, @ModelAttribute("odtd") OrdenDeTrabajoDetalle odtd, BindingResult result, Model model, HttpServletRequest request) {
        try {
            HttpSession objSesion = request.getSession(false);

            String in = (String) objSesion.getAttribute("in");
            if (in.equals("no")) {
                return "redirect:index.htm";
            }

            String menu = (String) objSesion.getAttribute("menu");
            if (!menu.equals("Administrador")) {
                return "redirect:error.htm";
            }

            odt.setId(Integer.parseInt(request.getParameter("id")));

            if (WebUtils.hasSubmitParameter(request, "agregar")) {
                try {
                    if (OrdenDeTrabajoDetalleDB.crearOrdenDeTrabajoDetalle(odt, odtd))
                    {
                        model.addAttribute("css", "success");
                        model.addAttribute("msg", "Actividad Registrada Correctamente !!!");
                    }
                } catch (NullPointerException ex) {
                }
            }

            OrdenDeTrabajoDB.obtenerOrdenDeTrabajo(odt);

            CentroDeCostoDB proyectos = new CentroDeCostoDB();
            proyectos.obtenerCentroDeCostos();
            model.addAttribute("proyectos", proyectos.getCentro_de_costos());

            ClienteDB propietarios = new ClienteDB();
            propietarios.obtenerClientes();
            model.addAttribute("propietarios", propietarios.getClientes());

            model.addAttribute("odt", odt);

            //Orden de Trabajo Detalle
            LaudoDB actividades = new LaudoDB();
            actividades.obtenerLaudos();
            model.addAttribute("actividades", actividades.getLaudos());
            
            OrdenDeTrabajoDetalleDB acti = new OrdenDeTrabajoDetalleDB();
            acti.obtenerOrdenDeTrabajoDetalle();
            model.addAttribute("acti", acti.getOrdene_de_trabajos_detalle());

            model.addAttribute("odtd", new OrdenDeTrabajoDetalle());
            //Orden de Trabajo Detalle

            return "editar_orden_de_trabajo";
        } catch (NullPointerException ex) {
            return "redirect:salir.htm";
        }
    }
}

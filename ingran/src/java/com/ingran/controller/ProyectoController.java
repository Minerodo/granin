package com.ingran.controller;

import com.ingran.data.CentroDeCostoDB;
import com.ingran.data.ProyectoDB;
import com.ingran.data.ProyectoHorarioDB;
import com.ingran.model.ProyectoHorario;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ProyectoController {

    @RequestMapping(value = "/crear_proyecto_horario", method = RequestMethod.GET)
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

            model.addAttribute("proyecto", new ProyectoHorario());

            CentroDeCostoDB proyectos = new CentroDeCostoDB();
            proyectos.obtenerProyectosSinHorario();
            model.addAttribute("proyectos", proyectos.getCentro_de_costos());

            return "crear_proyecto_horario";
        } catch (NullPointerException ex) {
            return "redirect:salir.htm";
        }
    }

    @RequestMapping(value = "/crear_proyecto_horario", method = RequestMethod.POST)
    public String cargarFormCrear(@ModelAttribute("proyecto") ProyectoHorario ph, BindingResult result, Model model, HttpServletRequest request) {
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

            if (ProyectoHorarioDB.agregarProyectoHorario(ph)) {
                model.addAttribute("css", "success");
                model.addAttribute("msg", "Horario de Proyecto Registrado Correctamente !!!");
            } else{
                model.addAttribute("css", "danger");
                model.addAttribute("msg", "Horario de Proyecto No Registrado !!!");
            }

            model.addAttribute("proyecto", ph);

            return "crear_proyecto_horario";
        } catch (NullPointerException ex) {
            return "redirect:salir.htm";
        }
    }

    @RequestMapping(value = "/listar_proyecto_horario", method = RequestMethod.GET)
    public String cargaFormListar(Model model, HttpServletRequest request) {
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

            ProyectoHorarioDB proyectos = new ProyectoHorarioDB();
            proyectos.obtenerProyectoHorarios();

            model.addAttribute("proyectos", proyectos.getProyecto_horarios());

            return "listar_proyecto_horario";
        } catch (NullPointerException ex) {
            return "redirect:salir.htm";
        }
    }

    @RequestMapping(value = "/editar_proyecto_horario", method = RequestMethod.GET)
    public String cargarFormEditar(HttpServletRequest request, Model model) {
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

            ProyectoHorario proyecto_horario = new ProyectoHorario(Integer.parseInt(request.getParameter("id")));
            ProyectoHorarioDB.obtenerProyectoHorario(proyecto_horario);

            model.addAttribute("proyecto", proyecto_horario);

            return "editar_proyecto_horario";
        } catch (NullPointerException ex) {
            return "redirect:salir.htm";
        }
    }

    @RequestMapping(value = "/editar_proyecto_horario", method = RequestMethod.POST)
    public String cargarFormEditar(@ModelAttribute("proyecto") ProyectoHorario ph, BindingResult result, Model model, HttpServletRequest request) {
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
            
            if (ProyectoHorarioDB.actualizarProyectoHorario(ph)) {
                model.addAttribute("css", "success");
                model.addAttribute("msg", "Horario de Proyecto Actualizado Correctamente !!!");
            }else{
                model.addAttribute("css", "success");
                model.addAttribute("msg", "Horario de Proyecto No se Actualizo !!!");
            }

            model.addAttribute("proyecto", ph);

            return "editar_proyecto_horario";
        } catch (NullPointerException ex) {
            return "redirect:salir.htm";
        }
    }
}

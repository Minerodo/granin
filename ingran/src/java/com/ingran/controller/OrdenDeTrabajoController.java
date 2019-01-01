package com.ingran.controller;

import com.ingran.data.CentroDeCostoDB;
import com.ingran.data.ClienteDB;
import com.ingran.data.OrdenDeTrabajoDB;
import com.ingran.model.CentroDeCosto;
import com.ingran.model.OrdenDeTrabajo;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
    public String cargarFormCrear(@ModelAttribute("catorcena") OrdenDeTrabajo odt, Model model, HttpServletRequest request) {
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
            
            if(OrdenDeTrabajoDB.crearOrdenDeTrabajo(odt)){
                model.addAttribute("css", "success");
                model.addAttribute("msg", "Orden De Trabajo Registrada Correctamente !!!");
            }

            model.addAttribute("odt", odt);

            return "crear_orden_de_trabajo";    
        } catch (NullPointerException ex) {
            return "redirect:salir.htm";
        }
    }
}

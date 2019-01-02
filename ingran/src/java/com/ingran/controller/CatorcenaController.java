package com.ingran.controller;

import com.ingran.data.CatorcenaDB;
import com.ingran.model.Catorcena;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class CatorcenaController {

    @RequestMapping(value = "/crear_catorcena", method = RequestMethod.GET)
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

            model.addAttribute("catorcena", new Catorcena());

            return "crear_catorcena";
        } catch (NullPointerException ex) {
            return "redirect:salir.htm";
        }
    }

    @RequestMapping(value = "/crear_catorcena", method = RequestMethod.POST)
    public String cargarFormCrear(@ModelAttribute("catorcena") Catorcena c, BindingResult result, Model model, HttpServletRequest request) {
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

            c.setActivo(false);
            c.setDescripcion(c.getFecha_inicio_formato() + " al " + c.getFecha_fin_formato());

            if (CatorcenaDB.agregarCatorcena(c)) {
                model.addAttribute("css", "success");
                model.addAttribute("msg", "Catorcena Registrada Correctamente !!!");
            }

            model.addAttribute("catorcena", c);

            return "crear_catorcena";
        } catch (NullPointerException ex) {
            return "redirect:salir.htm";
        }
    }

    @RequestMapping(value = "/editar_catorcena", method = RequestMethod.GET)
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

            Catorcena catorcena = new Catorcena(Integer.parseInt(request.getParameter("id")));
            CatorcenaDB.obtenerCatorcena(catorcena);

            model.addAttribute("catorcena", catorcena);

            return "editar_catorcena";
        } catch (NullPointerException ex) {
            return "redirect:salir.htm";
        }
    }

    @RequestMapping(value = "/editar_catorcena", method = RequestMethod.POST)
    public String cargarFormEditar(@ModelAttribute("catorcena") Catorcena c, BindingResult result, Model model, HttpServletRequest request) {
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

            c.setDescripcion(c.getFecha_inicio_formato() + " al " + c.getFecha_fin_formato());
            Catorcena edit = new Catorcena(c.getId());
            CatorcenaDB.obtenerCatorcena(edit);
            c.setActivo(edit.getActivo());

            if (!CatorcenaDB.actualizarCatorcena(c)) {
                model.addAttribute("css", "danger");
                model.addAttribute("msg", "Error al actualizar !!!");
            } else {
                model.addAttribute("css", "success");
                model.addAttribute("msg", "Catorena Modificada Correctamente !!!");
            }

            model.addAttribute("catorcena", c);

            return "editar_catorcena";
        } catch (NullPointerException ex) {
            return "redirect:salir.htm";
        }
    }

    @RequestMapping(value = "/listar_catorcenas", method = RequestMethod.GET)
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

            CatorcenaDB catorcenas = new CatorcenaDB();
            catorcenas.obtenerCatorcenas();

            model.addAttribute("catorcenas", catorcenas.getCatorcenas());

            return "listar_catorcenas";
        } catch (NullPointerException ex) {
            return "redirect:salir.htm";
        }
    }

    @RequestMapping(value = "/activar_catorcena", method = RequestMethod.GET)
    public String activarCatorcena(HttpServletRequest request, Model model) {
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

            Catorcena catorcena = new Catorcena(Integer.parseInt(request.getParameter("id")));
            CatorcenaDB.obtenerCatorcena(catorcena);
            catorcena.setActivo(true);

            CatorcenaDB.actualizarCatorcena(catorcena);

            return "redirect:listar_catorcenas.htm";
        } catch (NullPointerException ex) {
            return "redirect:salir.htm";
        }
    }

    @RequestMapping(value = "/desactivar_catorcena", method = RequestMethod.GET)
    public String desactivarCatorcena(HttpServletRequest request, Model model) {
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

            Catorcena catorcena = new Catorcena(Integer.parseInt(request.getParameter("id")));
            CatorcenaDB.obtenerCatorcena(catorcena);
            catorcena.setActivo(false);

            CatorcenaDB.actualizarCatorcena(catorcena);

            return "redirect:listar_catorcenas.htm";
        } catch (NullPointerException ex) {
            return "redirect:salir.htm";
        }
    }
}

package com.ingran.controller;

import com.ingran.data.LaudoDB;
import com.ingran.data.Unidad_MedidaDB;
import com.ingran.model.Laudo;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LaudoController {

    @RequestMapping(value = "/crear_laudo", method = RequestMethod.GET)
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

            model.addAttribute("laudo", new Laudo());

            LaudoDB laudos = new LaudoDB();
            laudos.obtenerLaudos();
            model.addAttribute("laudos", laudos.getLaudos());

            Unidad_MedidaDB unidades = new Unidad_MedidaDB();
            unidades.obtenerUnidades();
            model.addAttribute("unidades", unidades.getUnidades());

            return "crear_laudo";
        } catch (NullPointerException ex) {
            return "redirect:salir.htm";
        }

    }

    @RequestMapping(value = "/crear_laudo", method = RequestMethod.POST)
    public String cargarFormCrear(@ModelAttribute("laudo") Laudo u, BindingResult result, Model model, HttpServletRequest request) {
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

//            model.addAttribute("laudo", u);
            if (LaudoDB.agregarLaudo(u)) {
                model.addAttribute("css", "success");
                model.addAttribute("msg", "Usuario Registrado Correctamente !!!");
            }

            model.addAttribute("laudo", u);

            LaudoDB laudos = new LaudoDB();
            laudos.obtenerLaudos();
            model.addAttribute("laudos", laudos.getLaudos());

            Unidad_MedidaDB unidades = new Unidad_MedidaDB();
            unidades.obtenerUnidades();
            model.addAttribute("unidades", unidades.getUnidades());

            return "redirect:crear_laudo.htm";

        } catch (NullPointerException ex) {
            return "redirect:salir.htm";
        }

    }

    //(HttpServletRequest request, Model model)
    @RequestMapping(value = "/editar_laudo", method = RequestMethod.GET)
    public String cargarFormEditar(HttpServletRequest request, Model model) {
        try {

            int idLaudo;

            HttpSession objSesion = request.getSession(false);

            String in = (String) objSesion.getAttribute("in");
            if (in.equals("no")) {
                return "redirect:index.htm";
            }
            String menu = (String) objSesion.getAttribute("menu");
            if (!menu.equals("Administrador")) {
                return "redirect:error.htm";
            }

            Laudo laudo = new Laudo();
            laudo.setId(Integer.parseInt(request.getParameter("id")));
            //laudo = LaudoDB.obtenerLaudo(laudo);
            model.addAttribute("laudo", laudo);

            Unidad_MedidaDB unidades = new Unidad_MedidaDB();
            unidades.obtenerUnidades();
            model.addAttribute("unidades", unidades.getUnidades());

            model.addAttribute("idlaudo", request.getParameter("id"));

            return "editar_laudo";

        } catch (NullPointerException ex) {
            return "redirect:salir.htm";
        }

    }

}

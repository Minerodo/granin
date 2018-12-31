package com.ingran.controller;

import com.ingran.data.CentroDeCostoDB;
import com.ingran.data.LaudoCCostoDB;
import com.ingran.data.LaudoDB;
import com.ingran.data.Unidad_MedidaDB;
import com.ingran.model.Laudo;
import com.ingran.model.LaudoCCosto;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LaudoCCostoController {

    @RequestMapping(value = "/crear_laudo_ccosto", method = RequestMethod.GET)
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

            int idLaudo = Integer.valueOf(request.getParameter("idLaudo"));
            
            LaudoCCosto laudo = new LaudoCCosto();
            laudo.setId(idLaudo);
            
            LaudoDB.obtenerLaudo(laudo);

            LaudoCCostoDB laudoscc = new LaudoCCostoDB();
            laudoscc.obtenerLaudosCCostos(laudo);

            Unidad_MedidaDB unidades = new Unidad_MedidaDB();
            unidades.obtenerUnidades();
            model.addAttribute("unidades", unidades.getUnidades());

            CentroDeCostoDB centroCosto = new CentroDeCostoDB();
            centroCosto.obtenerCentroDeCostos();
            model.addAttribute("centrosdecostos", centroCosto.getCentro_de_costos());
            
            model.addAttribute("laudoccosto", laudo);
            
            model.addAttribute("laudosccosto", laudoscc.getLaudosCCosto());

            return "crear_laudo_ccosto";
        } catch (NullPointerException ex) {
            return "redirect:salir.htm";
        }

    }

    @RequestMapping(value = "/crear_laudo_ccosto", method = RequestMethod.POST)
    public String cargarFormCrear(@ModelAttribute("laudo") LaudoCCosto u, BindingResult result, Model model, HttpServletRequest request) {
        int idLaudo;
        LaudoCCostoDB laudoscc = new LaudoCCostoDB();
        LaudoDB laudo = new LaudoDB();
        Double costo = 0.0d;

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
            if (LaudoCCostoDB.agregarLaudoCCosto(u)) {
                model.addAttribute("css", "success");
                model.addAttribute("msg", "Usuario Registrado Correctamente !!!");
            }

            idLaudo = Integer.valueOf(request.getParameter("idLaudo"));

            model.addAttribute("laudoccosto", new LaudoCCosto());

            //laudoscc.obtenerLaudosCCostos(idLaudo);
            model.addAttribute("laudosccosto", laudoscc.getLaudosCCosto());
            model.addAttribute("idlaudo", idLaudo);

            costo = laudo.getMontoLaudo(idLaudo);

            model.addAttribute("costolaudo", costo);

            Unidad_MedidaDB unidades = new Unidad_MedidaDB();
            unidades.obtenerUnidades();
            model.addAttribute("unidades", unidades.getUnidades());

            CentroDeCostoDB centroCosto = new CentroDeCostoDB();
            centroCosto.obtenerCentroDeCostos();

            model.addAttribute("centrosdecostos", centroCosto.getCentro_de_costos());

            return "redirect:crear_laudo_ccosto.htm?idLaudo=" + String.valueOf(idLaudo);

        } catch (NullPointerException ex) {
            return "redirect:salir.htm";
        }

    }

}

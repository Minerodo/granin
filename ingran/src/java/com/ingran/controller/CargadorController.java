package com.ingran.controller;

import com.ingran.data.CargadorDB;
import com.ingran.data.CatorcenaDB;
import com.ingran.model.Catorcena;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class CargadorController {

    @RequestMapping(value = "cargador_catorcenal", method = RequestMethod.GET)
    public String cargarFormCargadorCatorcenal(Model model, HttpServletRequest request) {
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
            CargadorDB cargadors = new CargadorDB();
            //Se carga el reporte deseado a una lista
            cargadors.obtenerCargadorCatorcenal(catorcena);
            //Aqui envio el id de la catorcena para que se seleccione en el select de la pagina web
            model.addAttribute("cat", catorcena.getId());
            //Se envia el reporte para ser mostrado en pantalla
            model.addAttribute("cargadors", cargadors.getCargadors());

            return "cargador_catorcenal";
        } catch (NullPointerException ex) {
            return "redirect:salir.htm";
        }
    }
    
    @RequestMapping(value = "cargador_semanal", method = RequestMethod.GET)
    public String cargarFormCargadorSemanal(Model model, HttpServletRequest request) {
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
                    
                    CatorcenaDB.obtenerCatorcena(catorcena);
                }
            } catch (NullPointerException e) {
                //se obtiene la catorcena activa
                CatorcenaDB.obtenerCatorcenaActiva(catorcena);
            }
            
            //Se crea una instancia de la clase que obtiene los reportes
            CargadorDB cargadors1 = new CargadorDB();
            //Se carga el reporte deseado a una lista
            cargadors1.obtenerCargadorSemanal(catorcena, 1);
            //Aqui envio el id de la catorcena para que se seleccione en el select de la pagina web
            model.addAttribute("cat", catorcena.getId());
            //Se envia el reporte para ser mostrado en pantalla
            model.addAttribute("cargadors1", cargadors1.getCargadors());
            
            CargadorDB cargadors2 = new CargadorDB();
            //Se carga el reporte deseado a una lista
            cargadors2.obtenerCargadorSemanal(catorcena, 2);
            //Se envia el reporte para ser mostrado en pantalla
            model.addAttribute("cargadors2", cargadors2.getCargadors());

            return "cargador_semanal";
        } catch (NullPointerException ex) {
            return "redirect:salir.htm";
        }
    }
}

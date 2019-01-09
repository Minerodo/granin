package com.ingran.controller;

import com.ingran.data.UsuarioDB;
import com.ingran.model.Usuario;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class NavController {

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index(Model model, HttpServletRequest request) {
        HttpSession objSesion = request.getSession(true);

        objSesion.setAttribute("in", "no");
        objSesion.setAttribute("menu", "");

        model.addAttribute("usuario", new Usuario());

        return "index";
    }

    @RequestMapping(value = "/index", method = RequestMethod.POST)
    public String index(@ModelAttribute("usuario") Usuario u, Model model, HttpServletRequest request) {

        if (UsuarioDB.autenticarUsuario(u)) {
            u.setId(0);
            u = UsuarioDB.obtenerUsuario(u);

            HttpSession objSesion = request.getSession(true);

            objSesion.setAttribute("in", "yes");
            objSesion.setAttribute("userid", u.getId());
            objSesion.setAttribute("user", u.getUsuario());
            objSesion.setAttribute("menu", u.getRol().getNombre());
            objSesion.setAttribute("nombre", u.getEmpleado().getNombre());
            objSesion.setAttribute("dui", u.getEmpleado().getEmpleado());

//            if(u.getRecuperacion()){
//                return "redirect:editar_usuario_credenciales.htm";
//            }
            return "redirect:inicio.htm";
        }

        u.setContrasenia("");
        model.addAttribute("usuario", u);

        model.addAttribute("css", "danger");
        model.addAttribute("msg", "Usuario o Contraseña Incorrecta!!!");

        return "index";
    }
    
    @RequestMapping(value = "/editar_usuario_credenciales", method = RequestMethod.GET)
    public String credenciales(Model model, HttpServletRequest request) {
        try {
            HttpSession objSesion = request.getSession(false);

            String in = (String) objSesion.getAttribute("in");

            if (in.equals("no")) {
                return "redirect:index.htm";
            }
            
            Usuario usuario = new Usuario((Integer) objSesion.getAttribute("userid"));
            usuario.setUsuario((String) objSesion.getAttribute("user"));
            
            model.addAttribute(usuario);
            return "editar_usuario_credenciales";
        } catch (NullPointerException e) {
            return "redirect:salir.htm";
        }
    }

    @RequestMapping(value = "/inicio", method = RequestMethod.GET)
    public String inicio(HttpServletRequest request, Model model) {
        try {
            HttpSession objSesion = request.getSession(false);

            String in = (String) objSesion.getAttribute("in");

            if (in.equals("no")) {
                return "redirect:index.htm";
            }
            
            String menu = (String) objSesion.getAttribute("menu");
            
            if(menu.equals("Bodeguero")){
                
            }
            
            
            model.addAttribute("men", menu);

            return "inicio";
        } catch (NullPointerException e) {
            return "redirect:salir.htm";
        }
    }

    @RequestMapping(value = "/error", method = RequestMethod.GET)
    public String error(HttpServletRequest request) {

        HttpSession objSesion = request.getSession(false);

        String in = (String) objSesion.getAttribute("in");

        if (in.equals("no")) {
            return "redirect:index.htm";
        }

        return "error";
    }

    @RequestMapping(value = "/salir", method = RequestMethod.GET)
    public String salir(Model model, HttpServletRequest request) {

        HttpSession objSesion = request.getSession(true);

        objSesion.setAttribute("in", "no");

        return "redirect:index.htm";
    }
}

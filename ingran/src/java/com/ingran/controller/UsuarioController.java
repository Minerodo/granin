package com.ingran.controller;

import com.ingran.data.CentroDeCostoDB;
import com.ingran.data.EmpleadoDB;
import com.ingran.data.RolDB;
import com.ingran.data.UsuarioCentroDeCostoDB;
import com.ingran.data.UsuarioDB;
import com.ingran.model.Rol;
import com.ingran.model.Usuario;
import com.ingran.validator.UsuarioValidator;
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
public class UsuarioController {

    @RequestMapping(value = "/crear_usuario", method = RequestMethod.GET)
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

            model.addAttribute("usuario", new Usuario());

            RolDB rolDB = new RolDB();
            rolDB.obtenerRoles();
            model.addAttribute("roles", rolDB.getRoles());

            EmpleadoDB empleadoDB = new EmpleadoDB();
            empleadoDB.obtenerEmpleadosSinUsuario();
            model.addAttribute("empleados", empleadoDB.getEmpleados());

            return "crear_usuario";
        } catch (NullPointerException ex) {
            return "redirect:salir.htm";
        }
    }

    @RequestMapping(value = "/crear_usuario", method = RequestMethod.POST)
    public String cargarFormCrear(@ModelAttribute("usuario") Usuario u, BindingResult result, Model model, HttpServletRequest request) {
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

            UsuarioValidator usuarioValidator = new UsuarioValidator();
            usuarioValidator.validate(u, result);

            if (UsuarioDB.existeUsuario(u)) {
                result.rejectValue("usuario", "usuario.incorrect", "El usuario " + u.getUsuario() + " ya existe");
                model.addAttribute("css", "danger");
                model.addAttribute("msg", "!El Nombre de Usuario Ya se Encuentra Registrado");
            }
            if (result.hasErrors()) {
                RolDB roles = new RolDB();
                roles.obtenerRoles();
                model.addAttribute("roles", roles.getRoles());

                EmpleadoDB empleados = new EmpleadoDB();
                empleados.obtenerEmpleadosSinUsuario();
                model.addAttribute("empleados", empleados.getEmpleados());

                return "crear_usuario";
            }

            if (UsuarioDB.agregarUsuario(u)) {
                model.addAttribute("css", "success");
                model.addAttribute("msg", "Usuario Registrado Correctamente !!!");
            }

            model.addAttribute("usuario", u);

            RolDB roles = new RolDB();
            roles.obtenerRoles();
            model.addAttribute("roles", roles.getRoles());

            return "crear_usuario";
        } catch (NullPointerException ex) {
            return "redirect:salir.htm";
        }
    }

    @RequestMapping(value = "/editar_usuario", method = RequestMethod.GET)
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

            Usuario usuario = new Usuario();
            usuario.setId(Integer.parseInt(request.getParameter("id")));
            usuario = UsuarioDB.obtenerUsuario(usuario);
            model.addAttribute("usuario", usuario);
            model.addAttribute("rol", usuario.getRol());

            RolDB roles = new RolDB();
            roles.obtenerRoles();
            model.addAttribute("roles", roles.getRoles());

            return "editar_usuario";
        } catch (NullPointerException ex) {
            return "redirect:salir.htm";
        }
    }

    @RequestMapping(value = "/editar_usuario", method = RequestMethod.POST)
    public String cargarFormEditar(@ModelAttribute("usuario") Usuario u, BindingResult result, Model model, HttpServletRequest request) {
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

            UsuarioValidator usuarioValidator = new UsuarioValidator();
            usuarioValidator.validate(u, result);
            if (result.hasErrors()) {
                model.addAttribute("usuario", u);

                RolDB roles = new RolDB();
                roles.obtenerRoles();
                model.addAttribute("roles", roles.getRoles());

                return "editar_usuario";
            }
            System.out.println("id: " + u.getId() + " usuario: " + u.getUsuario());
            Usuario edit = UsuarioDB.obtenerUsuario(u);
            Rol rol = RolDB.obtenerRol(u.getRol().getId());

            edit.setUsuario(u.getUsuario());
            edit.setCorreo(u.getCorreo());
            edit.setTelefono(u.getTelefono());
            edit.setRol(rol);

            if (!UsuarioDB.actualizarUsuario(edit)) {
                edit = UsuarioDB.obtenerUsuario(u);
                model.addAttribute("css", "danger");
                model.addAttribute("msg", "Error al actualizar !!!");
            } else {
                model.addAttribute("css", "success");
                model.addAttribute("msg", "Usuario Modificado Correctamente !!!");
            }

            model.addAttribute("usuario", edit);

            RolDB roles = new RolDB();
            roles.obtenerRoles();
            model.addAttribute("roles", roles.getRoles());

            return "editar_usuario";
        } catch (NullPointerException ex) {
            return "redirect:salir.htm";
        }
    }

    @RequestMapping(value = "/listar_usuarios", method = RequestMethod.GET)
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

            UsuarioDB usuarios = new UsuarioDB();
            usuarios.obtenerUsuarios();
            model.addAttribute("usuarios", usuarios.getUsuarios());

            return "listar_usuarios";
        } catch (NullPointerException ex) {
            return "redirect:salir.htm";
        }
    }

    @RequestMapping(value = "/activar_usuario", method = RequestMethod.GET)
    public String activarUsuario(HttpServletRequest request, Model model) {
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

            Usuario usuario = new Usuario();
            usuario.setId(Integer.parseInt(request.getParameter("id")));
            usuario = UsuarioDB.obtenerUsuario(usuario);
            usuario.setActivo(true);

            UsuarioDB.estadoUsuario(usuario);

            return "redirect:listar_usuarios.htm";
        } catch (NullPointerException ex) {
            return "redirect:salir.htm";
        }
    }

    @RequestMapping(value = "/desactivar_usuario", method = RequestMethod.GET)
    public String desactivarUsuario(HttpServletRequest request, Model model) {
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

            Usuario usuario = new Usuario();
            usuario.setId(Integer.parseInt(request.getParameter("id")));
            usuario = UsuarioDB.obtenerUsuario(usuario);
            usuario.setActivo(false);

            UsuarioDB.estadoUsuario(usuario);

            return "redirect:listar_usuarios.htm";
        } catch (NullPointerException ex) {
            return "redirect:salir.htm";
        }
    }

    @RequestMapping(value = "/eliminar_usuario", method = RequestMethod.GET)
    public String eliminarUsuario(HttpServletRequest request, Model model) {
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

            Usuario usuario = new Usuario();
            usuario.setId(Integer.parseInt(request.getParameter("id")));
            usuario = UsuarioDB.obtenerUsuario(usuario);
            usuario.setEliminado(true);

            UsuarioDB.estadoUsuario(usuario);

            return "redirect:listar_usuarios.htm";
        } catch (NullPointerException ex) {
            return "redirect:salir.htm";
        }
    }

    @RequestMapping(value = "/asignar_proyecto_usuario", method = RequestMethod.GET)
    public String cargarFormAsignarProyecto(HttpServletRequest request, Model model) {
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

            Usuario usuario = new Usuario();
            usuario.setId(Integer.parseInt(request.getParameter("id")));
            usuario = UsuarioDB.obtenerUsuario(usuario);
            model.addAttribute("usuario", usuario);

            CentroDeCostoDB proyectos = new CentroDeCostoDB();
            proyectos.obtenerCentroDeCostos();
            model.addAttribute("proyectos", proyectos.getCentro_de_costos());

            model.addAttribute("centro_de_costos", UsuarioCentroDeCostoDB.obtenerUsuarioCentroDeCosto(usuario));

            return "asignar_proyecto_usuario";
        } catch (NullPointerException ex) {
            return "redirect:salir.htm";
        }
    }

    @RequestMapping(value = "/asignar_proyecto_usuario", method = RequestMethod.POST)
    public String cargarFormAsignarProyecto(@ModelAttribute("usuario") Usuario u, BindingResult result, Model model, HttpServletRequest request) {
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

            if (UsuarioCentroDeCostoDB.existeProyectoAsignado(u)) {
                result.rejectValue("centro_de_costo.centro_de_costo", "centro_de_costo.centro_de_costo.incorrect", "El proyecto " + u.getCentro_de_costo().getCentro_de_costo() + " ya se asigno a este usuario!");
            }

            if (result.hasErrors()) {
                CentroDeCostoDB proyectos = new CentroDeCostoDB();
                proyectos.obtenerCentroDeCostos();
                model.addAttribute("proyectos", proyectos.getCentro_de_costos());

                model.addAttribute("centro_de_costos", UsuarioCentroDeCostoDB.obtenerUsuarioCentroDeCosto(u));

                return "asignar_proyecto_usuario";
            }

            Usuario edit = UsuarioDB.obtenerUsuario(u);

            edit.setCentro_de_costo(u.getCentro_de_costo());

            UsuarioCentroDeCostoDB.asignarProyecto(edit);

            if (!result.hasErrors() && WebUtils.hasSubmitParameter(request, "cerrar")) {
                return "redirect:listar_usuarios.htm";
            }

            model.addAttribute("id", u.getId());

            return "redirect:asignar_proyecto_usuario.htm";
        } catch (NullPointerException ex) {
            return "redirect:salir.htm";
        }
    }

    @RequestMapping(value = "/desasignar_proyecto", method = RequestMethod.GET)
    public String cargarFormDesasignarProyecto(Model model, HttpServletRequest request) {
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

            int usr = 0;
            try {
                usr = Integer.parseInt((String) request.getParameter("usr"));
            } catch (NullPointerException e) {

            }
            int id_py = 0;
            try {
                id_py = Integer.parseInt((String) request.getParameter("id"));
            } catch (NullPointerException e) {

            }

            UsuarioCentroDeCostoDB.desasignarProyecto(id_py);

            model.addAttribute("id", usr);

            return "redirect:asignar_proyecto_usuario.htm";
        } catch (NullPointerException ex) {
            return "redirect:salir.htm";
        }
    }
}

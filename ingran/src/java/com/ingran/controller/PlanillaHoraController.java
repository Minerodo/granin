package com.ingran.controller;

import com.ingran.data.CatorcenaDB;
import com.ingran.data.CentroDeCostoDB;
import com.ingran.data.ClienteDB;
import com.ingran.data.ConceptoDB;
import com.ingran.data.EmpleadoDB;
import com.ingran.data.FaseDB;
import com.ingran.data.PlanillaHoraDetalleDB;
import com.ingran.data.PlanillaHoraEncabezadoDB;
import com.ingran.data.reportes.CatorcenaCargadaDB;
import com.ingran.data.ProyectoHorarioDB;
import com.ingran.data.UsuarioCentroDeCostoDB;
import com.ingran.data.UsuarioDB;
import com.ingran.model.Catorcena;
import com.ingran.model.CentroDeCosto;
import com.ingran.model.Cliente;
import com.ingran.model.Empleado;
import com.ingran.model.PlanillaHoraDetalle;
import com.ingran.model.PlanillaHoraEncabezado;
import com.ingran.model.Usuario;
import com.ingran.model.UsuarioCentroDeCosto;
import com.ingran.util.Fecha;
import com.ingran.validator.PlanillaHoraValidator;
import java.util.ArrayList;
import java.util.List;
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
public class PlanillaHoraController {

    @RequestMapping(value = "/crear_planilla_hora", method = RequestMethod.GET)
    public String cargarFormCrear(Model model, HttpServletRequest request) {
        try {
            HttpSession objSesion = request.getSession(false);

            String in = (String) objSesion.getAttribute("in");
            if (in.equals("no")) {
                return "redirect:index.htm";
            }
            String menu = (String) objSesion.getAttribute("menu");
            if (!menu.equals("Bodeguero")) {
                return "redirect:error.htm";
            }

            PlanillaHoraEncabezado planilla_hora = new PlanillaHoraEncabezado();

            int userid = (Integer) objSesion.getAttribute("userid");
            Usuario bodeguero = new Usuario(userid);
            bodeguero = UsuarioDB.obtenerUsuario(bodeguero);
            planilla_hora.setBodeguero(bodeguero);

            Catorcena catorcena = new Catorcena();
            CatorcenaDB.obtenerCatorcenaActiva(catorcena);
            planilla_hora.setCatorcena(catorcena);

            try {
                if (!request.getParameter("cdc").isEmpty()) {
                    CentroDeCosto cdc = new CentroDeCosto();
                    cdc.setCentro_de_costo(request.getParameter("cdc"));
                    cdc = CentroDeCostoDB.obtenerCentroDeCosto(cdc);
                    planilla_hora.setCentro_de_costo(cdc);
                    model.addAttribute("fases", FaseDB.obtenerFases(cdc.getProyecto()));

                    ProyectoHorarioDB.obtenerProyectoHorario(planilla_hora);

                    List<UsuarioCentroDeCosto> ucdc = new ArrayList<>();
                    UsuarioCentroDeCosto ucdcc = new UsuarioCentroDeCosto();
                    ucdcc.setCentro_de_costo(cdc);
                    ucdc.add(ucdcc);
                    model.addAttribute("centro_de_costos", ucdc);
                }
                if (request.getParameter("cdc").isEmpty() || request.getParameter("cdc").equals("undefined")) {
                    model.addAttribute("centro_de_costos", UsuarioCentroDeCostoDB.obtenerUsuarioCentroDeCosto(bodeguero));
                }
            } catch (NullPointerException e) {
                model.addAttribute("centro_de_costos", UsuarioCentroDeCostoDB.obtenerUsuarioCentroDeCosto(bodeguero));
            }

            try {
                if (!request.getParameter("emp").isEmpty()) {
                    Empleado emp = new Empleado();
                    emp.setEmpleado(request.getParameter("emp"));
                    emp = EmpleadoDB.obtenerEmpleado(emp.getEmpleado());
                    planilla_hora.setEmpleado(emp);

                    List<Empleado> empleados = new ArrayList<>();
                    empleados.add(emp);
                    model.addAttribute("empleados", empleados);
                }
                if (request.getParameter("emp").isEmpty() || request.getParameter("emp").equals("undefined")) {
                    EmpleadoDB empleados = new EmpleadoDB();
                    empleados.obtenerEmpleados();
                    model.addAttribute("empleados", empleados.getEmpleados());
                }
            } catch (NullPointerException e) {
                EmpleadoDB empleados = new EmpleadoDB();
                empleados.obtenerEmpleados();
                model.addAttribute("empleados", empleados.getEmpleados());
            }

            try {
                if (!request.getParameter("fh").isEmpty()) {
                    String fh = request.getParameter("fh");
                    planilla_hora.setFecha(Fecha.convertirFecha(fh));
                }
            } catch (NullPointerException e) {
            }

            try {
                if (!request.getParameter("cl").isEmpty()) {
                    Cliente cl = ClienteDB.obtenerCliente(request.getParameter("cl"));
                    planilla_hora.setCliente(cl);

                    List<Cliente> clientes = new ArrayList<>();
                    clientes.add(cl);
                    model.addAttribute("clientes", clientes);
                }
                if (request.getParameter("cl").isEmpty() || request.getParameter("cl").equals("undefined")) {
                    ClienteDB clientes = new ClienteDB();
                    clientes.obtenerClientes();
                    model.addAttribute("clientes", clientes.getClientes());
                }
            } catch (NullPointerException e) {
                ClienteDB clientes = new ClienteDB();
                clientes.obtenerClientes();
                model.addAttribute("clientes", clientes.getClientes());
            }

            ConceptoDB conceptos = new ConceptoDB();
            conceptos.obtenerConceptos();
            model.addAttribute("conceptos", conceptos.getConceptos());

            String lista = "";
            for (int i = 0; i < planilla_hora.getPlanilla_hora_detalle().size(); i++) {
                if (i > 0) {
                    lista += ",";
                }
                lista += "#" + i;
            }
            model.addAttribute("lista", lista);
            model.addAttribute("indice", planilla_hora.getPlanilla_hora_detalle().size());

            model.addAttribute("planilla", planilla_hora);

            return "crear_planilla_hora";
        } catch (NullPointerException ex) {
            return "redirect:salir.htm";
        }
    }

    @RequestMapping(value = "/crear_planilla_hora", method = RequestMethod.POST)
    public String cargarFormCrear(@ModelAttribute("planilla") PlanillaHoraEncabezado phe, BindingResult result, Model model, HttpServletRequest request) {
        try {
            HttpSession objSesion = request.getSession(false);

            String in = (String) objSesion.getAttribute("in");
            if (in.equals("no")) {
                return "redirect:index.htm";
            }
            String menu = (String) objSesion.getAttribute("menu");
            if (!menu.equals("Bodeguero")) {
                return "redirect:error.htm";
            }

            phe.getPlanilla_hora_detalle().forEach((phd) -> {
                phd.calcularDatos();
            });

            PlanillaHoraValidator pheValidator = new PlanillaHoraValidator();
            pheValidator.validate(phe, result);
            if (result.hasErrors()) {
                ConceptoDB conceptos = new ConceptoDB();
                conceptos.obtenerConceptos();
                model.addAttribute("conceptos", conceptos.getConceptos());

                CentroDeCosto cdc = CentroDeCostoDB.obtenerCentroDeCosto(phe.getCentro_de_costo());
                model.addAttribute("fases", FaseDB.obtenerFases(cdc.getProyecto()));

                String lista = "";
                if (phe.getPlanilla_hora_detalle().size() > 0) {
                    boolean loop = true;
                    int i = 0;
                    while (loop) {
                        lista += "#" + i;
                        i++;
                        if (i == phe.getPlanilla_hora_detalle().size()) {
                            loop = false;
                        } else {
                            lista += ",";
                        }
                    }
                } 
                model.addAttribute("lista", lista);
            }
            if (!result.hasErrors()) {

                boolean noExiste = true;
                String error = "";
                for (PlanillaHoraDetalle phd : phe.getPlanilla_hora_detalle()) {
                    String resultado = PlanillaHoraDetalleDB.validarSiExiste(phe.getEmpleado(), phe.getFecha(), phd);
                    noExiste = noExiste && resultado.isEmpty();
                    if (!resultado.isEmpty()) {
                        error += resultado;
                    }
                }

                if (error.isEmpty()) {
                    if (ProyectoHorarioDB.agregarProyectoHorarioPlanillaEncabezado(phe)) {
                        model.addAttribute("css", "success");
                        model.addAttribute("msg", "Planilla Registrada Correctamente !!!");

                        model.addAttribute("urlNuevo", "crear_planilla_hora.htm?fh=" + phe.getFecha() + "&cl=" + phe.getCliente().getCliente() + "&cdc=" + phe.getCentro_de_costo().getCentro_de_costo());
                    }
                }

                if (!error.isEmpty()) {
                    error = "El empleado ya fue registrado en las siguientes horas:<br>" + error;
                    model.addAttribute("css", "danger");
                    model.addAttribute("msg", error);
                }
            }

            model.addAttribute("indice", phe.getPlanilla_hora_detalle().size());
            model.addAttribute("planilla", phe);

            return "crear_planilla_hora";
        } catch (NullPointerException ex) {
            return "redirect:salir.htm";
        }
    }

    @RequestMapping(value = "listar_catorcena_cargada", method = RequestMethod.GET)
    public String cargarFormCatorcenaCargada(Model model, HttpServletRequest request) {
        try {
            HttpSession objSesion = request.getSession(false);

            String in = (String) objSesion.getAttribute("in");
            if (in.equals("no")) {
                return "redirect:index.htm";
            }

            CatorcenaCargadaDB catorcena_cargadas = new CatorcenaCargadaDB();

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

            String menu = (String) objSesion.getAttribute("menu");
            int id = (Integer) objSesion.getAttribute("userid");

            //se crea la instancia de las catorcenas
            CatorcenaDB catorcenas = new CatorcenaDB();

            if (menu.equals("Bodeguero")) {
                catorcena_cargadas.obtenerCatorcenasBodeguero(catorcena, id);
                //se obtienen las catorcenas que puede revisar el administrador
                catorcenas.obtenerCatorcenasBodeguero();
            }

            if (menu.equals("Ingeniero")) {
                catorcena_cargadas.obtenerCatorcenasIngeniero(catorcena, id);
                //se obtienen las catorcenas que puede revisar el administrador
                catorcenas.obtenerCatorcenasIngeniero();
            }

            if (menu.equals("Administrador")) {
                catorcena_cargadas.obtenerCatorcenasAdministrador(catorcena);
                //se obtienen las catorcenas que puede revisar el administrador
                catorcenas.obtenerCatorcenasAdministrador();
            }

            model.addAttribute("menu", menu);
            model.addAttribute("catorcena_cargadas", catorcena_cargadas.getCatorcena_cargada());
            //se envia las catorcenas al select para que el administrador pueda seleccionar una catorcena diferente
            model.addAttribute("catorcenas", catorcenas.getCatorcenas());
            //Aqui envio el id de la catorcena para que se seleccione en el select de la pagina web
            model.addAttribute("cat", catorcena.getId());

            return "listar_catorcena_cargada";
        } catch (NullPointerException ex) {
            return "redirect:salir.htm";
        }
    }

    @RequestMapping(value = "listar_catorcena_cargada", method = RequestMethod.POST)
    public String cargarFormCatorcenaCargada(HttpServletRequest request) {
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

            int id = (Integer) objSesion.getAttribute("userid");

            if (WebUtils.hasSubmitParameter(request, "at")) {
                CatorcenaCargadaDB aprobarTodos = new CatorcenaCargadaDB();
                aprobarTodos.aprobarTodosIngeniero(catorcena, id);
            }

            if (WebUtils.hasSubmitParameter(request, "afs")) {
                try {
                    String[] registros = request.getParameterValues("registro");

                    for (String registro : registros) {
                        CatorcenaCargadaDB aprobarFilasSeleccionadas = new CatorcenaCargadaDB();
                        aprobarFilasSeleccionadas.aprobarFilasSeleccionadasIngeniero(registro, id);
                    }
                } catch (NullPointerException ex) {
                }
            }

            if (WebUtils.hasSubmitParameter(request, "scfs")) {
                try {
                    String[] registros = request.getParameterValues("registro");

                    for (String registro : registros) {
                        CatorcenaCargadaDB solicitarCambiosEnFilasSeleccionadas = new CatorcenaCargadaDB();
                        solicitarCambiosEnFilasSeleccionadas.solicitarCambioEnFilasSeleccionadasIngeniero(registro, id);
                    }
                } catch (NullPointerException ex) {
                }
            }

            if (WebUtils.hasSubmitParameter(request, "rfs")) {
                try {
                    String[] registros = request.getParameterValues("registro");

                    for (String registro : registros) {
                        CatorcenaCargadaDB rechazarFilasSeleccionadas = new CatorcenaCargadaDB();
                        rechazarFilasSeleccionadas.rechazarFilasSeleccionadasIngeniero(registro, id);
                    }
                } catch (NullPointerException ex) {
                }
            }

            return "redirect:listar_catorcena_cargada.htm";
        } catch (NullPointerException ex) {
            return "redirect:salir.htm";
        }
    }

    @RequestMapping(value = "/editar_planilla_hora", method = RequestMethod.GET)
    public String cargarFormEditar(HttpServletRequest request, Model model) {
        try {
            HttpSession objSesion = request.getSession(false);

            String in = (String) objSesion.getAttribute("in");
            if (in.equals("no")) {
                return "redirect:index.htm";
            }

            String menu = (String) objSesion.getAttribute("menu");

            PlanillaHoraEncabezado phe = new PlanillaHoraEncabezado();
            phe.setId(Integer.parseInt(request.getParameter("id")));

            if (menu.equals("Bodeguero")) {
                PlanillaHoraEncabezadoDB.obtenerPlanillaEncabezado(phe);
            } else {
                return "redirect:salir.htm";
            }

            CentroDeCosto cdc = new CentroDeCosto();
            cdc.setCentro_de_costo(phe.getCentro_de_costo().getCentro_de_costo());
            cdc = CentroDeCostoDB.obtenerCentroDeCosto(cdc);
            model.addAttribute("fases", FaseDB.obtenerFases(cdc.getProyecto()));

            ConceptoDB conceptos = new ConceptoDB();
            conceptos.obtenerConceptos();
            model.addAttribute("conceptos", conceptos.getConceptos());

            model.addAttribute("indice", phe.getPlanilla_hora_detalle().size());

            model.addAttribute("planilla", phe);

            return "editar_planilla_hora";
        } catch (NullPointerException ex) {
            return "redirect:salir.htm";
        }
    }

    @RequestMapping(value = "/editar_planilla_hora", method = RequestMethod.POST)
    public String cargarFormEditar(@ModelAttribute("planilla") PlanillaHoraEncabezado phe, HttpServletRequest request, Model model) {
        try {
            HttpSession objSesion = request.getSession(false);

            String in = (String) objSesion.getAttribute("in");
            if (in.equals("no")) {
                return "redirect:index.htm";
            }

            String menu = (String) objSesion.getAttribute("menu");

            if (!menu.equals("Bodeguero")) {
                return "redirect:error.htm";
            }

            if (WebUtils.hasSubmitParameter(request, "df")) {
                try {
                    PlanillaHoraDetalleDB.eliminarPlanillaDetalle(phe);

                    CentroDeCosto cdc = new CentroDeCosto();
                    cdc.setCentro_de_costo(phe.getCentro_de_costo().getCentro_de_costo());
                    cdc = CentroDeCostoDB.obtenerCentroDeCosto(cdc);
                    model.addAttribute("fases", FaseDB.obtenerFases(cdc.getProyecto()));

                    ConceptoDB conceptos = new ConceptoDB();
                    conceptos.obtenerConceptos();
                    model.addAttribute("conceptos", conceptos.getConceptos());
                } catch (NullPointerException ex) {
                }
            }

            if (WebUtils.hasSubmitParameter(request, "y")) {
                try {
                    if (ProyectoHorarioDB.actualizarProyectoHorarioPlanillaEncabezado(phe)) {
                        model.addAttribute("css", "success");
                        model.addAttribute("msg", "Planilla Registrada Correctamente !!!");
                    }
                } catch (NullPointerException ex) {
                }
            }

            PlanillaHoraEncabezadoDB.obtenerPlanillaEncabezado(phe);

            model.addAttribute("indice", phe.getPlanilla_hora_detalle().size());
            model.addAttribute("planilla", phe);

            return "editar_planilla_hora";
        } catch (NullPointerException ex) {
            return "redirect:salir.htm";
        }
    }

    @RequestMapping(value = "enviar_catorcena", method = RequestMethod.GET)
    public String cargarFormEnviarCatorcenaCargada(Model model, HttpServletRequest request) {
        try {
            HttpSession objSesion = request.getSession(false);

            String in = (String) objSesion.getAttribute("in");
            if (in.equals("no")) {
                return "redirect:index.htm";
            }

            CatorcenaCargadaDB catorcena_cargadas = new CatorcenaCargadaDB();

            Catorcena catorcena = new Catorcena();
            CatorcenaDB.obtenerCatorcenaActiva(catorcena);

            String menu = (String) objSesion.getAttribute("menu");
            int id = (Integer) objSesion.getAttribute("userid");

            if (menu.equals("Bodeguero")) {
                catorcena_cargadas.enviarCatorcenaBodeguero(catorcena, id);
            }

            if (menu.equals("Ingeniero")) {
                CatorcenaCargadaDB comprobar = new CatorcenaCargadaDB();
                if (comprobar.comprobarEnviarIngeniero(catorcena, id)) {
                    CatorcenaCargadaDB enviar = new CatorcenaCargadaDB();
                    enviar.enviarCatorcenaIngeniero(catorcena, id);
                }
            }

            return "redirect:listar_catorcena_cargada.htm";
        } catch (NullPointerException ex) {
            return "redirect:salir.htm";
        }
    }
}

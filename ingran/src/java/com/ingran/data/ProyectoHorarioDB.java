package com.ingran.data;

import com.ingran.model.CentroDeCosto;
import com.ingran.model.PlanillaHoraEncabezado;
import com.ingran.model.ProyectoHorario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProyectoHorarioDB extends Conexion {

    private List<ProyectoHorario> proyecto_horarios = new ArrayList<>();

    public void obtenerProyectoHorarios() {
        if (getConexion() == null) {
            abrirConexion();
        }

        getProyecto_horarios().clear();

        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            String consultaSQL = "SELECT ph.id, cdc.DESCRIPCION, ph.hora_entrada, ph.hora_salida, ph.receso_hora, ph.receso_minutos, ph.horas_efectivas, cdc.CENTRO_COSTO FROM PROYECTO_HORARIO ph JOIN " + Conexion.getDBEXACTUS() + ".ingran.CENTRO_COSTO cdc ON ph.proyecto = cdc.CENTRO_COSTO WHERE NOT ph.proyecto = '' ORDER BY ph.proyecto DESC;";

            pst = getConexion().prepareStatement(consultaSQL);

            rs = pst.executeQuery();

            while (rs.next()) {
                ProyectoHorario proyecto_horario = new ProyectoHorario();

                proyecto_horario.setId_horario(rs.getInt(1));

                CentroDeCosto cdc = new CentroDeCosto();
                cdc.setCentro_de_costo(rs.getString(8));
                cdc.setDescripcion(rs.getString(2));
                proyecto_horario.setCentro_de_costo(cdc);

                proyecto_horario.setHora_entrada(rs.getString(3));
                proyecto_horario.setHora_salida(rs.getString(4));
                proyecto_horario.setTiempo_receso_horas(rs.getInt(5));
                proyecto_horario.setTiempo_receso_minutos(rs.getInt(6));
                proyecto_horario.setHoras_efectivas(rs.getString(7));

                getProyecto_horarios().add(proyecto_horario);
            }
        } catch (SQLException e) {
            System.err.println("ERROR: " + e);
        } finally {
            try {
                if (getConexion() != null) {
                    cerrarConexion();
                }
                if (pst != null) {
                    pst.close();
                }
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                System.err.println("ERROR: " + e);
            }
        }
    }

    public static void obtenerProyectoHorario(ProyectoHorario proyecto_horario) {
        Conexion conexion = new Conexion();
        conexion.abrirConexion();

        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            String consultaSQL = "SELECT ph.id, cdc.DESCRIPCION, ph.hora_entrada, ph.hora_salida, ph.receso_hora, ph.receso_minutos, ph.horas_efectivas, cdc.CENTRO_COSTO FROM PROYECTO_HORARIO ph JOIN " + Conexion.getDBEXACTUS() + ".ingran.CENTRO_COSTO cdc ON ph.proyecto = cdc.CENTRO_COSTO WHERE ph.id = ?;";

            pst = conexion.getConexion().prepareStatement(consultaSQL);

            pst.setInt(1, proyecto_horario.getId_horario());

            rs = pst.executeQuery();

            boolean existe = false;

            while (rs.next()) {
                existe = true;
                proyecto_horario.setId_horario(rs.getInt(1));

                CentroDeCosto cdc = new CentroDeCosto();
                cdc.setCentro_de_costo(rs.getString(8));
                cdc.setDescripcion(rs.getString(2));
                proyecto_horario.setCentro_de_costo(cdc);

                proyecto_horario.setHora_entrada(rs.getString(3));
                proyecto_horario.setHora_salida(rs.getString(4));
                proyecto_horario.setTiempo_receso_horas(rs.getInt(5));
                proyecto_horario.setTiempo_receso_minutos(rs.getInt(6));
                proyecto_horario.setHoras_efectivas(rs.getString(7));
            }

            if (!existe) {
                proyecto_horario.setCentro_de_costo(proyecto_horario.getCentro_de_costo());
                proyecto_horario.setHora_entrada("07:00");
                proyecto_horario.setHora_salida("16:00");
                proyecto_horario.setTiempo_receso_horas(1);
                proyecto_horario.setTiempo_receso_minutos(0);
                proyecto_horario.setHoras_efectivas("08:00");
            }
        } catch (SQLException e) {
            System.err.println("ERROR: " + e);
        } finally {
            try {
                if (conexion.getConexion() != null) {
                    conexion.cerrarConexion();
                }
                if (pst != null) {
                    pst.close();
                }
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                System.err.println("ERROR: " + e);
            }
        }
    }

    public static boolean yaExiste(ProyectoHorario proyecto_horario) {
        Conexion conexion = new Conexion();
        conexion.abrirConexion();

        PreparedStatement pst = null;
        ResultSet rs = null;

        boolean existe = false;
        try {
            String consultaSQL = "SELECT ph.id, cdc.DESCRIPCION, ph.hora_entrada, ph.hora_salida, ph.receso_hora, ph.receso_minutos, ph.horas_efectivas, cdc.CENTRO_COSTO FROM PROYECTO_HORARIO ph JOIN " + Conexion.getDBEXACTUS() + ".ingran.CENTRO_COSTO cdc ON ph.proyecto = cdc.CENTRO_COSTO WHERE ph.proyecto = ?;";

            pst = conexion.getConexion().prepareStatement(consultaSQL);

            pst.setString(1, proyecto_horario.getCentro_de_costo().getCentro_de_costo());

            rs = pst.executeQuery();

            while (rs.next()) {
                existe = true;
            }
        } catch (SQLException e) {
            System.err.println("ERROR: " + e);
        } finally {
            try {
                if (conexion.getConexion() != null) {
                    conexion.cerrarConexion();
                }
                if (pst != null) {
                    pst.close();
                }
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                System.err.println("ERROR: " + e);
            }
        }
        return existe;
    }

    public static Boolean agregarProyectoHorario(ProyectoHorario proyecto) {
        Boolean creado = true;
        Conexion conexion = new Conexion();
        conexion.abrirConexion();

        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            String consultaSQL = "INSERT INTO PROYECTO_HORARIO (proyecto, hora_entrada, hora_salida, receso_hora, receso_minutos, horas_efectivas) VALUES (?, ?, ?, ?, ?, ?);";

            pst = conexion.getConexion().prepareStatement(consultaSQL);

            pst.setString(1, proyecto.getCentro_de_costo().getCentro_de_costo());
            pst.setString(2, proyecto.getHora_entrada());
            pst.setString(3, proyecto.getHora_salida());
            pst.setInt(4, proyecto.getTiempo_receso_horas());
            pst.setInt(5, proyecto.getTiempo_receso_minutos());
            pst.setString(6, proyecto.getHoras_efectivas());

            pst.executeUpdate();
        } catch (SQLException e) {
            creado = false;
            System.err.println("ERROR: " + e);
        } finally {
            try {
                if (conexion.getConexion() != null) {
                    conexion.cerrarConexion();
                }
                if (pst != null) {
                    pst.close();
                }
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                System.err.println("ERROR: " + e);
            }
        }
        return creado;
    }

    public static Boolean agregarProyectoHorarioPlanillaEncabezado(PlanillaHoraEncabezado proyecto) {
        boolean creado = true;

        Conexion conexion = new Conexion();
        conexion.abrirConexion();
        Connection connect = conexion.getConexion();

        try {
            connect.setAutoCommit(false);
        } catch (SQLException ex) {
            Logger.getLogger(ProyectoHorarioDB.class.getName()).log(Level.SEVERE, null, ex);
        }

        PreparedStatement pst = null;
        try {
            String consultaSQL = "INSERT INTO PROYECTO_HORARIO(hora_entrada, hora_salida, receso_hora, receso_minutos, horas_efectivas) VALUES(?, ?, ?, ?, ?);";

            pst = connect.prepareStatement(consultaSQL, Statement.RETURN_GENERATED_KEYS);

            pst.setString(1, proyecto.getHora_entrada());
            pst.setString(2, proyecto.getHora_salida());
            pst.setInt(3, proyecto.getTiempo_receso_horas());
            pst.setInt(4, proyecto.getTiempo_receso_minutos());
            pst.setString(5, proyecto.getHoras_efectivas());

            int affectedRows = pst.executeUpdate();

            if (affectedRows == 0) {
                creado = false;
            }

            try (ResultSet generatedKeys = pst.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    proyecto.setId_horario(generatedKeys.getInt(1));
                } else {
                    creado = false;
                }
            }
        } catch (SQLException e) {
            creado = false;
        }

        if (creado) {
            creado = creado && PlanillaHoraEncabezadoDB.agregarPlanillaEncabezado(connect, pst, proyecto);
        }

        if (!creado) {
            try {
                connect.rollback();
            } catch (SQLException ex) {
                Logger.getLogger(ProyectoHorarioDB.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        try {
            connect.setAutoCommit(true);
            if (conexion.getConexion() != null) {
                conexion.cerrarConexion();
            }
            if (pst != null) {
                pst.close();
            }
        } catch (SQLException e) {
            System.err.println("ERROR: " + e);
        }
        return creado;
    }

    public static Boolean actualizarProyectoHorarioPlanillaEncabezado(PlanillaHoraEncabezado phe) {
        boolean creado = true;

        Conexion conexion = new Conexion();
        conexion.abrirConexion();
        Connection connect = conexion.getConexion();

        try {
            connect.setAutoCommit(false);
        } catch (SQLException ex) {
            Logger.getLogger(ProyectoHorarioDB.class.getName()).log(Level.SEVERE, null, ex);
        }

        PreparedStatement pst = null;

        if (creado) {
            creado = creado && PlanillaHoraEncabezadoDB.actualizarPlanillaEncabezado(connect, pst, phe);
        }

        if (!creado) {
            try {
                connect.rollback();
            } catch (SQLException ex) {
                Logger.getLogger(ProyectoHorarioDB.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        try {
            connect.setAutoCommit(true);
            if (conexion.getConexion() != null) {
                conexion.cerrarConexion();
            }
            if (pst != null) {
                pst.close();
            }
        } catch (SQLException e) {
            System.err.println("ERROR: " + e);
        }
        return creado;
    }

    public static Boolean actualizarProyectoHorario(ProyectoHorario proyecto, boolean eliminar) {
        Boolean actualizado = true;
        Conexion conexion = new Conexion();
        conexion.abrirConexion();

        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            String consultaSQL = "";
            if (eliminar) {
                consultaSQL = "UPDATE PROYECTO_HORARIO SET proyecto = NULL WHERE id = ?;";
            } else {
                consultaSQL = "UPDATE PROYECTO_HORARIO SET hora_entrada = ?, hora_salida = ?, receso_hora = ?, receso_minutos = ?, horas_efectivas = ? WHERE id = ?;";
            }

            pst = conexion.getConexion().prepareStatement(consultaSQL);

            if (eliminar) {
                pst.setInt(1, proyecto.getId_horario());
            } else {
                pst.setString(1, proyecto.getHora_entrada());
                pst.setString(2, proyecto.getHora_salida());
                pst.setInt(3, proyecto.getTiempo_receso_horas());
                pst.setInt(4, proyecto.getTiempo_receso_minutos());
                pst.setString(5, proyecto.getHoras_efectivas());
                pst.setInt(6, proyecto.getId_horario());
            }

            pst.executeUpdate();
        } catch (SQLException e) {
            actualizado = false;
            System.err.println("ERROR: " + e);
        } finally {
            try {
                if (conexion.getConexion() != null) {
                    conexion.cerrarConexion();
                }
                if (pst != null) {
                    pst.close();
                }
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                System.err.println("ERROR: " + e);
            }
        }
        return actualizado;
    }

    public List<ProyectoHorario> getProyecto_horarios() {
        return proyecto_horarios;
    }

    public void setProyecto_horarios(List<ProyectoHorario> proyecto_horarios) {
        this.proyecto_horarios = proyecto_horarios;
    }
}

package com.ingran.data.reportes;

import com.ingran.data.Conexion;
import com.ingran.model.Catorcena;
import com.ingran.model.reportes.CatorcenaCargada;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CatorcenaCargadaDB extends Conexion {

    private List<CatorcenaCargada> catorcena_cargadas = new ArrayList<>();

    public void obtenerCatorcenasBodeguero(Catorcena catorcena, int bodeguero) {
        String consultaSQL = "SELECT phe.fecha, phe.empleado, e.NOMBRE, p.DESCRIPCION, c.NOMBRE, cdc.DESCRIPCION, phd.fase, (SELECT sf.nombre from " + Conexion.getDBEXACTUS() + ".ingran.FASE_PY sf where sf.fase LIKE concat(left(REPLACE(f.FASE, '.00',''), LEN(REPLACE(f.FASE, '.00',''))-3),'.00%') AND sf.ACEPTA_DATOS = 'N' AND sf.PROYECTO = f.PROYECTO) AS NOMBRE, phd.hora_inicio, phd.hora_fin, tdh.DESCRIPCION, phd.cantidad_de_horas, phe.id, phd.administrador_estado, phd.id, phe.enviado FROM PLANILLA_HORA_DETALLE phd inner JOIN PLANILLA_HORA_ENCABEZADO phe ON phd.planilla_hora_encabezado = phe.id inner JOIN " + Conexion.getDBEXACTUS() + ".ingran.EMPLEADO e ON phe.empleado = e.EMPLEADO inner JOIN " + Conexion.getDBEXACTUS() + ".ingran.PUESTO p ON e.PUESTO = p.PUESTO inner JOIN " + Conexion.getDBEXACTUS() + ".ingran.CLIENTE c ON phe.cliente = c.CLIENTE inner JOIN " + Conexion.getDBEXACTUS() + ".ingran.CENTRO_COSTO cdc ON phe.centro_de_costo = cdc.CENTRO_COSTO inner JOIN " + Conexion.getDBEXACTUS() + ".ingran.FASE_PY f ON phd.fase = f.FASE inner JOIN " + Conexion.getDBEXACTUS() + ".ingran.PROYECTO_PY py ON cdc.CENTRO_COSTO = py.CENTRO_COSTO inner JOIN " + Conexion.getDBEXACTUS() + ".ingran.CONCEPTO tdh ON phd.tipo_de_hora = tdh.CONCEPTO WHERE f.PROYECTO = py.PROYECTO AND phe.catorcena = ? AND phe.bodeguero = ? ORDER BY e.NOMBRE ASC, phd.administrador_estado ASC;";
        obtenerCatorcenas(consultaSQL, catorcena, bodeguero);
    }

    public void obtenerCatorcenasIngeniero(Catorcena catorcena, int ingeniero) {
        String consultaSQL = "SELECT phe.fecha, phe.empleado, e.NOMBRE, p.DESCRIPCION, c.NOMBRE, cdc.DESCRIPCION, phd.fase, (SELECT sf.nombre from " + Conexion.getDBEXACTUS() + ".ingran.FASE_PY sf where sf.fase LIKE concat(left(REPLACE(f.FASE, '.00',''), LEN(REPLACE(f.FASE, '.00',''))-3),'.00%') AND sf.ACEPTA_DATOS = 'N' AND sf.PROYECTO = f.PROYECTO) AS NOMBRE, phd.hora_inicio, phd.hora_fin, tdh.DESCRIPCION, phd.cantidad_de_horas, phe.id, phd.ingeniero_estado, phd.id, phe.enviado FROM PLANILLA_HORA_DETALLE phd inner JOIN PLANILLA_HORA_ENCABEZADO phe ON phd.planilla_hora_encabezado = phe.id inner JOIN " + Conexion.getDBEXACTUS() + ".ingran.EMPLEADO e ON phe.empleado = e.EMPLEADO inner JOIN " + Conexion.getDBEXACTUS() + ".ingran.PUESTO p ON e.PUESTO = p.PUESTO inner JOIN " + Conexion.getDBEXACTUS() + ".ingran.CLIENTE c ON phe.cliente = c.CLIENTE inner JOIN " + Conexion.getDBEXACTUS() + ".ingran.CENTRO_COSTO cdc ON phe.centro_de_costo = cdc.CENTRO_COSTO inner JOIN " + Conexion.getDBEXACTUS() + ".ingran.FASE_PY f ON phd.fase = f.FASE inner JOIN " + Conexion.getDBEXACTUS() + ".ingran.PROYECTO_PY py ON cdc.CENTRO_COSTO = py.CENTRO_COSTO inner JOIN " + Conexion.getDBEXACTUS() + ".ingran.CONCEPTO tdh ON phd.tipo_de_hora = tdh.CONCEPTO WHERE f.PROYECTO = py.PROYECTO AND phe.catorcena = ? AND phe.enviado = 1 AND phe.centro_de_costo IN (SELECT centro_costo FROM USUARIO_CENTRO_COSTO WHERE usuario = ?) ORDER BY e.NOMBRE ASC, phd.ingeniero_estado ASC;";
        obtenerCatorcenas(consultaSQL, catorcena, ingeniero);
    }
    
    public void obtenerCatorcenasAdministrador(Catorcena catorcena) {
        String consultaSQL = "SELECT phe.fecha, phe.empleado, e.NOMBRE, p.DESCRIPCION, c.NOMBRE, cdc.DESCRIPCION, phd.fase, (SELECT sf.nombre from " + Conexion.getDBEXACTUS() + ".ingran.FASE_PY sf where sf.fase LIKE concat(left(REPLACE(f.FASE, '.00',''), LEN(REPLACE(f.FASE, '.00',''))-3),'.00%') AND sf.ACEPTA_DATOS = 'N' AND sf.PROYECTO = f.PROYECTO) AS NOMBRE, phd.hora_inicio, phd.hora_fin, tdh.DESCRIPCION, phd.cantidad_de_horas, phe.id, phd.administrador_estado, phd.id, phe.enviado FROM PLANILLA_HORA_DETALLE phd inner JOIN PLANILLA_HORA_ENCABEZADO phe ON phd.planilla_hora_encabezado = phe.id inner JOIN " + Conexion.getDBEXACTUS() + ".ingran.EMPLEADO e ON phe.empleado = e.EMPLEADO inner JOIN " + Conexion.getDBEXACTUS() + ".ingran.PUESTO p ON e.PUESTO = p.PUESTO inner JOIN " + Conexion.getDBEXACTUS() + ".ingran.CLIENTE c ON phe.cliente = c.CLIENTE inner JOIN " + Conexion.getDBEXACTUS() + ".ingran.CENTRO_COSTO cdc ON phe.centro_de_costo = cdc.CENTRO_COSTO inner JOIN " + Conexion.getDBEXACTUS() + ".ingran.FASE_PY f ON phd.fase = f.FASE inner JOIN " + Conexion.getDBEXACTUS() + ".ingran.PROYECTO_PY py ON cdc.CENTRO_COSTO = py.CENTRO_COSTO inner JOIN " + Conexion.getDBEXACTUS() + ".ingran.CONCEPTO tdh ON phd.tipo_de_hora = tdh.CONCEPTO WHERE f.PROYECTO = py.PROYECTO AND phe.catorcena = ? AND phe.enviado = 1 AND phd.ingeniero_enviado = ? AND phd.ingeniero_estado = 'APROBADO' ORDER BY e.NOMBRE ASC, phd.administrador_estado ASC;";
        obtenerCatorcenas(consultaSQL, catorcena, 1);
    }

    private void obtenerCatorcenas(String consultaSQL, Catorcena catorcena, int usuario) {
        if (getConexion() == null) {
            abrirConexion();
        }

        getCatorcena_cargada().clear();

        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            pst = getConexion().prepareStatement(consultaSQL);

            pst.setInt(1, catorcena.getId());
            pst.setInt(2, usuario);

            rs = pst.executeQuery();

            while (rs.next()) {
                CatorcenaCargada catorcena_cargada = new CatorcenaCargada();

                catorcena_cargada.setFecha(rs.getString(1));
                catorcena_cargada.setDui(rs.getString(2));
                catorcena_cargada.setNombre(rs.getString(3));
                catorcena_cargada.setCargo(rs.getString(4));
                catorcena_cargada.setCliente(rs.getString(5));
                catorcena_cargada.setCentro_de_costo(rs.getString(6));
                catorcena_cargada.setFase(rs.getString(7));
                catorcena_cargada.setDescripcion_de_fase(rs.getString(8));
                catorcena_cargada.setHora_inicio(rs.getString(9));
                catorcena_cargada.setHora_fin(rs.getString(10));
                catorcena_cargada.setTipo_de_hora(rs.getString(11));
                catorcena_cargada.setCantidad_de_horas(rs.getString(12));
                catorcena_cargada.setId(rs.getInt(13));
                catorcena_cargada.setIngeniero_estado(rs.getString(14));
                catorcena_cargada.setId_detalle(rs.getInt(15));
                catorcena_cargada.setEnviado(rs.getBoolean(16));

                catorcena_cargadas.add(catorcena_cargada);
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

    public void enviarCatorcenaBodeguero(Catorcena catorcena, int bodeguero) {
        String consultaSQL = "UPDATE PLANILLA_HORA_ENCABEZADO SET enviado = 1, enviado_fecha = GETDATE() WHERE catorcena = ? AND bodeguero = ? AND enviado = 0;";
        enviarCatorcena(consultaSQL, catorcena, bodeguero);
    }

    public void enviarCatorcenaIngeniero(Catorcena catorcena, int ingeniero) {
        String consultaSQL = "UPDATE PLANILLA_HORA_DETALLE SET ingeniero_enviado = 0 WHERE ingeniero_enviado IS NULL;UPDATE PLANILLA_HORA_DETALLE SET ingeniero_enviado = 1, ingeniero_enviado_fecha = GETDATE() FROM PLANILLA_HORA_DETALLE phd INNER JOIN PLANILLA_HORA_ENCABEZADO phe ON phd.planilla_hora_encabezado = phe.id  WHERE phd.ingeniero_enviado = 0 AND phd.ingeniero_estado = 'APROBADO' AND phe.catorcena = ? AND phd.ingeniero = ?;";
        enviarCatorcena(consultaSQL, catorcena, ingeniero);
    }

    private void enviarCatorcena(String consultaSQL, Catorcena catorcena, int usuario) {
        if (getConexion() == null) {
            abrirConexion();
        }

        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            pst = getConexion().prepareStatement(consultaSQL);

            pst.setInt(1, catorcena.getId());
            pst.setInt(2, usuario);

            rs = pst.executeQuery();

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

    public void aprobarTodosIngeniero(Catorcena catorcena, int ingeniero) {
        String consultaSQL = "UPDATE PLANILLA_HORA_DETALLE SET ingeniero_estado = 'APROBADO', ingeniero_fecha = GETDATE(), ingeniero = ? WHERE id IN (SELECT phd.id FROM PLANILLA_HORA_DETALLE phd JOIN PLANILLA_HORA_ENCABEZADO phe ON phd.planilla_hora_encabezado = phe.id WHERE phe.catorcena = ? AND phe.centro_de_costo IN (SELECT ucdc.centro_costo FROM USUARIO_CENTRO_COSTO ucdc WHERE ucdc.usuario = ?));";
        aprobarTodos(consultaSQL, catorcena, ingeniero);
    }

    private void aprobarTodos(String consultaSQL, Catorcena catorcena, int usuario) {
        if (getConexion() == null) {
            abrirConexion();
        }

        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            pst = getConexion().prepareStatement(consultaSQL);

            pst.setInt(1, usuario);
            pst.setInt(2, catorcena.getId());
            pst.setInt(3, usuario);

            rs = pst.executeQuery();

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

    public void aprobarFilasSeleccionadasIngeniero(String registro, int ingeniero) {
        String consultaSQL = "UPDATE PLANILLA_HORA_DETALLE SET ingeniero_estado = 'APROBADO', ingeniero_fecha = GETDATE(), ingeniero = ? WHERE id = ?;";
        filasSeleccionadas(consultaSQL, registro, ingeniero);
    }

    public void rechazarFilasSeleccionadasIngeniero(String registro, int ingeniero) {
        String consultaSQL = "UPDATE PLANILLA_HORA_DETALLE SET ingeniero_estado = 'RECHAZADO', ingeniero_fecha = GETDATE(), ingeniero = ? WHERE id = ?;";
        filasSeleccionadas(consultaSQL, registro, ingeniero);
    }

    public void solicitarCambioEnFilasSeleccionadasIngeniero(String registro, int ingeniero) {
        String consultaSQL = "UPDATE PLANILLA_HORA_DETALLE SET ingeniero_estado = 'REGRESADO', ingeniero_fecha = GETDATE(), ingeniero = ? WHERE id = ?;";
        filasSeleccionadas(consultaSQL, registro, ingeniero);
    }

    private void filasSeleccionadas(String consultaSQL, String registro, int usuario) {
        if (getConexion() == null) {
            abrirConexion();
        }

        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            pst = getConexion().prepareStatement(consultaSQL);

            pst.setInt(1, usuario);
            pst.setString(2, registro);

            rs = pst.executeQuery();

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

    public boolean comprobarEnviarIngeniero(Catorcena catorcena, int ingeniero) {
        String consultaSQL = "SELECT COUNT(*) AS VALIDO FROM PLANILLA_HORA_DETALLE phd JOIN PLANILLA_HORA_ENCABEZADO phe ON phd.planilla_hora_encabezado = phe.id JOIN USUARIO_CENTRO_COSTO ucdc ON phe.centro_de_costo = ucdc.centro_costo WHERE phd.ingeniero_estado IN ('REGRESADO','SIN VER') AND phe.catorcena = ? AND ucdc.usuario = ?;";
        return comprobarEnviar(consultaSQL, catorcena, ingeniero);
    }

    private boolean comprobarEnviar(String consultaSQL, Catorcena catorcena, int usuario) {
        boolean sinRevisar = false;
        if (getConexion() == null) {
            abrirConexion();
        }

        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            pst = getConexion().prepareStatement(consultaSQL);

            pst.setInt(1, catorcena.getId());
            pst.setInt(2, usuario);

            rs = pst.executeQuery();

            int cantidad = 0;
            while (rs.next()) {
                cantidad = rs.getInt(1);
            }
            if (cantidad == 0) {
                sinRevisar = true;
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
        return sinRevisar;
    }
    
    public void aprobarTodosAdministrador(int administrador, Catorcena catorcena) {
        String consultaSQL = "UPDATE PLANILLA_HORA_DETALLE SET administrador = ?, administrador_estado = 'APROBADO', administrador_fecha = GETDATE() FROM PLANILLA_HORA_DETALLE phd INNER JOIN PLANILLA_HORA_ENCABEZADO phe ON phd.planilla_hora_encabezado = phe.id INNER JOIN " + Conexion.getDBEXACTUS() + ".ingran.EMPLEADO e ON phe.empleado = e.EMPLEADO WHERE phe.catorcena = ? AND phd.ingeniero_enviado = 1 AND phd.ingeniero_estado = 'APROBADO'";
        aprobarTodosAdministrador(consultaSQL, catorcena, administrador);
    }

    private void aprobarTodosAdministrador(String consultaSQL, Catorcena catorcena, int usuario) {
        if (getConexion() == null) {
            abrirConexion();
        }

        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            pst = getConexion().prepareStatement(consultaSQL);

            pst.setInt(1, usuario);
            pst.setInt(2, catorcena.getId());

            rs = pst.executeQuery();

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
    
    public void aprobarFilasSeleccionadasAdministrador(int administrador, String empleado, Catorcena catorcena, String tipo_de_hora) {
        String consultaSQL = "UPDATE PLANILLA_HORA_DETALLE SET administrador = ?, administrador_estado = 'APROBADO', administrador_fecha = GETDATE() FROM PLANILLA_HORA_DETALLE phd INNER JOIN PLANILLA_HORA_ENCABEZADO phe ON phd.planilla_hora_encabezado = phe.id INNER JOIN " + Conexion.getDBEXACTUS() + ".ingran.EMPLEADO e ON phe.empleado = e.EMPLEADO WHERE e.EMPLEADO = ? AND phe.catorcena = ? AND phd.ingeniero_enviado = 1 AND phd.ingeniero_estado = 'APROBADO' AND phd.tipo_de_hora = ?;";
        filasSeleccionadasAdministrador(consultaSQL, administrador, empleado, catorcena, tipo_de_hora);
    }

    public void rechazarFilasSeleccionadasAdministrador(int administrador, String empleado, Catorcena catorcena, String tipo_de_hora) {
        String consultaSQL = "UPDATE PLANILLA_HORA_DETALLE SET administrador = ?, administrador_estado = 'RECHAZADO', administrador_fecha = GETDATE() FROM PLANILLA_HORA_DETALLE phd INNER JOIN PLANILLA_HORA_ENCABEZADO phe ON phd.planilla_hora_encabezado = phe.id INNER JOIN " + Conexion.getDBEXACTUS() + ".ingran.EMPLEADO e ON phe.empleado = e.EMPLEADO WHERE e.EMPLEADO = ? AND phe.catorcena = ? AND phd.ingeniero_enviado = 1 AND phd.ingeniero_estado = 'APROBADO' AND phd.tipo_de_hora = ?;";
        filasSeleccionadasAdministrador(consultaSQL, administrador, empleado, catorcena, tipo_de_hora);
    }

    public void solicitarCambioEnFilasSeleccionadasAdministrador(int administrador, String empleado, Catorcena catorcena, String tipo_de_hora) {
        String consultaSQL = "UPDATE PLANILLA_HORA_DETALLE SET administrador = ?, administrador_estado = 'REGRESADO', administrador_fecha = GETDATE() FROM PLANILLA_HORA_DETALLE phd INNER JOIN PLANILLA_HORA_ENCABEZADO phe ON phd.planilla_hora_encabezado = phe.id INNER JOIN " + Conexion.getDBEXACTUS() + ".ingran.EMPLEADO e ON phe.empleado = e.EMPLEADO WHERE e.EMPLEADO = ? AND phe.catorcena = ? AND phd.ingeniero_enviado = 1 AND phd.ingeniero_estado = 'APROBADO' AND phd.tipo_de_hora = ?;";
        filasSeleccionadasAdministrador(consultaSQL, administrador, empleado, catorcena, tipo_de_hora);
    }
    
    private void filasSeleccionadasAdministrador(String consultaSQL, int administrador, String empleado, Catorcena catorcena, String tipo_de_hora) {
        if (getConexion() == null) {
            abrirConexion();
        }

        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            pst = getConexion().prepareStatement(consultaSQL);

            pst.setInt(1, administrador);
            pst.setString(2, empleado);
            pst.setInt(3, catorcena.getId());
            pst.setString(4, tipo_de_hora);

            rs = pst.executeQuery();

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

    public List<CatorcenaCargada> getCatorcena_cargada() {
        return catorcena_cargadas;
    }

    public void setCatorcena_cargada(List<CatorcenaCargada> catorcena_cargada) {
        this.catorcena_cargadas = catorcena_cargada;
    }
}

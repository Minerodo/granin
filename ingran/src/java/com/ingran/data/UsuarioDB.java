package com.ingran.data;

import com.ingran.model.Usuario;
import com.ingran.util.Correo;
import com.ingran.util.PassWordGenerator;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDB extends Conexion {

    private List<Usuario> usuarios = new ArrayList<>();

    public void obtenerUsuarios() {
        if (getConexion() == null) {
            abrirConexion();
        }

        getUsuarios().clear();

        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            String consultaSQL = "SELECT u.id, u.empleado, u.rol, u.usuario, u.correo, u.telefono, u.restauracion, u.activo, u.eliminado FROM dbo.USUARIO u WHERE u.eliminado = 0 ORDER BY u.activo DESC, u.rol ASC, u.usuario ASC;";

            pst = getConexion().prepareStatement(consultaSQL);

            rs = pst.executeQuery();

            while (rs.next()) {
                Usuario usuario = new Usuario();

                usuario.setId(rs.getInt(1));
                usuario.setEmpleado(EmpleadoDB.obtenerEmpleado(rs.getString(2)));
                usuario.setRol(RolDB.obtenerRol(rs.getInt(3)));
                usuario.setUsuario(rs.getString(4));
                usuario.setCorreo(rs.getString(5));
                usuario.setTelefono(rs.getString(6));
                usuario.setRecuperacion(rs.getBoolean(7));
                usuario.setActivo(rs.getBoolean(8));
                usuario.setEliminado(rs.getBoolean(9));

                getUsuarios().add(usuario);
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

    public static Usuario obtenerUsuario(Usuario user) {
        Conexion conexion = new Conexion();
        conexion.abrirConexion();

        Usuario usuario = new Usuario();

        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            String consultaSQL = "SELECT u.id, u.empleado, u.rol, u.usuario, u.correo, u.telefono, u.restauracion, u.activo, u.eliminado FROM dbo.USUARIO u WHERE u.id = ? OR u.usuario = ?;";

            pst = conexion.getConexion().prepareStatement(consultaSQL);

            pst.setInt(1, user.getId());
            pst.setString(2, user.getUsuario());

            rs = pst.executeQuery();

            while (rs.next()) {
                usuario.setId(rs.getInt(1));
                usuario.setEmpleado(EmpleadoDB.obtenerEmpleado(rs.getString(2)));
                usuario.setRol(RolDB.obtenerRol(rs.getInt(3)));
                usuario.setUsuario(rs.getString(4));
                usuario.setCorreo(rs.getString(5));
                usuario.setTelefono(rs.getString(6));
                usuario.setRecuperacion(rs.getBoolean(7));
                usuario.setActivo(rs.getBoolean(8));
                usuario.setEliminado(rs.getBoolean(9));
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
        return usuario;
    }

    public static Boolean existeUsuario(Usuario user) {
        Boolean bool_existe = true;
        Conexion conexion = new Conexion();
        conexion.abrirConexion();

        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            String consultaSQL = "SELECT COUNT(*) FROM dbo.USUARIO WHERE usuario = ?;";

            pst = conexion.getConexion().prepareStatement(consultaSQL);

            pst.setString(1, user.getUsuario());

            rs = pst.executeQuery();

            while (rs.next()) {
                String str_existe = rs.getString(1);
                if (str_existe.equals("0")) {
                    bool_existe = false;
                }
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
        return bool_existe;
    }

    public static Boolean agregarUsuario(Usuario usuario) {
        Boolean creado = true;
        Conexion conexion = new Conexion();
        conexion.abrirConexion();

        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            String consultaSQL = "EXEC dbo.usuario_creacion @empleado = ?, @rol = ?, @usuario = ?, @contrasenia = ?, @correo = ?, @telefono = ?;";

            pst = conexion.getConexion().prepareStatement(consultaSQL);

            pst.setString(1, usuario.getEmpleado().getEmpleado());
            pst.setInt(2, usuario.getRol().getId());
            pst.setString(3, usuario.getUsuario());

            usuario.setContrasenia(PassWordGenerator.getPassword(10));
            pst.setString(4, usuario.getContrasenia());

            pst.setString(5, usuario.getCorreo());
            pst.setString(6, usuario.getTelefono());

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

        Correo.enviarCorreo(usuario.getCorreo(), "Bienvenido a INGRAN", "Bienvenido al sistema de planillas INGRAN sus nuevas credenciales son:\nUsuario:" + usuario.getUsuario() + "\nContraseña Temporal: " + usuario.getContrasenia());

        return creado;
    }

    public static Boolean actualizarUsuario(Usuario usuario) {
        Boolean actualizado = true;
        Conexion conexion = new Conexion();
        conexion.abrirConexion();

        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            String consultaSQL = "UPDATE USUARIO SET rol = ?, correo = ?, telefono = ? WHERE id = ?;";

            pst = conexion.getConexion().prepareStatement(consultaSQL);

            pst.setInt(1, usuario.getRol().getId());
            pst.setString(2, usuario.getCorreo());
            pst.setString(3, usuario.getTelefono());
            pst.setInt(4, usuario.getId());

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

    public static Boolean estadoUsuario(Usuario usuario) {
        Boolean actualizado = true;
        Conexion conexion = new Conexion();
        conexion.abrirConexion();

        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            String consultaSQL = "UPDATE usuario set activo = ?, eliminado = ?, restauracion = ? WHERE id = ?;";

            pst = conexion.getConexion().prepareStatement(consultaSQL);

            pst.setBoolean(1, usuario.getActivo());
            pst.setBoolean(2, usuario.getEliminado());
            pst.setBoolean(3, usuario.getRecuperacion());
            pst.setInt(4, usuario.getId());

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

    public static Boolean autenticarUsuario(Usuario usuario) {
        Conexion conexion = new Conexion();
        conexion.abrirConexion();

        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            String consultaSQL = "EXEC dbo.usuario_autenticacion @usuario = ?, @contrasenia = ?;";

            pst = conexion.getConexion().prepareStatement(consultaSQL);

            pst.setString(1, usuario.getUsuario());
            pst.setString(2, usuario.getContrasenia());

            rs = pst.executeQuery();

            if (rs.next()) {
                if (rs.getInt(1) == 1) {
                    return true;
                }
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
        return false;
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

//    public static void main(String[] args) {
//        Usuario usu = new Usuario();
//
//        usu.setUsuario("ingeniero");
//        usu.setEmpleado(EmpleadoDB.obtenerEmpleado("01698509-8"));
//        usu.setRol(RolDB.obtenerRol(2));
//        usu.setTelefono("7777-7777");
//        usu.setCorreo("ejemplo@correo.com");
//        System.out.println(UsuarioDB.agregarUsuario(usu));
//
//        UsuarioDB usuarios = new UsuarioDB();
//        usuarios.obtenerUsuarios();
//
//        for (Usuario usuario : usuarios.getUsuarios()) {
//            System.out.println(usuario.getUsuario());
//        }
//
//        System.out.println(UsuarioDB.obtenerUsuario(1, "").getUsuario());
//        System.out.println(UsuarioDB.obtenerUsuario(0, "mrivera").getRol().getNombre());
//        System.out.println(UsuarioDB.autenticarUsuario("usuario1", "joi"));
//    }
}

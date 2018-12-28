package com.ingran.model;

import java.util.List;

public class Usuario {

    private Integer id;
    private Empleado empleado;
    private Rol rol;
    private String usuario;
    private String correo;
    private String telefono;
    private String contrasenia;
    private Boolean activo;
    private Boolean eliminado;
    private Boolean recuperacion;
    private List<CentroDeCosto> centro_de_costos;
    private CentroDeCosto centro_de_costo;
    private List<UsuarioCentroDeCosto> usuario_centro_de_costos;
    private UsuarioCentroDeCosto usuario_centro_de_costo;

    public Usuario() {
    }

    public Usuario(Integer id) {
        setId(id);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean habilitado) {
        this.activo = habilitado;
    }

    public Boolean getEliminado() {
        return eliminado;
    }

    public void setEliminado(Boolean eliminado) {
        this.eliminado = eliminado;
    }

    public Boolean getRecuperacion() {
        return recuperacion;
    }

    public void setRecuperacion(Boolean recuperacion) {
        this.recuperacion = recuperacion;
    }

    public List<CentroDeCosto> getCentro_de_costos() {
        return centro_de_costos;
    }

    public void setCentro_de_costos(List<CentroDeCosto> centro_de_costos) {
        this.centro_de_costos = centro_de_costos;
    }

    public CentroDeCosto getCentro_de_costo() {
        return centro_de_costo;
    }

    public void setCentro_de_costo(CentroDeCosto centro_de_costo) {
        this.centro_de_costo = centro_de_costo;
    }

    public List<UsuarioCentroDeCosto> getUsuario_centro_de_costos() {
        return usuario_centro_de_costos;
    }

    public void setUsuario_centro_de_costos(List<UsuarioCentroDeCosto> usuario_centro_de_costos) {
        this.usuario_centro_de_costos = usuario_centro_de_costos;
    }

    public UsuarioCentroDeCosto getUsuario_centro_de_costo() {
        return usuario_centro_de_costo;
    }

    public void setUsuario_centro_de_costo(UsuarioCentroDeCosto usuario_centro_de_costo) {
        this.usuario_centro_de_costo = usuario_centro_de_costo;
    }

    @Override
    public String toString() {
        return getUsuario();
    }
}

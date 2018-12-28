package com.ingran.model;

public class UsuarioCentroDeCosto {

    Integer id;
    Usuario usuario;
    CentroDeCosto centro_de_costo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public CentroDeCosto getCentro_de_costo() {
        return centro_de_costo;
    }

    public void setCentro_de_costo(CentroDeCosto centro_de_costo) {
        this.centro_de_costo = centro_de_costo;
    }
}

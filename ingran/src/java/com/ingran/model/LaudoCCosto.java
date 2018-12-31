package com.ingran.model;

public class LaudoCCosto extends Laudo {

    private Integer id_laudo;
    private CentroDeCosto centro_costo;

    public Integer getId_laudo() {
        return id_laudo;
    }

    public void setId_laudo(Integer id_laudo) {
        this.id_laudo = id_laudo;
    }

    public CentroDeCosto getCentro_costo() {
        return centro_costo;
    }

    public void setCentro_costo(CentroDeCosto centro_costo) {
        this.centro_costo = centro_costo;
    }
}

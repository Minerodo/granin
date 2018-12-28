package com.ingran.model;

import java.util.List;

public class Proyecto {

    private String proyecto;
    private String descripcion;
    private String centro_de_costo;
    private String estado;
    List<Fase> fases;

    public String getProyecto() {
        return proyecto;
    }

    public void setProyecto(String proyecto) {
        this.proyecto = proyecto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCentro_de_costo() {
        return centro_de_costo;
    }

    public void setCentro_de_costo(String centro_de_costo) {
        this.centro_de_costo = centro_de_costo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public List<Fase> getFases() {
        return fases;
    }

    public void setFases(List<Fase> fases) {
        this.fases = fases;
    }

    public Proyecto() {
    }

    public Proyecto(String proyecto) {
        this.proyecto = proyecto;
    }

    @Override
    public String toString() {
        return getDescripcion();
    }
}

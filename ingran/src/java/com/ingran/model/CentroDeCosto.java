package com.ingran.model;

public class CentroDeCosto {

    private String centro_de_costo;
    private String descripcion;
    private String tipo;
    private Proyecto proyecto;

    public String getCentro_de_costo() {
        return centro_de_costo;
    }

    public void setCentro_de_costo(String centro_de_costo) {
        this.centro_de_costo = centro_de_costo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Proyecto getProyecto() {
        return proyecto;
    }

    public void setProyecto(Proyecto proyecto) {
        this.proyecto = proyecto;
    }

    public String toString() {
        return getDescripcion();
    }
}

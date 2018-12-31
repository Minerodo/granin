package com.ingran.model;

import java.util.List;

public class Laudo {

    private Integer id;
    private String descripcion;
    private Double costo;
    private Unidad_Medida unidad_medida;
    private String estado;
    private List<LaudoCCosto> laudo_especifico;

    public Unidad_Medida getUnidad_medida() {
        return unidad_medida;
    }

    public void setUnidad_medida(Unidad_Medida unidad_medida) {
        this.unidad_medida = unidad_medida;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Double getCosto() {
        return costo;
    }

    public void setCosto(Double costo) {
        this.costo = costo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getNombre() {
        return descripcion;
    }

    public List<LaudoCCosto> getLaudo_especifico() {
        return laudo_especifico;
    }

    public void setLaudo_especifico(List<LaudoCCosto> laudo_especifico) {
        this.laudo_especifico = laudo_especifico;
    }
}

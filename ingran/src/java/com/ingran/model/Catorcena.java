package com.ingran.model;

import com.ingran.util.Fecha;

public class Catorcena {

    private Integer id;
    private String fecha_inicio;
    private String fecha_fin;
    private Boolean activo;
    private String descripcion;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFecha_inicio() {
        return fecha_inicio;
    }

    public String getFecha_inicio_formato() {
        return Fecha.convertirTextoAFecha(getFecha_inicio());
    }

    public void setFecha_inicio(String fecha_inicio) {
        this.fecha_inicio = fecha_inicio;
    }

    public String getFecha_fin() {
        return fecha_fin;
    }

    public String getFecha_fin_formato() {
        return Fecha.convertirTextoAFecha(getFecha_fin());
    }

    public void setFecha_fin(String fecha_fin) {
        this.fecha_fin = fecha_fin;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Catorcena() {
    }

    public Catorcena(Integer id) {
        setId(id);
    }
}

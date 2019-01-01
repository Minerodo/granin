package com.ingran.model;

import java.sql.Date;
import java.util.List;

public class OrdenDeTrabajo {

    private Integer id;
    private CentroDeCosto proyecto;
    private Cliente propietario;
    private String titulo;
    private Date fecha;
    private Double avance;
    private Double monto;
    private List<OrdenDeTrabajoDetalle> actividades;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public CentroDeCosto getProyecto() {
        return proyecto;
    }

    public void setProyecto(CentroDeCosto proyecto) {
        this.proyecto = proyecto;
    }

    public Cliente getPropietario() {
        return propietario;
    }

    public void setPropietario(Cliente propietario) {
        this.propietario = propietario;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Double getAvance() {
        return avance;
    }

    public void setAvance(Double avance) {
        this.avance = avance;
    }

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    public List<OrdenDeTrabajoDetalle> getActividades() {
        return actividades;
    }

    public void setActividades(List<OrdenDeTrabajoDetalle> actividades) {
        this.actividades = actividades;
    }
}

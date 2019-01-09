package com.ingran.model;

import java.util.List;

public class OrdenDeTrabajo {

    private Integer id;
    private CentroDeCosto proyecto;
    private Cliente propietario;
    private String titulo;
    private String fecha;
    private Double avance;
    private Double monto;
    //private Fase fase;
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

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
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

//    public Fase getFase() {
//        return fase;
//    }

//    public void setFase(Fase fase) {
//        this.fase = fase;
//    }

    public List<OrdenDeTrabajoDetalle> getActividades() {
        return actividades;
    }

    public void setActividades(List<OrdenDeTrabajoDetalle> actividades) {
        this.actividades = actividades;
    }
}

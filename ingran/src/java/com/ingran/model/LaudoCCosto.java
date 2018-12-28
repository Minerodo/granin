/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ingran.model;

/**
 *
 * @author admin
 */
public class LaudoCCosto {
    private Integer id;
    private CentroDeCosto centro_costo;
    private Integer id_laudo_general;
    private double costo = 0.00d;
    private String estado;
    private Unidad_Medida unidad_medida;
    private String descripcion;
    private Integer idlaudo;

    public Integer getIdlaudo() {
        return idlaudo;
    }

    public void setIdlaudo(Integer idlaudo) {
        this.idlaudo = idlaudo;
    }
    
    

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    
    
    public Unidad_Medida getUnidad_medida() {
        return unidad_medida;
    }

    public void setUnidad_medida(Unidad_Medida unidad_medida) {
        this.unidad_medida = unidad_medida;
    }
    

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    
    
    
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public CentroDeCosto getCentro_costo() {
        return centro_costo;
    }

    public void setCentro_costo(CentroDeCosto centro_costo) {
        this.centro_costo = centro_costo;
    }

    public Integer getId_laudo_general() {
        return id_laudo_general;
    }

    public void setId_laudo_general(Integer id_laudo_general) {
        this.id_laudo_general = id_laudo_general;
    }

    public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }
    
    
    
}

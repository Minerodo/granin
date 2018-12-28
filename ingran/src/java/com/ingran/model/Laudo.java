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
public class Laudo {
    private Integer id;
    private String descripcion;
    private double  costo = 0.00d;
    private Unidad_Medida unidad_medida;
    private String estado;

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

    public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    
    
//    public String toString(){
//        return getNombre();
//    }
//    
    public String getNombre() {
        return descripcion;
    }
    
    
}

package com.ingran.model.reportes;

import com.ingran.data.ConceptoDB;
import com.ingran.model.Concepto;
import java.util.ArrayList;
import java.util.List;

public class PlanillaHoraReportes {

    //Todos los reportes
    String nombre;
    //Horas por Persona
    String cargo;
    String tipo_de_hora;
    String concepto;
    //Actividad por Persona y Persona por Actividad
    String actividad;

    List<String> fechas = new ArrayList<>();
    String total_general;

    String estado;
    String horas_efectivas;
    String horas_pagadas;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getTipo_de_hora() {
        return tipo_de_hora;
    }

    public void setTipo_de_hora(String tipo_de_hora) {
        this.concepto = tipo_de_hora;
        Concepto tdh = new Concepto();
        tdh.setConcepto(tipo_de_hora);
        this.tipo_de_hora = ConceptoDB.obtenerConcepto(tdh).getDescripcion();
    }

    public String getActividad() {
        return actividad;
    }

    public void setActividad(String actividad) {
        this.actividad = actividad;
    }

    public List<String> getFechas() {
        return fechas;
    }

    public void setFechas(List<String> fechas) {
        this.fechas = fechas;
    }

    public String getTotal_general() {
        return total_general;
    }

    public void setTotal_general(String total_general) {
        this.total_general = total_general;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getHoras_efectivas() {
        return horas_efectivas;
    }

    public void setHoras_efectivas(String horas_efectivas) {
        this.horas_efectivas = horas_efectivas;
    }

    public String getHoras_pagadas() {
        return horas_pagadas;
    }

    public void setHoras_pagadas(String horas_pagadas) {
        this.horas_pagadas = horas_pagadas;
    }
}

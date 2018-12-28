package com.ingran.model;

import java.sql.Date;

public class PlanillaHoraDetalle {

    private int id;
    //Datos ingresados por el bodeguero
    private PlanillaHoraEncabezado planilla_hora_encabezado;
    private Fase fase;
    private String hora_inicio;
    private String hora_fin;
    private Concepto concepto;
    private String cantidad_hora;

    private Usuario ingeniero;
    private String ingeniero_estado;
    private String ingeniero_regresado;
    private Date ingeniero_fecha;

    private Usuario administrador;
    private String administrador_estado;
    private Date administrador_fecha;
    private Boolean consolidado;
    private String administrador_color;

    private Double horas;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public PlanillaHoraEncabezado getPlanilla_hora_encabezado() {
        return planilla_hora_encabezado;
    }

    public void setPlanilla_hora_encabezado(PlanillaHoraEncabezado planilla_hora_encabezado) {
        this.planilla_hora_encabezado = planilla_hora_encabezado;
    }

    public Fase getFase() {
        return fase;
    }

    public void setFase(Fase fase) {
        this.fase = fase;
    }

    public String getHora_inicio() {
        return hora_inicio;
    }

    public void setHora_inicio(String hora_inicio) {
        this.hora_inicio = hora_inicio.substring(0, 5);
    }

    public String getHora_fin() {
        return hora_fin;
    }

    public void setHora_fin(String hora_fin) {
        this.hora_fin = hora_fin.substring(0, 5);
    }

    public Concepto getConcepto() {
        return concepto;
    }

    public void setConcepto(Concepto concepto) {
        this.concepto = concepto;
    }

    public String getCantidad_hora() {
        return cantidad_hora;
    }

    public void setCantidad_hora(String cantidad_hora) {
        this.cantidad_hora = cantidad_hora;
    }

    public Usuario getIngeniero() {
        return ingeniero;
    }

    public void setIngeniero(Usuario ingeniero) {
        this.ingeniero = ingeniero;
    }

    public String getIngeniero_estado() {
        return ingeniero_estado;
    }

    public void setIngeniero_estado(String ingeniero_estado) {
        this.ingeniero_estado = ingeniero_estado;
    }

    public String getIngeniero_regresado() {
        return ingeniero_regresado;
    }

    public void setIngeniero_regresado(String ingeniero_regresado) {
        this.ingeniero_regresado = ingeniero_regresado;
    }

    public Date getIngeniero_fecha() {
        return ingeniero_fecha;
    }

    public void setIngeniero_fecha(Date ingeniero_fecha) {
        this.ingeniero_fecha = ingeniero_fecha;
    }

    public Usuario getAdministrador() {
        return administrador;
    }

    public void setAdministrador(Usuario administrador) {
        this.administrador = administrador;
    }

    public String getAdministrador_estado() {
        return administrador_estado;
    }

    public void setAdministrador_estado(String administrador_estado) {
        this.administrador_estado = administrador_estado;
    }

    public Date getAdministrador_fecha() {
        return administrador_fecha;
    }

    public void setAdministrador_fecha(Date administrador_fecha) {
        this.administrador_fecha = administrador_fecha;
    }

    public Boolean getConsolidado() {
        return consolidado;
    }

    public void setConsolidado(Boolean consolidado) {
        this.consolidado = consolidado;
    }

    public String getAdministrador_color() {
        return administrador_color;
    }

    public void setAdministrador_color(String administrador_color) {
        this.administrador_color = administrador_color;
    }

    public Double getHoras() {
        return horas;
    }

    public void setHoras(Double horas) {
        this.horas = horas;
    }

    public void calcularDatos() {
        int horasInicio = 0;
        int minutosInicio = 0;
        try {
            horasInicio = Integer.parseInt(getHora_inicio().substring(0, 2));
        } catch (NumberFormatException e) {
            System.err.println("Error: " + e);
        }
        try {
            minutosInicio = Integer.parseInt(getHora_inicio().substring(3, 5));
        } catch (NumberFormatException e) {
            System.err.println("Error: " + e);
        }
        int horasFin = 0;
        int minutosFin = 0;
        try {
            horasFin = Integer.parseInt(getHora_fin().substring(0, 2));
        } catch (NumberFormatException e) {
            System.err.println("Error: " + e);
        }
        try {
            minutosFin = Integer.parseInt(getHora_fin().substring(3, 5));
        } catch (NumberFormatException e) {
            System.err.println("Error: " + e);
        }
        int horasEfectivas = horasFin - horasInicio;
        int minutosEfectivas = minutosFin - minutosInicio;
        while (minutosEfectivas < 0) {
            minutosEfectivas += 60;
            horasEfectivas--;
        }
        String horasEfect = String.valueOf(horasEfectivas);
        String minutosEfect = String.valueOf(minutosEfectivas);
        
        if (horasEfect.length() < 2) {
            horasEfect = "0" + horasEfect;
        }
        if (minutosEfect.length() < 2) {
            minutosEfect = "0" + minutosEfect;
        }
        setCantidad_hora(horasEfect + ":" + minutosEfect);
        double horasTotales = horasEfectivas + minutosEfectivas / 60;
        setHoras(horasTotales);
    }

    @Override
    public String toString() {
        return "Planilla Hora Detalle";
    }
}

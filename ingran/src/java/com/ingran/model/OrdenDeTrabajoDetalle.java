package com.ingran.model;

public class OrdenDeTrabajoDetalle {

    private Integer id;
    private Laudo laudo;
    private Double cantidad;
    private Double precio_unitario;
    private Double cantidad_avanzada;
    private Double subtotal;
    private Fase fase;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Laudo getLaudo() {
        return laudo;
    }

    public void setLaudo(Laudo laudo) {
        this.laudo = laudo;
    }

    public Double getCantidad() {
        return cantidad;
    }

    public void setCantidad(Double cantidad) {
        this.cantidad = cantidad;
    }

    public Double getPrecio_unitario() {
        return precio_unitario;
    }

    public void setPrecio_unitario(Double precio_unitario) {
        this.precio_unitario = precio_unitario;
    }

    public Double getCantidad_avanzada() {
        return cantidad_avanzada;
    }

    public void setCantidad_avanzada(Double cantidad_avanzada) {
        this.cantidad_avanzada = cantidad_avanzada;
    }
    
    public Double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }

    public Fase getFase() {
        return fase;
    }

    public void setFase(Fase fase) {
        this.fase = fase;
    }
}

package com.ingran.model.reportes;

public class CatorcenaCargada {

    private Integer id;
    private String fecha;
    private String dui;
    private String nombre;
    private String cargo;
    private String cliente;
    private String centro_de_costo;
    private String fase;
    private String descripcion_de_fase;
    private String hora_inicio;
    private String hora_fin;
    private String tipo_de_hora;
    private String cantidad_de_horas;
    private String ingeniero_estado;
    private Integer id_detalle;
    private Boolean enviado;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getDui() {
        return dui;
    }

    public void setDui(String dui) {
        this.dui = dui;
    }

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

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getCentro_de_costo() {
        return centro_de_costo;
    }

    public void setCentro_de_costo(String centro_de_costo) {
        this.centro_de_costo = centro_de_costo;
    }

    public String getFase() {
        return fase;
    }

    public void setFase(String fase) {
        this.fase = fase;
    }

    public String getDescripcion_de_fase() {
        return descripcion_de_fase;
    }

    public void setDescripcion_de_fase(String descripcion_de_fase) {
        this.descripcion_de_fase = descripcion_de_fase;
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

    public String getTipo_de_hora() {
        return tipo_de_hora;
    }

    public void setTipo_de_hora(String tipo_de_hora) {
        this.tipo_de_hora = tipo_de_hora;
    }

    public String getCantidad_de_horas() {
        return cantidad_de_horas;
    }

    public void setCantidad_de_horas(String cantidad_de_horas) {
        this.cantidad_de_horas = cantidad_de_horas;
    }

    public String getIngeniero_estado() {
        return ingeniero_estado;
    }

    public void setIngeniero_estado(String ingeniero_estado) {
        this.ingeniero_estado = ingeniero_estado;
    }

    public Integer getId_detalle() {
        return id_detalle;
    }

    public void setId_detalle(Integer id_detalle) {
        this.id_detalle = id_detalle;
    }

    public Boolean getEnviado() {
        return enviado;
    }

    public void setEnviado(Boolean enviado) {
        this.enviado = enviado;
    }
}

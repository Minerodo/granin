package com.ingran.model;

public class ProyectoHorario {

    private int id_horario;
    private CentroDeCosto centro_de_costo;
    private String hora_entrada;
    private String hora_salida;
    private int tiempo_receso_horas;
    private int tiempo_receso_minutos;
    private String horas_efectivas;

    public int getId_horario() {
        return id_horario;
    }

    public void setId_horario(int id_horario) {
        this.id_horario = id_horario;
    }

    public CentroDeCosto getCentro_de_costo() {
        return centro_de_costo;
    }

    public void setCentro_de_costo(CentroDeCosto centro_de_costo) {
        this.centro_de_costo = centro_de_costo;
    }

    public String getHora_entrada() {
        return hora_entrada;
    }

    public void setHora_entrada(String hora_entrada) {
        this.hora_entrada = hora_entrada;
    }

    public String getHora_salida() {
        return hora_salida;
    }

    public void setHora_salida(String hora_salida) {
        this.hora_salida = hora_salida;
    }

    public int getTiempo_receso_horas() {
        return tiempo_receso_horas;
    }

    public void setTiempo_receso_horas(int tiempo_receso_horas) {
        this.tiempo_receso_horas = tiempo_receso_horas;
    }

    public int getTiempo_receso_minutos() {
        return tiempo_receso_minutos;
    }

    public void setTiempo_receso_minutos(int tiempo_receso_minutos) {
        this.tiempo_receso_minutos = tiempo_receso_minutos;
    }

    public String getHoras_efectivas() {
        return horas_efectivas;
    }

    public void setHoras_efectivas(String horas_efectivas) {
        this.horas_efectivas = horas_efectivas;
    }

    public ProyectoHorario() {
    }

    public ProyectoHorario(int id) {
        this.id_horario = id;
    }

    @Override
    public String toString() {
        return getCentro_de_costo().getDescripcion();
    }
}

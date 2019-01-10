package com.ingran.model;

import java.util.ArrayList;
import java.util.List;

public class PlanillaHoraEncabezado extends ProyectoHorario {

    private int id;
    private Usuario bodeguero;
    private Catorcena catorcena;
    private String fecha;
    private Cliente cliente;
    private CentroDeCosto centro_de_costo;
    private Empleado empleado;
    private Boolean enviado;
    private List<PlanillaHoraDetalle> planilla_hora_detalle;
    private String cantidad_de_horas;
    private Double horas;

    public PlanillaHoraEncabezado() {
        planilla_hora_detalle = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Usuario getBodeguero() {
        return bodeguero;
    }

    public void setBodeguero(Usuario bodeguero) {
        this.bodeguero = bodeguero;
    }

    public Catorcena getCatorcena() {
        return catorcena;
    }

    public void setCatorcena(Catorcena catorcena) {
        this.catorcena = catorcena;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public CentroDeCosto getCentro_de_costo() {
        return centro_de_costo;
    }

    public void setCentro_de_costo(CentroDeCosto centro_de_costo) {
        this.centro_de_costo = centro_de_costo;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public Boolean getEnviado() {
        return enviado;
    }

    public void setEnviado(Boolean enviado) {
        this.enviado = enviado;
    }

    public List<PlanillaHoraDetalle> getPlanilla_hora_detalle() {
        return planilla_hora_detalle;
    }

    public void setPlanilla_hora_detalle(List<PlanillaHoraDetalle> planilla_hora_detalle) {
        this.planilla_hora_detalle = planilla_hora_detalle;
    }

    public String getCantidad_de_horas() {
        return cantidad_de_horas;
    }

    public void setCantidad_de_horas(String cantidad_de_horas) {
        this.cantidad_de_horas = cantidad_de_horas;
    }

    public Double getHoras() {
        return horas;
    }

    public void setHoras(Double horas) {
        this.horas = horas;
    }

    public void calcularDatos() {
        int horasEntrada = 0;
        int minutosEntrada = 0;
        try {
            horasEntrada = Integer.parseInt(getHora_entrada().substring(0, 2));
        } catch (NumberFormatException e) {
            System.err.println("Error: " + e);
        }
        try {
            minutosEntrada = Integer.parseInt(getHora_entrada().substring(3, 5));
        } catch (NumberFormatException e) {
            System.err.println("Error: " + e);
        }
        int horasSalida = 0;
        int minutosSalida = 0;
        try {
            horasSalida = Integer.parseInt(getHora_salida().substring(0, 2));
        } catch (NumberFormatException e) {
            System.err.println("Error: " + e);
        }
        try {
            minutosSalida = Integer.parseInt(getHora_salida().substring(3, 5));
        } catch (NumberFormatException e) {
            System.err.println("Error: " + e);
        }
        int horasEfectivas = horasSalida - horasEntrada - getTiempo_receso_horas();
        int minutosEfectivas = minutosSalida - minutosEntrada - getTiempo_receso_minutos();
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
        setHoras_efectivas(horasEfect + ":" + minutosEfect);
        calcularCantidadDeHoras();
    }

    private void calcularCantidadDeHoras() {
        int horas = 0;
        int minutos = 0;
        for (PlanillaHoraDetalle phd : getPlanilla_hora_detalle()) {
            int hora = 0;
            int minuto = 0;
            try {
                hora = Integer.parseInt(phd.getHora_fin().substring(0, 2)) - Integer.parseInt(phd.getHora_inicio().substring(0, 2));
            } catch (NumberFormatException ex) {

            }
            try {
                minuto = Integer.parseInt(phd.getHora_fin().substring(3, 5)) - Integer.parseInt(phd.getHora_inicio().substring(3, 5));
            } catch (NumberFormatException ex) {

            }
            while (minuto < 0) {
                minuto += 60;
                hora--;
            }
            while (minuto >= 60) {
                minuto -= 60;
                hora++;
            }
            horas += hora;
            minutos += minuto;
        }
        while (minutos < 0) {
                minutos += 60;
                horas--;
            }
            while (minutos >= 60) {
                minutos -= 60;
                horas++;
            }
        String horasTotales = String.valueOf(horas);
        String minutosTotales = String.valueOf(minutos);
        if (horasTotales.length() < 2) {
            horasTotales = "0" + horasTotales;
        }
        if (minutosTotales.length() < 2) {
            minutosTotales = "0" + minutosTotales;
        }
        setCantidad_de_horas(horasTotales + ":" + minutosTotales);
        double horasTotale = horas + minutos / 60;
        setHoras(horasTotale);
    }

    @Override
    public String toString() {
        return "Planilla Hora Encabezado";
    }
}

package com.ingran.util;

import com.ingran.data.ProyectoHorarioDB;
import com.ingran.model.Catorcena;
import com.ingran.model.CentroDeCosto;
import com.ingran.model.Cliente;
import com.ingran.model.Concepto;
import com.ingran.model.Empleado;
import com.ingran.model.Fase;
import com.ingran.model.PlanillaHoraDetalle;
import com.ingran.model.PlanillaHoraEncabezado;
import com.ingran.model.Usuario;
import java.sql.Date;

public class prueba {

    public static void main(String[] args) {
        Empleado empleado = new Empleado();
        empleado.setEmpleado("01074426-6");
        
        Catorcena catorcena = new Catorcena(15);
        
        CentroDeCosto cdc = new CentroDeCosto();
        cdc.setCentro_de_costo("005.082");
        
        Date fecha = Fecha.convertitTextoADate("04-06-2018");
        
        Usuario bodeguero = new Usuario(18);
        
        Fase fase = new Fase();
        fase.setFase("03.01.02.00");
        
        Cliente cliente = new Cliente();
        cliente.setCliente("20500-1");
        
        Concepto concepto = new Concepto();
        concepto.setDescripcion("HORA NORMAL DIURNA PROYECTOS");
        
        PlanillaHoraDetalle phd  = new PlanillaHoraDetalle();
        phd.setFase(fase);
        phd.setConcepto(concepto);
        phd.setHora_inicio("07:00");
        phd.setHora_fin("12:00");
        phd.setConcepto(concepto);
        phd.calcularDatos();
        
        PlanillaHoraDetalle phd1  = new PlanillaHoraDetalle();
        phd1.setFase(fase);
        phd1.setConcepto(concepto);
        phd1.setHora_inicio("13:00");
        phd1.setHora_fin("16:00");
        phd1.setConcepto(concepto);
        phd1.calcularDatos();
        
        PlanillaHoraEncabezado phe = new PlanillaHoraEncabezado();
        phe.setBodeguero(bodeguero);
        phe.setCatorcena(catorcena);
        phe.setCliente(cliente);
        phe.setEmpleado(empleado);
        phe.setCentro_de_costo(cdc);
        phe.setFecha(fecha);
        phe.getPlanilla_hora_detalle().add(phd);
        phe.getPlanilla_hora_detalle().add(phd1);
        
        phe.setHora_entrada("07:00");
        phe.setHora_salida("16:00");
        phe.setTiempo_receso_horas(1);
        phe.setTiempo_receso_minutos(0);
        phe.calcularDatos();
        
        System.out.println(ProyectoHorarioDB.agregarProyectoHorarioPlanillaEncabezado(phe));
    }
    
}

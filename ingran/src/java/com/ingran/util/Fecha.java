package com.ingran.util;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Fecha {

    public static Date convertitTextoADate(String fecha) {
        Date fech = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", new Locale("es", "SV"));
            java.util.Date fechaDate = sdf.parse(fecha);
            fech = new Date(fechaDate.getTime());
        } catch (ParseException ex) {
            Logger.getLogger(Fecha.class.getName()).log(Level.SEVERE, null, ex);
        }
        return fech;
    }

    public static String convertirDateATexto(Date fecha) {
        DateFormat df = new SimpleDateFormat("dd-MMMM-yyyy", new Locale("es", "SV"));
        return df.format(fecha);
    }

    public static String convertirFecha(String fecha) {
        String fecha_nueva = "";
        if (fecha.substring(4, 5).equals("-") && fecha.substring(7, 8).equals("-")) {
            fecha_nueva = fecha.substring(8, 10) + "-" + fecha.substring(5, 7) + "-" + fecha.substring(0, 4);
        }
        if (fecha.substring(2, 3).equals("-") && fecha.substring(5, 6).equals("-")) {
            fecha_nueva = fecha.substring(6, 10) + "-" + fecha.substring(3, 5) + "-" + fecha.substring(0, 2);
        }
        return fecha_nueva;
    }

    public static String obtenerAnio(String fecha) {
        String anio = "";
        if (fecha.substring(4, 5).equals("-") && fecha.substring(7, 8).equals("-")) {
            anio = fecha.substring(0, 4);
        }
        if (fecha.substring(2, 3).equals("-") && fecha.substring(5, 6).equals("-")) {
            anio = fecha.substring(6, 10);
        }
        return anio;
    }

    public static String obtenerMes(String fecha) {
        String mes = "";
        if (fecha.substring(4, 5).equals("-") && fecha.substring(7, 8).equals("-")) {
            mes = fecha.substring(5, 7);
        }
        if (fecha.substring(2, 3).equals("-") && fecha.substring(5, 6).equals("-")) {
            mes = fecha.substring(3, 5);
        }
        return mes;
    }

    public static String obtenerDia(String fecha) {
        String dia = "0";
        if (fecha.substring(4, 5).equals("-") && fecha.substring(7, 8).equals("-")) {
            dia = fecha.substring(8, 10);
        }
        if (fecha.substring(2, 3).equals("-") && fecha.substring(5, 6).equals("-")) {
            dia = fecha.substring(0, 2);
        }
        return dia;
    }
}

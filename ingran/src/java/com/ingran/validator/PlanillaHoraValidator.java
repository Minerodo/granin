package com.ingran.validator;

import com.ingran.model.PlanillaHoraDetalle;
import com.ingran.model.PlanillaHoraEncabezado;
import java.util.List;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class PlanillaHoraValidator implements Validator {

    @Override
    public boolean supports(Class<?> type) {
        return PlanillaHoraEncabezado.class.isAssignableFrom(type);
    }

    @Override
    public void validate(Object o, Errors errors) {
        PlanillaHoraEncabezado phe = (PlanillaHoraEncabezado) o;

        phe.calcularDatos();
        if (!phe.getHoras_efectivas().equals(phe.getCantidad_de_horas())) {
            errors.rejectValue("cantidad_de_horas", "cantidad_de_horas.incorrect", "La cantidad de horas ingresadas no coincide con el horario indicado");
        }
        if (validarHorasDetalles(phe)) {
            errors.rejectValue("bodeguero.empleado.nombre", "bodeguero.empleado.nombre.incorrect", "Las horas de las Fases son incorrectas");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "fecha", "required.fecha", "El campo es Obligatorio.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "empleado.nombre", "required.emplado.nombre", "El campo es Obligatorio.");
    }

    private boolean validarHorasDetalles(PlanillaHoraEncabezado phe) {
        boolean error = false;
        List<PlanillaHoraDetalle> phd = phe.getPlanilla_hora_detalle();
        for (int a = 0; a < phd.size(); a++) {
            if (a + 1 < phd.size()) {
                for (int b = a + 1; b < phd.size(); b++) {
                    PlanillaHoraDetalle phda = phe.getPlanilla_hora_detalle().get(a);
                    PlanillaHoraDetalle phdb = phe.getPlanilla_hora_detalle().get(b);
                    int ahi = 0;
                    try {
                        ahi = Integer.parseInt(phda.getHora_inicio().substring(0, 2));
                    } catch (NumberFormatException ex) {
                    }
                    int ami = 0;
                    try {
                        ami = Integer.parseInt(phda.getHora_inicio().substring(3, 5));
                    } catch (NumberFormatException ex) {
                    }
                    int ahf = 0;
                    try {
                        ahf = Integer.parseInt(phda.getHora_fin().substring(0, 2));
                    } catch (NumberFormatException ex) {
                    }
                    int amf = 0;
                    try {
                        amf = Integer.parseInt(phda.getHora_fin().substring(3, 5));
                    } catch (NumberFormatException ex) {
                    }

                    int bhi = 0;
                    try {
                        bhi = Integer.parseInt(phdb.getHora_inicio().substring(0, 2));
                    } catch (NumberFormatException ex) {
                    }
                    int bmi = 0;
                    try {
                        bmi = Integer.parseInt(phdb.getHora_inicio().substring(3, 5));
                    } catch (NumberFormatException ex) {
                    }
                    int bhf = 0;
                    try {
                        bhf = Integer.parseInt(phdb.getHora_fin().substring(0, 2));
                    } catch (NumberFormatException ex) {
                    }
                    int bmf = 0;
                    try {
                        bmf = Integer.parseInt(phdb.getHora_fin().substring(3, 5));
                    } catch (NumberFormatException ex) {
                    }

                    if (((ahi >= bhf && ami >= bmf) && (ahf >= bhf && amf >= bmf)) || (bhi >= ahf && bmi >= amf) && (bhf >= ahf && bmf >= amf)) {

                    } else {
                        error = true;
                        a = phd.size();
                    }
                }
            }
        }
        return error;
    }
}

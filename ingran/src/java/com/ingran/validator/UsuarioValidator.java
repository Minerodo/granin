package com.ingran.validator;

import com.ingran.model.Usuario;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class UsuarioValidator implements Validator {

    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    private Pattern pattern;
    private Matcher matcher;

    @Override
    public boolean supports(Class<?> type) {
        return Usuario.class.isAssignableFrom(type);
    }

    @Override
    public void validate(Object object, Errors errors) {
        Usuario usuario = (Usuario) object;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "usuario", "required.usuario", "El campo Usuario es Obligatorio.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "correo", "required.correo", "El campo Correo electrónico es Obligatorio.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "telefono", "required.telefono", "El campo Teléfono es Obligatorio.");
        //ValidationUtils.rejectIfEmptyOrWhitespace(errors, "contrasenia", "required.contrasenia", "El campo Contraseña es Obligatorio.");
        
        if (!(usuario.getCorreo() != null && usuario.getCorreo().isEmpty())) {
            this.pattern = Pattern.compile(EMAIL_PATTERN);
            this.matcher = pattern.matcher(usuario.getCorreo());
            if (!matcher.matches()) {
                errors.rejectValue("correo", "correo.incorrect", "El Correo electrónico " + usuario.getCorreo() + " no es válido");
            }
        }
    }
}

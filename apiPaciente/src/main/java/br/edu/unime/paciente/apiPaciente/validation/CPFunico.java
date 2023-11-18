package br.edu.unime.paciente.apiPaciente.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = CPFunicoValidador.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CPFunico {
    String message() default "CPF já existe no banco de dados";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

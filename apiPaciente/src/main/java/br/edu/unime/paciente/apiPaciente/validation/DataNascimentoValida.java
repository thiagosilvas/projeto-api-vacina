package br.edu.unime.paciente.apiPaciente.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DataNascimentoValidador.class)
public @interface DataNascimentoValida {
    String message() default "A data de nascimento deve estar no passado e apenas até o ano 1900";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}



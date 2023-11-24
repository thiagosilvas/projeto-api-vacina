package br.edu.unime.paciente.apiPaciente.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class DataNascimentoValidador implements ConstraintValidator<DataNascimentoValida, LocalDate> {
    @Override
    public void initialize(DataNascimentoValida constraintAnnotation){

    }

    @Override
    public boolean isValid(LocalDate dataNascimento, ConstraintValidatorContext context) {
        if ( dataNascimento == null){
            return false;
        }

        LocalDate dataAtual =  LocalDate.now();

        LocalDate limiteSuperior = LocalDate.of(1900, 1, 1);

        return !dataNascimento.isAfter(dataAtual) && !dataNascimento.isBefore(limiteSuperior);
    }
}

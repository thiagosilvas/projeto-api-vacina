package br.edu.unime.paciente.apiPaciente.validation;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import br.edu.unime.paciente.apiPaciente.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class CPFunicoValidador implements ConstraintValidator<CPFunico, String> {

    private final PacienteRepository pacienteRepository;

    @Autowired
    private HttpServletRequest request;


    public void setHttpServletRequest(HttpServletRequest request) {
        this.request = request;
    }

    public CPFunicoValidador(PacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    @Override
    public void initialize(CPFunico constraintAnnotation) {
    }

    @Override
    public boolean isValid(String cpf, ConstraintValidatorContext context) {
        if (isPostRequest()) {
            return !pacienteRepository.existsByCpf(cpf);
        }
        return true;
    }

    private boolean isPostRequest() {
        return request != null &&
                "POST".equalsIgnoreCase(request.getMethod());
    }

}
package com.registroVacinacao.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;


@Data
@Document(collection = "registro")
public class RegistroVacinacao {
    @Id
    private String id;
    @NotBlank(message = "Nome não pode estar em branco.")
    private String nomeProfissional;
    @NotBlank(message = "Sobrenome não pode estar em branco.")
    private String sobrenomeProfissional;
    @NotNull(message = "Data não pode estar em branco.")
    private LocalDate dataVacinacao;
    @NotBlank(message = "CPF não pode estar em branco.")
    private String cpfProfissional;
    @NotBlank(message = "Paciente não pode estar em branco.")
    private String identificacaoPaciente;
    @NotBlank(message = "Vacina não pode estar em branco.")
    private String identificacaoVacina;
    @NotBlank(message = "Dose não pode estar em branco.")
    private String identificacaoDose;
}

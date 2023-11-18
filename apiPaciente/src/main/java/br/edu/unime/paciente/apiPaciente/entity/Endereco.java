package br.edu.unime.paciente.apiPaciente.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.validation.constraints.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Endereco {

    @NotBlank(message = "Logradouro não pode ser nulo e não pode estar em branco.")
    private String Logradouro;

    @NotNull(message = "Numero não pode ser nulo e não pode estar em branco.")
    @Min(value = 1, message = "Número deve ser no mínimo um.")
    private Integer Numero;

    @NotBlank(message = "Bairro não pode ser nulo e não pode estar em brancoo.")
    private String Bairro;

    @NotBlank(message = "CEP não pode ser nulo e não pode estar em branco.")
    @Pattern(regexp = "\\d{8}", message = "CEP deve conter 8 dígitos.")
    private String Cep;

    @NotBlank(message = "Municipio não pode ser nulo e não pode estar em branco.")
    private String Municipio;

    @NotBlank(message = "Estado não pode ser nulo e não pode estar em branco.")
    @Size(min = 2, max = 2, message = "Estado precisa ser uma sigla de duas letras.")
    private String Estado;
}

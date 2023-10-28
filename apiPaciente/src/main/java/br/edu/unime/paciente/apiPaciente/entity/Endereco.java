package br.edu.unime.paciente.apiPaciente.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Endereco {

    @NotBlank(message = "Logradouro não pode estar em branco.")
    private String Logradouro;

    @NotNull(message = "Numero não pode estar em branco.")
    @Min(value = 1, message = "Número deve ser no mínimo um.")
    private Integer Numero;

    @NotBlank(message = "Bairro não pode estar em branco.")
    private String Bairro;

    @NotBlank(message = "CEP não pode estar em branco.")
    @Pattern(regexp = "\\d{8}", message = "CEP deve conter 8 dígitos.")
    private String CEP;

    @NotBlank(message = "Municipio não pode estar em branco.")
    private String Municipio;

    @NotBlank(message = "Estado não pode estar em branco.")
    private String Estado;



}

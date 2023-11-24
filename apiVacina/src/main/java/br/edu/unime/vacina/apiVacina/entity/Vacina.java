package br.edu.unime.vacina.apiVacina.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Vacina {

    @Id
    private String id;

    @NotBlank(message = "Fabricante não pode estar em branco.")
    @NotNull(message = "Fabricante não pode estar em nulo.")
    @Size(min = 3, max = 100, message = "O Fabricante deve ter entre 3 a 100 digitos")
    private String fabricante;

    @NotBlank(message = "Lote não pode estar em branco!")
    @NotNull(message = "Lote não pode estar nulo!")
    @Size(min = 3, max = 100, message = "O Lote deve ter entre 3 a 100 digitos!")
    private String lote;

    @NotNull(message = "A data de validade deve ser inserida!")
    private LocalDate dataDeValidade;

    @NotNull(message = "O número de doses deve ser inserido!")
    private Integer numeroDeDoses;


    private Integer intervaloDeDoses;
}

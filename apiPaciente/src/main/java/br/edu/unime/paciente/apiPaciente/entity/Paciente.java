package br.edu.unime.paciente.apiPaciente.entity;

import br.edu.unime.paciente.apiPaciente.validation.CPFunico;
import br.edu.unime.paciente.apiPaciente.validation.DataNascimentoValida;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.data.annotation.Id;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Paciente {

    @Id
    private String id;
    @NotBlank(message = "Nome não pode ser nulo e não pode estar em branco.")
    @Size(min = 2, max = 100, message = "O nome deve ter entre 2 a 100 digitos.")
    private String nome;

    @Size(min = 2, max = 100, message = "O sobrenome deve ter entre 2 a 100 digitos.")
    @NotBlank(message = "Sobrenome não ser nulo e não pode estar em branco.")
    private String sobrenome;

    @CPF
    @NotBlank(message = "CPF não pode ser nulo e não pode estar em branco.")
    @CPFunico
    private String cpf;

    @NotNull(message = "Data não pode ser nulo e não pode estar em branco.")
    @DataNascimentoValida
    private LocalDate dataDeNascimento;

    @NotBlank(message = "Genero não pode ser nulo e não pode estar em branco.")
    @Pattern(regexp = "^(Masculino|Feminino)$", message = "O Genero deve ou ser 'Masculino' ou 'Feminino'")
    private String genero;

    @NotEmpty(message = "Contatos não pode ser nulo e não pode estar em branco.")
    private List<String> contatos;

    @Valid
    @NotEmpty(message = "Enderecos não pode ser nulo e não pode estar em branco.")
    private List<Endereco> enderecos;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Endereco> getEnderecos() {
        return enderecos;
    }

    public void setEnderecos(List<Endereco> enderecos) {
        this.enderecos = enderecos;
    }
}

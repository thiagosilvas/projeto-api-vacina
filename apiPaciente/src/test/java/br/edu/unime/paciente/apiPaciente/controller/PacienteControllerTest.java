package br.edu.unime.paciente.apiPaciente.controller;

import br.edu.unime.paciente.apiPaciente.entity.Endereco;
import br.edu.unime.paciente.apiPaciente.entity.Paciente;
import br.edu.unime.paciente.apiPaciente.repository.PacienteRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import br.edu.unime.paciente.apiPaciente.service.PacienteService;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
public class PacienteControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    PacienteService pacienteService;

    @MockBean
    PacienteRepository pacienteRepository;

    @Test
    @DisplayName("Deve ser possivél obter todos os pacientes cadastrados")
    public void testeDeObterTodosPacientes() throws Exception {

        //Arrange
        Paciente paciente = new Paciente();
        paciente.setNome("Antonio Vitor");
        paciente.setSobrenome("Guimaraes");
        paciente.setDataDeNascimento(LocalDate.of(2000, 9, 9));
        paciente.setCpf("46602318012");
        paciente.setContatos(Collections.singletonList("92685988"));
        paciente.setGenero("Masculino");

        Endereco endereco = new Endereco();
        endereco.setLogradouro("Vila Alto de Amaralina");
        endereco.setCep("41905586");
        endereco.setNumero(10);
        endereco.setBairro( "Nordeste");
        endereco.setMunicipio("Salvador");
        endereco.setEstado("BA");

        paciente.setEnderecos(Collections.singletonList(endereco));

        Paciente paciente2 = new Paciente();
        paciente2.setNome("Carol");
        paciente2.setSobrenome("Guimaraes");
        paciente2.setDataDeNascimento(LocalDate.of(1999, 4, 12));
        paciente2.setCpf("20785019057");
        paciente2.setContatos(Collections.singletonList("23372466"));
        paciente2.setGenero("Feminino");

        Endereco endereco2 = new Endereco();
        endereco2.setLogradouro("Rua A");
        endereco2.setCep("45608180");
        endereco2.setNumero(10);
        endereco2.setBairro( "Vila Anália");
        endereco2.setMunicipio("Itabuna");
        endereco2.setEstado("BA");

        paciente2.setEnderecos(Collections.singletonList(endereco2));

        List<Paciente> pacientes = Arrays.asList(paciente, paciente2);

        //Mock
        when(pacienteService.obterTodos()).thenReturn(pacientes);

        //Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/pacientes"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].nome").value(paciente.getNome()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].sobrenome").value(paciente.getSobrenome()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].genero").value(paciente.getGenero()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].cpf").value(paciente.getCpf()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].dataDeNascimento").value(String.valueOf(paciente.getDataDeNascimento())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].contatos[0]").value(paciente.getContatos().get(0)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].enderecos[0].logradouro").value(paciente.getEnderecos().get(0).getLogradouro()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].enderecos[0].municipio").value(paciente.getEnderecos().get(0).getMunicipio()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].enderecos[0].numero").value(paciente.getEnderecos().get(0).getNumero()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].enderecos[0].estado").value(paciente.getEnderecos().get(0).getEstado()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].enderecos[0].bairro").value(paciente.getEnderecos().get(0).getBairro()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].enderecos[0].cep").value(paciente.getEnderecos().get(0).getCep()));

        //Verify
        verify(pacienteService, times(2)).obterTodos();

    }

    @Test
    @DisplayName("Deve retornar um array vazio quando busca não retornar pacientes")
    public void testesObterListagemEmBranco() throws Exception {

        //Arrange

        List<Paciente> pacientes = new ArrayList<>();

        //Mock
        when(pacienteService.obterTodos()).thenReturn(pacientes);

        //Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/pacientes"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(0));

        //Verify
        verify(pacienteService, times(2)).obterTodos();
    }

    @Test
    @DisplayName("Deve ser possivél obter o paciente pelo ID")
    public void testeObterPacientePeloId() throws Exception {

        //Arrange
        Paciente paciente = new Paciente();
        paciente.setId("12345");
        paciente.setNome("Antonio Vitor");
        paciente.setSobrenome("Guimaraes");
        paciente.setDataDeNascimento(LocalDate.of(2000, 9, 9));
        paciente.setCpf("02691076067");
        paciente.setContatos(Collections.singletonList("92685988"));
        paciente.setGenero("Masculino");

        Endereco endereco = new Endereco();
        endereco.setLogradouro("Vila Alto de Amaralina");
        endereco.setCep("41905586");
        endereco.setNumero(10);
        endereco.setBairro( "Nordeste");
        endereco.setMunicipio("Salvador");
        endereco.setEstado("BA");

        paciente.setEnderecos(Collections.singletonList(endereco));

        List<Paciente> pacientes = Arrays.asList(paciente);

        //Mock
        when(pacienteService.encontrarPaciente("12345")).thenReturn(paciente);

        //Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/pacientes/" + paciente.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(paciente.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nome").value(paciente.getNome()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.sobrenome").value(paciente.getSobrenome()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.genero").value(paciente.getGenero()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.cpf").value(paciente.getCpf()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.dataDeNascimento").value(String.valueOf(paciente.getDataDeNascimento())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.contatos[0]").value(paciente.getContatos().get(0)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.enderecos[0].logradouro").value(paciente.getEnderecos().get(0).getLogradouro()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.enderecos[0].municipio").value(paciente.getEnderecos().get(0).getMunicipio()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.enderecos[0].numero").value(paciente.getEnderecos().get(0).getNumero()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.enderecos[0].estado").value(paciente.getEnderecos().get(0).getEstado()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.enderecos[0].bairro").value(paciente.getEnderecos().get(0).getBairro()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.enderecos[0].cep").value(paciente.getEnderecos().get(0).getCep()));

        //Verify
        verify(pacienteService, times(1)).encontrarPaciente(paciente.getId());

    }

    @Test
    @DisplayName("Deve lançar uma ResourceNotFoundException ao buscar um ID inexistente")
    public void testeObterPacientePeloIdInexistente() throws Exception {

        //Arrange
        String id = "12312321131311";

        //Mock
        when(pacienteService.encontrarPaciente(id)).thenThrow(ResourceNotFoundException.class);

        //Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/pacientes/" + id))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
        //Verify
        verify(pacienteService, times(1)).encontrarPaciente(id);

    }

    @Test
    @DisplayName("Deve ser possivel adicionar um paciente no banco de dados. ")
    public void testeAdicionarPacienteAoBancoDeDados() throws Exception {

        //Arrange
        Paciente paciente = new Paciente();
        paciente.setId("12345");
        paciente.setNome("Antonio Vitor");
        paciente.setSobrenome("Guimaraes");
        paciente.setDataDeNascimento(LocalDate.of(2000, 9, 9));
        paciente.setCpf("94639685041");
        paciente.setContatos(Collections.singletonList("92685988"));
        paciente.setGenero("Masculino");

        Endereco endereco = new Endereco();
        endereco.setLogradouro("Vila Alto de Amaralina");
        endereco.setCep("41905586");
        endereco.setNumero(10);
        endereco.setBairro( "Nordeste");
        endereco.setMunicipio("Salvador");
        endereco.setEstado("BA");

        paciente.setEnderecos(Collections.singletonList(endereco));

        //Mock
        doNothing().when(pacienteService).inserir(any(Paciente.class));

        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        when(mockRequest.getMethod()).thenReturn("POST");

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, false);
        String pacienteJson = objectMapper.writeValueAsString(paciente);

        //Act & Assert

        mockMvc.perform(MockMvcRequestBuilders.post("/pacientes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(pacienteJson))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(paciente.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nome").value(paciente.getNome()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.sobrenome").value(paciente.getSobrenome()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.genero").value(paciente.getGenero()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.cpf").value(paciente.getCpf()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.dataDeNascimento").value(String.valueOf(paciente.getDataDeNascimento())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.contatos[0]").value(paciente.getContatos().get(0)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.enderecos[0].logradouro").value(paciente.getEnderecos().get(0).getLogradouro()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.enderecos[0].municipio").value(paciente.getEnderecos().get(0).getMunicipio()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.enderecos[0].numero").value(paciente.getEnderecos().get(0).getNumero()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.enderecos[0].estado").value(paciente.getEnderecos().get(0).getEstado()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.enderecos[0].bairro").value(paciente.getEnderecos().get(0).getBairro()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.enderecos[0].cep").value(paciente.getEnderecos().get(0).getCep()));

        //Verify
        verify(pacienteService, times(1)).inserir(any(Paciente.class));


    }
    @Test
    @DisplayName("Deve ser retornar erro quando tenta cadastrar no banco de dados com endereco em branco. ")
    public void testeAdicionarPacienteAoBancoDeDadosComDadosIncompletos() throws Exception {

        //Arrange

        Paciente paciente = new Paciente();
        paciente.setId("12345");
        paciente.setNome("Antonio Vitor");
        paciente.setSobrenome("Guimaraes");
        paciente.setDataDeNascimento(LocalDate.of(2000, 9, 9));
        paciente.setCpf("33203242095");
        paciente.setContatos(Collections.singletonList("92685988"));
        paciente.setGenero("Masculino");



        //Mock
        doNothing().when(pacienteService).inserir(any(Paciente.class));

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, false);
        String pacienteJson = objectMapper.writeValueAsString(paciente);

        //Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/pacientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(pacienteJson))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0]").value("Enderecos não pode ser nulo e não pode estar em branco."));

    }
    @Test
    @DisplayName("Deve se retornar erro ao tentar adicionar um paciente no banco de dados com data de nascimento após do dia de hoje. ")
    public void testeDeveDarErroAoTentarAdicionarPacienteComDataDeNascimentoInvalida() throws Exception {

        //Arrange
        Paciente paciente = new Paciente();
        paciente.setId("12345");
        paciente.setNome("Antonio Vitor");
        paciente.setSobrenome("Guimaraes");
        paciente.setDataDeNascimento(LocalDate.of(2030, 9, 9));
        paciente.setCpf("38105829053");
        paciente.setContatos(Collections.singletonList("92685988"));
        paciente.setGenero("Masculino");

        Endereco endereco = new Endereco();
        endereco.setLogradouro("Vila Alto de Amaralina");
        endereco.setCep("41905586");
        endereco.setNumero(10);
        endereco.setBairro( "Nordeste");
        endereco.setMunicipio("Salvador");
        endereco.setEstado("BA");

        paciente.setEnderecos(Collections.singletonList(endereco));

        //Mock
        doNothing().when(pacienteService).inserir(any(Paciente.class));

        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        when(mockRequest.getMethod()).thenReturn("POST");

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, false);
        String pacienteJson = objectMapper.writeValueAsString(paciente);

        //Act & Assert

        mockMvc.perform(MockMvcRequestBuilders.post("/pacientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(pacienteJson))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0]").value("A data de nascimento deve estar no passado e apenas até o ano 1900"));

    }


    @Test
    @DisplayName("Deve alterar as informações de um paciente já existente no banco de dados. ")
    public void testeAlterarInformacaoPaciente() throws Exception {

        //Arrange
        Paciente paciente = new Paciente();
        paciente.setId("12345");
        paciente.setNome("Antonio Vitor");
        paciente.setSobrenome("Guimaraes");
        paciente.setDataDeNascimento(LocalDate.of(2000, 9, 9));
        paciente.setCpf("60204364035");
        paciente.setContatos(Collections.singletonList("92685988"));
        paciente.setGenero("Masculino");

        Endereco endereco = new Endereco();
        endereco.setLogradouro("Vila Alto de Amaralina");
        endereco.setCep("41905586");
        endereco.setNumero(10);
        endereco.setBairro( "Nordeste");
        endereco.setMunicipio("Salvador");
        endereco.setEstado("BA");

        paciente.setEnderecos(Collections.singletonList(endereco));


        //Mock
        doNothing().when(pacienteService).inserir(any(Paciente.class));

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, false);
        String pacienteJson = objectMapper.writeValueAsString(paciente);

        //Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/pacientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(pacienteJson))
                .andExpect(MockMvcResultMatchers.status().isCreated());

        Paciente pacienteAtualizado = new Paciente();
        pacienteAtualizado = paciente;
        pacienteAtualizado.setNome("Victor");

        String updatedPacienteJson = objectMapper.writeValueAsString(pacienteAtualizado);

        //Mock
        when(pacienteService.atualizar(eq(pacienteAtualizado.getId()), any(Paciente.class))).thenReturn(pacienteAtualizado);

        //Act & Assert

        mockMvc.perform(MockMvcRequestBuilders.put("/pacientes/" + pacienteAtualizado.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(pacienteJson))
                        .andExpect(MockMvcResultMatchers.status().isOk())
                        .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                        .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(pacienteAtualizado.getId()))
                        .andExpect(MockMvcResultMatchers.jsonPath("$.nome").value(pacienteAtualizado.getNome()));

        //Verify
        verify(pacienteService, times(1)).atualizar(eq(pacienteAtualizado.getId()), any(Paciente.class));

    }
    @Test
    @DisplayName("Deve ser retornar erro quando tenta alterar um paciente utilizando um id que não consta no banco de dados.")
    public void testeAlterarInformacaoComIdInvalido() throws Exception {

        //Arrange
        Paciente paciente = new Paciente();
        paciente.setId("12345");
        paciente.setNome("Antonio Vitor");
        paciente.setSobrenome("Guimaraes");
        paciente.setDataDeNascimento(LocalDate.of(2000, 9, 9));
        paciente.setCpf("60204364035");
        paciente.setContatos(Collections.singletonList("92685988"));
        paciente.setGenero("Masculino");

        Endereco endereco = new Endereco();
        endereco.setLogradouro("Vila Alto de Amaralina");
        endereco.setCep("41905586");
        endereco.setNumero(10);
        endereco.setBairro( "Nordeste");
        endereco.setMunicipio("Salvador");
        endereco.setEstado("BA");

        paciente.setEnderecos(Collections.singletonList(endereco));

        //Mock
        doNothing().when(pacienteService).inserir(any(Paciente.class));

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, false);
        String pacienteJson = objectMapper.writeValueAsString(paciente);

        //Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/pacientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(pacienteJson))
                .andExpect(MockMvcResultMatchers.status().isCreated());

        Paciente pacienteAtualizado = new Paciente();
        pacienteAtualizado = paciente;
        pacienteAtualizado.setNome("Victor");

        String updatedPacienteJson = objectMapper.writeValueAsString(pacienteAtualizado);

        //Mock
        when(pacienteService.atualizar(eq(pacienteAtualizado.getId()), any(Paciente.class))).thenReturn(pacienteAtualizado);

        //Act & Assert

        mockMvc.perform(MockMvcRequestBuilders.put("/pacientes/" + "idinvalido")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(pacienteJson))
                .andExpect(MockMvcResultMatchers.status().isNotFound());

    }
    @Test
    @DisplayName("Deve ser retornar erro quando tenta alterar um paciente no banco de dados com informação em branco. ")
    public void testeTentarAlterarPacienteComInformacaoEmBranco() throws Exception {

        //Arrange
        Paciente paciente = new Paciente();
        paciente.setId("12345");
        paciente.setNome("Antonio Vitor");
        paciente.setSobrenome("Guimaraes");
        paciente.setDataDeNascimento(LocalDate.of(2000, 9, 9));
        paciente.setCpf("00075973049");
        paciente.setContatos(Collections.singletonList("92685988"));
        paciente.setGenero("Masculino");

        Endereco endereco = new Endereco();
        endereco.setLogradouro("Vila Alto de Amaralina");
        endereco.setCep("41905586");
        endereco.setNumero(10);
        endereco.setBairro( "Nordeste");
        endereco.setMunicipio("Salvador");
        endereco.setEstado("BA");

        paciente.setEnderecos(Collections.singletonList(endereco));

        //Mock
        doNothing().when(pacienteService).inserir(any(Paciente.class));

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, false);
        String pacienteJson = objectMapper.writeValueAsString(paciente);

        //Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/pacientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(pacienteJson))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));

        //Arrange

        Paciente pacienteAtualizado = new Paciente();
        pacienteAtualizado.setId("12345");
        pacienteAtualizado.setNome("Antonio Vitor");
        pacienteAtualizado.setSobrenome("Guimaraes");
        pacienteAtualizado.setDataDeNascimento(LocalDate.of(2000, 9, 9));
        pacienteAtualizado.setCpf("00075973049");
        pacienteAtualizado.setContatos(Collections.singletonList("92685988"));
        pacienteAtualizado.setGenero("Masculino");

        //Mock
        when(pacienteService.atualizar(eq(pacienteAtualizado.getId()), any(Paciente.class))).thenReturn(pacienteAtualizado);

        String updatedPacienteJson = objectMapper.writeValueAsString(pacienteAtualizado);

        //Act & Assert

        mockMvc.perform(MockMvcRequestBuilders.put("/pacientes/" + paciente.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedPacienteJson))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0]").value("Enderecos não pode ser nulo e não pode estar em branco."));

    }
    @Test
    @DisplayName("Deve deletar um paciente existente do banco de dados. ")
    public void testeDeletarUmPaciente() throws Exception {

        //Arrange
        Paciente paciente = new Paciente();
        paciente.setId("12345");
        paciente.setNome("Antonio Vitor");
        paciente.setSobrenome("Guimaraes");
        paciente.setDataDeNascimento(LocalDate.of(2000, 9, 9));
        paciente.setCpf("29229920045");
        paciente.setContatos(Collections.singletonList("92685988"));
        paciente.setGenero("Masculino");

        Endereco endereco = new Endereco();
        endereco.setLogradouro("Vila Alto de Amaralina");
        endereco.setCep("41905586");
        endereco.setNumero(10);
        endereco.setBairro( "Nordeste");
        endereco.setMunicipio("Salvador");
        endereco.setEstado("BA");

        paciente.setEnderecos(Collections.singletonList(endereco));


        //Mock
        doNothing().when(pacienteService).inserir(any(Paciente.class));

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, false);
        String pacienteJson = objectMapper.writeValueAsString(paciente);

        //Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/pacientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(pacienteJson))
                .andExpect(MockMvcResultMatchers.status().isCreated());

        //Mock
        Mockito.when(pacienteService.encontrarPaciente(ArgumentMatchers.anyString())).thenReturn(paciente);
        doNothing().when(pacienteService).deletar(eq(paciente.getId()));

        //Act & Assert

        mockMvc.perform(MockMvcRequestBuilders.delete("/pacientes/" + paciente.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(MockMvcResultMatchers.status().isNoContent());
        //Verify
        verify(pacienteService, times(1)).deletar(eq(paciente.getId()));

    }
    @Test
    @DisplayName("Deve retornar erro ao tentar deletar um paciente utilizando um ID que não consta no banco de dados. ")
    public void testeErroAoTentarDeletarPacienteComIdInvalido() throws Exception {

        //Arrange
        String id = "12312321131311";

        //Mock
        doThrow(new ResourceNotFoundException("Paciente não encontrado")).when(pacienteService).deletar(id);

        //Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.delete("/pacientes/" + id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @DisplayName("Deve retornar erro ao de not found caso tente acessar um endereço invalido ")
    public void testeErroAoTentarAcessarLocalInvalido() throws Exception {


        //Mock
        doThrow(new ResourceNotFoundException("Endereço não encontrado")).when(pacienteService).encontrarPaciente(anyString());

        //Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/pacientez")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}
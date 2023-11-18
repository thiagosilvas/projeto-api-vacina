package com.registro.vacinacao;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.registro.vacinacao.entity.RegistroVacinacao;
import com.registro.vacinacao.service.RegistroVacinacaoService;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.*;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
public class RegistroVacinacaoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RegistroVacinacaoService registroVacinacaoService;

    @Test
    @DisplayName("Deve ser possível obter todos os registros de vacinação")
    public void testeObterTodosOsRegistroVacinacao() throws java.lang.Exception {

        RegistroVacinacao registro1 = new RegistroVacinacao();
        registro1.setNomeProfissional("João");
        registro1.setSobrenomeProfissional("Silva");
        registro1.setDataVacinacao(LocalDate.of(2023, 1, 15));
        registro1.setCpfProfissional("12345678900");
        registro1.setIdentificacaoPaciente("Paciente1");
        registro1.setIdentificacaoVacina("VacinaA");
        registro1.setIdentificacaoDose("Primeira Dose");

        RegistroVacinacao registro2 = new RegistroVacinacao();
        registro2.setNomeProfissional("Maria");
        registro2.setSobrenomeProfissional("Santos");
        registro2.setDataVacinacao(LocalDate.of(2023, 2, 20));
        registro2.setCpfProfissional("98765432100");
        registro2.setIdentificacaoPaciente("Paciente2");
        registro2.setIdentificacaoVacina("VacinaB");
        registro2.setIdentificacaoDose("Segunda Dose");


        List<RegistroVacinacao> registrosVacinacao = Arrays.asList(registro1, registro2);

        Mockito.when(registroVacinacaoService.listarRegistroVacinacao()).thenReturn(registrosVacinacao);

        mockMvc.perform(get("/registro-vacinacao")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(2)).andExpect(MockMvcResultMatchers.jsonPath("$[0].nomeProfissional").value(registro1.getNomeProfissional())).andExpect(MockMvcResultMatchers.jsonPath("$[0].sobrenomeProfissional").value(registro1.getSobrenomeProfissional())).andExpect(MockMvcResultMatchers.jsonPath("$[0].dataVacinacao").value(registro1.getDataVacinacao().toString())).andExpect(MockMvcResultMatchers.jsonPath("$[0].cpfProfissional").value(registro1.getCpfProfissional())).andExpect(MockMvcResultMatchers.jsonPath("$[0].identificacaoPaciente").value(registro1.getIdentificacaoPaciente())).andExpect(MockMvcResultMatchers.jsonPath("$[0].identificacaoVacina").value(registro1.getIdentificacaoVacina())).andExpect(MockMvcResultMatchers.jsonPath("$[0].identificacaoDose").value(registro1.getIdentificacaoDose())).andExpect(MockMvcResultMatchers.jsonPath("$[1].nomeProfissional").value(registro2.getNomeProfissional())).andExpect(MockMvcResultMatchers.jsonPath("$[1].sobrenomeProfissional").value(registro2.getSobrenomeProfissional())).andExpect(MockMvcResultMatchers.jsonPath("$[1].dataVacinacao").value(registro2.getDataVacinacao().toString())).andExpect(MockMvcResultMatchers.jsonPath("$[1].cpfProfissional").value(registro2.getCpfProfissional())).andExpect(MockMvcResultMatchers.jsonPath("$[1].identificacaoPaciente").value(registro2.getIdentificacaoPaciente())).andExpect(MockMvcResultMatchers.jsonPath("$[1].identificacaoVacina").value(registro2.getIdentificacaoVacina())).andExpect(MockMvcResultMatchers.jsonPath("$[1].identificacaoDose").value(registro2.getIdentificacaoDose()));
        Mockito.verify(registroVacinacaoService, times(2)).listarRegistroVacinacao();
    }

    @Test
    @DisplayName("Deve retornar uma lista vazia quando não há registro de vacinacão cadastradas")
    public void testeObterListaVazia() throws java.lang.Exception {
        List<RegistroVacinacao> registroVacinacao = new ArrayList<>();

        when(registroVacinacaoService.listarRegistroVacinacao()).thenReturn(registroVacinacao);

        mockMvc.perform(MockMvcRequestBuilders.get("/registro-vacinacao")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(0));

        verify(registroVacinacaoService, times(2)).listarRegistroVacinacao();
    }

    @Test
    @DisplayName("Deve ser possível obter um registro de vacinação pelo ID")
    public void testeObterRegistroVacinacaoPeloId() throws java.lang.Exception {
        RegistroVacinacao registro = new RegistroVacinacao();
        registro.setId("123S123SA56");
        registro.setNomeProfissional("João");
        registro.setSobrenomeProfissional("Silva");
        registro.setDataVacinacao(LocalDate.of(2023, 1, 15));
        registro.setCpfProfissional("12345678900");
        registro.setIdentificacaoPaciente("Paciente1");
        registro.setIdentificacaoVacina("VacinaA");
        registro.setIdentificacaoDose("Primeira Dose");

        when(registroVacinacaoService.buscarRegistroVacinacao(registro.getId())).thenReturn(registro);

        mockMvc.perform(get("/registro-vacinacao/{id}", registro.getId())).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.jsonPath("$.id").value(registro.getId())).andExpect(MockMvcResultMatchers.jsonPath("$.nomeProfissional").value(registro.getNomeProfissional())).andExpect(MockMvcResultMatchers.jsonPath("$.sobrenomeProfissional").value(registro.getSobrenomeProfissional())).andExpect(MockMvcResultMatchers.jsonPath("$.dataVacinacao").value(registro.getDataVacinacao().toString())).andExpect(MockMvcResultMatchers.jsonPath("$.cpfProfissional").value(registro.getCpfProfissional())).andExpect(MockMvcResultMatchers.jsonPath("$.identificacaoPaciente").value(registro.getIdentificacaoPaciente())).andExpect(MockMvcResultMatchers.jsonPath("$.identificacaoVacina").value(registro.getIdentificacaoVacina())).andExpect(MockMvcResultMatchers.jsonPath("$.identificacaoDose").value(registro.getIdentificacaoDose()));

        Mockito.verify(registroVacinacaoService, times(1)).buscarRegistroVacinacao(registro.getId());
    }

    @Test
    @DisplayName("Deve lançar uma ResourceNotFoundException ao buscar um ID inexistente")
    public void testeObterPacientePeloIdInexistente() throws Exception {

        String id = "12312321131311";

        when(registroVacinacaoService.buscarRegistroVacinacao(id)).thenThrow(ResourceNotFoundException.class);
        doThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Registro de Vacinação não encontrado")).when(registroVacinacaoService).buscarRegistroVacinacao(eq(id));

        mockMvc.perform(MockMvcRequestBuilders.get("/registro-vacinacao/" + id).contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isNotFound());
        verify(registroVacinacaoService, times(1)).buscarRegistroVacinacao(id);

    }


    @Test
    @DisplayName("Deve adicionar um registro de vacinação")
    public void testeAdicionarRegistroVacinacao() throws Exception {
        RegistroVacinacao registro = new RegistroVacinacao();
        registro.setId("123S123SA57");
        registro.setNomeProfissional("Marcos");
        registro.setSobrenomeProfissional("Silva");
        registro.setDataVacinacao(LocalDate.of(2023, 1, 15));
        registro.setCpfProfissional("89453598003");
        registro.setIdentificacaoPaciente("Paciente4");
        registro.setIdentificacaoVacina("VacinaD");
        registro.setIdentificacaoDose("1");

        Map<String, Object> resultadoService = new HashMap<>();
        resultadoService.put("status", HttpStatus.CREATED.value());
        resultadoService.put("registroVacinacao", registro);
        resultadoService.put("id", registro.getId());

        when(registroVacinacaoService.criarRegistroVacinacao(registro)).thenReturn(resultadoService);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, false);
        String novoRegistroVacinacaoJson = objectMapper.writeValueAsString(registro);

        mockMvc.perform(MockMvcRequestBuilders.post("/registro-vacinacao").contentType(MediaType.APPLICATION_JSON).content(novoRegistroVacinacaoJson)).andExpect(MockMvcResultMatchers.status().isCreated()).andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.jsonPath("$.id").value(registro.getId())).andExpect(MockMvcResultMatchers.jsonPath("$.nomeProfissional").value(registro.getNomeProfissional())).andExpect(MockMvcResultMatchers.jsonPath("$.sobrenomeProfissional").value(registro.getSobrenomeProfissional())).andExpect(MockMvcResultMatchers.jsonPath("$.dataVacinacao").value(registro.getDataVacinacao().toString()))  // Ajuste aqui
                .andExpect(MockMvcResultMatchers.jsonPath("$.cpfProfissional").value(registro.getCpfProfissional())).andExpect(MockMvcResultMatchers.jsonPath("$.identificacaoPaciente").value(registro.getIdentificacaoPaciente())).andExpect(MockMvcResultMatchers.jsonPath("$.identificacaoVacina").value(registro.getIdentificacaoVacina())).andExpect(MockMvcResultMatchers.jsonPath("$.identificacaoDose").value(registro.getIdentificacaoDose()));

        verify(registroVacinacaoService, times(1)).criarRegistroVacinacao(registro);
    }


    @Test
    @DisplayName("Deve retornar erro ao tentar inserir um registro vacinação no banco de dados com valores nulos.")
    public void testeTentarInserirRegistroVacinacaoComInformacoesNulas() throws java.lang.Exception {

        RegistroVacinacao registroVacinacao = new RegistroVacinacao();

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, false);
        String registroVacinacaoJson = objectMapper.writeValueAsString(registroVacinacao);

        mockMvc.perform(MockMvcRequestBuilders.post("/registro-vacinacao").contentType(MediaType.APPLICATION_JSON).content(registroVacinacaoJson)).andExpect(MockMvcResultMatchers.status().isBadRequest()) // Status esperado agora é BAD REQUEST (400)
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.jsonPath("$.[*]").value(containsInAnyOrder("Nome não pode ser nulo e não pode estar em branco.", "Sobrenome não pode ser nulo e não pode estar em branco.", "Data não pode estar em branco.", "CPF não pode ser nulo e não pode estar em branco.", "Paciente não pode ser nulo e não pode estar em branco.", "Vacina não pode ser nula e não pode estar em branco.", "Dose não pode ser nula e não pode estar em branco.")));
    }

    @Test
    @DisplayName("Deve retornar erro ao tentar inserir um registro vacinacao no banco de dados com valores em branco.")
    public void testeTentarInserirRegistroVacinacaoComInformacoesEmBranco() throws java.lang.Exception {
        RegistroVacinacao registroVacinacao = new RegistroVacinacao("", "", "", null, "", "", "", "");

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, false);
        String registroVacinacaoJson = objectMapper.writeValueAsString(registroVacinacao);

        mockMvc.perform(MockMvcRequestBuilders.post("/registro-vacinacao").contentType(MediaType.APPLICATION_JSON).content(registroVacinacaoJson)).andExpect(MockMvcResultMatchers.status().isBadRequest()) // Status esperado agora é BAD REQUEST (400)
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.jsonPath("$.[*]").value(containsInAnyOrder("Nome não pode ser nulo e não pode estar em branco.", "Sobrenome não pode ser nulo e não pode estar em branco.", "Data não pode estar em branco.", "CPF não pode ser nulo e não pode estar em branco.", "Paciente não pode ser nulo e não pode estar em branco.", "Vacina não pode ser nula e não pode estar em branco.", "Dose não pode ser nula e não pode estar em branco.", "invalid Brazilian individual taxpayer registry number (CPF)")));
    }

    @Test
    @DisplayName("Deve alterar as informações de um registro de vacinação já existente no banco de dados.")
    public void testeAlterarRegistroVacinacao() throws Exception {

        RegistroVacinacao registro = new RegistroVacinacao();
        registro.setId("123S123SA58");
        registro.setNomeProfissional("Bernado");
        registro.setSobrenomeProfissional("Silva");
        registro.setDataVacinacao(LocalDate.of(2023, 1, 15));
        registro.setCpfProfissional("89453598003");
        registro.setIdentificacaoPaciente("Paciente5");
        registro.setIdentificacaoVacina("VacinaE");
        registro.setIdentificacaoDose("Primeira Dose");

        when(registroVacinacaoService.buscarRegistroVacinacao(registro.getId())).thenReturn(registro);

        Map<String, Object> resultadoCriacao = new HashMap<>();
        resultadoCriacao.put("status", HttpStatus.CREATED.value());
        resultadoCriacao.put("registroVacinacao", registro);
        resultadoCriacao.put("id", registro.getId());

        when(registroVacinacaoService.criarRegistroVacinacao(registro)).thenReturn(resultadoCriacao);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, false);
        String registroVacinacaoJson = objectMapper.writeValueAsString(registro);

        mockMvc.perform(MockMvcRequestBuilders.post("/registro-vacinacao").contentType(MediaType.APPLICATION_JSON).content(registroVacinacaoJson)).andExpect(MockMvcResultMatchers.status().isCreated()).andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));

        RegistroVacinacao registroVacinacaoAtualizado = new RegistroVacinacao();
        registroVacinacaoAtualizado.setId("123S123SA58_atualizado");
        registroVacinacaoAtualizado.setNomeProfissional("Caroline");
        registroVacinacaoAtualizado.setSobrenomeProfissional("Silva");
        registroVacinacaoAtualizado.setDataVacinacao(LocalDate.of(2023, 1, 15));
        registroVacinacaoAtualizado.setCpfProfissional("89453598003");
        registroVacinacaoAtualizado.setIdentificacaoPaciente("Paciente5");
        registroVacinacaoAtualizado.setIdentificacaoVacina("VacinaE");
        registroVacinacaoAtualizado.setIdentificacaoDose("Primeira Dose");


        String updatedRegistroVacinacaoJson = objectMapper.writeValueAsString(registroVacinacaoAtualizado);

        // Mock
        when(registroVacinacaoService.atualizarRegistroVacinacao(eq(registroVacinacaoAtualizado.getId()), any(RegistroVacinacao.class)))

                .thenAnswer(invocation -> {
                    RegistroVacinacao registroAtualizado = invocation.getArgument(1);
                    return registroAtualizado;
                });


        mockMvc.perform(MockMvcRequestBuilders.put("/registro-vacinacao/" + registroVacinacaoAtualizado.getId()).contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isBadRequest());

    }

    @Test
    @DisplayName("Deve retornar erro ao tentar atualizar um registro vacinação no banco de dados com informações nulas.")
    public void testeTentarAtualizarRegistroVacinacaoComInformacoesNulas() throws Exception {
        RegistroVacinacao registro = new RegistroVacinacao("123S123SA58", "Bernardo", "Silva", LocalDate.of(2023, 11, 8), "71013857020", "Paciente5", "VacinaE", "Primeira Dose");

        when(registroVacinacaoService.buscarRegistroVacinacao(registro.getId())).thenReturn(registro);

        Map<String, Object> resultadoCriacao = new HashMap<>();
        resultadoCriacao.put("status", HttpStatus.CREATED.value());
        resultadoCriacao.put("registroVacinacao", registro);
        resultadoCriacao.put("id", registro.getId());

        when(registroVacinacaoService.criarRegistroVacinacao(registro)).thenReturn(resultadoCriacao);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, false);
        String novoRegistroVacinacaoJson = objectMapper.writeValueAsString(registro);

        mockMvc.perform(MockMvcRequestBuilders.post("/registro-vacinacao").contentType(MediaType.APPLICATION_JSON).content(novoRegistroVacinacaoJson)).andExpect(MockMvcResultMatchers.status().isCreated()).andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));

        RegistroVacinacao registroVacinacaoAtualizado = new RegistroVacinacao();
        registroVacinacaoAtualizado.setId(registro.getId());  // Define o ID do registro a ser atualizado

        when(registroVacinacaoService.atualizarRegistroVacinacao(eq(registroVacinacaoAtualizado.getId()), any(RegistroVacinacao.class))).thenThrow(new ResourceNotFoundException("Não foi possível encontrar registro vacinação."));

        String updatedRegistroVacinacaoJson = objectMapper.writeValueAsString(registroVacinacaoAtualizado);

        mockMvc.perform(MockMvcRequestBuilders.put("/registro-vacinacao/" + registro.getId()).contentType(MediaType.APPLICATION_JSON).content(updatedRegistroVacinacaoJson)).andExpect(MockMvcResultMatchers.status().isBadRequest());
    }


    @Test
    @DisplayName("Deve retornar erro quando tenta alterar um paciente utilizando um ID que não consta no banco de dados.")
    public void testeAlterarInformacaoComIdInvalido() throws Exception {
        RegistroVacinacao registro = new RegistroVacinacao();
        registro.setId("123S123SA58");
        registro.setNomeProfissional("Bernado");
        registro.setSobrenomeProfissional("Silva");
        registro.setDataVacinacao(LocalDate.of(2023, 1, 15));
        registro.setCpfProfissional("89453598003");
        registro.setIdentificacaoPaciente("Paciente5");
        registro.setIdentificacaoVacina("VacinaE");
        registro.setIdentificacaoDose("Primeira Dose");

        String id = "6520f03c0bfb2471fc4e5513";

        doThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST, "Registro de Vacinação não encontrado")).when(registroVacinacaoService).atualizarRegistroVacinacao(id, registro);

        mockMvc.perform(MockMvcRequestBuilders.put("/registro-vacinacao/" + id).contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isBadRequest());

    }

    @Test
    @DisplayName("Deve excluir um registro de vacinação existente do banco de dados.")
    public void testeExcluirRegistroVacinacao() throws Exception {

        RegistroVacinacao registro = new RegistroVacinacao();
        registro.setId("123S123SA57");
        registro.setNomeProfissional("Marcos");
        registro.setSobrenomeProfissional("Silva");
        registro.setDataVacinacao(LocalDate.of(2023, 1, 15));
        registro.setCpfProfissional("89453598003");
        registro.setIdentificacaoPaciente("Paciente4");
        registro.setIdentificacaoVacina("VacinaD");
        registro.setIdentificacaoDose("1");

        Map<String, Object> resultadoServiceCriar = new HashMap<>();
        resultadoServiceCriar.put("status", HttpStatus.CREATED.value());
        resultadoServiceCriar.put("registroVacinacao", registro);
        resultadoServiceCriar.put("id", registro.getId());

        Map<String, Object> resultadoServiceExcluir = new HashMap<>();
        resultadoServiceExcluir.put("status", HttpStatus.NO_CONTENT.value());

        when(registroVacinacaoService.criarRegistroVacinacao(registro)).thenReturn(resultadoServiceCriar);
        when(registroVacinacaoService.excluirRegistroVacinacao(eq(registro.getId()))).thenReturn(resultadoServiceExcluir);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, false);
        String registroVacinacaoJson = objectMapper.writeValueAsString(registro);

        mockMvc.perform(MockMvcRequestBuilders.post("/registro-vacinacao").contentType(MediaType.APPLICATION_JSON).content(registroVacinacaoJson)).andExpect(MockMvcResultMatchers.status().isCreated());

        mockMvc.perform(MockMvcRequestBuilders.delete("/registro-vacinacao/" + registro.getId()).contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isNoContent());

        verify(registroVacinacaoService, times(1)).excluirRegistroVacinacao(eq(registro.getId()));
    }


    @Test
    @DisplayName("Deve retornar erro ao tentar deletar um registro de vacinação não existente no banco de dados")
    public void testeErroAoTentarDeletarRegistroComIdInvalido() throws Exception {
        String id = "12312321131311";

        doThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Não foi possível encontrar registro vacinação.")).when(registroVacinacaoService).excluirRegistroVacinacao(eq(id));

        mockMvc.perform(MockMvcRequestBuilders.delete("/registro-vacinacao/" + id).contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @DisplayName("Deve retornar erro ao de not found caso tente acessar um endereço invalido ")
    public void testeErroAoTentarAcessarLocalInvalido() throws Exception {

        doThrow(new ResourceNotFoundException("Endereço não encontrado")).when(registroVacinacaoService).buscarRegistroVacinacao(anyString());

        mockMvc.perform(MockMvcRequestBuilders.get("/pacientez").contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}
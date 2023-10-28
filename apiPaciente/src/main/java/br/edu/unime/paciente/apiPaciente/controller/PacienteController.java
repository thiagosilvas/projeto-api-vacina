package br.edu.unime.paciente.apiPaciente.controller;

import br.edu.unime.paciente.apiPaciente.entity.Paciente;
import br.edu.unime.paciente.apiPaciente.service.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import lombok.Data;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Data

@RestController
@RequestMapping("/pacientes")
public class PacienteController {

    @Autowired
    PacienteService pacienteService;
    @GetMapping
    public ResponseEntity<List<Paciente>> obterTodos(){
        int statusCode = HttpServletResponse.SC_OK;
        pacienteService.registrarLog("GET" , "Buscar Pacientes", pacienteService.obterTodos().toString(), statusCode);
        return ResponseEntity.ok().body(pacienteService.obterTodos());
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> encontrarPaciente(@PathVariable String id) {
        try{
            Paciente paciente = pacienteService.encontrarPaciente(id);
            int statusCode = HttpServletResponse.SC_OK;
            pacienteService.registrarLog("GET" , "Buscar Paciente pelo id", id , statusCode);
            return ResponseEntity.ok().body(paciente);

        } catch (Exception e) {

            Map<String, String> resposta = new HashMap<>();

            resposta.put("mensagem", e.getMessage());
            int statusCode = HttpServletResponse.SC_NOT_FOUND;
            pacienteService.registrarLog("GET" , "Buscar Paciente pelo id" ,"Erro ao buscar Paciente pelo id: "+id, statusCode);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(resposta);
        }

    }
    @PostMapping

    public ResponseEntity<?> inserir(@RequestBody @Valid Paciente paciente, BindingResult bindingResult) {

        if (bindingResult.hasErrors()){

            List<String> erros = bindingResult
                    .getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toList();
            int statusCode = HttpServletResponse.SC_BAD_GATEWAY;
            pacienteService.registrarLog("POST" , "Erro ao adicionar Paciente", "Objeto: "+ paciente , statusCode);

            return ResponseEntity.badRequest().body(erros.toArray());
        }
        pacienteService.inserir(paciente);

        int statusCode = HttpServletResponse.SC_CREATED;
        pacienteService.registrarLog("POST" , "Adicionar Paciente", "Objeto: "+ paciente , statusCode);

        return ResponseEntity.created(null).body(paciente);
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(
            @PathVariable String id,
            @RequestBody Paciente paciente
    ) {
        try {
            Paciente pacienteAtualizado = pacienteService.atualizar(id, paciente);

            int statusCode = HttpServletResponse.SC_OK;
            pacienteService.registrarLog("PUT" , "Atualizar Cadastro do Paciente",paciente + " pelo id: "+ id , statusCode);

            return ResponseEntity.ok().body(pacienteAtualizado);
        } catch (Exception e) {
            Map<String, String> resposta = new HashMap<>();
            resposta.put("mensagem", e.getMessage());

            int statusCode = HttpServletResponse.SC_NOT_FOUND;
            pacienteService.registrarLog("PUT" , "Atualizar Paciente" ,"Erro ao atualizar Paciente" + paciente + " pelo id: "+ id, statusCode);

            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> excluir(
            @PathVariable String id
    ) {
        try {
            pacienteService.deletar(id);

            int statusCode = HttpServletResponse.SC_NO_CONTENT;
            pacienteService.registrarLog("DELETE" , "Deletar Paciente por id", "Id do Paciente: "+id, statusCode);

            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            Map<String, String> resposta = new HashMap<>();
            resposta.put("mensagem", e.getMessage());

            int statusCode = HttpServletResponse.SC_NOT_FOUND;
            pacienteService.registrarLog("DELETE" , "Erro ao deletar Paciente por id", "Id do Paciente"+ id, statusCode);

            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

}

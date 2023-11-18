package br.edu.unime.vacina.apiVacina.controller;

import br.edu.unime.vacina.apiVacina.cadastroVacinas.ListaVacinas;
import br.edu.unime.vacina.apiVacina.entity.Vacina;
import br.edu.unime.vacina.apiVacina.service.VacinaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.dao.DataIntegrityViolationException;
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
@RequestMapping("/vacina")
public class VacinaController {

    @Autowired
    VacinaService vacinaService;
    @GetMapping
    public ResponseEntity<List<Vacina>> obterTodos(){
        int statusCode = HttpServletResponse.SC_OK;
        vacinaService.registrarLog("GET" , "Buscar Vacinas", vacinaService.obterTodos().toString(), statusCode);
        return ResponseEntity.ok().body(vacinaService.obterTodos());
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> encontrarVacina(@PathVariable String id) {
        try{
            Vacina paciente = vacinaService.encontrarVacina(id);
            int statusCode = HttpServletResponse.SC_OK;
            vacinaService.registrarLog("GET" , "Buscar Vacina pelo id", id , statusCode);
            return ResponseEntity.ok().body(paciente);

        } catch (Exception e) {

            Map<String, String> resposta = new HashMap<>();

            resposta.put("mensagem", e.getMessage());

            int statusCode = HttpServletResponse.SC_NOT_FOUND;
            vacinaService.registrarLog("GET" , "Buscar Vacina pelo id" ,"Erro ao buscar Vacina pelo id: "+id, statusCode);

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(resposta);
        }

    }
    @PostMapping

    public ResponseEntity<?> inserir(@RequestBody @Valid Vacina vacina, BindingResult bindingResult) {

        if (bindingResult.hasErrors()){

            List<String> erros = bindingResult
                    .getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toList();

            int statusCode = HttpServletResponse.SC_BAD_GATEWAY;
            vacinaService.registrarLog("POST" , "Erro ao adicionar Vacina", "Objeto: "+vacina , statusCode);

            return ResponseEntity.badRequest().body(erros.toArray());
        }
        vacinaService.inserir(vacina);

        int statusCode = HttpServletResponse.SC_CREATED;
        vacinaService.registrarLog("POST" , "Adicionar Vacina", "Objeto: "+vacina , statusCode);

        return ResponseEntity.created(null).body(vacina);
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(
            @PathVariable String id,
            @RequestBody Vacina vacina
    ) {
        try {
            Vacina vacinaAtualizado = vacinaService.atualizar(id, vacina);

            int statusCode = HttpServletResponse.SC_OK;
            vacinaService.registrarLog("PUT", "Atualizar Vacina", vacina + " pelo id: " + id, statusCode);

            return ResponseEntity.ok().body(vacinaAtualizado);

        } catch (DataIntegrityViolationException e) {
            Map<String, String> resposta = new HashMap<>();
            resposta.put("mensagem", "Erro de validação: " + e.getMessage());

            int statusCode = HttpServletResponse.SC_NOT_FOUND;
            vacinaService.registrarLog("PUT", "Atualizar Vacina", "Erro ao atualizar Vacina " + vacina + " pelo id: " + id, statusCode);

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(resposta);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> excluir(
            @PathVariable String id
    ) {
        try {
            vacinaService.deletar(id);

            int statusCode = HttpServletResponse.SC_NO_CONTENT;
            vacinaService.registrarLog("DELETE" , "Deletar Vacina por id", "Id da vacina: "+id, statusCode);

            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            Map<String, String> resposta = new HashMap<>();
            resposta.put("mensagem", e.getMessage());

            int statusCode = HttpServletResponse.SC_NOT_FOUND;
            vacinaService.registrarLog("DELETE" , "Erro ao deletar Vacina por id", "Id da vacina"+ id, statusCode);

            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/adicionar-vacinas")
    public ResponseEntity<?> inserirVacinasPredefinidasAoBanco() {
        try {
            ListaVacinas pacientesPredefinidos = new ListaVacinas();

            List<Vacina> pacientes = pacientesPredefinidos.CadastrarVacinasPredefinidas();

            for (Vacina paciente : pacientes) {
                vacinaService.inserir(paciente);
                int statusCode = HttpServletResponse.SC_BAD_REQUEST;
                vacinaService.registrarLog("POST", "Adicionar vacinas predefinidas", "Quantidade de vacinas adicionadas: " + pacientes.size(), statusCode);
            }
            return ResponseEntity.status(HttpStatus.CREATED).body("Vacinas inseridas");

        } catch (Exception e) {

            Map<String, String> resposta = new HashMap<>();
            resposta.put("mensagem", e.getMessage());

            int statusCode = HttpServletResponse.SC_BAD_REQUEST;
            vacinaService.registrarLog("POST", "Adicionar vacinas predefinidas","Erro ao tentar adicionar vacinas predefinidas", statusCode);

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resposta);
        }

    }

}

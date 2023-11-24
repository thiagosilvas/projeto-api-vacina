package br.edu.unime.vacina.apiVacina.controller;

import br.edu.unime.vacina.apiVacina.cadastroVacinas.ListaVacinas;
import br.edu.unime.vacina.apiVacina.entity.Vacina;
import br.edu.unime.vacina.apiVacina.repository.VacinaRepository;
import br.edu.unime.vacina.apiVacina.service.VacinaService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.context.support.DefaultMessageSourceResolvable;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/vacina")
public class VacinaController {

    @Autowired
    VacinaService vacinaService;

    @Autowired
    VacinaRepository vacinaRepository;

    @GetMapping
    public ResponseEntity<List<Vacina>> obterTodos() {
        List<Vacina> vacinas = vacinaService.obterTodos();
        logRequest("GET", "Buscar Vacinas", vacinas.toString(), HttpServletResponse.SC_OK);
        return ResponseEntity.ok().body(vacinas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> encontrarVacina(@PathVariable String id) {
        try {
            Vacina vacina = vacinaService.encontrarVacina(id);
            logRequest("GET", "Buscar Vacina pelo id", id, HttpServletResponse.SC_OK);
            return ResponseEntity.ok().body(vacina);
        } catch (Exception e) {
            return handleException("GET", "Buscar Vacina pelo id", id, e, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<?> inserir(@RequestBody @Valid Vacina vacina, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return handleValidationErrors(bindingResult, "POST", "Erro ao adicionar Vacina", vacina);
        }

        List<String> erros = vacinaService.validarVacina(vacina, "POST");

        if (!erros.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erros);
        }

        try {
            vacinaService.inserir(vacina);

            logRequest("POST", "Adicionar Vacina", "Objeto: " + vacina, HttpServletResponse.SC_CREATED);
            return ResponseEntity.created(null).body(vacina);

        } catch (Exception e) {
            return handleException("POST", "Adicionar Vacina", "Objeto: " + vacina, e, HttpStatus.BAD_REQUEST);
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable String id, @RequestBody @NotNull Vacina vacina) {
        List<String> erros = vacinaService.validarVacina(vacina, "PUT");

        if (!erros.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erros);
        }

        try {
            Vacina vacinaAtualizado = vacinaService.atualizar(id, vacina);

            logRequest("PUT", "Atualizar Vacina", "Atualizar Vacina: " + vacina + " pelo id: " + id, HttpServletResponse.SC_NO_CONTENT);
            return ResponseEntity.ok().body(vacinaAtualizado);

        } catch (DataIntegrityViolationException e) {
            return handleException("PUT", "Atualizar Vacina", "Erro ao atualizar Vacina " + vacina + " pelo id: " + id, e, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return handleException("PUT", "Atualizar Vacina", vacina + " pelo id: " + id, e, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> excluir(@PathVariable String id) {
        try {
            vacinaService.deletar(id);
            logRequest("DELETE", "Deletar Vacina por id", "Id da vacina: " + id, HttpServletResponse.SC_NO_CONTENT);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return handleException("DELETE", "Deletar Vacina por id", "Id da vacina" + id, e, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/adicionar-vacinas")
    public ResponseEntity<?> inserirVacinasPredefinidasAoBanco() {
        try {
            List<Vacina> vacinasPredefinidas = new ListaVacinas().CadastrarVacinasPredefinidas();
            vacinasPredefinidas.forEach(vacinaService::inserir);
            logRequest("POST", "Adicionar vacinas predefinidas", "Quantidade de vacinas adicionadas: " + vacinasPredefinidas.size(), HttpServletResponse.SC_CREATED);
            return ResponseEntity.status(HttpStatus.CREATED).body("Vacinas inseridas");
        } catch (Exception e) {
            return handleException("POST", "Adicionar vacinas predefinidas", "Erro ao tentar adicionar vacinas predefinidas", e, HttpStatus.BAD_REQUEST);
        }
    }

    private void logRequest(String method, String action, String details, int statusCode) {
        vacinaService.registrarLog(method, action, details, statusCode);
    }

    private ResponseEntity<?> handleValidationErrors(BindingResult bindingResult, String method, String action, Vacina vacina) {
        List<String> errors = bindingResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList();
        logRequest(method, action, "Objeto: " + vacina, HttpServletResponse.SC_BAD_GATEWAY);
        return ResponseEntity.badRequest().body(errors.toArray());
    }

    private ResponseEntity<?> handleException(String method, String action, String details, Exception e, HttpStatus status) {
        Map<String, String> response = new HashMap<>();
        response.put("mensagem", e.getMessage());
        logRequest(method, action, details, HttpServletResponse.SC_NOT_FOUND);
        return ResponseEntity.status(status).body(response);
    }
}

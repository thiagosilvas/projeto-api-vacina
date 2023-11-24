    package br.edu.unime.paciente.apiPaciente.controller;

    import br.edu.unime.paciente.apiPaciente.entity.Paciente;
    import br.edu.unime.paciente.apiPaciente.repository.PacienteRepository;
    import br.edu.unime.paciente.apiPaciente.service.PacienteService;
    import br.edu.unime.paciente.apiPaciente.testing.ListaPaciente;
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

        @Autowired
        PacienteRepository pacienteRepository;

        @GetMapping
        public ResponseEntity<List<Paciente>> obterTodos() {

            List<Paciente> pacientes = pacienteService.obterTodos();

            pacienteService.registrarLog("GET", "Buscar Pacientes", pacientes.toString(), HttpServletResponse.SC_OK);

            return ResponseEntity.ok().body(pacienteService.obterTodos());

        }

        @GetMapping("/{id}")
        public ResponseEntity<?> encontrarPaciente(@PathVariable String id) {

            try {

                Paciente paciente = pacienteService.encontrarPaciente(id);

                logRequest("GET", "Buscar Paciente pelo id", id, HttpServletResponse.SC_OK);

                return ResponseEntity.ok().body(paciente);

            } catch (Exception e) {

                return handleException("GET", "Buscar Paciente pelo id", "Erro ao buscar Paciente pelo id: " + id, e, HttpStatus.NOT_FOUND);

            }

        }

        @PostMapping
        public ResponseEntity<?> inserir(@RequestBody @Valid Paciente paciente, BindingResult bindingResult) {

            if (bindingResult.hasErrors()) {

                return handleValidationErrors(bindingResult, "POST", "Erro ao adicionar Paciente", paciente);

            }
            try {

                pacienteService.inserir(paciente);

                logRequest("POST", "Adicionar Paciente", "Objeto: " + paciente, HttpServletResponse.SC_CREATED);
                return ResponseEntity.created(null).body(paciente);

            } catch (Exception e) {

                return handleException("POST", "Adicionar Paciente", "Erro ao adicionar paciente: " + paciente, e, HttpStatus.BAD_REQUEST);

            }
        }

        @PutMapping("/{id}")
        public ResponseEntity<?> atualizar(
                @PathVariable String id,
                @Valid @RequestBody Paciente paciente,
                BindingResult bindingResult
        ) {
            if (bindingResult.hasErrors()) {

                return handleValidationErrors(bindingResult, "Put", "Erro ao atualizar Paciente", paciente);
            }

            try {
                Paciente pacienteAtualizado = pacienteService.atualizar(id, paciente);
                if (pacienteAtualizado != null) {

                    int statusCode = HttpServletResponse.SC_OK;

                    logRequest("PUT", "Atualizar cadastro do Paciente", "Objeto: " + pacienteAtualizado, statusCode);

                    return ResponseEntity.ok().body(pacienteAtualizado);

                } else {

                    String mensagemDeErro = "Paciente com ID " + id + " não encontrado.";

                    int statusCode = HttpServletResponse.SC_NOT_FOUND;

                    logRequest("PUT", "Atualizar cadastro do Paciente", mensagemDeErro, statusCode);

                    Map<String, String> resposta = new HashMap<>();

                    resposta.put("mensagem", mensagemDeErro);

                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(resposta);

                }

            } catch (Exception e) {

                return handleException("PUT", "Atualizar cadastro do Paciente", paciente + " pelo id:" + id, e, HttpStatus.BAD_REQUEST);
            }
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<?> excluir(
                @PathVariable String id
        ) {
            try {

                pacienteService.deletar(id);

                logRequest("DELETE", "Deletar Paciente por id", "Id do Paciente: " + id, HttpServletResponse.SC_NO_CONTENT);

                return ResponseEntity.noContent().build();

            } catch (Exception e) {

                return handleException("DELETE", "Deletar paciente por id","Id do paciente" + id, e, HttpStatus.NOT_FOUND);

            }
        }

        @PostMapping("/adicionar-pacientes")
        public ResponseEntity<?> inserirPacientesPredefinidosAoBanco() {
            try {

                List<Paciente> pacientespredefinidos = new ListaPaciente().CadastrarPacientes();

                pacientespredefinidos.forEach(pacienteService::inserir);

                logRequest("POST", "Adicionar pacientes predefinidos " ,

                        "Quantidade de pacientes adicionados:" + pacientespredefinidos.size(), HttpServletResponse.SC_CREATED);

                return ResponseEntity.status(HttpStatus.CREATED).body("Pacientes inseridos");

            } catch (Exception e) {

                return handleException("POST", "Adicionar pacientes predefinidos " ,
                        "Erro ao tentar adicionar pacientes predefinidos", e,  HttpStatus.BAD_REQUEST);
            }

        }

        private void logRequest(String method, String action, String details, int statusCode) {
            pacienteService.registrarLog(method, action, details, statusCode);
        }

        private ResponseEntity<?> handleValidationErrors(BindingResult bindingResult, String method, String action, Paciente paciente) {
            List<String> errors = bindingResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList();
            logRequest(method, action, "Objeto: " + paciente, HttpServletResponse.SC_BAD_GATEWAY);
            return ResponseEntity.badRequest().body(errors.toArray());
        }

        private ResponseEntity<?> handleException(String method, String action, String details, Exception e, HttpStatus status) {
            Map<String, String> response = new HashMap<>();
            response.put("mensagem", e.getMessage());
            logRequest(method, action, details, HttpServletResponse.SC_NOT_FOUND);
            return ResponseEntity.status(status).body(response);
        }
    }
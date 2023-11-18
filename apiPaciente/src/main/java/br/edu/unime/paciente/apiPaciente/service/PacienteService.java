package br.edu.unime.paciente.apiPaciente.service;

import br.edu.unime.paciente.apiPaciente.entity.Log;
import br.edu.unime.paciente.apiPaciente.entity.Paciente;
import br.edu.unime.paciente.apiPaciente.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PacienteService {
    @Autowired
    PacienteRepository pacienteRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    public PacienteService(CacheManager cacheManager, PacienteRepository pacienteRepository) {
        this.cacheManager = cacheManager;
        this.pacienteRepository = pacienteRepository;
    }

    private final CacheManager cacheManager;

    public void registrarLog(String metodo, String acao, String mensagem, int statusCode) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String data = dateFormat.format(new Date());

        Log log = new Log();
        log.setTimestamp(data);
        log.setLevel("INFO");
        log.setMethod(metodo);
        log.setAction(acao);
        log.setStatusCode(statusCode);

        log.setMessage(mensagem);

        mongoTemplate.insert(log, "log");
    }

    public List<Paciente> obterTodos(){
        return pacienteRepository.findAll();
    }

    @Cacheable("pacienteCache")
    public Paciente encontrarPaciente(String id) throws Exception {

        Optional<Paciente> pacienteOptional = pacienteRepository.findById(id);

        if (pacienteOptional.isEmpty()) {
            throw new Exception("Paciente não encontrado.");
        }
        return pacienteOptional.get();
    }

    @CachePut("pacienteCache")
    public void inserir(Paciente paciente){

        pacienteRepository.insert(paciente);
    }

    @CachePut(value = "pacienteCache", key = "#id")
    public Paciente atualizar(String id, Paciente paciente) throws Exception {
        Paciente pacienteAntigo = encontrarPaciente(id);

        pacienteAntigo.setNome(paciente.getNome());
        pacienteAntigo.setSobrenome(paciente.getSobrenome());
        pacienteAntigo.setCpf(paciente.getCpf());
        pacienteAntigo.setDataDeNascimento(paciente.getDataDeNascimento());
        pacienteAntigo.setGenero(paciente.getGenero());
        pacienteAntigo.setContatos(paciente.getContatos());
        pacienteAntigo.setEnderecos(paciente.getEnderecos());

        pacienteRepository.save(pacienteAntigo);

        return pacienteAntigo;
    }

    public void deletar(String id) throws Exception {

        Cache cache = cacheManager.getCache("pacienteCache");
        if (cache != null) {
            cache.evict(id);
        }

        Paciente paciente = encontrarPaciente(id);

        pacienteRepository.delete(paciente);

    }


}

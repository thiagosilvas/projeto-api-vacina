package br.edu.unime.vacina.apiVacina.service;

import br.edu.unime.vacina.apiVacina.entity.Log;
import br.edu.unime.vacina.apiVacina.entity.Vacina;
import br.edu.unime.vacina.apiVacina.repository.VacinaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

@Service
public class VacinaService {
    @Autowired
    VacinaRepository vacinaRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    public VacinaService(CacheManager cacheManager, VacinaRepository vacinaRepository) {
        this.cacheManager = cacheManager;
        this.vacinaRepository = vacinaRepository;
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

    public List<String> validarVacina(Vacina vacina, String metodo) {
        List<String> erros = new ArrayList<>();

        if (Objects.equals(metodo, "POST") && vacinaRepository.existsByFabricanteAndLote(vacina.getFabricante(), vacina.getLote())) {
            erros.add("Já existe uma vacina cadastrada com esses dados.");
        }

        if (vacina.getNumeroDeDoses() != null && vacina.getNumeroDeDoses() <= 0) {
            erros.add("Número de doses deve ser um valor positivo.");
        }

        if (vacina.getIntervaloDeDoses() != null && vacina.getIntervaloDeDoses() <= 0) {
            erros.add("Intervalo entre doses deve ser um valor positivo.");
        }

        if (vacina.getDataDeValidade() != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            try {
                // Tentar fazer o parse da data
                formatter.format(vacina.getDataDeValidade());
            } catch (DateTimeParseException e) {
                erros.add("Formato de data inválido. Use o formato yyyy-MM-dd.");
            }

            if (vacina.getDataDeValidade().isBefore(LocalDate.now())) {
                erros.add("A data de validade não pode estar no passado.");
            }
        } else {
            erros.add("A data de validade deve ser inserida.");
        }

        return erros;
    }

    public List<Vacina> obterTodos(){
        return vacinaRepository.findAll();
    }

    @Cacheable("vacinaCache")
    public Vacina encontrarVacina(String id) throws Exception {

        Optional<Vacina> vacinaOptional = vacinaRepository.findById(id);

        if (vacinaOptional.isEmpty()) {
            throw new Exception("Vacina não encontrada!");
        }

        return vacinaOptional.get();
    }

    @CachePut("vacinaCache")
    public void inserir(Vacina vacina) {
        vacinaRepository.insert(vacina);
    }


    @CachePut(value = "vacinaCache", key = "#id")
    public Vacina atualizar(String id, Vacina vacina) throws Exception {
        Vacina vacinaAntigo = encontrarVacina(id);

        if (vacina.getFabricante() != null) {
            vacinaAntigo.setFabricante(vacina.getFabricante());
        }

        if (vacina.getLote() != null) {
            vacinaAntigo.setLote(vacina.getLote());
        }

        if (vacina.getIntervaloDeDoses() != null) {
            vacinaAntigo.setIntervaloDeDoses(vacina.getIntervaloDeDoses());
        }

        if (vacina.getDataDeValidade() != null) {
            vacinaAntigo.setDataDeValidade(vacina.getDataDeValidade());
        }

        if (vacina.getNumeroDeDoses() != null) {
            vacinaAntigo.setNumeroDeDoses(vacina.getNumeroDeDoses());
        }

        try {
            vacinaRepository.save(vacinaAntigo);
        } catch (Exception e) {
            throw new Exception("Erro ao salvar a vacina: " + e.getMessage());
        }

        return vacinaAntigo;
    }

    public void deletar(String id) throws Exception {
        Cache cache = cacheManager.getCache("vacinaCache");
        if (cache != null) {
            cache.evict(id);
        }

        Vacina vacina = encontrarVacina(id);

        vacinaRepository.delete(vacina);
    }

}

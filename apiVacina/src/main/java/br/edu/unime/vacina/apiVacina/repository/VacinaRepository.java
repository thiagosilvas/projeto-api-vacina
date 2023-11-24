package br.edu.unime.vacina.apiVacina.repository;

import br.edu.unime.vacina.apiVacina.entity.Vacina;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VacinaRepository extends MongoRepository<Vacina, String> {

    Optional<Vacina> findById(String id);

    boolean existsByFabricanteAndLote(String fabricante, String lote);
}

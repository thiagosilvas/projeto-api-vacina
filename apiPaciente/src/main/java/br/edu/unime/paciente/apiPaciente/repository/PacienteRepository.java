package br.edu.unime.paciente.apiPaciente.repository;

import br.edu.unime.paciente.apiPaciente.entity.Paciente;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PacienteRepository extends MongoRepository<Paciente, String> {

    Optional<Paciente> findById(String id);
}

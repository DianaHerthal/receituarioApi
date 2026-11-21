package br.edu.utfpr.td.tsi.repository;

import br.edu.utfpr.td.tsi.model.Receituario;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ReceituarioRepository extends MongoRepository<Receituario, String> {
    Optional<Receituario> findByPacienteCpf(String cpf);
    Optional<Receituario> findByPacienteNome(String nome);
    Optional<Receituario> findByMedicoNome(String id);
    Optional<Receituario> findByMedicoCrm(String crm);
}
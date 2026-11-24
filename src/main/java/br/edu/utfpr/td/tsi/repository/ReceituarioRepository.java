package br.edu.utfpr.td.tsi.repository;

import br.edu.utfpr.td.tsi.model.Receituario;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ReceituarioRepository extends MongoRepository<Receituario, String> {

    List<Receituario> findByPacienteCpf(String cpf);
    List<Receituario> findByPacienteNomeContainingIgnoreCase(String nome);

    List<Receituario> findByMedicoNomeContainingIgnoreCase(String nome);
    List<Receituario> findByMedicoCrm(String crm);

    List<Receituario> findByPacienteNomeContainingIgnoreCaseAndMedicoNomeContainingIgnoreCase(
            String pacienteNome, String medicoNome);
}
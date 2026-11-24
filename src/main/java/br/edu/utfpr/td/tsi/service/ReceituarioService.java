package br.edu.utfpr.td.tsi.service;

import br.edu.utfpr.td.tsi.exception.ReceituarioNaoEncontradoException;
import br.edu.utfpr.td.tsi.model.Receituario;
import br.edu.utfpr.td.tsi.repository.ReceituarioRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class ReceituarioService {

    private static final Logger log = LoggerFactory.getLogger(ReceituarioService.class);

    private final ReceituarioRepository repository;
    private final MongoTemplate mongoTemplate;

    public ReceituarioService(ReceituarioRepository repository, MongoTemplate mongoTemplate) {
        this.repository = repository;
        this.mongoTemplate = mongoTemplate;
    }

    public List<Receituario> cadastrar(List<Receituario> receituarios) {
        if (receituarios == null || receituarios.isEmpty()) {
            throw new IllegalArgumentException("Nenhum receituario informado.");
        }

        log.debug("Recebido pedido de cadastro para {} receituario(s)", receituarios.size());

        receituarios.forEach(receituario -> receituario.setId(null));

        List<Receituario> salvos = repository.saveAll(receituarios);
        log.info("Cadastrados {} receituario(s) com sucesso", salvos.size());
        return salvos;
    }

    public List<Receituario> pesquisar(String nomePaciente, String nomeMedico, String cpfPaciente, String crmMedico) {
        Query query = new Query();
        boolean temFiltro = false;

        if (temValor(nomePaciente)) {
            query.addCriteria(Criteria.where("PACIENTE_NOME").regex(Pattern.quote(nomePaciente.trim()), "i"));
            temFiltro = true;
        }

        if (temValor(nomeMedico)) {
            query.addCriteria(Criteria.where("MEDICO_NOME").regex(Pattern.quote(nomeMedico.trim()), "i"));
            temFiltro = true;
        }

        if (temValor(cpfPaciente)) {
            String cpfLimpo = cpfPaciente.replaceAll("\\D", "");
            query.addCriteria(Criteria.where("PACIENTE_CPF").is(cpfLimpo));
            temFiltro = true;
        }

        if (temValor(crmMedico)) {
            query.addCriteria(Criteria.where("MEDICO_CRM").is(crmMedico.trim()));
            temFiltro = true;
        }

        if (!temFiltro) {
            return List.of();
        }

        return mongoTemplate.find(query, Receituario.class);
    }


    public Receituario buscarPorId(String id) {
        log.debug("Buscando receituario por ID: {}", id);
        return repository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Receituario não encontrado: {}", id);
                    return new ReceituarioNaoEncontradoException(id);
                });
    }

    public Receituario atualizar(String id, Receituario novosDados) {
        log.debug("Atualizando receituario ID: {}", id);

        Receituario receituario = buscarPorId(id);

        receituario.setDataEmissao(novosDados.getDataEmissao());
        receituario.setDataValidade(novosDados.getDataValidade());
        receituario.setTipoReceita(novosDados.getTipoReceita());
        receituario.setPacienteId(novosDados.getPacienteId());
        receituario.setPacienteNome(novosDados.getPacienteNome());
        receituario.setPacienteCpf(novosDados.getPacienteCpf());
        receituario.setMedicoId(novosDados.getMedicoId());
        receituario.setMedicoNome(novosDados.getMedicoNome());
        receituario.setMedicoCrm(novosDados.getMedicoCrm());
        receituario.setMedicoUf(novosDados.getMedicoUf());
        receituario.setMedicoEspecialidade(novosDados.getMedicoEspecialidade());
        receituario.setItensReceituario(novosDados.getItensReceituario());


        Receituario salvo = repository.save(receituario);
        log.info("Receituario atualizado: {} ({})", salvo.getMedicoCrm(), salvo.getMedicoNome(), salvo.getPacienteNome(), salvo.getPacienteCpf(), id);
        return salvo;
    }

    public void deletar(String id) {
        if (!temValor(id)) {
            throw new IllegalArgumentException("ID do receituario não informado.");
        }
        log.debug("Deletando receituario ID: {}", id);
        Optional<Receituario> receituario = repository.findById(id);
        if (!receituario.isPresent()) {
            log.warn("Receituario não encontrado para deleção: {}", id);
            throw new ReceituarioNaoEncontradoException(id);
        }
        repository.delete(receituario.get());
        log.info("Receituario deletado: {} ({})", receituario.get().getMedicoCrm(), id);
    }

    public long contar() {
        return repository.count();
    }

    private boolean temValor(String valor) {
        return valor != null && !valor.isBlank();
    }
}

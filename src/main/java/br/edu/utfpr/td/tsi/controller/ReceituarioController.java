package br.edu.utfpr.td.tsi.controller;

import br.edu.utfpr.td.tsi.model.Receituario;
import br.edu.utfpr.td.tsi.service.ReceituarioService;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/receituarios")
@CrossOrigin(origins = "*")
public class ReceituarioController {

    private static final Logger log = LoggerFactory.getLogger(ReceituarioController.class);

    private ReceituarioService service;

    public void ReceituarioController(ReceituarioService service) {
        this.service = service;
    }

    @PostMapping("/cadastrar")
    public List<Receituario> cadastrar(@Valid @RequestBody List<Receituario> receituarios) {
        log.info("POST /receituarios/cadastrar — {} receituario(s)", receituarios.size());
        return service.cadastrar(receituarios);
    }

    @GetMapping
    public List<Receituario> pesquisar(
            @RequestParam(required = false) String nomePaciente,
            @RequestParam (required = false) String nomeMedico,
            @RequestParam(required = false) String cpfPaciente,
            @RequestParam(required = false) String crm) {

        log.debug("GET /receituarios?nome={}&crm={}&especialidade={}",
                nomePaciente, nomeMedico, cpfPaciente, crm);

        if (vazio(nomePaciente) && vazio(nomeMedico) && vazio(cpfPaciente) && vazio(crm)) {
            return List.of();
        }

        return service.pesquisar(nomePaciente, nomeMedico, cpfPaciente, crm);
    }

    @GetMapping("/{id}")
    public Receituario buscar(@PathVariable String id) {
        log.debug("GET /receituarios/{}", id);
        return service.buscarPorId(id);
    }

    @PutMapping("/{id}")
    public Receituario atualizar(
            @PathVariable String id,
            @RequestBody Receituario receituario) {
        log.info("PUT /receituarios/{}", id);
        return service.atualizar(id, receituario);
    }

    @DeleteMapping("/{id}")
    public String deletar(@PathVariable String id) {
        log.info("DELETE /receituarios/{}", id);
        service.deletar(id);
        return "Receituario deletado com sucesso!";
    }

    private boolean vazio(String valor) {
        return valor == null || valor.isBlank();
    }
}
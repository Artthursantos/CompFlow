package br.ufpel.compflow.service;

import br.ufpel.compflow.dto.request.DisciplinaRequest;
import br.ufpel.compflow.dto.response.DisciplinaResponse;
import br.ufpel.compflow.entity.Disciplina;
import br.ufpel.compflow.repository.DisciplinaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DisciplinaService {

    private final DisciplinaRepository repo;

    public List<DisciplinaResponse> listarTodas() {
        return repo.findAll().stream().map(DisciplinaResponse::from).toList();
    }

    public DisciplinaResponse buscarPorId(Long id) {
        return DisciplinaResponse.from(getOuErro(id));
    }

    public DisciplinaResponse criar(DisciplinaRequest req) {
        if (repo.existsByCodigo(req.getCodigo())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Código já cadastrado");
        }
        Disciplina d = new Disciplina();
        d.setCodigo(req.getCodigo());
        d.setNome(req.getNome());
        d.setCreditos(req.getCreditos());
        d.setTipo(req.getTipo());
        return DisciplinaResponse.from(repo.save(d));
    }

    public DisciplinaResponse atualizar(Long id, DisciplinaRequest req) {
        Disciplina d = getOuErro(id);
        d.setCodigo(req.getCodigo());
        d.setNome(req.getNome());
        d.setCreditos(req.getCreditos());
        d.setTipo(req.getTipo());
        return DisciplinaResponse.from(repo.save(d));
    }

    public void excluir(Long id) {
        getOuErro(id);
        repo.deleteById(id);
    }

    public Disciplina getOuErro(Long id) {
        return repo.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Disciplina não encontrada"));
    }
}

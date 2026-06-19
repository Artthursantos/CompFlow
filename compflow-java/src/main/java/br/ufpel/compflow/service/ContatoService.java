package br.ufpel.compflow.service;

import br.ufpel.compflow.dto.request.ContatoRequest;
import br.ufpel.compflow.dto.response.ContatoResponse;
import br.ufpel.compflow.entity.Contato;
import br.ufpel.compflow.repository.ContatoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ContatoService {

    private final ContatoRepository repo;

    public List<ContatoResponse> listarTodos() {
        return repo.findAll().stream().map(ContatoResponse::from).toList();
    }

    public ContatoResponse criar(ContatoRequest req) {
        Contato c = new Contato();
        preencher(c, req);
        return ContatoResponse.from(repo.save(c));
    }

    public ContatoResponse atualizar(Long id, ContatoRequest req) {
        Contato c = getOuErro(id);
        preencher(c, req);
        return ContatoResponse.from(repo.save(c));
    }

    public void excluir(Long id) {
        getOuErro(id);
        repo.deleteById(id);
    }

    private void preencher(Contato c, ContatoRequest req) {
        c.setNome(req.getNome());
        c.setCargo(req.getCargo());
        c.setCategoria(req.getCategoria());
        c.setEmail(req.getEmail());
        c.setDepartamento(req.getDepartamento());
    }

    private Contato getOuErro(Long id) {
        return repo.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Contato não encontrado"));
    }
}

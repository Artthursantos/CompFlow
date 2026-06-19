package br.ufpel.compflow.service;

import br.ufpel.compflow.dto.request.EventoRequest;
import br.ufpel.compflow.dto.response.EventoResponse;
import br.ufpel.compflow.entity.Evento;
import br.ufpel.compflow.repository.EventoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventoService {

    private final EventoRepository repo;

    public List<EventoResponse> listarTodos() {
        return repo.findAll().stream().map(EventoResponse::from).toList();
    }

    public EventoResponse criar(EventoRequest req) {
        Evento e = new Evento();
        e.setTitulo(req.getTitulo());
        e.setDataInicio(req.getDataInicio());
        e.setDataFim(req.getDataFim());
        e.setTipo(req.getTipo());
        return EventoResponse.from(repo.save(e));
    }

    public EventoResponse atualizar(Long id, EventoRequest req) {
        Evento e = getOuErro(id);
        e.setTitulo(req.getTitulo());
        e.setDataInicio(req.getDataInicio());
        e.setDataFim(req.getDataFim());
        e.setTipo(req.getTipo());
        return EventoResponse.from(repo.save(e));
    }

    public void excluir(Long id) {
        getOuErro(id);
        repo.deleteById(id);
    }

    private Evento getOuErro(Long id) {
        return repo.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Evento não encontrado"));
    }
}

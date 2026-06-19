package br.ufpel.compflow.controller;

import br.ufpel.compflow.dto.request.EventoRequest;
import br.ufpel.compflow.dto.response.EventoResponse;
import br.ufpel.compflow.service.EventoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/eventos")
@RequiredArgsConstructor
public class EventoController {

    private final EventoService service;

    @GetMapping
    public List<EventoResponse> listar() {
        return service.listarTodos();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EventoResponse criar(@RequestBody @Valid EventoRequest req) {
        return service.criar(req);
    }

    @PutMapping("/{id}")
    public EventoResponse atualizar(
            @PathVariable Long id,
            @RequestBody @Valid EventoRequest req) {
        return service.atualizar(id, req);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id) {
        service.excluir(id);
    }
}

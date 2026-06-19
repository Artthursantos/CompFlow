package br.ufpel.compflow.controller;

import br.ufpel.compflow.dto.request.ContatoRequest;
import br.ufpel.compflow.dto.response.ContatoResponse;
import br.ufpel.compflow.service.ContatoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/contatos")
@RequiredArgsConstructor
public class ContatoController {

    private final ContatoService service;

    @GetMapping
    public List<ContatoResponse> listar() {
        return service.listarTodos();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ContatoResponse criar(@RequestBody @Valid ContatoRequest req) {
        return service.criar(req);
    }

    @PutMapping("/{id}")
    public ContatoResponse atualizar(
            @PathVariable Long id,
            @RequestBody @Valid ContatoRequest req) {
        return service.atualizar(id, req);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id) {
        service.excluir(id);
    }
}

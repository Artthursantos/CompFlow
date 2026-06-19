package br.ufpel.compflow.controller;

import br.ufpel.compflow.dto.request.DisciplinaRequest;
import br.ufpel.compflow.dto.response.DisciplinaResponse;
import br.ufpel.compflow.service.DisciplinaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/disciplinas")
@RequiredArgsConstructor
public class DisciplinaController {

    private final DisciplinaService service;

    @GetMapping
    public List<DisciplinaResponse> listar() {
        return service.listarTodas();
    }

    @GetMapping("/{id}")
    public DisciplinaResponse buscar(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DisciplinaResponse criar(@RequestBody @Valid DisciplinaRequest req) {
        return service.criar(req);
    }

    @PutMapping("/{id}")
    public DisciplinaResponse atualizar(
            @PathVariable Long id,
            @RequestBody @Valid DisciplinaRequest req) {
        return service.atualizar(id, req);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id) {
        service.excluir(id);
    }
}

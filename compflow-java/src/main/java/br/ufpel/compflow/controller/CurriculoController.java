package br.ufpel.compflow.controller;

import br.ufpel.compflow.dto.request.CurriculoRequest;
import br.ufpel.compflow.dto.response.CurriculoResponse;
import br.ufpel.compflow.service.CurriculoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/curriculos")
@RequiredArgsConstructor
public class CurriculoController {

    private final CurriculoService service;

    @GetMapping
    public List<CurriculoResponse> listar() {
        return service.listarTodos();
    }

    @GetMapping("/{id}")
    public CurriculoResponse buscar(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CurriculoResponse criar(@RequestBody @Valid CurriculoRequest req) {
        return service.criar(req);
    }

    @PutMapping("/{id}")
    public CurriculoResponse atualizar(
            @PathVariable Long id,
            @RequestBody @Valid CurriculoRequest req) {
        return service.atualizar(id, req);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id) {
        service.excluir(id);
    }
}

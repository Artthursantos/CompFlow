package br.ufpel.compflow.controller;

import br.ufpel.compflow.dto.request.ProgressoRequest;
import br.ufpel.compflow.dto.response.ProgressoResponse;
import br.ufpel.compflow.service.ProgressoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/progresso")
@RequiredArgsConstructor
public class ProgressoController {

    private final ProgressoService service;

    @GetMapping("/{usuarioId}")
    public ProgressoResponse buscar(@PathVariable Long usuarioId) {
        return service.buscarProgresso(usuarioId);
    }

    @PutMapping("/{usuarioId}/{disciplinaId}")
    public void atualizar(
            @PathVariable Long usuarioId,
            @PathVariable Long disciplinaId,
            @RequestBody @Valid ProgressoRequest req) {
        service.atualizarStatus(usuarioId, disciplinaId, req);
    }
}

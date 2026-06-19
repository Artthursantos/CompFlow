package br.ufpel.compflow.controller;

import br.ufpel.compflow.dto.request.AtualizarNomeRequest;
import br.ufpel.compflow.dto.response.UsuarioResponse;
import br.ufpel.compflow.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService service;

    @GetMapping
    public List<UsuarioResponse> listar() {
        return service.listarTodos();
    }

    @GetMapping("/{id}")
    public UsuarioResponse buscar(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @PatchMapping("/{id}/nome")
    public UsuarioResponse atualizarNome(
            @PathVariable Long id,
            @RequestBody @Valid AtualizarNomeRequest req) {
        return service.atualizarNome(id, req.getNome());
    }
}

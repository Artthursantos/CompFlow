package br.ufpel.compflow.controller;

import br.ufpel.compflow.dto.response.CursoResponse;
import br.ufpel.compflow.service.CursoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/cursos")
@RequiredArgsConstructor
public class CursoController {

    private final CursoService service;

    @GetMapping
    public List<CursoResponse> listar() {
        return service.listarTodos();
    }
}

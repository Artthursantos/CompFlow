package br.ufpel.compflow.controller;

import br.ufpel.compflow.dto.request.CriarUsuarioRequest;
import br.ufpel.compflow.dto.request.LoginRequest;
import br.ufpel.compflow.dto.response.UsuarioResponse;
import br.ufpel.compflow.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UsuarioService service;

    @PostMapping("/login")
    public UsuarioResponse login(@RequestBody @Valid LoginRequest req) {
        return service.login(req);
    }

    @PostMapping("/registrar")
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioResponse registrar(@RequestBody @Valid CriarUsuarioRequest req) {
        return service.registrar(req);
    }
}

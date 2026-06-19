package br.ufpel.compflow.service;

import br.ufpel.compflow.dto.response.CursoResponse;
import br.ufpel.compflow.repository.CursoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CursoService {

    private final CursoRepository repo;

    public List<CursoResponse> listarTodos() {
        return repo.findAll().stream().map(CursoResponse::from).toList();
    }
}

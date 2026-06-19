package br.ufpel.compflow.service;

import br.ufpel.compflow.dto.request.CurriculoRequest;
import br.ufpel.compflow.dto.response.CurriculoResponse;
import br.ufpel.compflow.dto.response.DisciplinaResponse;
import br.ufpel.compflow.entity.*;
import br.ufpel.compflow.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CurriculoService {

    private final CurriculoRepository           curriculoRepo;
    private final CurriculoDisciplinaRepository cdRepo;
    private final DisciplinaRepository          disciplinaRepo;
    private final CursoRepository               cursoRepo;

    public List<CurriculoResponse> listarTodos() {
        return curriculoRepo.findAll().stream().map(this::toResponse).toList();
    }

    public CurriculoResponse buscarPorId(Long id) {
        return toResponse(getOuErro(id));
    }

    @Transactional
    public CurriculoResponse criar(CurriculoRequest req) {
        Curriculo c = new Curriculo();
        c.setNome(req.getNome());
        if (req.getCursoId() != null) {
            c.setCurso(cursoRepo.findById(req.getCursoId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Curso não encontrado")));
        }
        curriculoRepo.save(c);
        salvarSemestres(c, req.getSemestres());
        return toResponse(c);
    }

    @Transactional
    public CurriculoResponse atualizar(Long id, CurriculoRequest req) {
        Curriculo c = getOuErro(id);
        c.setNome(req.getNome());
        if (req.getCursoId() != null) {
            c.setCurso(cursoRepo.findById(req.getCursoId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Curso não encontrado")));
        }
        cdRepo.deleteByCurriculoId(id);
        cdRepo.flush();
        salvarSemestres(c, req.getSemestres());
        return toResponse(curriculoRepo.save(c));
    }

    public void excluir(Long id) {
        getOuErro(id);
        curriculoRepo.deleteById(id);
    }

    private void salvarSemestres(Curriculo curriculo, List<CurriculoRequest.ItemSemestre> semestres) {
        if (semestres == null) return;
        for (CurriculoRequest.ItemSemestre item : semestres) {
            for (Long discId : item.getDisciplinaIds()) {
                Disciplina d = disciplinaRepo.findById(discId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Disciplina não encontrada: " + discId));
                CurriculoDisciplina cd = new CurriculoDisciplina();
                cd.setCurriculo(curriculo);
                cd.setDisciplina(d);
                cd.setSemestre(item.getSemestre());
                cdRepo.save(cd);
            }
        }
    }

    private CurriculoResponse toResponse(Curriculo c) {
        List<CurriculoDisciplina> cds = cdRepo.findByCurriculoIdOrderBySemestre(c.getId());
        Map<Integer, List<DisciplinaResponse>> porSemestre = new TreeMap<>();
        for (CurriculoDisciplina cd : cds) {
            porSemestre
                .computeIfAbsent(cd.getSemestre(), k -> new ArrayList<>())
                .add(DisciplinaResponse.from(cd.getDisciplina()));
        }
        List<CurriculoResponse.SemestreResponse> semestresResp = porSemestre.entrySet().stream()
            .map(e -> new CurriculoResponse.SemestreResponse(e.getKey(), e.getValue()))
            .collect(Collectors.toList());
        return new CurriculoResponse(
            c.getId(), c.getNome(),
            c.getCurso() != null ? c.getCurso().getNome() : null,
            semestresResp
        );
    }

    private Curriculo getOuErro(Long id) {
        return curriculoRepo.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Currículo não encontrado"));
    }
}

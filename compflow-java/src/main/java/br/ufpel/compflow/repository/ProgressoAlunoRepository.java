package br.ufpel.compflow.repository;

import br.ufpel.compflow.entity.ProgressoAluno;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface ProgressoAlunoRepository extends JpaRepository<ProgressoAluno, Long> {
    List<ProgressoAluno> findByUsuarioId(Long usuarioId);
    Optional<ProgressoAluno> findByUsuarioIdAndDisciplinaId(Long usuarioId, Long disciplinaId);
    long countByUsuarioIdAndStatus(Long usuarioId, ProgressoAluno.Status status);
}

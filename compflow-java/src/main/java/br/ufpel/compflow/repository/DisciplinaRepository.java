package br.ufpel.compflow.repository;

import br.ufpel.compflow.entity.Disciplina;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DisciplinaRepository extends JpaRepository<Disciplina, Long> {
    List<Disciplina> findByNomeContainingIgnoreCase(String nome);
    boolean existsByCodigo(String codigo);
}

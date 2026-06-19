package br.ufpel.compflow.repository;

import br.ufpel.compflow.entity.Curriculo;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CurriculoRepository extends JpaRepository<Curriculo, Long> {
    List<Curriculo> findByCursoId(Long cursoId);
}

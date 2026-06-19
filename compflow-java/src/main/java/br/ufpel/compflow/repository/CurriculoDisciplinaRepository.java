package br.ufpel.compflow.repository;

import br.ufpel.compflow.entity.CurriculoDisciplina;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

public interface CurriculoDisciplinaRepository extends JpaRepository<CurriculoDisciplina, Long> {
    List<CurriculoDisciplina> findByCurriculoIdOrderBySemestre(Long curriculoId);

    @Transactional
    void deleteByCurriculoId(Long curriculoId);
}

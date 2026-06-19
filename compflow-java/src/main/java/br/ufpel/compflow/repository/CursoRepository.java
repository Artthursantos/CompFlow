package br.ufpel.compflow.repository;

import br.ufpel.compflow.entity.Curso;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CursoRepository extends JpaRepository<Curso, Long> {}

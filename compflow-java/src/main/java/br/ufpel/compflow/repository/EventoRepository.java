package br.ufpel.compflow.repository;

import br.ufpel.compflow.entity.Evento;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;

public interface EventoRepository extends JpaRepository<Evento, Long> {
    List<Evento> findByDataInicioGreaterThanEqualOrderByDataInicio(LocalDate data);
    List<Evento> findByTipo(Evento.Tipo tipo);
}

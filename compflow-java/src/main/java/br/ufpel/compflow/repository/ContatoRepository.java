package br.ufpel.compflow.repository;

import br.ufpel.compflow.entity.Contato;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ContatoRepository extends JpaRepository<Contato, Long> {
    List<Contato> findByCategoria(Contato.Categoria categoria);
    List<Contato> findByNomeContainingIgnoreCase(String nome);
}

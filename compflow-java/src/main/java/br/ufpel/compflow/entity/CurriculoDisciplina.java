package br.ufpel.compflow.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "curriculo_disciplinas")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class CurriculoDisciplina {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "curriculo_id", nullable = false)
    private Curriculo curriculo;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "disciplina_id", nullable = false)
    private Disciplina disciplina;

    @Column(nullable = false)
    private Integer semestre;
}

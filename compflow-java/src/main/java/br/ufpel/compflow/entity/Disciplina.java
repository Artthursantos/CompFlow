package br.ufpel.compflow.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "disciplinas")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Disciplina {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 20)
    private String codigo;

    @Column(nullable = false, length = 150)
    private String nome;

    @Column(nullable = false)
    private Integer creditos;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Tipo tipo = Tipo.INTERNO;

    public enum Tipo { INTERNO, EXTERNO }
}

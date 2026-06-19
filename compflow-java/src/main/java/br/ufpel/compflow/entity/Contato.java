package br.ufpel.compflow.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "contatos")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Contato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String nome;

    @Column(length = 150)
    private String cargo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Categoria categoria;

    @Column(nullable = false, length = 150)
    private String email;

    @Column(length = 150)
    private String departamento;

    public enum Categoria { DOCENTE, APOIO }
}

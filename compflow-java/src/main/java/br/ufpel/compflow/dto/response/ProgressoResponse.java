package br.ufpel.compflow.dto.response;

import lombok.*;
import java.util.List;

@Getter @Setter @AllArgsConstructor
public class ProgressoResponse {
    private Long usuarioId;
    private String nomeUsuario;
    private int totalDisciplinas;
    private int aprovadas;
    private int creditorConcluidos;
    private int totalCreditos;
    private int percentual;
    private List<SemestreProgressoResponse> semestres;

    @Getter @Setter @AllArgsConstructor
    public static class SemestreProgressoResponse {
        private Integer semestre;
        private List<DisciplinaProgressoResponse> disciplinas;
    }

    @Getter @Setter @AllArgsConstructor
    public static class DisciplinaProgressoResponse {
        private Long id;
        private String codigo;
        private String nome;
        private Integer creditos;
        private String status;
    }
}

package br.ufpel.compflow.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;
import java.util.List;

@Getter @Setter
public class CurriculoRequest {

    @NotBlank
    private String nome;

    private Long cursoId;

    @NotNull
    private List<ItemSemestre> semestres;

    @Getter @Setter
    public static class ItemSemestre {
        @NotNull @Min(1)
        private Integer semestre;

        @NotNull
        private List<Long> disciplinaIds;
    }
}

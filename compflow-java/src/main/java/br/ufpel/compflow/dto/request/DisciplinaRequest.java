package br.ufpel.compflow.dto.request;

import br.ufpel.compflow.entity.Disciplina;
import jakarta.validation.constraints.*;
import lombok.*;

@Getter @Setter
public class DisciplinaRequest {
    @NotBlank
    private String codigo;

    @NotBlank
    private String nome;

    @NotNull @Min(1)
    private Integer creditos;

    @NotNull
    private Disciplina.Tipo tipo;
}

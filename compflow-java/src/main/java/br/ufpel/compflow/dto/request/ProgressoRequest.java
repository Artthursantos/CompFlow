package br.ufpel.compflow.dto.request;

import br.ufpel.compflow.entity.ProgressoAluno;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter @Setter
public class ProgressoRequest {
    @NotNull
    private ProgressoAluno.Status status;
}

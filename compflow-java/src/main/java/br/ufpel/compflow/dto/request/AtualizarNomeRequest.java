package br.ufpel.compflow.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter @Setter
public class AtualizarNomeRequest {
    @NotBlank
    private String nome;
}

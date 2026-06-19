package br.ufpel.compflow.dto.request;

import br.ufpel.compflow.entity.Contato;
import jakarta.validation.constraints.*;
import lombok.*;

@Getter @Setter
public class ContatoRequest {
    @NotBlank
    private String nome;

    private String cargo;

    @NotNull
    private Contato.Categoria categoria;

    @NotBlank @Email
    private String email;

    private String departamento;
}

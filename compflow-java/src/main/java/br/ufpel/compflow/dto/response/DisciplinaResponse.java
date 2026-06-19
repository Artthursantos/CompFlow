package br.ufpel.compflow.dto.response;

import br.ufpel.compflow.entity.Disciplina;
import lombok.*;

@Getter @Setter @AllArgsConstructor
public class DisciplinaResponse {
    private Long id;
    private String codigo;
    private String nome;
    private Integer creditos;
    private String tipo;

    public static DisciplinaResponse from(Disciplina d) {
        return new DisciplinaResponse(
            d.getId(), d.getCodigo(), d.getNome(),
            d.getCreditos(), d.getTipo().name()
        );
    }
}

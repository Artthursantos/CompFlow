package br.ufpel.compflow.dto.response;

import br.ufpel.compflow.entity.Curso;
import lombok.*;

@Getter @Setter @AllArgsConstructor
public class CursoResponse {
    private Long id;
    private String nome;
    private String codigo;

    public static CursoResponse from(Curso c) {
        return new CursoResponse(c.getId(), c.getNome(), c.getCodigo());
    }
}

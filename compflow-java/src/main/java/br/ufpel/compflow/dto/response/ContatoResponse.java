package br.ufpel.compflow.dto.response;

import br.ufpel.compflow.entity.Contato;
import lombok.*;

@Getter @Setter @AllArgsConstructor
public class ContatoResponse {
    private Long id;
    private String nome;
    private String cargo;
    private String categoria;
    private String email;
    private String departamento;

    public static ContatoResponse from(Contato c) {
        return new ContatoResponse(
            c.getId(), c.getNome(), c.getCargo(),
            c.getCategoria().name(), c.getEmail(),
            c.getDepartamento()
        );
    }
}

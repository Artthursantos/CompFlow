package br.ufpel.compflow.dto.response;

import br.ufpel.compflow.entity.Usuario;
import lombok.*;

@Getter @Setter @AllArgsConstructor
public class UsuarioResponse {
    private Long id;
    private String nome;
    private String email;
    private String role;
    private String nomeCurso;

    public static UsuarioResponse from(Usuario u) {
        return new UsuarioResponse(
            u.getId(),
            u.getNome(),
            u.getEmail(),
            u.getRole().name(),
            u.getCurso() != null ? u.getCurso().getNome() : null
        );
    }
}

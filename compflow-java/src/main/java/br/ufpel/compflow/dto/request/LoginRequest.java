package br.ufpel.compflow.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter @Setter
public class LoginRequest {
    @NotBlank @Email
    private String email;

    @NotBlank
    private String senha;
}

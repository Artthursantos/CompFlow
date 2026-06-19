package br.ufpel.compflow.dto.request;

import br.ufpel.compflow.entity.Evento;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;

@Getter @Setter
public class EventoRequest {
    @NotBlank
    private String titulo;

    @NotNull
    private LocalDate dataInicio;

    private LocalDate dataFim;

    @NotNull
    private Evento.Tipo tipo;
}

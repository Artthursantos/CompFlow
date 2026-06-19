package br.ufpel.compflow.dto.response;

import br.ufpel.compflow.entity.Evento;
import lombok.*;
import java.time.LocalDate;

@Getter @Setter @AllArgsConstructor
public class EventoResponse {
    private Long id;
    private String titulo;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private String tipo;

    public static EventoResponse from(Evento e) {
        return new EventoResponse(
            e.getId(), e.getTitulo(),
            e.getDataInicio(), e.getDataFim(),
            e.getTipo().name()
        );
    }
}

package br.com.cwi.crescer.api.controller.response;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AfazerResumidoResponse {

    private Long id;
    private String nome;
    private boolean finalizado;
}

package br.com.cwi.crescer.api.controller.response;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HabitoResumidoResponse {

    private Long id;
    private String nome;
    private Integer execucoes;
}

package br.com.cwi.crescer.api.controller.response;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DiariaResumidaResponse {

    private Long id;
    private String nome;
    private boolean finalizado;
    private String hora;
}

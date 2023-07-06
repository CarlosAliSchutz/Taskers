package br.com.cwi.crescer.api.controller.response;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NotificacaoResponse {

    private Long id;
    private String titulo;
    private String texto;
}

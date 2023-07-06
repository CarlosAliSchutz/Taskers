package br.com.cwi.crescer.api.controller.response;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioConquistaResponse {

    private Long id;
    private int progresso;
    private boolean concluida;
    private ConquistaResponse conquista;
}

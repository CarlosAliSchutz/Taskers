package br.com.cwi.crescer.api.controller.response;

import br.com.cwi.crescer.api.domain.Dificuldade;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AfazerDetalhadoResponse {

    private Long id;
    private String nome;
    private String descricao;
    private Dificuldade dificuldade;
    private boolean finalizado;

}

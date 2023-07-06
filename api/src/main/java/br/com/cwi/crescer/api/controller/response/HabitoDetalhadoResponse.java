package br.com.cwi.crescer.api.controller.response;

import br.com.cwi.crescer.api.domain.Dificuldade;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HabitoDetalhadoResponse {

    private Long id;
    private String nome;
    private String descricao;
    private Dificuldade dificuldade;
    private Integer execucoes;

}

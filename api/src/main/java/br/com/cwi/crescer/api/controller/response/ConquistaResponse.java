package br.com.cwi.crescer.api.controller.response;

import br.com.cwi.crescer.api.domain.Dificuldade;
import br.com.cwi.crescer.api.domain.TipoConquista;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ConquistaResponse {
    private Long id;
    private String nome;
    private String descricao;
    private int objetivo;
    private TipoConquista tipo;
    private Dificuldade dificuldade;

}

package br.com.cwi.crescer.api.controller.response;

import br.com.cwi.crescer.api.domain.Dificuldade;
import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DiariaDetalhadaResponse {

    private Long id;
    private String nome;
    private String descricao;
    private Dificuldade dificuldade;
    private boolean finalizado;
    private String hora;
    private List<DiaResponse> dias;

}

package br.com.cwi.crescer.api.controller.response;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MissaoResponse {
    private Long id;
    private String nome;
    private String descricao;
    private boolean concluida;
    private Integer execucoesRealizadas;
    private Integer execucoesNecessarias;
}

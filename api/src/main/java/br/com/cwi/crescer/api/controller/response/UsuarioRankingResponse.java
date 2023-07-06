package br.com.cwi.crescer.api.controller.response;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioRankingResponse {

    private Long posicaoRanking;
    private String nomeCompleto;
    private String imagemPerfil;
    private Integer experiencia;

}

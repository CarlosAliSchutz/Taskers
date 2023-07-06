package br.com.cwi.crescer.api.controller.response;

import br.com.cwi.crescer.api.domain.TipoCosmetico;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CosmeticoResponse {

    private Long id;
    private String nome;
    private TipoCosmetico tipo;
    private Integer valor;

}

package br.com.cwi.crescer.api.mapper;

import br.com.cwi.crescer.api.controller.response.AfazerDetalhadoResponse;
import br.com.cwi.crescer.api.domain.Afazer;
import lombok.experimental.UtilityClass;

@UtilityClass
public class AfazerDetalhadoResponseMapper {

    public static AfazerDetalhadoResponse toResponse(Afazer entity){
        return AfazerDetalhadoResponse
                .builder()
                .id(entity.getId())
                .nome(entity.getNome())
                .descricao(entity.getDescricao())
                .dificuldade(entity.getDificuldade())
                .finalizado(entity.isFinalizado())
                .build();
    }
}

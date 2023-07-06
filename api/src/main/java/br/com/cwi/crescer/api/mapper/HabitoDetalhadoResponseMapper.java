package br.com.cwi.crescer.api.mapper;

import br.com.cwi.crescer.api.controller.response.HabitoDetalhadoResponse;
import br.com.cwi.crescer.api.domain.Habito;
import lombok.experimental.UtilityClass;

@UtilityClass
public class HabitoDetalhadoResponseMapper {

    public static HabitoDetalhadoResponse toResponse(Habito entity){
        return HabitoDetalhadoResponse
                .builder()
                .id(entity.getId())
                .nome(entity.getNome())
                .descricao(entity.getDescricao())
                .dificuldade(entity.getDificuldade())
                .execucoes(entity.getExecucoes())
                .build();
    }
}

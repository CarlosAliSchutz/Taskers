package br.com.cwi.crescer.api.mapper;

import br.com.cwi.crescer.api.controller.response.AfazerResumidoResponse;
import br.com.cwi.crescer.api.domain.Afazer;
import lombok.experimental.UtilityClass;

@UtilityClass
public class AfazerResumidoResponseMapper {

    public static AfazerResumidoResponse toResponse(Afazer entity){
        return AfazerResumidoResponse
                .builder()
                .id(entity.getId())
                .nome(entity.getNome())
                .finalizado(entity.isFinalizado())
                .build();
    }
}

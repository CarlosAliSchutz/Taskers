package br.com.cwi.crescer.api.mapper;

import br.com.cwi.crescer.api.controller.response.HabitoResumidoResponse;
import br.com.cwi.crescer.api.domain.Habito;
import lombok.experimental.UtilityClass;

@UtilityClass
public class HabitoResumidoResponseMapper {

    public static HabitoResumidoResponse toResponse(Habito entity){
        return HabitoResumidoResponse
                .builder()
                .id(entity.getId())
                .nome(entity.getNome())
                .execucoes(entity.getExecucoes())
                .build();
    }
}

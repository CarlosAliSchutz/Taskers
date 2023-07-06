package br.com.cwi.crescer.api.mapper;

import br.com.cwi.crescer.api.controller.response.DiaResponse;
import br.com.cwi.crescer.api.domain.Dia;
import lombok.experimental.UtilityClass;

@UtilityClass
public class DiaResponseMapper {

    public static DiaResponse toResponse(Dia dia){
        return DiaResponse
                .builder()
                .id(dia.getId())
                .nome(dia.getNome())
                .build();
    }
}

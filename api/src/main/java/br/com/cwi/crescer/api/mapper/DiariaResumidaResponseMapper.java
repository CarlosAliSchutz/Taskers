package br.com.cwi.crescer.api.mapper;

import br.com.cwi.crescer.api.controller.response.DiariaResumidaResponse;
import br.com.cwi.crescer.api.domain.Diaria;
import lombok.experimental.UtilityClass;

import java.time.format.DateTimeFormatter;

@UtilityClass
public class DiariaResumidaResponseMapper {

    public static DiariaResumidaResponse toResponse(Diaria entity){
        return DiariaResumidaResponse
                .builder()
                .id(entity.getId())
                .nome(entity.getNome())
                .finalizado(entity.isFinalizado())
                .hora(entity.getHora().format(DateTimeFormatter.ofPattern("HH:mm")))
                .build();
    }
}

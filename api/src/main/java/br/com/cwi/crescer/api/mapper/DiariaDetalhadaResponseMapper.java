package br.com.cwi.crescer.api.mapper;

import br.com.cwi.crescer.api.controller.response.DiaResponse;
import br.com.cwi.crescer.api.controller.response.DiariaDetalhadaResponse;
import br.com.cwi.crescer.api.domain.Dia;
import br.com.cwi.crescer.api.domain.Diaria;
import lombok.experimental.UtilityClass;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class DiariaDetalhadaResponseMapper {

    public static DiariaDetalhadaResponse toResponse(Diaria entity){
        return DiariaDetalhadaResponse
                .builder()
                .id(entity.getId())
                .nome(entity.getNome())
                .descricao(entity.getDescricao())
                .dificuldade(entity.getDificuldade())
                .finalizado(entity.isFinalizado())
                .hora(entity.getHora().format(DateTimeFormatter.ofPattern("HH:mm")))
                .dias(buildDiaResponse(entity.getDias()))
                .build();
    }

    public static List<DiaResponse> buildDiaResponse(List<Dia> dias) {
        return dias
                .stream()
                .map(DiaResponseMapper::toResponse)
                .collect(Collectors.toList());
    }
}

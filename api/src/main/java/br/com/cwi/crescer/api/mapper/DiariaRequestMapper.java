package br.com.cwi.crescer.api.mapper;

import br.com.cwi.crescer.api.controller.request.DiariaRequest;
import br.com.cwi.crescer.api.domain.Dia;
import br.com.cwi.crescer.api.domain.Diaria;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class DiariaRequestMapper {

    public static Diaria toEntity(DiariaRequest entity){
        return Diaria
                .builder()
                .nome(entity.getNome())
                .descricao(entity.getDescricao())
                .dificuldade(entity.getDificuldade())
                .hora(entity.getHora())
                .dias(buildDiasDiaria(entity.getDias()))
                .build();
    }

        public static List<Dia> buildDiasDiaria(List<Long> diasId) {
        return diasId
                .stream()
                .map(id -> Dia.builder().id(id).build())
                .collect(Collectors.toList());
    }
}

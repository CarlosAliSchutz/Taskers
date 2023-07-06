package br.com.cwi.crescer.api.mapper;

import br.com.cwi.crescer.api.controller.request.HabitoRequest;
import br.com.cwi.crescer.api.domain.Habito;
import lombok.experimental.UtilityClass;

@UtilityClass
public class HabitoRequestMapper {

    public static Habito toEntity(HabitoRequest request) {
        return Habito.builder()
                .nome(request.getNome())
                .descricao(request.getDescricao())
                .dificuldade(request.getDificuldade())
                .build();
    }

}

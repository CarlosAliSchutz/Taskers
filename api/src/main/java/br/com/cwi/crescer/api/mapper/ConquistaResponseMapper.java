package br.com.cwi.crescer.api.mapper;

import br.com.cwi.crescer.api.controller.response.ConquistaResponse;
import br.com.cwi.crescer.api.domain.Conquista;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ConquistaResponseMapper {

    public static ConquistaResponse toResponse(Conquista entity){
        return ConquistaResponse
                .builder()
                .id(entity.getId())
                .dificuldade(entity.getDificuldade())
                .tipo(entity.getTipo())
                .descricao(entity.getDescricao())
                .nome(entity.getNome())
                .objetivo(entity.getObjetivo())
                .build();
    }
}
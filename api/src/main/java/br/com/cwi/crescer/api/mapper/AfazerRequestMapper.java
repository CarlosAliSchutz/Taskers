package br.com.cwi.crescer.api.mapper;

import br.com.cwi.crescer.api.controller.request.AfazerRequest;
import br.com.cwi.crescer.api.domain.Afazer;
import lombok.experimental.UtilityClass;

@UtilityClass
public class AfazerRequestMapper {

    public static Afazer toEntity(AfazerRequest entity){
        return Afazer
                .builder()
                .nome(entity.getNome())
                .descricao(entity.getDescricao())
                .dificuldade(entity.getDificuldade())
                .build();
    }
}

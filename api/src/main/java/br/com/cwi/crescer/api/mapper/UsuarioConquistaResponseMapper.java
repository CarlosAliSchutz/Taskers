package br.com.cwi.crescer.api.mapper;

import br.com.cwi.crescer.api.controller.response.UsuarioConquistaResponse;
import br.com.cwi.crescer.api.domain.UsuarioConquista;
import lombok.experimental.UtilityClass;

@UtilityClass
public class UsuarioConquistaResponseMapper {

    public static UsuarioConquistaResponse toResponse(UsuarioConquista entity){
        return UsuarioConquistaResponse
                .builder()
                .id(entity.getId())
                .progresso(entity.getProgresso())
                .conquista(ConquistaResponseMapper.toResponse(entity.getConquista()))
                .concluida(entity.isConcluida())
                .build();
    }
}
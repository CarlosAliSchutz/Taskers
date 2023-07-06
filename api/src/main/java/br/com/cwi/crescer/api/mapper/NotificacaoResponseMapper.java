package br.com.cwi.crescer.api.mapper;

import br.com.cwi.crescer.api.controller.response.NotificacaoResponse;
import br.com.cwi.crescer.api.domain.Notificacao;
import lombok.experimental.UtilityClass;

@UtilityClass
public class NotificacaoResponseMapper {

    public static NotificacaoResponse toResponse(Notificacao entity){
        return NotificacaoResponse
                .builder()
                .id(entity.getId())
                .titulo(entity.getTitulo())
                .texto(entity.getTexto())
                .build();
    }
}
package br.com.cwi.crescer.api.mapper;

import br.com.cwi.crescer.api.controller.response.UsuarioRankingResponse;
import br.com.cwi.crescer.api.security.domain.Usuario;
import lombok.experimental.UtilityClass;

@UtilityClass
public class UsuarioRankingResponseMapper {

    public static UsuarioRankingResponse toResponse(Usuario entity, Long posicaoRanking){
        return UsuarioRankingResponse
                .builder()
                .posicaoRanking(posicaoRanking)
                .nomeCompleto(entity.getNomeCompleto())
                .experiencia(entity.getExperiencia())
                .imagemPerfil(entity.getImagemPerfil())
                .build();
    }
}
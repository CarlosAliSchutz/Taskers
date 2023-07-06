package br.com.cwi.crescer.api.mapper;

import br.com.cwi.crescer.api.controller.response.MissaoResponse;
import br.com.cwi.crescer.api.domain.MissaoAfazer;
import br.com.cwi.crescer.api.domain.MissaoDiaria;
import br.com.cwi.crescer.api.domain.MissaoHabito;
import lombok.experimental.UtilityClass;

@UtilityClass
public class MissaoResponseMapper {

    public static MissaoResponse toResponse(MissaoAfazer entity){
        return MissaoResponse
                .builder()
                .id(entity.getId())
                .nome(entity.getNome())
                .descricao(entity.getDescricao())
                .concluida(entity.isConcluida())
                .build();
    }
    public static MissaoResponse toResponse(MissaoDiaria entity){
        return MissaoResponse
                .builder()
                .id(entity.getId())
                .nome(entity.getNome())
                .descricao(entity.getDescricao())
                .concluida(entity.isConcluida())
                .build();
    }
    public static MissaoResponse toResponse(MissaoHabito entity){
        return MissaoResponse
                .builder()
                .id(entity.getId())
                .nome(entity.getNome())
                .descricao(entity.getDescricao())
                .concluida(entity.isConcluida())
                .execucoesRealizadas(retornaNumeroDeExecucaoDoHabitoLimitado(entity))
                .execucoesNecessarias(entity.getExecucoesNecessarias())
                .build();
    }

    private static Integer retornaNumeroDeExecucaoDoHabitoLimitado(MissaoHabito entity){
        if(entity.isConcluida()){
            return entity.getExecucoesNecessarias();
        } else {
            return entity.getHabito().getExecucoes();
        }
    }
}

package br.com.cwi.api.factories;

import br.com.cwi.crescer.api.controller.response.MissaoResponse;
import br.com.cwi.crescer.api.domain.MissaoAfazer;
import br.com.cwi.crescer.api.domain.MissaoDiaria;
import br.com.cwi.crescer.api.domain.MissaoHabito;
import br.com.cwi.crescer.api.mapper.MissaoResponseMapper;

import java.util.List;

public class MissaoFactory {

    public static MissaoAfazer getMissaoAfazer() {
        return MissaoAfazer.builder()
                .id(SimpleFactory.getRandomLong())
                .nome("Teste")
                .descricao("Missao teste")
                .concluida(false)
                .afazer(AfazerFactory.getAfazerFacil())
                .build();
    }


    public static MissaoHabito getMissaoHabito() {
        return MissaoHabito.builder()
                .id(SimpleFactory.getRandomLong())
                .nome("Teste")
                .descricao("Missao teste")
                .execucoesNecessarias(2)
                .concluida(false)
                .habito(HabitoFactory.getHabitoFacil())
                .build();
    }

    public static MissaoDiaria getMissaoDiaria() {
        return MissaoDiaria.builder()
                .id(SimpleFactory.getRandomLong())
                .nome("Teste")
                .descricao("Missao teste")
                .concluida(false)
                .diaria(DiariaFactory.getDiariaFacil())
                .build();
    }


    public static List<MissaoResponse> getListaResponse() {
        return List.of(
                MissaoResponseMapper.toResponse(getMissaoAfazer()),
                MissaoResponseMapper.toResponse(getMissaoHabito()),
                MissaoResponseMapper.toResponse(getMissaoDiaria())
        );
    }

    public static List<MissaoResponse> getListaVazia() {
        return List.of();
    }



}

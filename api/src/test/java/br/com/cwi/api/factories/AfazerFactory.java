package br.com.cwi.api.factories;

import br.com.cwi.crescer.api.domain.Afazer;

import java.util.List;

import static br.com.cwi.crescer.api.domain.Dificuldade.FACIL;

public class AfazerFactory {

    public static Afazer getAfazerFacil() {
        return Afazer.builder()
                .id(SimpleFactory.getRandomLong())
                .nome("Teste")
                .descricao("Descrição Teste")
                .dificuldade(FACIL)
                .finalizado(false)
                .build();
    }

    public static Afazer getAfazerFinalizado() {
        return Afazer.builder()
                .id(SimpleFactory.getRandomLong())
                .nome("Teste")
                .descricao("Descrição Teste")
                .dificuldade(FACIL)
                .finalizado(true)
                .build();
    }

    public static List<Afazer> getLista() {
        return List.of(
                getAfazerFacil(), getAfazerFacil(), getAfazerFacil()
        );
    }

    public static List<Afazer> getListaVazia() {
        return List.of();
    }



}

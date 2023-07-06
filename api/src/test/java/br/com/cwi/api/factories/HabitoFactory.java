package br.com.cwi.api.factories;

import br.com.cwi.crescer.api.domain.Habito;

import java.util.List;

import static br.com.cwi.crescer.api.domain.Dificuldade.FACIL;

public class HabitoFactory {

    public static Habito getHabitoFacil() {
        return Habito.builder()
                .id(SimpleFactory.getRandomLong())
                .nome("Teste")
                .descricao("Descrição Teste")
                .dificuldade(FACIL)
                .execucoes(0)
                .build();
    }
    public static Habito getHabitoExecutado() {
        return Habito.builder()
                .id(SimpleFactory.getRandomLong())
                .nome("Teste")
                .descricao("Descrição Teste")
                .dificuldade(FACIL)
                .execucoes(1)
                .build();
    }
    public static List<Habito> getLista() {
        return List.of(
                getHabitoFacil(), getHabitoExecutado(), getHabitoExecutado()
        );
    }

    public static List<Habito> getListaVazia() {
        return List.of();
    }



}

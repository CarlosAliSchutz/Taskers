package br.com.cwi.api.factories;

import br.com.cwi.crescer.api.domain.Diaria;

import java.time.LocalTime;
import java.util.List;

import static br.com.cwi.crescer.api.domain.Dificuldade.FACIL;

public class DiariaFactory {

    public static Diaria getDiariaFacil() {
        return Diaria.builder()
                .id(SimpleFactory.getRandomLong())
                .nome("Teste")
                .descricao("Descrição Teste")
                .dificuldade(FACIL)
                .finalizado(false)
                .hora(LocalTime.now())
                .build();
    }

    public static Diaria getDiariaFinalizado() {
        return Diaria.builder()
                .id(SimpleFactory.getRandomLong())
                .nome("Teste")
                .descricao("Descrição Teste")
                .dificuldade(FACIL)
                .finalizado(true)
                .hora(LocalTime.now())
                .build();
    }

    public static List<Diaria> getLista() {
        return List.of(
                getDiariaFacil(), getDiariaFacil(), getDiariaFinalizado()
        );
    }

    public static List<Diaria> getListaVazia() {
        return List.of();
    }


}

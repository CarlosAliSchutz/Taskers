package br.com.cwi.api.factories;

import br.com.cwi.crescer.api.domain.Conquista;
import br.com.cwi.crescer.api.domain.TipoConquista;

import java.util.List;

import static br.com.cwi.crescer.api.domain.Dificuldade.*;

public class ConquistaFactory {

    public static Conquista getConquistaFacil() {
        return Conquista.builder()
                .id(SimpleFactory.getRandomLong())
                .objetivo(5)
                .tipo(TipoConquista.INCLUIR_AFAZERES)
                .descricao("Conquista teste facil")
                .dificuldade(FACIL)
                .build();
    }

    public static Conquista getConquistaMedia() {
        return Conquista.builder()
                .id(SimpleFactory.getRandomLong())
                .objetivo(5)
                .tipo(TipoConquista.COMPRAR_COSMETICO)
                .descricao("Conquista teste media")
                .dificuldade(MEDIO)
                .build();
    }

    public static Conquista getConquistaDificil() {
        return Conquista.builder()
                .id(SimpleFactory.getRandomLong())
                .objetivo(5)
                .tipo(TipoConquista.INCLUIR_DIARIAS)
                .descricao("Conquista teste dificil")
                .dificuldade(DIFICIL)
                .build();
    }

    public static List<Conquista> getLista() {
        return List.of(
                getConquistaFacil(), getConquistaFacil(), getConquistaDificil()
        );
    }

    public static List<Conquista> getListaVazia() {
        return List.of();
    }



}

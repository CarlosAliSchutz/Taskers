package br.com.cwi.api.factories;

import br.com.cwi.crescer.api.domain.UsuarioConquista;

import java.util.List;

public class UsuarioConquistaFactory {

    public static UsuarioConquista getUsuarioConquistaInconcluida() {
        return UsuarioConquista.builder()
                .id(SimpleFactory.getRandomLong())
                .conquista(ConquistaFactory.getConquistaMedia())
                .concluida(false)
                .progresso(1)
                .build();
    }

    public static UsuarioConquista getUsuarioConquistaConcluida() {
        return UsuarioConquista.builder()
                .id(SimpleFactory.getRandomLong())
                .conquista(ConquistaFactory.getConquistaMedia())
                .concluida(true)
                .progresso(5)
                .build();
    }


    public static List<UsuarioConquista> getLista() {
        return List.of(
                getUsuarioConquistaInconcluida(), getUsuarioConquistaInconcluida(), getUsuarioConquistaConcluida()
        );
    }

    public static List<UsuarioConquista> getListaVazia() {
        return List.of();
    }



}

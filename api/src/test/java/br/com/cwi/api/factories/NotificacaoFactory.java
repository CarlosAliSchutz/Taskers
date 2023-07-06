package br.com.cwi.api.factories;

import br.com.cwi.crescer.api.domain.Notificacao;

import java.util.List;


public class NotificacaoFactory {

    public static Notificacao getNotificacaoAtiva() {
        return Notificacao.builder()
                .id(SimpleFactory.getRandomLong())
                .titulo("Teste")
                .texto("Descrição Teste")
                .ativo(true)
                .build();
    }

    public static Notificacao getNotificacaoInativa() {
        return Notificacao.builder()
                .id(SimpleFactory.getRandomLong())
                .titulo("Teste inativo")
                .texto("Descrição Teste inativo")
                .ativo(false)
                .build();
    }

    public static List<Notificacao> getListaAtivas() {
        return List.of(
                getNotificacaoAtiva(), getNotificacaoAtiva(), getNotificacaoAtiva()
        );
    }

    public static List<Notificacao> getListaInativas() {
        return List.of(
                getNotificacaoInativa(), getNotificacaoInativa(), getNotificacaoInativa()
        );
    }

    public static List<Notificacao> getListaVazia() {
        return List.of();
    }



}

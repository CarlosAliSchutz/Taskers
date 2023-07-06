package br.com.cwi.api.mapper;

import br.com.cwi.api.factories.NotificacaoFactory;
import br.com.cwi.crescer.api.controller.response.NotificacaoResponse;
import br.com.cwi.crescer.api.domain.Notificacao;
import br.com.cwi.crescer.api.mapper.NotificacaoResponseMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class NotificacoaResponseMapperTest {

    @Test
    @DisplayName("Deve retornar o response quando receber uma notificacao ativa")
    void deveRetornarOResponseQuandoReceberUmaNotificacaoAtiva() {

        Notificacao notificacao = NotificacaoFactory.getNotificacaoAtiva();

        NotificacaoResponse response = NotificacaoResponseMapper.toResponse(notificacao);

        assertEquals(notificacao.getId(), response.getId());
        assertEquals(notificacao.getTexto(), response.getTexto());
        assertEquals(notificacao.getTitulo(), response.getTitulo());
    }

    @Test
    @DisplayName("Deve retornar o response quando receber uma notificacao inativa")
    void deveRetornarOResponseQuandoReceberUmaNotificacaoInativa() {

        Notificacao notificacao = NotificacaoFactory.getNotificacaoInativa();

        NotificacaoResponse response = NotificacaoResponseMapper.toResponse(notificacao);

        assertEquals(notificacao.getId(), response.getId());
        assertEquals(notificacao.getTexto(), response.getTexto());
        assertEquals(notificacao.getTitulo(), response.getTitulo());
    }

    @Test
    @DisplayName("Deve lançar erro quando notificacao for nula")
    void deveLancarErroQuandoNotificacaoForNula() {

        Assertions.assertThrows(NullPointerException.class, () -> {
            NotificacaoResponse response = NotificacaoResponseMapper.toResponse(null);
        });
    }
}

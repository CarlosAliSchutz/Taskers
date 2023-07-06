package br.com.cwi.api.service;

import br.com.cwi.api.factories.HabitoFactory;
import br.com.cwi.api.factories.NotificacaoFactory;
import br.com.cwi.crescer.api.domain.Habito;
import br.com.cwi.crescer.api.domain.Notificacao;
import br.com.cwi.crescer.api.repository.HabitoRepository;
import br.com.cwi.crescer.api.repository.NotificacaoRepository;
import br.com.cwi.crescer.api.service.DeletarHabitoService;
import br.com.cwi.crescer.api.service.DeletarNotificacaoService;
import br.com.cwi.crescer.api.service.core.BuscarHabitoService;
import br.com.cwi.crescer.api.service.core.BuscarNotificacaoService;
import br.com.cwi.crescer.api.service.core.ValidarProprietarioService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DeletarNotificacaoServiceTest {

    @InjectMocks
    private DeletarNotificacaoService tested;
    @Mock
    private NotificacaoRepository notificacaoRepository;
    @Mock
    private BuscarNotificacaoService buscarNotificacaoService;
    @Mock
    private ValidarProprietarioService validarProprietarioService;

    @Test
    @DisplayName("Deve deletar notificacao")
    void deveDeletarNotificacao() {
        Notificacao notificacao = NotificacaoFactory.getNotificacaoAtiva();

        when(buscarNotificacaoService.porId(notificacao.getId())).thenReturn(notificacao);

        tested.deletar(notificacao.getId());

        verify(buscarNotificacaoService).porId(notificacao.getId());
        verify(validarProprietarioService).daNotificacao(notificacao);
        verify(notificacaoRepository).save(notificacao);

        assertFalse(notificacao.isAtivo());
    }

    @Test
    @DisplayName("Não deve deletar caso não encontre a notificacao")
    void naoDeveDeletarNaoEncontre() {
        Notificacao notificacao = NotificacaoFactory.getNotificacaoAtiva();

        doThrow(ResponseStatusException.class)
                .when(buscarNotificacaoService).porId(notificacao.getId());


        assertThrows(ResponseStatusException.class, () -> {
            tested.deletar(notificacao.getId());
        });

        verify(buscarNotificacaoService).porId(notificacao.getId());
        verify(validarProprietarioService, never()).daNotificacao(notificacao);
        verify(notificacaoRepository, never()).save(notificacao);
    }

    @Test
    @DisplayName("Não deve deletar caso não seja o proprietário da notificacao")
    void naoDeveDeletarCasoProprietarioErrado() {
        Notificacao notificacao = NotificacaoFactory.getNotificacaoAtiva();


        when(buscarNotificacaoService.porId(notificacao.getId())).thenReturn(notificacao);
        doThrow(ResponseStatusException.class)
                .when(validarProprietarioService).daNotificacao(notificacao);

        assertThrows(ResponseStatusException.class, () -> {
            tested.deletar(notificacao.getId());
        });

        verify(buscarNotificacaoService).porId(notificacao.getId());
        verify(validarProprietarioService).daNotificacao(notificacao);
        verify(notificacaoRepository, never()).save(notificacao);
    }

}

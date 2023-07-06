package br.com.cwi.api.service.core;

import br.com.cwi.api.factories.DiariaFactory;
import br.com.cwi.api.factories.NotificacaoFactory;
import br.com.cwi.crescer.api.domain.Diaria;
import br.com.cwi.crescer.api.domain.Notificacao;
import br.com.cwi.crescer.api.repository.DiariaRepository;
import br.com.cwi.crescer.api.repository.NotificacaoRepository;
import br.com.cwi.crescer.api.service.core.BuscarDiariaService;
import br.com.cwi.crescer.api.service.core.BuscarNotificacaoService;
import br.com.cwi.crescer.api.validator.FoiRemovidoValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BuscarNotificacaoServiceTest {

    @InjectMocks
    private BuscarNotificacaoService tested;

    @Mock
    private NotificacaoRepository notificacaoRepository;

    @Mock
    private FoiRemovidoValidator foiRemovidoValidator;

    @Test
    @DisplayName("Deve buscar diaria por id")
    void deveRetornarDiaria() {
        Notificacao notificacao = NotificacaoFactory.getNotificacaoAtiva();
        when(notificacaoRepository.findById(notificacao.getId())).thenReturn(Optional.of(notificacao));

        Notificacao retorno = tested.porId(notificacao.getId());

        verify(foiRemovidoValidator).validar(notificacao.isAtivo());
        verify(notificacaoRepository).findById(notificacao.getId());
        assertEquals(notificacao, retorno);
    }

    @Test
    @DisplayName("Deve retornar erro quando não encontrar diaria")
    void deveRetornarErro() {

        ResponseStatusException exception =
                assertThrows(ResponseStatusException.class, () -> tested.porId(1L));

        assertEquals("Notificação não encontrada", exception.getReason());
    }

}

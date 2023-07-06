package br.com.cwi.api.service;

import br.com.cwi.api.factories.NotificacaoFactory;
import br.com.cwi.api.factories.UsuarioFactory;
import br.com.cwi.crescer.api.controller.response.NotificacaoResponse;
import br.com.cwi.crescer.api.domain.Notificacao;
import br.com.cwi.crescer.api.repository.NotificacaoRepository;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.security.service.UsuarioAutenticadoService;
import br.com.cwi.crescer.api.service.ListarNotificacoesService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ListarNotificacoesServiceTest {

    @InjectMocks
    private ListarNotificacoesService tested;

    @Mock
    private UsuarioAutenticadoService autenticadoService;

    @Mock
    private NotificacaoRepository notificacaoRepository;

    @Test
    @DisplayName("Deve listar todas as missoes do usuario")
    void deveListarTodasAsNotificacaoAtivasDoUsuario() {
        Usuario usuario = UsuarioFactory.getUsuario();
        Pageable pageable = PageRequest.of(0,5);
        List<Notificacao> notificacaos = NotificacaoFactory.getListaAtivas();
        Page<Notificacao> notificacaosaginadas = new PageImpl<>(notificacaos);

        when(autenticadoService.getId()).thenReturn(usuario.getId());
        when(notificacaoRepository.findAllByAtivoAndProprietarioId(true, usuario.getId(), pageable)).thenReturn(notificacaosaginadas);

        Page<NotificacaoResponse> response = tested.listar(pageable);

        verify(notificacaoRepository).findAllByAtivoAndProprietarioId(true, usuario.getId(), pageable);
        verify(autenticadoService).getId();

        assertEquals(notificacaos.size(), response.getSize());
        assertEquals(notificacaos.get(0).getId(), response.getContent().get(0).getId());
        assertEquals(notificacaos.get(1).getId(), response.getContent().get(1).getId());
        assertEquals(notificacaos.get(2).getId(), response.getContent().get(2).getId());
    }

    @Test
    @DisplayName("Não Deve listar todas as notificacoes do usuario quando forem inativas")
    void naoDeveListarTodasAsNotificacaoInativasDoUsuario() {
        Usuario usuario = UsuarioFactory.getUsuario();
        Pageable pageable = PageRequest.of(0,5);
        List<Notificacao> notificacaos = NotificacaoFactory.getListaInativas();
        Page<Notificacao> notificacaosVazia = new PageImpl<>(NotificacaoFactory.getListaVazia());

        when(autenticadoService.getId()).thenReturn(usuario.getId());
        when(notificacaoRepository.findAllByAtivoAndProprietarioId(true, usuario.getId(), pageable)).thenReturn(notificacaosVazia);

        Page<NotificacaoResponse> response = tested.listar(pageable);

        verify(notificacaoRepository).findAllByAtivoAndProprietarioId(true, usuario.getId(), pageable);
        verify(autenticadoService).getId();

        assertEquals(3, notificacaos.size());
        assertEquals(0, response.getSize());
    }
}

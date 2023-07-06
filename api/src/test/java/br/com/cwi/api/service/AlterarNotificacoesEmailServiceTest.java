package br.com.cwi.api.service;

import br.com.cwi.api.factories.UsuarioFactory;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.security.repository.UsuarioRepository;
import br.com.cwi.crescer.api.security.service.UsuarioAutenticadoService;
import br.com.cwi.crescer.api.service.AlterarNotificacoesEmailService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AlterarNotificacoesEmailServiceTest {

    @InjectMocks
    private AlterarNotificacoesEmailService tested;

    @Mock
    private UsuarioAutenticadoService usuarioAutenticadoService;

    @Mock
    private UsuarioRepository usuarioRepository;
    @Captor
    private ArgumentCaptor<Usuario> usuarioCaptor;
    @Test
    @DisplayName("Deve alterar a preferencia de notificacao para false quando for true")
    void deveAlterarAPreferenciaDeNotificacaoParaFalseQuandoForTrue() {
        Usuario usuario = UsuarioFactory.getUsuario();

        when(usuarioAutenticadoService.get()).thenReturn(usuario);

        tested.alterar();

        verify(usuarioAutenticadoService).get();
        verify(usuarioRepository).save(usuarioCaptor.capture());

        Usuario usuarioSalvo = usuarioCaptor.getValue();

        assertEquals(usuario.getId(), usuarioSalvo.getId());
        assertEquals(usuario.getEmail(), usuarioSalvo.getEmail());
        assertFalse(usuarioSalvo.isNotificacoesEmail());
    }

    @Test
    @DisplayName("Deve alterar a preferencia de notificacao para true quando for false")
    void deveAlterarAPreferenciaDeNotificacaoParaTrueQuandoForFalse() {
        Usuario usuario = UsuarioFactory.getUsuarioComXP();

        when(usuarioAutenticadoService.get()).thenReturn(usuario);

        tested.alterar();

        verify(usuarioAutenticadoService).get();
        verify(usuarioRepository).save(usuarioCaptor.capture());

        Usuario usuarioSalvo = usuarioCaptor.getValue();

        assertEquals(usuario.getId(), usuarioSalvo.getId());
        assertEquals(usuario.getEmail(), usuarioSalvo.getEmail());
        assertTrue(usuarioSalvo.isNotificacoesEmail());
    }
}

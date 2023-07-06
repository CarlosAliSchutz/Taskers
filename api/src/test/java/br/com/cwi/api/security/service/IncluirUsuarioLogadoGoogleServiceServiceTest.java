package br.com.cwi.api.security.service;


import br.com.cwi.crescer.api.security.controller.request.IncluirUsuarioRequest;
import br.com.cwi.crescer.api.security.domain.Provider;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.security.repository.UsuarioRepository;
import br.com.cwi.crescer.api.security.service.IncluirUsuarioLogadoGoogleService;
import br.com.cwi.crescer.api.security.service.IncluirUsuarioService;
import br.com.cwi.crescer.api.security.service.core.GerarSenhaCriptografadaService;
import br.com.cwi.crescer.api.service.IncluirConquistasPadraoUsuarioService;
import br.com.cwi.crescer.api.service.IncluirCosmeticoPadraoUsuarioService;
import org.aspectj.apache.bcel.classfile.Module;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class IncluirUsuarioLogadoGoogleServiceServiceTest {

    @InjectMocks
    private IncluirUsuarioLogadoGoogleService tested;

    @Mock
    private UsuarioRepository usuarioRepository;
    @Mock
    private IncluirCosmeticoPadraoUsuarioService incluirCosmeticoPadraoUsuarioService;
    @Mock
    private IncluirConquistasPadraoUsuarioService incluirConquistasPadraoUsuarioService;

    @Captor
    private ArgumentCaptor<Usuario> usuarioCaptor;

    @Test
    @DisplayName("Deve incluir usuario logado com o google completo")
    void deveIncluirUsuarioCompleto() {
        String nome = "nome teste";
        String email = "email@teste.com";
        String foto = "imagem teste";

        when(usuarioRepository.existsByEmail(email)).thenReturn(false);

        tested.incluir(nome, email, foto);

        verify(usuarioRepository).existsByEmail(email);
        verify(usuarioRepository).save(usuarioCaptor.capture());

        Usuario usuarioSalvo = usuarioCaptor.getValue();

        verify(incluirCosmeticoPadraoUsuarioService).incluir(usuarioSalvo);
        verify(incluirConquistasPadraoUsuarioService).incluir(usuarioSalvo);

        assertEquals(nome, usuarioSalvo.getNomeCompleto());
        assertEquals(foto, usuarioSalvo.getImagemPerfil());
        assertEquals(email, usuarioSalvo.getEmail());

        assertEquals(0, usuarioSalvo.getTaskcoin());
        assertEquals(0, usuarioSalvo.getExperiencia());
        assertTrue(usuarioSalvo.isAtivo());
        assertEquals(Provider.GOOGLE, usuarioSalvo.getProvider());
        assertTrue(usuarioSalvo.isNotificacoesEmail());
    }

    @Test
    @DisplayName("Nao Deve Incluir O Usuario Quando Ja Existir")
    void naoDeveIncluirOUsuarioQuandoJaExistir() {
        String nome = "nome teste";
        String email = "email@teste.com";
        String foto = "imagem teste";

        when(usuarioRepository.existsByEmail(email)).thenReturn(true);

        tested.incluir(nome, email, foto);

        verify(usuarioRepository).existsByEmail(email);

        verify(usuarioRepository, never()).save(any());
        verify(incluirCosmeticoPadraoUsuarioService, never()).incluir(any());
        verify(incluirConquistasPadraoUsuarioService, never()).incluir(any());
    }

}

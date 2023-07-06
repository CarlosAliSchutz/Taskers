package br.com.cwi.api.security.service;


import br.com.cwi.crescer.api.security.controller.request.IncluirUsuarioRequest;
import br.com.cwi.crescer.api.security.domain.Provider;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.security.repository.UsuarioRepository;
import br.com.cwi.crescer.api.security.service.IncluirUsuarioService;
import br.com.cwi.crescer.api.security.service.core.GerarSenhaCriptografadaService;
import br.com.cwi.crescer.api.service.IncluirConquistasPadraoUsuarioService;
import br.com.cwi.crescer.api.service.IncluirCosmeticoPadraoUsuarioService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class IncluirUsuarioServiceTest {

    @InjectMocks
    private IncluirUsuarioService tested;

    @Mock
    private UsuarioRepository usuarioRepository;
    @Mock
    private GerarSenhaCriptografadaService gerarSenhaCriptografadaService;
    @Mock
    private IncluirCosmeticoPadraoUsuarioService incluirCosmeticoPadraoUsuarioService;
    @Mock
    private IncluirConquistasPadraoUsuarioService incluirConquistasPadraoUsuarioService;

    @Captor
    private ArgumentCaptor<Usuario> usuarioCaptor;

    @Test
    @DisplayName("Deve incluir usuario completo")
    void deveIncluirUsuarioCompleto() {
        IncluirUsuarioRequest request = new IncluirUsuarioRequest();
        request.setSenha("123");
        request.setNomeCompleto("Usuario teste");
        request.setEmail("email@teste");
        request.setImagemPerfil("imagem teste");
        String senhaCriptografada = "$2a$10$ck.Fx6TIYOLYyt5hMj1kf.GdZ8GJdZKWHjZgLPYGchm71svnfZKYO";

        when(usuarioRepository.existsByEmail(request.getEmail())).thenReturn(false);
        when(gerarSenhaCriptografadaService.getSenhaCriptografada(request.getSenha())).thenReturn(senhaCriptografada);

        tested.incluir(request);

        verify(usuarioRepository).existsByEmail(request.getEmail());
        verify(gerarSenhaCriptografadaService).getSenhaCriptografada(request.getSenha());
        verify(usuarioRepository).save(usuarioCaptor.capture());

        Usuario usuarioSalvo = usuarioCaptor.getValue();

        verify(incluirCosmeticoPadraoUsuarioService).incluir(usuarioSalvo);
        verify(incluirConquistasPadraoUsuarioService).incluir(usuarioSalvo);

        assertEquals(request.getNomeCompleto(), usuarioSalvo.getNomeCompleto());
        assertEquals(request.getImagemPerfil(), usuarioSalvo.getImagemPerfil());
        assertEquals(request.getEmail(), usuarioSalvo.getEmail());
        assertEquals(senhaCriptografada, usuarioSalvo.getSenha());
        assertEquals(0, usuarioSalvo.getTaskcoin());
        assertEquals(0, usuarioSalvo.getExperiencia());
        assertTrue(usuarioSalvo.isAtivo());
        assertTrue(usuarioSalvo.isNotificacoesEmail());
        assertEquals(Provider.LOCAL, usuarioSalvo.getProvider());
    }

    @Test
    @DisplayName("Deve incluir usuario sem imagem")
    void deveIncluirUsuarioSemImagem() {
        IncluirUsuarioRequest request = new IncluirUsuarioRequest();
        request.setSenha("123");
        request.setNomeCompleto("Usuario teste");
        request.setEmail("email@teste");
        String senhaCriptografada = "$2a$10$ck.Fx6TIYOLYyt5hMj1kf.GdZ8GJdZKWHjZgLPYGchm71svnfZKYO";
        Integer valorInicial = 0;

        when(usuarioRepository.existsByEmail(request.getEmail())).thenReturn(false);
        when(gerarSenhaCriptografadaService.getSenhaCriptografada(request.getSenha())).thenReturn(senhaCriptografada);

        tested.incluir(request);

        verify(usuarioRepository).existsByEmail(request.getEmail());
        verify(gerarSenhaCriptografadaService).getSenhaCriptografada(request.getSenha());
        verify(usuarioRepository).save(usuarioCaptor.capture());

        Usuario usuarioSalvo = usuarioCaptor.getValue();

        verify(incluirCosmeticoPadraoUsuarioService).incluir(usuarioSalvo);
        verify(incluirConquistasPadraoUsuarioService).incluir(usuarioSalvo);

        assertEquals(request.getNomeCompleto(), usuarioSalvo.getNomeCompleto());
        assertEquals(request.getImagemPerfil(), usuarioSalvo.getImagemPerfil());
        assertEquals(request.getEmail(), usuarioSalvo.getEmail());
        assertEquals(senhaCriptografada, usuarioSalvo.getSenha());
        assertEquals(0, usuarioSalvo.getTaskcoin());
        assertEquals(0, usuarioSalvo.getExperiencia());
        assertTrue(usuarioSalvo.isAtivo());
        assertTrue(usuarioSalvo.isNotificacoesEmail());
        assertEquals(Provider.LOCAL, usuarioSalvo.getProvider());
    }


    @Test
    @DisplayName("Nao Deve Incluir Usuario Com Email Ja Existente")
    void naoDeveIncluirUsuarioComEmailJaExistente() {
        IncluirUsuarioRequest request = new IncluirUsuarioRequest();
        request.setEmail("email@teste");
        request.setSenha("123");
        request.setNomeCompleto("Usuario teste");

        when(usuarioRepository.existsByEmail(request.getEmail())).thenReturn(true);

        assertThrows(ResponseStatusException.class, () -> {
            tested.incluir(request);
        });

        verify(usuarioRepository).existsByEmail(request.getEmail());
        verify(usuarioRepository, Mockito.never()).save(Mockito.any());
        verify(gerarSenhaCriptografadaService, never()).getSenhaCriptografada(request.getSenha());
        verify(incluirCosmeticoPadraoUsuarioService, never()).incluir(Mockito.any());
        verify(incluirConquistasPadraoUsuarioService, never()).incluir(any());

    }
}

package br.com.cwi.api.security.service;

import br.com.cwi.api.factories.UsuarioFactory;
import br.com.cwi.crescer.api.security.controller.request.EditarSenhaEsquecidaRequest;
import br.com.cwi.crescer.api.security.controller.request.EditarSenhaRequest;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.security.repository.UsuarioRepository;
import br.com.cwi.crescer.api.security.service.EditarSenhaUsuarioService;
import br.com.cwi.crescer.api.security.service.UsuarioAutenticadoService;
import br.com.cwi.crescer.api.security.service.core.GerarSenhaCriptografadaService;
import br.com.cwi.crescer.api.security.validator.UsuarioProviderGoogleValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EditarSenhaUsuarioServiceTest {

    @InjectMocks
    private EditarSenhaUsuarioService tested;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private GerarSenhaCriptografadaService gerarSenhaCriptografadaService;

    @Mock
    private UsuarioAutenticadoService usuarioAutenticadoService;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private UsuarioProviderGoogleValidator usuarioProviderGoogleValidator;

    @Captor
    private ArgumentCaptor<Usuario> usuarioCaptor;

    @Test
    @DisplayName("Deve alterar a senha do usuario")
    void deveAlterarASenhaDoUsuario() {
        Usuario usuario = UsuarioFactory.getUsuarioComXP();
        Usuario usuarioAntigo = UsuarioFactory.getUsuarioComXP();
        EditarSenhaRequest request = new EditarSenhaRequest();
        request.setSenhaAtual("123");
        request.setNovaSenha("nova senha");
        String senhaCriptografada = "$2a$10$ck.Fx6TIYOLYyt5hMj1kf.GdZ8GJdZKWHjZgLPYGchm71svnfZKYO";

        when(usuarioAutenticadoService.get()).thenReturn(usuario);
        when(passwordEncoder.matches(request.getSenhaAtual(), usuario.getSenha())).thenReturn(true);
        when(gerarSenhaCriptografadaService.getSenhaCriptografada(request.getNovaSenha())).thenReturn(senhaCriptografada);

        tested.editar(request);

        verify(usuarioAutenticadoService).get();
        verify(usuarioProviderGoogleValidator).validar(usuario);
        verify(passwordEncoder).matches(request.getSenhaAtual(), usuarioAntigo.getSenha());
        verify(gerarSenhaCriptografadaService).getSenhaCriptografada(request.getNovaSenha());
        verify(usuarioRepository).save(usuarioCaptor.capture());

        Usuario usuarioSalvo = usuarioCaptor.getValue();

        assertEquals(usuarioSalvo.getNomeCompleto(), usuario.getNomeCompleto());
        assertEquals(usuarioSalvo.getEmail(), usuario.getEmail());
        assertEquals(usuarioSalvo.getImagemPerfil(), usuario.getImagemPerfil());
        assertEquals(usuarioSalvo.getSenha(), usuario.getSenha());
    }

    @Test
    @DisplayName("Nao Deve Alterar A Senha Do Usuario Quando For Provider Google")
    void naoDeveAlterarASenhaDoUsuarioQuandoForProviderGoogle() {
        Usuario usuario = UsuarioFactory.getUsuario();
        EditarSenhaRequest request = new EditarSenhaRequest();
        request.setSenhaAtual("123");
        request.setNovaSenha("nova senha");

        when(usuarioAutenticadoService.get()).thenReturn(usuario);

        doThrow(ResponseStatusException.class)
                .when(usuarioProviderGoogleValidator).validar(usuario);

        assertThrows(ResponseStatusException.class, () -> {
            tested.editar(request);
        });

        verify(usuarioAutenticadoService).get();
        verify(usuarioProviderGoogleValidator).validar(usuario);
        verify(gerarSenhaCriptografadaService, never()).getSenhaCriptografada(request.getNovaSenha());
        verify(passwordEncoder, never()).matches(request.getSenhaAtual(), usuario.getSenha());
        verify(usuarioRepository, never()).save(any());
    }

    @Test
    @DisplayName("Nao Deve Alterar A Senha Do Usuario Quando A Senha Atual Estiver Incorreta")
    void naoDeveAlterarASenhaDoUsuarioQuandoASenhaAtualEstiverIncorreta() {
        Usuario usuario = UsuarioFactory.getUsuario();
        EditarSenhaRequest request = new EditarSenhaRequest();
        request.setSenhaAtual("1235");
        request.setNovaSenha("nova senha");

        when(usuarioAutenticadoService.get()).thenReturn(usuario);
        when(passwordEncoder.matches(request.getSenhaAtual(), usuario.getSenha())).thenReturn(false);

        assertThrows(ResponseStatusException.class, () -> {
            tested.editar(request);
        });

        verify(usuarioAutenticadoService).get();
        verify(usuarioProviderGoogleValidator).validar(usuario);
        verify(passwordEncoder).matches(request.getSenhaAtual(), usuario.getSenha());
        verify(gerarSenhaCriptografadaService, never()).getSenhaCriptografada(request.getNovaSenha());
        verify(usuarioRepository, never()).save(any());
    }
}

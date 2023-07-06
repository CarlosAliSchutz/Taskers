package br.com.cwi.api.security.service;

import br.com.cwi.api.factories.UsuarioFactory;
import br.com.cwi.crescer.api.security.controller.request.EditarUsuarioRequest;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.security.repository.UsuarioRepository;
import br.com.cwi.crescer.api.security.service.EditarUsuarioService;
import br.com.cwi.crescer.api.security.service.UsuarioAutenticadoService;
import br.com.cwi.crescer.api.security.validator.UsuarioProviderGoogleValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EditarUsuarioServiceTest {

    @InjectMocks
    private EditarUsuarioService tested;

    @Mock
    private UsuarioAutenticadoService usuarioAutenticadoService;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private UsuarioProviderGoogleValidator usuarioProviderGoogleValidator;

    @Captor
    private ArgumentCaptor<Usuario> usuarioCaptor;

    @Test
    @DisplayName("Deve alterar o usuario")
    void deveAlterarOUsuario() {
        Usuario usuario = UsuarioFactory.getUsuarioComXP();
        EditarUsuarioRequest request = new EditarUsuarioRequest();
        request.setImagemPerfil("Imagem editado");
        request.setEmail("email@editado");
        request.setNomeCompleto("Nome editado");

        when(usuarioAutenticadoService.get()).thenReturn(usuario);

        tested.editar(request);

        verify(usuarioAutenticadoService).get();
        verify(usuarioProviderGoogleValidator).validar(usuario);
        verify(usuarioRepository).save(usuarioCaptor.capture());

        Usuario usuarioSalvo = usuarioCaptor.getValue();

        assertEquals(usuarioSalvo.getNomeCompleto(), usuario.getNomeCompleto());
        assertEquals(usuarioSalvo.getEmail(), usuario.getEmail());
        assertEquals(usuarioSalvo.getImagemPerfil(), usuario.getImagemPerfil());
    }

    @Test
    @DisplayName("Deve alterar o usuario quando a imagem for vazia")
    void deveAlterarOUsuarioQuandoAImagemForVazia() {
        Usuario usuario = UsuarioFactory.getUsuarioComXP();
        EditarUsuarioRequest request = new EditarUsuarioRequest();
        request.setEmail("email@editado");
        request.setNomeCompleto("Nome editado");

        when(usuarioAutenticadoService.get()).thenReturn(usuario);

        tested.editar(request);

        verify(usuarioAutenticadoService).get();
        verify(usuarioProviderGoogleValidator).validar(usuario);
        verify(usuarioRepository).save(usuarioCaptor.capture());

        Usuario usuarioSalvo = usuarioCaptor.getValue();

        assertEquals(usuarioSalvo.getNomeCompleto(), usuario.getNomeCompleto());
        assertEquals(usuarioSalvo.getEmail(), usuario.getEmail());
        assertNull(usuarioSalvo.getImagemPerfil());
    }

    @Test
    @DisplayName("Nao Deve Alterar O Usuario Quando O Provider For Google")
    void naoDeveAlterarOUsuarioQuandoOProviderForGoogle() {
        Usuario usuario = UsuarioFactory.getUsuario();
        EditarUsuarioRequest request = new EditarUsuarioRequest();
        request.setEmail("email@editado");
        request.setNomeCompleto("Nome editado");

        when(usuarioAutenticadoService.get()).thenReturn(usuario);

        doThrow(ResponseStatusException.class)
                .when(usuarioProviderGoogleValidator).validar(usuario);

        assertThrows(ResponseStatusException.class, () -> {
            tested.editar(request);
        });

        verify(usuarioAutenticadoService).get();
        verify(usuarioProviderGoogleValidator).validar(usuario);
        verify(usuarioRepository, never()).save(usuarioCaptor.capture());
    }
}

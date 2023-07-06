package br.com.cwi.api.security.service.core;

import br.com.cwi.api.factories.UsuarioFactory;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.security.repository.UsuarioRepository;
import br.com.cwi.crescer.api.security.service.core.BuscarUsuarioService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BuscarUsuarioServiceTest {

    @InjectMocks
    private BuscarUsuarioService tested;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Test
    @DisplayName("Deve buscar usuario por email")
    void deveRetornarUsuario() {
        Usuario usuario = UsuarioFactory.getUsuario();

        when(usuarioRepository.findByEmail(usuario.getEmail())).thenReturn(Optional.of(usuario));

        Usuario retorno = tested.buscarPorEmail(usuario.getEmail());

        verify(usuarioRepository).findByEmail(usuario.getEmail());
        assertEquals(usuario, retorno);
    }

    @Test
    @DisplayName("Deve retornar erro quando não encontrar usuario")
    void deveRetornarErro() {

        ResponseStatusException exception =
                assertThrows(ResponseStatusException.class, () -> tested.buscarPorEmail("emailTeste@gmail"));

        assertEquals("Usuário não encontrado", exception.getReason());
    }

}

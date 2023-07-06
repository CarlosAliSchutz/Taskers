package br.com.cwi.api.mapper;

import br.com.cwi.api.factories.UsuarioConquistaFactory;
import br.com.cwi.api.factories.UsuarioFactory;
import br.com.cwi.crescer.api.controller.response.UsuarioConquistaResponse;
import br.com.cwi.crescer.api.domain.UsuarioConquista;
import br.com.cwi.crescer.api.mapper.UsuarioConquistaResponseMapper;
import br.com.cwi.crescer.api.security.domain.Usuario;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class UsuarioConquistaResponseMapperTest {

    @Test
    @DisplayName("Deve retornar o response quando receber uma conquista concluida")
    void deveRetornarOResponseQuandoReceberConquistaConcluida() {
        Usuario usuario = UsuarioFactory.getUsuario();
        UsuarioConquista usuarioConquista = UsuarioConquistaFactory.getUsuarioConquistaConcluida();
        usuarioConquista.setUsuario(usuario);

        UsuarioConquistaResponse response = UsuarioConquistaResponseMapper.toResponse(usuarioConquista);

        assertEquals(usuarioConquista.getId(), response.getId());
        assertEquals(usuarioConquista.getConquista().getId(), response.getConquista().getId());
        assertEquals(usuarioConquista.getProgresso(), response.getProgresso());
    }
    @Test
    @DisplayName("Deve retornar o response quando receber uma conquista inconcluida")
    void deveRetornarOResponseQuandoReceberConquistaInconcluida() {
        Usuario usuario = UsuarioFactory.getUsuario();
        UsuarioConquista usuarioConquista = UsuarioConquistaFactory.getUsuarioConquistaInconcluida();
        usuarioConquista.setUsuario(usuario);

        UsuarioConquistaResponse response = UsuarioConquistaResponseMapper.toResponse(usuarioConquista);

        assertEquals(usuarioConquista.getId(), response.getId());
        assertEquals(usuarioConquista.getConquista().getId(), response.getConquista().getId());
        assertEquals(usuarioConquista.getProgresso(), response.getProgresso());
    }

    @Test
    @DisplayName("Deve lançar erro quando usuario conquista for nula")
    void deveLancarErroQuandoUsuarioConquistaForNula() {

        Assertions.assertThrows(NullPointerException.class, () -> {
            UsuarioConquistaResponse response = UsuarioConquistaResponseMapper.toResponse(null);
        });
    }
}

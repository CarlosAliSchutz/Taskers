package br.com.cwi.api.security.mapper;

import br.com.cwi.api.factories.UsuarioFactory;
import br.com.cwi.crescer.api.security.controller.request.IncluirUsuarioRequest;
import br.com.cwi.crescer.api.security.controller.response.UsuarioResponse;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.security.mapper.UsuarioMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class UsuarioMapperTest {


    @Test
    @DisplayName("Deve retornar o response quando receber um usuario")
    void deveRetornarResponse() {
        Usuario usuario = UsuarioFactory.getUsuario();

        UsuarioResponse response = UsuarioMapper.toResponse(usuario);

        assertEquals(usuario.getId(), response.getId());
        assertEquals(usuario.getNomeCompleto(), response.getNomeCompleto());
        assertEquals(usuario.getExperiencia(), response.getExperiencia());
        assertEquals(usuario.getTaskcoin(), response.getTaskcoin());
        assertEquals(usuario.getEmail(), response.getEmail());
        assertEquals(usuario.getImagemPerfil(), response.getImagemPerfil());
    }

    @Test
    @DisplayName("Deve retornar o response com xp quando receber um usuario com xp")
    void deveRetornarResponseComXp() {
        Usuario usuario = UsuarioFactory.getUsuarioComXP();

        UsuarioResponse response = UsuarioMapper.toResponse(usuario);

        assertEquals(usuario.getId(), response.getId());
        assertEquals(usuario.getNomeCompleto(), response.getNomeCompleto());
        assertEquals(usuario.getExperiencia(), response.getExperiencia());
        assertEquals(usuario.getTaskcoin(), response.getTaskcoin());
        assertEquals(usuario.getEmail(), response.getEmail());
        assertEquals(usuario.getImagemPerfil(), response.getImagemPerfil());
    }

    @Test
    @DisplayName("Deve lançar erro quando o usuario for nulo")
    void deveLancarErroQuandoUsuarioForNulo() {

        Assertions.assertThrows(NullPointerException.class, () -> {
            UsuarioResponse response = UsuarioMapper.toResponse(null);
        });
    }

    @Test
    @DisplayName("Deve retornar o usuario")
    void deveRetornarOUsuario() {
        IncluirUsuarioRequest request = new IncluirUsuarioRequest();
        request.setNomeCompleto("Afazer testo ");
        request.setEmail("email@teste.com");
        request.setImagemPerfil("imagem");
        request.setSenha("123");

        Usuario usuario = UsuarioMapper.toEntity(request);

        assertEquals(usuario.getNomeCompleto(), request.getNomeCompleto());
        assertEquals(usuario.getEmail(), request.getEmail());
        assertEquals(usuario.getImagemPerfil(), request.getImagemPerfil());
    }

}
